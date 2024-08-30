package com.github.rpcodelearner.dir_tree;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestDirTreeArguments {

    @Test
    void testNoArguments() {
        final StringWriter outputString = new StringWriter();
        final StringWriter errorString = new StringWriter();
        DirTree.operate(new String[]{}, new PrintWriter(outputString), new PrintWriter(errorString));
        assertEquals(DirTree.USAGE_MESSAGE + "\n", outputString.toString());
        assertEquals("", errorString.toString());
    }

    @Test
    void testGoodArguments() {
        DirMaker dirMaker = new JustFiles();
        final StringWriter outputString = new StringWriter();
        final StringWriter errorString = new StringWriter();
        final String rootPath = dirMaker.getTestDirectory().getPath();
        DirTree.operate(new String[]{rootPath}, new PrintWriter(outputString), new PrintWriter(errorString));
        assertNotEquals(0, outputString.toString().length());
        assertEquals("", errorString.toString());
    }

    @Test
    void testTooManyArguments() {
        final StringWriter outputString = new StringWriter();
        final StringWriter errorString = new StringWriter();
        DirTree.operate(new String[]{"foo", "bar"}, new PrintWriter(outputString), new PrintWriter(errorString));
        assertEquals(DirTree.USAGE_MESSAGE + "\n", outputString.toString());
        assertEquals("", errorString.toString());
    }

    @Test
    void testInvalidArgumentIsFile() {
        DirMaker fileMaker = new InvalidDirIsFile();
        final StringWriter outputString = new StringWriter();
        final StringWriter errorString = new StringWriter();
        final String isAFile = fileMaker.getTestDirectory().getPath();
        DirTree.operate(new String[]{isAFile}, new PrintWriter(outputString), new PrintWriter(errorString));
        assertEquals("", outputString.toString());
        assertEquals(DirTree.DIR_IS_FILE_MESSAGE + isAFile + "\n", errorString.toString());
    }

    @Test
    void testInvalidArgumentDoesNotExist() {
        final String nonExistingDirectoryFilename = getNonExistingDirName();
        final StringWriter outputString = new StringWriter();
        final StringWriter errorString = new StringWriter();
        DirTree.operate(new String[]{nonExistingDirectoryFilename}, new PrintWriter(outputString), new PrintWriter(errorString));
        assertEquals("", outputString.toString());
        assertEquals(DirTree.DIR_NOT_FOUND_MESSAGE + nonExistingDirectoryFilename + "\n", errorString.toString());
    }

    String getNonExistingDirName() {
        for (int i = 0; i < 100; i++) {
            String candidateName = "foobar" + i;
            File candidate = new File(candidateName);
            if (!candidate.exists()) return candidateName;
        }
        throw new RuntimeException("Could not invent the name of a non-existing file/directory");
    }

}
