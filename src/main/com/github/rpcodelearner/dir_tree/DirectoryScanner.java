package com.github.rpcodelearner.dir_tree;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

class DirectoryScanner {
    private Entry rootEntry = null;

    DirectoryScanner(File providedRoot) {
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

    private Entry scanDir(File root) {
        DirEntry dir = new DirEntry(root.getName());
        for (File file : root.listFiles()) {
            Entry entry;
            if (file.isDirectory()) {
                entry = scanDir(file);
            } else {
                entry = new FileEntry(file.getName());
            }
            dir.add(entry);
        }
        return dir;
    }

}
