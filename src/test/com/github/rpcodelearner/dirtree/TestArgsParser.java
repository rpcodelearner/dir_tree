package com.github.rpcodelearner.dirtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestArgsParser {

    @Test
    void nullArgs() {
        assertThrows(RuntimeException.class, () -> new ArgsParser(null));
    }

    @Test
    void emptyArgs() {
        assertThrows(RuntimeException.class, () -> new ArgsParser(new String[]{}));
    }

    @Test
    void onlyDirName() {
        final String expected = "directory name";
        String[] args = {expected};
        ArgsParser parser = new ArgsParser(args);
        assertEquals(expected, parser.getRootFileName());
    }

    @Test
    void filesSwitch() {
        final String dirName = "dir_name";
        final String filesSwitch = "-f";
        String[] args = {filesSwitch, dirName};
        ArgsParser parser = new ArgsParser(args);
        assertTrue(parser.areFilesIncluded());
        assertEquals(dirName, parser.getRootFileName());
    }

    @Test
    void redundantFilesSwitch() {
        // redundant '-f' are equivalent to only one '-f'
        // args will be "-f -f -ff dir_name"
        final String dirName = "dir_name";
        final String filesSwitch = "-f";
        final String doubleFilesSwitch = "-ff";
        String[] args = {filesSwitch, filesSwitch, doubleFilesSwitch, dirName};
        ArgsParser parser = new ArgsParser(args);
        assertTrue(parser.areFilesIncluded());
        assertEquals(dirName, parser.getRootFileName());
    }
}