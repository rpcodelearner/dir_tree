package com.github.rpcodelearner.dir_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestVariousDirectories {

    @Test
    void testJustFiles() {
        DirMaker dirMaker = new JustFiles();
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory());
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }

    @Test
    void testStructuredTestDirectory() {
        DirMaker dirMaker = new StructuredTestDirectory();
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory());
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }

    @Test
    void testNestedDirectories() {
        DirMaker dirMaker = new NestedDirectories();
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory());
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }


}