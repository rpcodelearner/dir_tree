package com.github.rpcodelearner.dirtree;

import java.io.File;

class JustFiles extends DirMaker {

    JustFiles() {
        root = makeSubDir(TEMP_DIR, "justFilesDIR");
        makeFile(root, "file0");
        makeFile(root, "file1");
        makeFile(root, "file2");
        makeFile(root, "file3");
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
    File getTestDirectory() {
        return root;
    }
}
