package com.github.rpcodelearner.dir_tree;

import java.util.ArrayList;
import java.util.List;

class DirEntry implements Entry {
    private final List<Entry> entries = new ArrayList<>();
    private final String name;

    DirEntry(String name) {
        this.name = name;
    }

    void add(Entry entry) {
        entries.add(entry);
    }

    @Override
    public String getName() {
        return name;
    }

    List<Entry> getContents() {
        entries.sort(new EntryComparatorByAlphabet());
        return entries;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


}
