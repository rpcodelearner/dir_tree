package com.github.rpcodelearner.dirtree;

import java.util.Comparator;

class EntryComparatorByAlphabet implements Comparator<Entry> {
    @Override
    public int compare(Entry o1, Entry o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
    }
}
