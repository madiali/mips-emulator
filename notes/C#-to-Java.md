# Some notes regarding C# ==> Java peculiarities

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
* But note that in C#, if there is no constructor or a constructor with no parameters, C# does automatically generate a parameter-less constructor, and all fields are set to default values (e.g. 0 for `uint`).
* [StackOverflow](https://stackoverflow.com/questions/2963742/empty-constructor-or-no-constructor)

## Signed vs. unsigned

* The original code uses C#'s `uint` (32-bit) a lot.
* Java doesn't support unsigned integers.
* We instead use Java's `int` (signed 32-bit) in place of `uint`.
* Using `long` to support the full range of `uint` doesn't work out
  * For example, Java arrays can't be subscripted by a `long` type. So if we were to use `long`, we'd have to cast to an `int` anyway.
* The numbers are small enough that the halved range (0-2000000) should be fine.
* *Note*: `Registers.cs` has two constructors with signatures `RegisterToName(int regNumber)` and `RegisterToName(uint regNumber)`.
  * The second one is one line of code, `return RegisterToName((int)regNumber);`
* Since we're just using signed `int`s anyway, I *think* it's fine to leave out the second constructor.

## Passing value by reference

* The original code passes values by reference.
    * e.g. `pc`
    * Most instructions set `pc` to `pc+4` after execution
* Instead, we use getters and setters.

## Namespace

* Java doesn't have namespaces.
* We use `package` at the top of classes.
* *Note*: I (Jesse) can't figure out how to turn folders into packages in IntelliJ?? I'll add in the `package` keyword later when I figure that out.

## [`readonly`](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/readonly)

* Java doesn't have the `readonly` keyword.
* We use `final` instead.

## [`internal class`](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/internal)

* This is an access modifier.
* According to [StackOverflow](https://stackoverflow.com/questions/5981107/is-there-anything-like-an-internal-class-in-java), we get equivalent behavior in Java by dropping the access modifier for the class.

## [`Console.WriteLine`](https://learn.microsoft.com/en-us/dotnet/api/system.console.writeline?view=net-7.0)

* We use `System.out.println`, which is [equivalent](https://zetcode.com/csharp/basics/).
 
## [`Console.ReadLine`](https://learn.microsoft.com/en-us/dotnet/api/system.console.readline?view=net-7.0)

* We use `Scanner.nextLine()`.

## [`Console.ReadKey()`](https://learn.microsoft.com/en-us/dotnet/api/system.console.readkey?view=net-7.0)

* Seen in `Program.cs`.
* This method blocks until a key is pressed, then gets the key.
* [Java can't handle non-blocking (i.e. user doesn't have to hit Enter) input easily](https://stackoverflow.com/questions/1066318/how-to-read-a-single-char-from-the-console-in-java-as-the-user-types-it?noredirect=1&lq=1), so we use `nextLine()`, which requires the user to hit Enter after typing something.
* This is fine in `Program.cs`, where user input isn't fed into a game that requires non-blocking input.
  * I use `nextLine()` and require the user to hit Enter for now... TODO need to improve this later.
* This will not be fine when we get to game implementation, and we'll need to use a workaround, which exists.
* [StackOverflow](https://stackoverflow.com/questions/1066318/how-to-read-a-single-char-from-the-console-in-java-as-the-user-types-it) and [information from 68 upvote answer](https://darkcoding.net/software/non-blocking-console-io-is-not-possible/).
* I (Jesse) haven't read it too carefully since it's not necessary *yet*.

## [`FileInfo` class](https://www.javatpoint.com/c-sharp-fileinfo)

* We use `java.io.File`, though it isn't exactly equivalent.
* `FileInfo` seems like it doesn't have read/write methods, just information like `CreationTime`, `Directory`, `LastAccessTime`, etc.