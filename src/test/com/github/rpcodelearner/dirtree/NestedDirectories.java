package com.github.rpcodelearner.dirtree;

import java.io.File;

class NestedDirectories extends DirMaker {
    private final String testDirectoryPath;

    NestedDirectories() {
        File dir0 = makeSubDir(TEMP_DIR, "dir0");
        File dir1 = makeSubDir(dir0, "dir1");
        File dir2 = makeSubDir(dir1, "dir2");
        makeSubDir(dir2, "dir3");
        testDirectoryPath = dir0.getPath();
    }

    @Override
    String getTestDirectoryPath() {
        return testDirectoryPath;
    }

    @Override
    String getExpected() {
        return "dir0" + System.lineSeparator() + "   dir1" + System.lineSeparator() + "      dir2" + System.lineSeparator() + "         dir3" + System.lineSeparator();
    }

}
