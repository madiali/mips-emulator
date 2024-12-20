# Contributing

We welcome contributions! After all, this project started as a port of [another](https://github.com/jordanel/mips-emulator). If you want to contribute, here's some information you might find useful.

## Issue/PR

For minor fixes (e.g., documentation improvements), feel free to submit a PR directly. If you would like to fix a bug or implement a new feature, please open an [issue](https://github.com/madiali/mips-emulator/issues/new/choose) first, and mention the issue in your PR.

Once you make a PR, your code will be tested and built into a JAR automatically. You can download and test the generated artifact in the [Actions](https://github.com/madiali/mips-emulator/actions) tab. For more information, see [Build JAR](#build-jar).

## Dev setup

We recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) to work on this project. You should be able to work on any OS. On Windows specifically, you may want to consider IntellJ's [WSL integration](https://www.jetbrains.com/help/idea/how-to-use-wsl-development-environment-in-product.html), but this is somewhat janky.

The [.gitattributes](../.gitattributes) specifies `* text=auto eol=lf` to make working in WSL2 less painful.

## Project information

This is a standard [Maven Java project](https://maven.apache.org/), and the `Main` class is [Main.java](../src/main/java/com/comp541/Main.java). We use JavaFX for the GUI, and we use [SceneBuilder](https://gluonhq.com/products/scene-builder/) to do drap & drop GUI development. Our FXML file is at [gui.fxml](../src/main/resources/com/comp541/fxml/gui.fxml). Do not edit this directly; use SceneBuilder.

Dependencies are listed in [pom.xml](../pom.xml). This project has to work across all OS's on various architectures, and a lot of stuff in that file is necessary for this.

Use [Google Java format](https://plugins.jetbrains.com/plugin/8527-google-java-format) to format all code (4 spaces).

## Build JAR

### Automatic

The JAR is automatically built via a GitHub Action (see [build.yml](workflows/ci.yml)) that uses Maven. As long as users have a compatible Java version (17+, with JavaFX bundled), this JAR should work on all OS's and architectures (x86 and ARM64). However, we have in the past encountered compatibility issues with some OS/arch combinations, so it is best to test manually (e.g., on M1 macOS) before releasing.

### Manual

To build the JAR manually, install `maven` on your system. Then run `mvn package`. The output JAR will be at `target/dependency/mips-emulator-<version>-SNAPSHOT-jar-with-dependencies.jar` and can be run with `java -jar`. The version is specified in [pom.xml](../pom.xml).

## Deploy

To deploy,

1. Change the version number everywhere by doing a global find and replace for the old version number with the new one
2. Push changes
3. Download the artifact from GH [Actions](https://github.com/madiali/mips-emulator/actions)
4. Test it on macOS and Ubuntu (WSL2) via `install.sh` and on Windows by running with `java -jar`
5. Create a [release](https://github.com/madiali/mips-emulator/releases)
