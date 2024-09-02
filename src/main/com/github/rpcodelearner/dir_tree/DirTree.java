package com.github.rpcodelearner.dir_tree;

import java.io.File;

class DirTree {
    static final String USAGE_MESSAGE = "Usage: java DirTree <directory name/path>\nDirTree will return 1 if the supplied name/path is not a valid directory.";
    static final String DIR_NOT_FOUND_MESSAGE = "Directory not found: ";
    static final String DIR_IS_FILE_MESSAGE = "Directory is actually a file: ";

    /**
     * Exit Status / Exit Code
     * SUCCESS=0 , FAILURE=1
     */
    enum exitStatus {
        SUCCESS(0), FAILURE(1);
        final int intValue;
        exitStatus(int i) {
            intValue = i;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE_MESSAGE);
            System.exit(exitStatus.SUCCESS.intValue); // TODO add a WRONG_ARGS exit case
        }
        final File rootFolder = new File(args[0]);
        if (confirmValidDirectory(rootFolder)) {
            final DirectoryScanner scanner = new DirectoryScanner(rootFolder);
            System.out.println(scanner.getResult());
            System.exit(exitStatus.SUCCESS.intValue);
        } else {
            System.exit(exitStatus.FAILURE.intValue);
        }
    }

    private static boolean confirmValidDirectory(File file) {
        if (file.isFile()) {
            System.err.println(DIR_IS_FILE_MESSAGE + file);
            return false;
        }
        if (!file.exists()) {
            System.err.println(DIR_NOT_FOUND_MESSAGE + file);
            return false;
        }
        return true;
    }

}
