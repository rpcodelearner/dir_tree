package com.github.rpcodelearner.dirtree;

import java.io.File;
import java.nio.file.FileSystems;


class InvalidDirIsFile extends DirMaker {
    @Override
    String getExpected() {
        throw new RuntimeException("Meaningless call to " + this.getClass().getName() + ".getExpected(). This class is used to test invalid input and not valid output is expected.");
    }

    @Override
    File getTestDirectory() {
        final String fileName = "foobar";
        makeFile(TEMP_DIR, fileName);
        return new File(TEMP_DIR + FileSystems.getDefault().getSeparator() + fileName);
    }
}
