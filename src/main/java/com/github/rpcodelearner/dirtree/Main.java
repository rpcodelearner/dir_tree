package com.github.rpcodelearner.dirtree;

class Main {
    static final String USAGE_MESSAGE = "Usage: java DirTree <directory name/path>\nDirTree will return 1 if the supplied name/path is not a valid directory.";

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
        } catch (RuntimeException ignored) {
            System.out.println(USAGE_MESSAGE);
            return ExitStatus.MALFORMED_ARGS;
        }
        try {
            final DirectoryScanner scanner = new DirectoryScanner(argsParser);
            System.out.print(scanner.getResult());
            return ExitStatus.SUCCESS;
        } catch (RuntimeException dirError) {
            System.err.println(dirError.getMessage());
            return ExitStatus.FAILURE;
        }
    }

}
