package com.github.rpcodelearner.dir_tree;

interface Entry {
    String getName();

    void accept(Visitor visitor);

}
