#!/usr/bin/env bash

INSTALL_DIR=$HOME/.mips-emulator
MIPS_EMULATOR=https://github.com/madiali/mips-emulator/releases/latest/download/mips-emulator-1.0.0-with-dependencies.jar
# Must be a version with fx bundled
JAVA_VER="17.0.9.fx-librca"
# String after final slash in URL
JAR_NAME="${MIPS_EMULATOR##*/}"

# Install SDKMAN!
curl -s "https://get.sdkman.io" | bash
source $HOME/.sdkman/bin/sdkman-init.sh
# sdkman asks whether to set the installed java version
# as default. Answer yes
yes | sdk install java $JAVA_VER
# Make sure it's the default version
sdk default java $JAVA_VER

mkdir -p $INSTALL_DIR
curl -OLH "Accept: application/zip" --create-dirs --remote-name $MIPS_EMULATOR
mv $JAR_NAME $INSTALL_DIR

# Determine name of shell rc file
# This script is always run via bash
# But $SHELL will be user's shell (e.g., zsh)
# Even if run via bash

if [[ $SHELL = *"bash"* ]]; then
	SHELL_RC_FILE=$HOME/.bashrc
elif [[ $SHELL = *"zsh"* ]]; then
	SHELL_RC_FILE=$HOME/.zshrc
else
    printf "MIPS Emulator has been downloaded to $INSTALL_DIR/$JAR_NAME.
    
However, this script cannot alias the command java -jar $INSTALL_DIR/$JAR_NAME because your \$SHELL is not bash or zsh, so I don't know what your shell's equivalent to .bashrc or .zshrc is.
You can alias the command yourself, if you'd like.
Otherwise, the .jar has already been downloaded and can be run however you normally run a .jar file.\n"
    exit 1
fi

# Alias the command so user can start MIPS Emulator from any directory
echo "alias mips-em=\"java -jar $INSTALL_DIR/$JAR_NAME\"" >> "$SHELL_RC_FILE"
printf "$JAR_NAME has been downloaded to $INSTALL_DIR/$JAR_NAME,
and the command \`mips-em\` has been aliased in your $SHELL_RC_FILE file.
Run \`source $SHELL_RC_FILE\` to apply changes to your current shell session. Otherwise, restart your terminal.
Then run \`mips-em\` to start MIPS Emulator.
For usage details, please read https://github.com/madiali/mips-emulator#usage and/or https://comp541.web.unc.edu.
Enjoy!\n"
