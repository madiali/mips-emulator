# Some notes regarding C# ==> Java peculiarities:

## [Indexer](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/indexers/indexers-in-interfaces)

```C#
public uint this[uint index] {
    get => index == 0 ? SpriteY : SpriteX;
    set {
        if (index == 0) {
            SpriteY = value;
        } else {
            SpriteX = value;
        }
    }
}
```

from `SpriteMemory.cs`

* Indexers do not exist in Java.
* Our getters have signature `getter(index)`.
* Our setters have signature `setter(index, value)`.

## Constructor default values

```C#
public SpriteMemory(uint size, uint wordSize = 4) {

}
```

from `SpriteMemory.cs`

* Java does not support default values in constructors.
* Instead, we overload the constructor.
* One has parameters `size, wordSize`, and the other just has the parameter `size` and sets `wordSize` to 4.

## Empty constructor

* Note in the above example that the constructor body is empty, which is weird.
* After some googling, I don't think C# automatically generates a body for the constructor.
* So when I've seen a constructor with empty body, I left the Java constructor with an empty body also.
* But note that in C#, if there is no constructor or a constructor with no parameters, C# does automatically generate a parameter-less constructor, and all fields are set to default values (in the language).
* [StackOverflow](https://stackoverflow.com/questions/2963742/empty-constructor-or-no-constructor)

## Signed vs. unsigned

* The original code uses C#'s `uint` (32-bit) a lot.
* Java doesn't support unsigned integers.
* We instead use Java's `int` (signed 32-bit) in place of `uint`.
* Using `long` to support the full range of `uint` doesn't work out
  * For example, Java arrays can't be subscripted by a `long` type. So if we were to use `long`, we'd have to cast to an `int` anyway.
* The numbers are small enough that the halved range (0-2000000) should be fine.

## Passing value by reference

* The original code passes values by reference.
    * e.g. `pc`
    * Most instructions set `pc` to `pc+4` after execution
* Instead, we use getters and setters.

## Namespace

* Java doesn't have namespaces.
* We use `package` at the top of classes.

## `readonly`

* Java doesn't have the `readonly` keyword.
* We use `final` instead.