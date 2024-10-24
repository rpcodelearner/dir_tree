package com.github.rpcodelearner.dirtree;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

abstract class DirMaker {
    protected static final File TEMP_DIR = new File(System.getProperty("java.io.tmpdir"));
    protected File root = null;

    abstract String getExpected();

    abstract File getTestDirectory();

    protected static File makeSubDir(File parentDir, String childName) {
        File subDir = new File(parentDir, childName);
        if (!subDir.exists()) assertTrue(subDir.mkdir());
        return subDir;
    }

    protected static void makeFile(File dir, String fileName) {
        File file = new File(dir, fileName);
        try {
            if (!file.exists()) assertTrue(file.createNewFile());
        } catch (IOException e) {
            fail("Could not create file '" + fileName +  "' in directory " + dir.getName());
        }
    }
}
