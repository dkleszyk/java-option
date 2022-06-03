# What?

An alternative to `java.util.Optional`.

# Why?

- Allows instances to contain null (`map` is fully monadic)
- Provides initialization methods for both possible null-handling
  behaviors (`maybe` and `someNullable`)
- Instances are serializable
- Provides specialized instances for all eight primitive types
- Primitive instances are cached for small values
- Has some nice convenience methods not provided by `Optional`
  (`matches` for applying `filter` + `isSome` in one operation,
  and `filterTo{Type}` for applying `filter` type checking + `map`
  type casting in one operation)

# Why not?

- Uses byte code re-writing (enables using a single `None` instance
  for both primitive and reference types, and eliminates some
  `ClassCastException`s in bridged methods)
- `Optional` is standard and probably good enough
- Will probably be obsoleted by Valhalla

# Usage & Examples

## `some` and `none`

    final var some = Option.some(true);
    final var none = (BooleanOption) Option.<Boolean>none();
    assert some.booleanOrElse(false) == true;
    assert none.booleanOrElse(false) == false;

## `map`

    assert Option.some(0).mapInt(i -> i == 0 ? null : (Integer) i).orElseThrow() == null;

## `filterToType`

    final var some = Option.some(123);
    assert some.filterToType(Object.class).orElse("ABC").equals(123);
    assert some.filterToType(String.class).orElse("ABC").equals("ABC");

## Caching

    assert Option.some(1) == Option.some(1);

## Interop with `Optional`

    assert Option.some(123).toOptionalInt().orElseThrow() == 123;
    assert Option.from(Optional.of("ABC")).orElseThrow().equals("ABC");

# Comparison with `Optional`

| Method                                |    `Optional`     |    `Option`    |
| --------------------------------------|:-----------------:|:--------------:|
| initialize none                       |      `empty`      |     `none`     |
| initialize non-null -> some           |       `of`        |     `some`     |
| initialize non-null/null -> some      |                   | `someNullable` |
| initialize non-null/null -> some/none |    `ofNullable`   |     `maybe`    |
| check if value is present             |    `isPresent`    |    `isSome`    |
| check if value is absent              |     `isEmpty`     |    `isNone`    |
| action if present                     |    `ifPresent`    |    `ifSome`    |
| action if absent                      |                   |    `ifNone`    |
| action depending on if present/absent | `ifPresentOrElse` | `ifSomeOrElse` |
| test value                            |                   |    `matches`   |
| filter value                          |     `filter`      |    `filter`    |
| filter value type                     |                   | `filterToType` |
| map value                             |       `map`       |     `map`      |
| flat map value                        |     `flatMap`     |   `flatMap`    |
| 'and' with option                     |                   |     `and`      |
| 'and' with lazy option                |                   |    `andGet`    |
| 'or' with option                      |                   |      `or`      |
| 'or' with lazy option                 |       `or`        |    `orGet`     |
| get value                             |       `get`       |                |
| get value or default value            |     `orElse`      |   `orElse`     |
| get value or lazy default value       |    `orElseGet`    |  `orElseGet`   |
| get value or throw                    |   `orElseThrow`   | `orElseThrow`  |
| get value stream                      |     `stream`      |    `stream`    |

# Q & A

## What is the difference between `maybe` and `someNullable`?

`some(value)` returns `Some[value]` if `value` is not `null`; otherwise,
throws `NullPointerException`.

`maybe(value)` returns `Some[value]` if `value` is not `null`; otherwise,
returns `None`.

`someNullable(value)` returns `Some[value]` if `value` is not `null`;
otherwise, returns `Some[null]`.

## What is the difference between `Option<Boolean>` and `BooleanOption`?

### (Applies similarly to other primitive specializations)

`Option<Boolean>` can be one of four instances: `Some[true]`, `Some[false]`,
`Some[null]`, or `None`.

`BooleanOption` can be one of three instances: `Some[true]`, `Some[false]`,
or `None`.

`filterToBoolean` can be used to convert an `Option<Boolean>` to a
`BooleanOption`. `Some[null]` will be mapped to `None`.

`BooleanOption` can be cast directly to `Option<Boolean>`.

## Any gotchas?

Calling `toOptional` on `Some[null]` will throw a `NullPointerException`
because `Optional` doesn't allow instances to contain `null`. If desired,
use `filterToObject` to indicate explicitly that `Some[null]` should be
mapped to `None` before calling `toOptional`.
