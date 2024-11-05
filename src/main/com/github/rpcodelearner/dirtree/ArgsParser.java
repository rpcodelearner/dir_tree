package com.github.rpcodelearner.dirtree;

class ArgsParser {
    private final String rootFileName;

    ArgsParser(String[] args) {
        if (args == null || args.length != 1) {
            throw new RuntimeException();
        }
        rootFileName = args[0];
    }


    String getRootFileName() {
        return rootFileName;
    }
}
