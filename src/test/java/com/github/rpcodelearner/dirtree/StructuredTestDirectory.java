package com.github.rpcodelearner.dirtree;

import java.io.File;


/**
 * The structure below ensures there is at least: an empty dir, a dir with files, a dir with sub-dirs,
 * and a dir with both type of children
 * -----------------------
 * dirWithSubdir        // as root directory
 *    dirWithAFile
 *        file0
 *    dirWithFileAndSubdir
 *        file1
 *        emptyDir
 * -----------------------
 */
class StructuredTestDirectory extends DirMaker {
    private final String testDirectoryPath;

    StructuredTestDirectory() {
        File testRoot = makeSubDir(TEMP_DIR, "dirWithSubdir");
        File dirWithAFile = makeSubDir(testRoot, "dirWithAFile");
        makeFile(dirWithAFile, "file0");
        File dirWithFileAndSubdir = makeSubDir(testRoot, "dirWithFileAndSubdir");
        makeFile(dirWithFileAndSubdir, "file1");
        makeSubDir(dirWithFileAndSubdir, "emptyDir");
        testDirectoryPath = testRoot.getPath();
    }

    String getExpected() {
        return "dirWithSubdir" + System.lineSeparator() +
                "   dirWithAFile" + System.lineSeparator() +
                "      file0" + System.lineSeparator() +
                "   dirWithFileAndSubdir" + System.lineSeparator() +
                "      emptyDir" + System.lineSeparator() +
                "      file1" + System.lineSeparator();
    }

    @Override
    String getTestDirectoryPath() {
        return testDirectoryPath;
    }

}
