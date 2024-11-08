package com.github.rpcodelearner.dirtree;

import java.io.File;

class Main {
    static final String USAGE_MESSAGE = "Usage: java DirTree <directory name/path>\nDirTree will return 1 if the supplied name/path is not a valid directory.";
    static final String DIR_NOT_FOUND_MESSAGE = "Directory not found: ";
    static final String DIR_IS_FILE_MESSAGE = "Directory is actually a file: ";
    static final String NOT_A_DIRECTORY = "Pathname is not a directory: ";

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
        System.exit(innerMain(args).intValue);
    }

    static ExitStatus innerMain(String[] args) {
        ArgsParser argsParser;
        try {
            argsParser = new ArgsParser(args);
        } catch (RuntimeException e) {
            System.out.println(USAGE_MESSAGE);
            return ExitStatus.MALFORMED_ARGS;
        }
        final File rootFolder = new File(argsParser.getRootFileName());
        if (rootFolder.isDirectory()) {
            final DirectoryScanner scanner = new DirectoryScanner(rootFolder, argsParser);
            System.out.print(scanner.getResult());
            return ExitStatus.SUCCESS;
        } else {
            System.err.println(computeErrorMessage(rootFolder));
            return ExitStatus.FAILURE;
        }
    }

    private static String computeErrorMessage(File rootFolder) {
        if (rootFolder.isFile()) {
            return DIR_IS_FILE_MESSAGE + rootFolder;
        }
        if (!rootFolder.exists()) {
            return DIR_NOT_FOUND_MESSAGE + rootFolder;
        }
        return NOT_A_DIRECTORY + rootFolder;
    }

}
