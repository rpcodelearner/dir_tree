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
        assertEquals(args[0], parser.getRootFileName());
    }
}