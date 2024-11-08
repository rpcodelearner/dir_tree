package com.github.rpcodelearner.dirtree;

import java.io.File;

class JustFiles extends DirMaker {

    private final String testDirectoryPath;

    JustFiles() {
        File root = makeSubDir(TEMP_DIR, "justFilesDIR");
        makeFile(root, "file0");
        makeFile(root, "file1");
        makeFile(root, "file2");
        makeFile(root, "file3");
        testDirectoryPath = root.getPath();
    }

    @Override
    String getExpected() {
        return "justFilesDIR" + System.lineSeparator() +
                "   file0" + System.lineSeparator() +
                "   file1" + System.lineSeparator() +
                "   file2" + System.lineSeparator() +
                "   file3" + System.lineSeparator();
    }

    @Override
    String getTestDirectoryPath() {
        return testDirectoryPath;
    }
}
