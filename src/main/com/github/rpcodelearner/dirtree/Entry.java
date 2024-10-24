package com.github.rpcodelearner.dirtree;

interface Entry {
    String getName();

    void accept(Visitor visitor);

}
