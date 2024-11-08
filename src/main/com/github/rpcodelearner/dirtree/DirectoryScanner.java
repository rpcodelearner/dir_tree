package com.github.rpcodelearner.dirtree;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

class DirectoryScanner {
    static final String DIR_NOT_FOUND_MESSAGE = "Directory not found: ";
    static final String DIR_IS_FILE_MESSAGE = "Directory is actually a file: ";
    static final String NOT_A_DIRECTORY = "Pathname is not a directory: ";
    private final ArgsParser argsParser;
    private final Entry rootEntry;

    DirectoryScanner(ArgsParser argsParser) {
        this.argsParser = argsParser;
        File providedRoot = new File(argsParser.getRootFileName());
        if (providedRoot.isDirectory()) {
            rootEntry = scanDir(providedRoot);
        } else if (providedRoot.isFile()) {
            throw new RuntimeException(DIR_IS_FILE_MESSAGE + providedRoot);
        } else if (!providedRoot.exists()) {
            throw new RuntimeException(DIR_NOT_FOUND_MESSAGE + providedRoot);
        } else {
            throw new RuntimeException(NOT_A_DIRECTORY + providedRoot);
        }
    }

    public String getResult() {
        final StringWriter output = new StringWriter();
        PrintWriter result = new PrintWriter(output);
        rootEntry.accept(new Visitor(result));
        return output.toString();
    }

    /**
     * Populates a tree of Entry (as per the Composite design pattern)
     * @param dir by contract this <u>must</u> be a directory
     * @return root of the Composite tree
     */
    private Entry scanDir(File dir) {
        DirEntry dirEntry = new DirEntry(dir.getName());
        for (File file : dir.listFiles()) {
            Entry entry;
            if (file.isDirectory()) {
                entry = scanDir(file);
            } else {
                entry = new FileEntry(file.getName());
            }
            if (entry instanceof DirEntry || argsParser.areFilesIncluded()) {
                dirEntry.add(entry);
            }
        }
        return dirEntry;
    }

}
