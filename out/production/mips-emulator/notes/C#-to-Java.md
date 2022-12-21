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

from `MIPS_Emulator.SpriteMemory.cs`

* Indexers do not exist in Java.
* Our getters have signature `getter(index)`.
* Our setters have signature `setter(index, value)`.

## Constructor default values

```C#
public MIPS_Emulator.SpriteMemory(uint size, uint wordSize = 4) {

}
```

from `MIPS_Emulator.SpriteMemory.cs`

* Java does not support default values in constructors.
* Instead, we overload the constructor and use [constructor chaining](https://www.geeksforgeeks.org/constructor-chaining-java-examples/) where convenient.
* One has parameters `size, wordSize`, and the other just has the parameter `size` and sets `wordSize` to 4.
* Preferably, we should list the default values of parameters in a docstring above the function so we know what the values are if we call it.

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
* *Note*: `MIPS_Emulator.Registers.cs` has two constructors with signatures `RegisterToName(int regNumber)` and `RegisterToName(uint regNumber)`.
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

## [`readonly`](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/readonly)

* Java doesn't have the `readonly` keyword.
* We use `final`, which is slightly [less restrictive](https://stackoverflow.com/questions/15026924/c-sharp-readonly-vs-java-final).
* tl;dr `readonly` allows the variable to be instantiated in the constructor and nowhere else, whereas `final` allows the variable to be instantiated once from anywhere in the program.

## [`internal class`](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/internal)

* This is an access modifier.
* According to [StackOverflow](https://stackoverflow.com/questions/5981107/is-there-anything-like-an-internal-class-in-java), we get equivalent behavior in Java by dropping the access modifier for the class.

## [`Console.WriteLine`](https://learn.microsoft.com/en-us/dotnet/api/system.console.writeline?view=net-7.0)

* We use `System.out.println`, which is [equivalent](https://zetcode.com/csharp/basics/).
 
## [`Console.ReadLine`](https://learn.microsoft.com/en-us/dotnet/api/system.console.readline?view=net-7.0)

* We use `Scanner.nextLine()`.

## [`Console.ReadKey()`](https://learn.microsoft.com/en-us/dotnet/api/system.console.readkey?view=net-7.0)

* Seen in `MIPS_Emulator.Program.cs`.
* This method blocks until a key is pressed, then gets the key.
* [Java can't handle non-blocking (i.e. user doesn't have to hit Enter) input easily](https://stackoverflow.com/questions/1066318/how-to-read-a-single-char-from-the-console-in-java-as-the-user-types-it?noredirect=1&lq=1), so we use `nextLine()`, which requires the user to hit Enter after typing something.
* This is fine in `MIPS_Emulator.Program.cs`, where user input isn't fed into a game that requires non-blocking input.
  * I use `nextLine()` and require the user to hit Enter for now... TODO need to improve this later.
* This will not be fine when we get to game implementation, and we'll need to use a workaround, which exists.
* [StackOverflow](https://stackoverflow.com/questions/1066318/how-to-read-a-single-char-from-the-console-in-java-as-the-user-types-it) and [information from 68 upvote answer](https://darkcoding.net/software/non-blocking-console-io-is-not-possible/).
* I (Jesse) haven't read it too carefully since it's not necessary *yet*.
* It must be possible to get non-blocking input in Java since [Mars.jar](https://courses.missouristate.edu/kenvollmar/mars/download.htm) is able to do it.

## [`FileInfo` class](https://www.javatpoint.com/c-sharp-fileinfo)

* We use `java.io.File`, though it isn't exactly equivalent.
* `FileInfo` seems like it doesn't have read/write methods, just information like `CreationTime`, `Directory`, `LastAccessTime`, etc.

## [`IDictionary`](https://learn.microsoft.com/en-us/dotnet/api/system.collections.generic.idictionary-2?view=net-7.0)

* We use `Map`.

## [`Type`](https://learn.microsoft.com/en-us/dotnet/api/system.type?view=net-7.0)

* As seen in [Mips.cs](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/Mips.cs)
* `public IDictionary<Type, List<MemoryUnit>> MemDict;`
* ChatGPT uses [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) to replace `Type`, so I'll go with that for now.
* The above *seems* to match the Microsoft article about `Type` in C#.

## [Expression-bodied members](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/statements-expressions-operators/expression-bodied-members)

* As seen in [Mips.cs](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/Mips.cs)

```C#
private uint pc;
public uint Pc => pc;
```

* I think this isn't two separate `pc` variables. Instead, the latter is a `getPC` method that returns `pc`.
* Given that line of code, ChatGPT generates a getter method, so I think the above is correct.

## [`x ?? y`](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/operators/null-coalescing-operator)

* Null-coalescing operator
* Bruh what is this syntax
* `(x == null) ? y : x`

## [`x?.y`](https://stackoverflow.com/questions/37851873/what-does-mean-after-variable-in-c)

* Null-conditional operator
* Return `null` if `x` is `null`, else return `x.y`
 
## `GetType().Name`

* As seen in [MemoryMappedUnit.cs](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/MappedMemoryUnit.cs)
* Java's `.getClass().getName()` does the same thing

## String formatting stuff

* `.Trim()` -> `.trim()`
* `.ToLower()` -> `.toLowerCase()`
* `.Replace(oldchar, newchar)` -> `.replace(oldchar, newchar)`
* `Convert.ToUInt32(String, radix)` -> `Integer.parseInt(String, radix)`
  * Oh crap it's possible this might actually cause issues since `Integer` is signed.
  * TODO: Check this!!!

## [`Regex`](https://learn.microsoft.com/en-us/dotnet/api/system.text.regularexpressions.regex?view=net-7.0)

* We use `Pattern` and `Matcher` instead.
 
## [`compareTo` with ints](https://learn.microsoft.com/en-us/dotnet/api/system.string.compareto?view=net-7.0)

|Condition| Return value |
|---|--------------|
|This precedes arg| <0           |
|Equal| 0            |
|This follows arg| \>0          |

Same as Java's [`Integer.compare(x, y)`](https://www.geeksforgeeks.org/java-integer-compare-method/)

| Condition |Return value|
|-----------|---|
| x < y     |<0|
| x == y    |0|
| x \> y    |>0|

## [`MemUnits = new List<MappedMemoryUnit> {mappedMem};`](https://stackoverflow.com/questions/39548446/c-sharp-list-definition-parentheses-vs-curly-braces)

* From `MemoryMapper`
* `this.memUnits = new ArrayList<MappedMemoryUnit>();`
  * Can't type `new List()` like the original since `List` is abstract.
  * Unfortunately, this makes it less generic. But should be fine.
* `this.memUnits.add(mappedMem);`

## `"0x{address:X8}"`

* As seen in [`MemoryMapper.cs`](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/MemoryMapper.cs) and some other file that I forget, but Ctrl-F for "X8" to find it
* In an f-string, this syntax converts the `uint` address to an 8-digit hex value.
* Instead, we use `String.format("0x%08X", address)`.
* The `08`, instead of just `8`, is important. Otherwise, leading 0's aren't displayed (instead, blank spaces take the spot of leading 0's).

## Custom exceptions

* In [`MemoryMapper.cs`](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/MemoryMapper.cs), there is a custom exception class within the same file.
* Per Java convention, we create separate classes for custom Exceptions.

## [`abstract` applied to a variable](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/abstract)

* No direct counterpart in Java

```C#
protected abstract string Name { get; }
```

* From [`ITypeInstruction.cs`](https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator/Instructions/IType/ITypeInstruction.cs)
* We omit the getter and assume that derived classes will implement a getter