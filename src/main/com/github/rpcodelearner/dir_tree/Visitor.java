package com.github.rpcodelearner.dir_tree;

import java.io.PrintWriter;

class Visitor {
    final PrintWriter output;
    private String currentPrefix = "";

    Visitor(PrintWriter output) {
        this.output = output;
    }

    void visit(FileEntry fileEntry) {
        output.println(currentPrefix + fileEntry.getName());
    }

    void visit(DirEntry dirEntry) {
        output.println(currentPrefix + dirEntry.getName());
        String oldPrefix = currentPrefix;
        currentPrefix += "   ";
        for (Entry entry : dirEntry.getContents()) {
            entry.accept(this);
        }
        currentPrefix = oldPrefix;
    }

}
