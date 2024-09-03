package com.github.rpcodelearner.dir_tree;

import java.io.File;

class DirTree {
    static final String USAGE_MESSAGE = "Usage: java DirTree <directory name/path>\nDirTree will return 1 if the supplied name/path is not a valid directory.";
    static final String DIR_NOT_FOUND_MESSAGE = "Directory not found: ";
    static final String DIR_IS_FILE_MESSAGE = "Directory is actually a file: ";

    /**
     * Exit Status / Exit Code
     * SUCCESS=0 , FAILURE=1 , MALFORMED_ARGS=2
     */
    enum ExitStatus {
        SUCCESS(0), FAILURE(1), MALFORMED_ARGS(2);
        final int intValue;
        ExitStatus(int i) {
            intValue = i;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE_MESSAGE);
            System.exit(ExitStatus.MALFORMED_ARGS.intValue);
        }
        final File rootFolder = new File(args[0]);
        if (rootFolder.isDirectory()) {
            final DirectoryScanner scanner = new DirectoryScanner(rootFolder);
            System.out.println(scanner.getResult());
            System.exit(ExitStatus.SUCCESS.intValue);
        } else {
            printErrorMessage(rootFolder);
            System.exit(ExitStatus.FAILURE.intValue);
        }
    }

    private static void printErrorMessage(File rootFolder) {
        if (rootFolder.isFile()) {
            System.err.println(DIR_IS_FILE_MESSAGE + rootFolder);
        }
        if (!rootFolder.exists()) {
            System.err.println(DIR_NOT_FOUND_MESSAGE + rootFolder);
        }
    }

}
