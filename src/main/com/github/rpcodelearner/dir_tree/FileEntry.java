package com.github.rpcodelearner.dir_tree;


class FileEntry implements Entry {
    private final String name;

    FileEntry(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
