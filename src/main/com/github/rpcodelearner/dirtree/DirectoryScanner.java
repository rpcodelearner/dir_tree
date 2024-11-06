package com.github.rpcodelearner.dirtree;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

class DirectoryScanner {
    private final ArgsParser argsParser;
    private Entry rootEntry = null;

    DirectoryScanner(File providedRoot, ArgsParser argsParser) {
        this.argsParser = argsParser;
        if (providedRoot.isDirectory()) {
            rootEntry = scanDir(providedRoot);
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
