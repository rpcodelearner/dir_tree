package com.github.rpcodelearner.dirtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestVariousDirectories {

    @Test
    void testJustFiles() {
        DirMaker dirMaker = new JustFiles();
        ArgsParser argsParser = new ArgsParser(new String[]{"-f", dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }

    @Test
    void justFilesNoFilesSwitch() {
        DirMaker dirMaker = new JustFiles();
        ArgsParser argsParser = new ArgsParser(new String[]{dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        final String expected = filterOutFiles(dirMaker.getExpected());
        assertEquals(expected, directoryScanner.getResult());
    }

    @Test
    void testStructuredTestDirectory() {
        DirMaker dirMaker = new StructuredTestDirectory();
        ArgsParser argsParser = new ArgsParser(new String[]{"-f", dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }

    @Test
    void testStructuredTestDirectoryNoFilesSwitch() {
        DirMaker dirMaker = new StructuredTestDirectory();
        ArgsParser argsParser = new ArgsParser(new String[]{dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        String expected = filterOutFiles(dirMaker.getExpected());
        assertEquals(expected, directoryScanner.getResult());
    }

    @Test
    void testNestedDirectories() {
        DirMaker dirMaker = new NestedDirectories();
        ArgsParser argsParser = new ArgsParser(new String[]{"-f", dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        assertEquals(dirMaker.getExpected(), directoryScanner.getResult());
    }

    @Test
    void testNestedDirectoriesNoFilesSwitch() {
        DirMaker dirMaker = new NestedDirectories();
        ArgsParser argsParser = new ArgsParser(new String[]{dirMaker.getTestDirectory().toString()});
        DirectoryScanner directoryScanner = new DirectoryScanner(dirMaker.getTestDirectory(), argsParser);
        String expected = filterOutFiles(dirMaker.getExpected());
        assertEquals(expected, directoryScanner.getResult());
    }


    private String filterOutFiles(String totalDirStruct) {
        StringBuilder result = new StringBuilder();
        String[] lines = totalDirStruct.split(System.lineSeparator());
        for (String line : lines) {
            if (!line.trim().toLowerCase().startsWith("file")) {
                result.append(line).append(System.lineSeparator());
            }
        }
        return result.toString();
    }
}