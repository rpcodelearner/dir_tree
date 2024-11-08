package com.github.rpcodelearner.dirtree;

import java.nio.file.FileSystems;


class InvalidDirIsFile extends DirMaker {
    final String fileName = "fileFoobar";
    private final String testDirectoryPath;

    InvalidDirIsFile() {
        makeFile(TEMP_DIR, fileName);
        testDirectoryPath = TEMP_DIR + FileSystems.getDefault().getSeparator() + fileName;
    }

    @Override
    String getExpected() {
        throw new RuntimeException("Meaningless call to " + this.getClass().getName() + ".getExpected(). This class is used to test invalid input and no valid output is expected.");
    }

    @Override
    String getTestDirectoryPath() {
        return testDirectoryPath;
    }

}
