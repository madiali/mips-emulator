// This file is necessary for JAR packaging

package com.comp541;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    public static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) { AppLauncher.main(args); }
}
