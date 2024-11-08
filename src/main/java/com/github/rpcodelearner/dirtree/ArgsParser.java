package com.github.rpcodelearner.dirtree;

import java.util.HashSet;
import java.util.Set;

class ArgsParser {
    private final Set<String> candidateRootNames = new HashSet<>();
    private boolean includeFiles = false;

    ArgsParser(String[] args) {
        if (args == null || args.length == 0) {
            throw new RuntimeException();
        }
        for (String arg : args) {
            if (arg.startsWith("-")) {
                decodeSwitches(arg);
            } else {
                candidateRootNames.add(arg);
            }
        }
        if (candidateRootNames.size() != 1) {
            throw new RuntimeException();
        }
    }

    private void decodeSwitches(String arg) {
        if (arg.contains("f")) {
            includeFiles = true;
        }
    }


    String getRootFileName() {
        return candidateRootNames.iterator().next();
    }

    boolean areFilesIncluded() {
        return includeFiles;
    }
}
