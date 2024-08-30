package com.github.rpcodelearner.dir_tree;

import java.io.File;
import java.io.PrintWriter;

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
        int exitStatus = operate(args, new PrintWriter(System.out), new PrintWriter(System.err));
        System.exit(exitStatus);
    }

    /**
     * This method is like main(), except the caller can easily specify output and error streams
     * @param args String[] args as in public static void main(String[] args)
     * @param output the PrintWriter that will receive the "normal" output (as per stdout)
     * @param error the PrintWriter that will receive the error stream (as per stderr)
     * @return 0 for success, 1 for any failure; the value can be used as the process EXIT STATUS code
     */
    static int operate(String[] args, PrintWriter output, PrintWriter error) {
        if (args.length != 1) {
            printUsage(output);
            return exitStatus.SUCCESS.intValue;
        }
        final File rootFolder = new File(args[0]);
        if (confirmValidDirectory(rootFolder, error)) {
            final DirectoryScanner scanner = new DirectoryScanner(rootFolder);
            output.println(scanner.getResult());
            return exitStatus.SUCCESS.intValue;
        } else {
            return exitStatus.FAILURE.intValue;
        }
    }

    private static boolean confirmValidDirectory(File file, PrintWriter error) {
        if (file.isFile()) {
            error.println(DIR_IS_FILE_MESSAGE + file);
            return false;
        }
        if (!file.exists()) {
            error.println(DIR_NOT_FOUND_MESSAGE + file);
            return false;
        }
        return true;
    }

    private static void printUsage(PrintWriter output) {
        output.println(USAGE_MESSAGE);
        output.flush();
    }
}
