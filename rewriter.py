#!/bin/python3

# The MIT License
#
# Copyright 2022 David Kleszyk <dkleszyk@gmail.com>.
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.

import sys
sys.path.append("krakatau")

from os import remove
from os.path import abspath, join, split
from re import sub
from disassemble import disassembleSub
from assemble import assembleClass
from Krakatau.script_util import makeWriter

def disassemble(classname, classpath="", asmpath=None):
    nameparts = classname.split(".")
    packagepath, name = join(*nameparts[:-1]), nameparts[-1]
    readdir = join(classpath, packagepath)
    writedir = asmpath if asmpath is not None else readdir
    def readFile(filename):
        with open(join(readdir, filename), "rb") as f:
            return f.read()
    with makeWriter(writedir, ".j") as out:
        disassembleSub(readFile, out, targets=[name + ".class"], roundtrip=False, outputClassName=False)

def assemble(classname, asmpath="", classpath=None):
    readdir = asmpath
    writedir = classpath if classpath is not None else readdir
    with makeWriter(writedir, ".class") as out:
        pairs = assembleClass(join(readdir, classname.split(".")[-1] + ".j"))
        for name, data in pairs:
            filename = out.write(name, data)
            print("Class written to", filename)

types = (
    # u/c name, l/c name, boxed type, primitive type, type code
    ("Boolean", "boolean", "Boolean", "boolean", "Z"),
    ("Byte", "byte", "Byte", "byte", "B"),
    ("Char", "char", "Character", "char", "C"),
    ("Double", "double", "Double", "double", "D"),
    ("Float", "float", "Float", "float", "F"),
    ("Int", "int", "Integer", "int", "I"),
    ("Long", "long", "Long", "long", "J"),
    ("Short", "short", "Short", "short", "S")
)

basepath = abspath(".")
asmpath = basepath
classpath = join(basepath, "target", "classes")
srcpath = join(basepath, "src", "main", "java")

# Patch bridge methods for 'orElse' in specialized impls so ClassCastException doesn't get thrown.
#
# Parameter that gets checkcast'ed is unused, so we can eliminate cast by replacing method body
# with implementation from non-bridged method
for u in (t[0] for t in types):
    opt = "Some" + u
    cls = "me.dkleszyk.java.option." + opt
    disassemble(cls, classpath, asmpath)
    asmfile = join(asmpath, opt + ".j")
    with open(asmfile, "r") as f:
        lines = f.read().split("\n")

    implStartIdx = next((idx for idx, line in enumerate(lines) if line.startswith(".method public orElse :")))
    implEndIdx = next((idx for idx, line in enumerate(lines) if idx > implStartIdx and line == ".end method "))
    implLineNoIdx = next((idx for idx, line in enumerate(lines) if idx > implStartIdx and idx < implEndIdx and line.startswith("            L0 ")))
    implArgIdx = next((idx for idx, line in enumerate(lines) if idx > implStartIdx and idx < implEndIdx and line.startswith("            1 is value")))
    bridgeStartIdx = next((idx for idx, line in enumerate(lines) if line.startswith(".method public bridge synthetic orElse :")))
    bridgeEndIdx = next((idx for idx, line in enumerate(lines) if idx > bridgeStartIdx and line == ".end method "))
    bridgeLineNoIdx = next((idx for idx, line in enumerate(lines) if idx > bridgeStartIdx and idx < bridgeEndIdx and line.startswith("            L0 ")))

    impl = lines[(implStartIdx + 1):(implEndIdx - 1)]
    impl[implLineNoIdx - (implStartIdx + 1)] = lines[bridgeLineNoIdx]
    impl[implArgIdx - (implStartIdx + 1)] = sub(r"^( +1 is value L).+(; from L\d+ to L\d+ )$", r"\1java/lang/Object\2", impl[implArgIdx - (implStartIdx + 1)])

    lines = lines[:(bridgeStartIdx + 1)] + impl + lines[(bridgeEndIdx - 1):]
    with open(asmfile, "w") as f:
        f.write("\n".join(lines))
    assemble(cls, asmpath, classpath)
    remove(asmfile)

# Patch 'None' to add impls of specialized option interfaces,
# as well as necessary bridge methods
#
# Collect methods we need to implement
methods = []
for u in [""] + [t[0] for t in types]:
    opt = u + "Option"
    cls = "me.dkleszyk.java.option." + opt
    disassemble(cls, classpath, asmpath)
    asmfile = join(asmpath, opt + ".j")
    with open(asmfile, "r") as f:
        lines = f.read().split("\n")
    method = None
    for line in lines:
        if method:
            method.append(line)
            if line == ".end method ":
                decl = method[0]
                sig = next((line for line in method if line.startswith("    .signature ")), "")
                if u:
                    sig = sig.replace("<T:", "<U:").replace("TT;", "TU;")
                elif not ("(TT;" in sig or ";TT;" in sig or ")TT;" in sig):
                    # Method doesn't need to be bridged
                    method = None
                    continue
                methods.append((decl, sig))
                method = None
        elif line.startswith(".method public abstract"):
            method = [line]
    remove(asmfile)

# Start patching
opt = "None"
cls = "me.dkleszyk.java.option." + opt
disassemble(cls, classpath, asmpath)
asmfile = join(asmpath, opt + ".j")
with open(asmfile, "r") as f:
    lines = f.read().split("\n")

# Add interface impls
ifaceIdx = 1 + next((idx for idx, line in enumerate(lines) if line == ".implements me/dkleszyk/java/option/Option "))
lines = lines[:ifaceIdx] + [".implements me/dkleszyk/java/option/" + u + "Option " for u in (t[0] for t in types)] + lines[ifaceIdx:]
sigIdx = next((idx for idx, line in enumerate(lines) if line.startswith(".signature ")))
sig = lines[sigIdx]
sigopt = ";Lme/dkleszyk/java/option/Option<TT;>;"
sig = sig.replace(sigopt, sigopt + ";".join(["Lme/dkleszyk/java/option/" + u + "Option" for u in [t[0] for t in types]]) + ";")
lines[sigIdx] = sig

# Get lineno for class
with open(join(*([srcpath] + cls.split("."))) + ".java", "r") as f:
    lineno = str(1 + next((idx for idx, line in enumerate(f.read().split("\n")) if line == "final class None<T>")))

# Add bridge methods
bridges = []
for decl, sig in methods:
    aidx = decl.find(" : (") + 4
    ridx = decl.rfind(")")
    xidx = -1
    # 24 ::= len(".method public abstract ")
    declstart, declname, declargs, declret, declend = (decl[:24], decl[24:(aidx-4)], decl[aidx:ridx], decl[(ridx+1):xidx], decl[xidx:])

    aidx = sig.find("(") + 1
    ridx = sig.rfind(")")
    xidx = sig.rfind("^")
    if xidx == -1 and sig.endswith("' "):
        xidx = -2
    sigstart, sigargs, sigret, sigend = (sig[:(aidx-1)], sig[aidx:ridx], sig[(ridx+1):xidx], sig[xidx:]) if sig else ("", "", "", "")

    declarglist = []
    i = 0
    while i < len(declargs):
        j = declargs.find(";", i)
        k = declargs.find("<", i)
        l = declargs.find(">", i)
        if -1 < k and k < j:
            n = 0
            while -1 < k and k < l:
                n += 1
                k = declargs.find("<", k + 1)
            k = l - 1
            while n > 0:
                n -= 1
                k = declargs.find(">", k + 1)
            j = k + 1
        j = j + 1 if j > -1 else len(declargs)
        declarglist.append(declargs[i:j])
        i = j

    sigarglist = []
    i = 0
    while i < len(sigargs):
        j = sigargs.find(";", i)
        k = sigargs.find("<", i)
        l = sigargs.find(">", i)
        if -1 < k and k < j:
            n = 0
            while -1 < k and k < l:
                n += 1
                k = sigargs.find("<", k + 1)
            k = l - 1
            while n > 0:
                n -= 1
                k = sigargs.find(">", k + 1)
            j = k + 1
        j = j + 1 if j > -1 else len(sigargs)
        sigarglist.append(sigargs[i:j])
        i = j

    origsig = sig
    for b in [t[2] for t in types] if sigret == "TT;" or "TT;" in sigarglist else [("")]:
        sig = origsig
        bridgedtype = "Ljava/lang/" + b + ";"
        # Only need ACC_BRIDGE for methods with covariant return type
        # and invariant parameter types
        decl = declstart.replace(" abstract ", " bridge synthetic ") if sigret == "TT;" and "TT;" not in sigarglist else declstart.replace(" abstract ", " synthetic ")
        decl += declname + " : ("
        if not sig:
            for a in declarglist:
                decl += a
            decl += ")"
            decl += declret
            decl += declend
        else:
            sig = sigstart + "("
            for i, a in enumerate(sigarglist):
                if a == "TT;":
                    decl += bridgedtype
                    sig += bridgedtype
                else:
                    decl += declarglist[i]
                    sig += a.replace("TT;", bridgedtype)
            decl += ")"
            sig += ")"
            if sigret == "TT;":
                decl += bridgedtype
                sig += bridgedtype
            else:
                decl += declret
                sig += sigret
            decl += declend
            sig += sigend
            # Might be invariant after replacing bridged type
            if "<" not in sig:
                sig = ""

        method = ["", decl]
        while True:
            argc = len(declarglist)
            if argc == 0:
                if declname.startswith("get") or declname.endswith("ElseThrow"):
                    method += [
                        "    .code stack 1 locals 1 ",
                        "L0:     invokestatic Method me/dkleszyk/java/option/Exceptions optionNoValue ()Ljava/lang/RuntimeException; ",
                        "L3:     athrow ",
                        "L4:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L4 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L4 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
                elif declname.startswith("toOptional"):
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 1 locals 1 ",
                        "L0:     invokestatic Method " + ret + " empty ()L" + ret + "; ",
                        "L3:     areturn ",
                        "L4:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L4 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L4 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
                elif declname.endswith("Stream"):
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 1 locals 1 ",
                        "L0:     invokestatic InterfaceMethod " + ret + " empty ()L" + ret + "; ",
                        "L3:     areturn ",
                        "L4:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L4 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L4 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.endswith("ElseThrow"):
                if argc == 1:
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     aload_1 ",
                        "L1:     invokeinterface InterfaceMethod java/util/function/Supplier get ()Ljava/lang/Object; 1 ",
                        "L6:     checkcast java/lang/Throwable ",
                        "L9:     athrow ",
                        "L10:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier; from L0 to L10 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier<+TX;>; from L0 to L10 ",
                        "        .end localvariabletypetable ",
                        "    .end code ",
                        "    .exceptions java/lang/Throwable "
                    ]
                    break
                elif argc == 2:
                    method += [
                        "    .code stack 2 locals 3 ",
                        "L0:     aload_1 ",
                        "L1:     aload_2 ",
                        "L2:     invokeinterface InterfaceMethod java/util/function/Function apply (Ljava/lang/Object;)Ljava/lang/Object; 2 ",
                        "L7:     checkcast java/lang/Throwable ",
                        "L10:    athrow ",
                        "L11:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function; from L0 to L11 ",
                        "            2 is arg Ljava/lang/Object; from L0 to L11 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function<-TA;+TX;>; from L0 to L11 ",
                        "            2 is arg TA; from L0 to L11 ",
                        "        .end localvariabletypetable ",
                        "    .end code ",
                        "    .exceptions java/lang/Throwable "
                    ]
                    break
            elif declname == "or":
                if argc == 1:
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     aload_1 ",
                        "L1:     areturn ",
                        "L2:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L2 ",
                        "            1 is option L" + ret + "; from L0 to L2 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L2 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("orGet"):
                if argc == 1:
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     aload_1 ",
                        "L1:     invokeinterface InterfaceMethod java/util/function/Supplier get ()Ljava/lang/Object; 1 ",
                        "L6:     checkcast " + ret + " ",
                        "L9:     areturn ",
                        "L10:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier; from L0 to L10 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier<+L" + ret + ";>; from L0 to L10 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
                elif argc == 2:
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 2 locals 3 ",
                        "L0:     aload_1 ",
                        "L1:     aload_2 ",
                        "L2:     invokeinterface InterfaceMethod java/util/function/Function apply (Ljava/lang/Object;)Ljava/lang/Object; 2 ",
                        "L7:     checkcast " + ret + " ",
                        "L10:    areturn ",
                        "L11:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function; from L0 to L11 ",
                        "            2 is arg Ljava/lang/Object; from L0 to L11 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function<-TA;+L" + ret + ";>; from L0 to L11 ",
                        "            2 is arg TA; from L0 to L11 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname == "orElse":
                method += [
                    "    .code stack 1 locals 2 ",
                    "L0:     aload_1 ",
                    "L1:     areturn ",
                    "L2:     ",
                    "        .linenumbertable ",
                    "            L0 " + lineno + " ",
                    "        .end linenumbertable ",
                    "        .localvariabletable ",
                    "            0 is this Lme/dkleszyk/java/option/None; from L0 to L2 ",
                    "            1 is value " + bridgedtype + " from L0 to L2 ",
                    "        .end localvariabletable ",
                    "        .localvariabletypetable ",
                    "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L2 ",
                    "        .end localvariabletypetable ",
                    "    .end code "
                ]
                break
            elif declname == "orElseGet":
                if argc == 1:
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     aload_1 ",
                        "L1:     invokeinterface InterfaceMethod java/util/function/Supplier get ()Ljava/lang/Object; 1 ",
                        "L6:     checkcast " + bridgedtype[1:-1] + " ",
                        "L9:     areturn ",
                        "L10:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier; from L0 to L10 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L10 ",
                        "            1 is supplier Ljava/util/function/Supplier<+" + bridgedtype + ">; from L0 to L10 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
                elif argc == 2:
                    method += [
                        "    .code stack 2 locals 3 ",
                        "L0:     aload_1 ",
                        "L1:     aload_2 ",
                        "L2:     invokeinterface InterfaceMethod java/util/function/Function apply (Ljava/lang/Object;)Ljava/lang/Object; 2 ",
                        "L7:     checkcast " + bridgedtype[1:-1] + " ",
                        "L10:    areturn ",
                        "L11:    ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function; from L0 to L11 ",
                        "            2 is arg Ljava/lang/Object; from L0 to L11 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L11 ",
                        "            1 is supplier Ljava/util/function/Function<-TA;+" + bridgedtype + ">; from L0 to L11 ",
                        "            2 is arg TA; from L0 to L11 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("filter"):
                if argc == 1:
                    pred = declarglist[0][1:-1]
                    ret = declret[1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                        "L3:     checkcast " + ret + " ",
                        "L4:     areturn ",
                        "L5:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L5 ",
                        "            1 is predicate L" + pred + "; from L0 to L5 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L5 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("matches"):
                if argc == 1:
                    pred = declarglist[0][1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     iconst_0 ",
                        "L1:     ireturn ",
                        "L2:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L2 ",
                        "            1 is predicate L" + pred + "; from L0 to L2 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L2 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("ifSome"):
                if declname.endswith("OrElse"):
                    u = declname[6:-6]
                    tcsmr = "/" + u + "Consumer;"
                    objtcsmr = "/Obj" + u + "Consumer;"
                    if argc == 2:
                        if declarglist[0].endswith(tcsmr) and declarglist[1].endswith("/Runnable;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 2 locals 3 ",
                                "L0:     aload_0 ",
                                "L1:     aload_2 ",
                                "L2:     invokevirtual Method me/dkleszyk/java/option/None ifNone (Ljava/lang/Runnable;)V ",
                                "L5:     return ",
                                "L6:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L6 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L6 ",
                                "            2 is ifNone Ljava/lang/Runnable; from L0 to L6 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L6 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                    elif argc == 3:
                        if declarglist[0].endswith(objtcsmr) and declarglist[1].endswith("/Object;") and declarglist[2].endswith("/Runnable;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 2 locals 4 ",
                                "L0:     aload_0 ",
                                "L1:     aload_3 ",
                                "L2:     invokevirtual Method me/dkleszyk/java/option/None ifNone (Ljava/lang/Runnable;)V ",
                                "L5:     return ",
                                "L6:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L6 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L6 ",
                                "            2 is ifSomeArg Ljava/lang/Object; from L0 to L6 ",
                                "            3 is ifNone Ljava/lang/Runnable; from L0 to L6 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L6 ",
                                "            1 is ifSome L" + ifsome + "<-TA;>; from L0 to L6 ",
                                "            2 is ifSomeArg TA; from L0 to L6 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                        elif declarglist[0].endswith(tcsmr) and declarglist[1].endswith("/Consumer;") and declarglist[2].endswith("/Object;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 3 locals 4 ",
                                "L0:     aload_0 ",
                                "L1:     aload_2 ",
                                "L2:     aload_3 ",
                                "L3:     invokevirtual Method me/dkleszyk/java/option/None ifNone (Ljava/util/function/Consumer;Ljava/lang/Object;)V ",
                                "L6:     return ",
                                "L7:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L7 ",
                                "            2 is ifNone Ljava/util/function/Consumer; from L0 to L7 ",
                                "            3 is ifNoneArg Ljava/lang/Object; from L0 to L7 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                                "            2 is ifNone Ljava/util/function/Consumer<-TA;>; from L0 to L7 ",
                                "            3 is ifNoneArg TA; from L0 to L7 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                        elif declarglist[0].endswith(objtcsmr) and declarglist[1].endswith("/Consumer;") and declarglist[2].endswith("/Object;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 3 locals 4 ",
                                "L0:     aload_0 ",
                                "L1:     aload_2 ",
                                "L2:     aload_3 ",
                                "L3:     invokevirtual Method me/dkleszyk/java/option/None ifNone (Ljava/util/function/Consumer;Ljava/lang/Object;)V ",
                                "L6:     return ",
                                "L7:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L7 ",
                                "            2 is ifNone Ljava/util/function/Consumer; from L0 to L7 ",
                                "            3 is arg Ljava/lang/Object; from L0 to L7 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                                "            1 is ifSome L" + ifsome + "<-TA;>; from L0 to L7 ",
                                "            2 is ifNone Ljava/util/function/Consumer<-TA;>; from L0 to L7 ",
                                "            3 is arg TA; from L0 to L7 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                    elif argc == 4:
                        if declarglist[0].endswith(objtcsmr) and declarglist[1].endswith("/Object;") and declarglist[2].endswith("/Consumer;") and declarglist[3].endswith("/Object;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 3 locals 5 ",
                                "L0:     aload_0 ",
                                "L1:     aload_3 ",
                                "L2:     aload 4 ",
                                "L4:     invokevirtual Method me/dkleszyk/java/option/None ifNone (Ljava/util/function/Consumer;Ljava/lang/Object;)V ",
                                "L7:     return ",
                                "L8:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L8 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L8 ",
                                "            2 is ifSomeArg Ljava/lang/Object; from L0 to L8 ",
                                "            3 is ifNone Ljava/util/function/Consumer; from L0 to L8 ",
                                "            4 is ifNoneArg Ljava/lang/Object; from L0 to L8 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L8 ",
                                "            1 is ifSome L" + ifsome + "<-TA;>; from L0 to L8 ",
                                "            2 is ifSomeArg TA; from L0 to L8 ",
                                "            3 is ifNone Ljava/util/function/Consumer<-TB;>; from L0 to L8 ",
                                "            4 is ifNoneArg TB; from L0 to L8 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                else:
                    u = declname[6:]
                    tcsmr = "/" + u + "Consumer;"
                    objtcsmr = "/Obj" + u + "Consumer;"
                    if argc == 1:
                        if declarglist[0].endswith(tcsmr):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 1 locals 2 ",
                                "L0:     return ",
                                "L1:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L1 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L1 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L1 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
                    elif argc == 2 :
                        if declarglist[0].endswith(objtcsmr) and declarglist[1].endswith("/Object;"):
                            ifsome = declarglist[0][1:-1]
                            method += [
                                "    .code stack 1 locals 3 ",
                                "L0:     return ",
                                "L1:     ",
                                "        .linenumbertable ",
                                "            L0 " + lineno + " ",
                                "        .end linenumbertable ",
                                "        .localvariabletable ",
                                "            0 is this Lme/dkleszyk/java/option/None; from L0 to L1 ",
                                "            1 is ifSome L" + ifsome + "; from L0 to L1 ",
                                "            2 is arg Ljava/lang/Object; from L0 to L1 ",
                                "        .end localvariabletable ",
                                "        .localvariabletypetable ",
                                "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L1 ",
                                "            1 is ifSome L" + ifsome + "<-TA;>; from L0 to L1 ",
                                "            2 is arg TA; from L0 to L1 ",
                                "        .end localvariabletypetable ",
                                "    .end code "
                            ]
                            break
            elif declname.endswith("OrElse"):
                lt = declname[:-6]
                c = next((c for _, l, _, _, c in types if l == lt))
                method += ["    .code stack 2 locals 3 "] if c == "D" or c == "J" else ["    .code stack 1 locals 2 "]
                if c == "D":
                    method += [
                        "L0:     dload_1 ",
                        "L1:     dreturn "
                    ]
                elif c == "J":
                    method += [
                        "L0:     lload_1 ",
                        "L1:     lreturn "
                    ]
                elif c == "F":
                    method += [
                        "L0:     fload_1 ",
                        "L1:     freturn ",
                    ]
                else:
                    method += [
                        "L0:     iload_1 ",
                        "L1:     ireturn ",
                    ]
                method += [
                    "L2:     ",
                    "        .linenumbertable ",
                    "            L0 " + lineno + " ",
                    "        .end linenumbertable ",
                    "        .localvariabletable ",
                    "            0 is this Lme/dkleszyk/java/option/None; from L0 to L2 ",
                    "            1 is value " + c + " from L0 to L2 ",
                    "        .end localvariabletable ",
                    "        .localvariabletypetable ",
                    "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L2 ",
                    "        .end localvariabletypetable ",
                    "    .end code "
                ]
                break
            elif declname.endswith("OrElseGet"):
                lt = declname[:-9]
                u, c = next(((u, c) for u, l, _, _, c in types if l == lt))
                supp = declarglist[0][1:-1]
                if argc == 1:
                    get = "getAs" + u
                    method += ["    .code stack 2 locals 3 "] if c == "D" or c == "J" else ["    .code stack 1 locals 2 "]
                    method += [
                        "L0:     aload_1 ",
                        "L1:     invokeinterface InterfaceMethod " + supp + " " + get + " ()" + c + " 1 "
                    ]
                    if c == "D":
                        method += [
                            "L6:     dreturn "
                        ]
                    elif c == "J":
                        method += [
                            "L6:     lreturn "
                        ]
                    elif c == "F":
                        method += [
                            "L6:     freturn ",
                        ]
                    else:
                        method += [
                            "L6:     ireturn ",
                        ]
                    method += [
                        "L7:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                        "            1 is supplier L" + supp + "; from L0 to L7 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
                elif argc == 2:
                    get = "applyAs" + u if u != "Boolean" else "test"
                    method += ["    .code stack 3 locals 4 "] if c == "D" or c == "J" else ["    .code stack 2 locals 3 "]
                    method += [
                        "L0:     aload_1 ",
                        "L1:     aload_2 ",
                        "L2:     invokeinterface InterfaceMethod " + supp + " " + get + " (Ljava/lang/Object;)" + c + " 2 "
                    ]
                    if c == "D":
                        method += [
                            "L7:     dreturn "
                        ]
                    elif c == "J":
                        method += [
                            "L7:     lreturn "
                        ]
                    elif c == "F":
                        method += [
                            "L7:     freturn ",
                        ]
                    else:
                        method += [
                            "L7:     ireturn ",
                        ]
                    method += [
                        "L8:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L8 ",
                        "            1 is supplier L" + supp + "; from L0 to L8 ",
                        "            2 is arg Ljava/lang/Object; from L0 to L8 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L8 ",
                        "            1 is supplier L" + supp + "<-TA;>; from L0 to L8 ",
                        "            2 is arg TA; from L0 to L8 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("map"):
                if "To" in declname:
                    if argc == 1:
                        map = declarglist[0][1:-1]
                        ret = declret[1:-1]
                        method += [
                            "    .code stack 1 locals 2 ",
                            "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                            "L3:     checkcast " + ret + " ",
                            "L6:     areturn ",
                            "L7:     ",
                            "        .linenumbertable ",
                            "            L0 " + lineno + " ",
                            "        .end linenumbertable ",
                            "        .localvariabletable ",
                            "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                            "            1 is mapper L" + map + "; from L0 to L7 ",
                            "        .end localvariabletable ",
                            "        .localvariabletypetable ",
                            "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                            "        .end localvariabletypetable ",
                            "    .end code "
                        ]
                        break
                    elif argc == 2:
                        map = declarglist[0][1:-1]
                        ret = declret[1:-1]
                        c = declarglist[1]
                        method += ["    .code stack 1 locals 4 "] if c == "D" or c == "J" else ["    .code stack 1 locals 3 "]
                        method += [
                            "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                            "L3:     checkcast " + ret + " ",
                            "L6:     areturn ",
                            "L7:     ",
                            "        .linenumbertable ",
                            "            L0 " + lineno + " ",
                            "        .end linenumbertable ",
                            "        .localvariabletable ",
                            "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                            "            1 is mapper L" + map + "; from L0 to L7 ",
                            "            2 is arg " + c + " from L0 to L7 ",
                            "        .end localvariabletable ",
                            "        .localvariabletypetable ",
                            "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                            "        .end localvariabletypetable ",
                            "    .end code "
                        ]
                        break
                elif argc == 1:
                    map = declarglist[0][1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                        "L3:     areturn ",
                        "L4:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L4 ",
                        "            1 is mapper L" + map + "; from L0 to L4 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L4 ",
                        "            1 is mapper L" + map + "<+TU;>; from L0 to L4 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            elif declname.startswith("flatMap"):
                if "To" in declname:
                    if argc == 1:
                        map = declarglist[0][1:-1]
                        ret = declret[1:-1]
                        method += [
                            "    .code stack 1 locals 2 ",
                            "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                            "L3:     checkcast " + ret + " ",
                            "L6:     areturn ",
                            "L7:     ",
                            "        .linenumbertable ",
                            "            L0 " + lineno + " ",
                            "        .end linenumbertable ",
                            "        .localvariabletable ",
                            "            0 is this Lme/dkleszyk/java/option/None; from L0 to L7 ",
                            "            1 is mapper L" + map + "; from L0 to L7 ",
                            "        .end localvariabletable ",
                            "        .localvariabletypetable ",
                            "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L7 ",
                            "            1 is mapper L" + map + "<+" + ret + ";>; from L0 to L7 ",
                            "        .end localvariabletypetable ",
                            "    .end code "
                        ]
                        break
                elif argc == 1:
                    map = declarglist[0][1:-1]
                    method += [
                        "    .code stack 1 locals 2 ",
                        "L0:     invokestatic Method me/dkleszyk/java/option/Options noneUnchecked ()Lme/dkleszyk/java/option/Option; ",
                        "L3:     areturn ",
                        "L4:     ",
                        "        .linenumbertable ",
                        "            L0 " + lineno + " ",
                        "        .end linenumbertable ",
                        "        .localvariabletable ",
                        "            0 is this Lme/dkleszyk/java/option/None; from L0 to L4 ",
                        "            1 is mapper L" + map + "; from L0 to L4 ",
                        "        .end localvariabletable ",
                        "        .localvariabletypetable ",
                        "            0 is this Lme/dkleszyk/java/option/None<TT;>; from L0 to L4 ",
                        "            1 is mapper L" + map + "<+Lme/dkleszyk/java/option/Option<+TU;>;>; from L0 to L4 ",
                        "        .end localvariabletypetable ",
                        "    .end code "
                    ]
                    break
            raise NameError("\n".join((decl, sig)))
        if sig:
            method.append(sig)
        method.append(".end method ")
        bridges += method

lines = lines[:sigIdx] + bridges + lines[sigIdx:]
with open(asmfile, "w") as f:
    f.write("\n".join(lines))
assemble(cls, asmpath, classpath)
remove(asmfile)
