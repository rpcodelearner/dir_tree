package com.github.rpcodelearner.dirtree;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.rpcodelearner.dirtree.Main.ExitStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests {@link Main} by instantiating a process with
 * required arguments and checking its stdout, stderr and exit status code.
 * Since it is not easy to collect Coverage data, after a slight modification to
 * DirTree, we introduced {@link TestInnerMain} instead. We keep this class
 * for completeness, but we don't expect to add further tests. <b>NOTE</b> that we
 * may disable or delete redundant tests instead!
 */
class TestMainArguments {

    @Test
    void testNoArguments() {
        DirTreeProcess dirTreeRun = new DirTreeProcess();
        dirTreeRun.go();
        assertEquals(Main.USAGE_MESSAGE, dirTreeRun.stdout);
        assertEquals("", dirTreeRun.stderr);
        assertEquals(MALFORMED_ARGS.intValue, dirTreeRun.exitValue);
    }

    @Test
    void testInvalidArgumentIsFile() {
        DirMaker fileMaker = new InvalidDirIsFile();
        final String isAFile = fileMaker.getTestDirectory().getPath();
        DirTreeProcess dirTreeRun = new DirTreeProcess(isAFile);
        dirTreeRun.go();
        assertEquals("", dirTreeRun.stdout);
        assertEquals(Main.DIR_IS_FILE_MESSAGE + isAFile, dirTreeRun.stderr);
        assertEquals(FAILURE.intValue, dirTreeRun.exitValue);
    }

    @Test
    void testInvalidArgumentDoesNotExist() {
        final String nonExistingDirName = getNonExistingDirName();
        DirTreeProcess dirTreeRun = new DirTreeProcess(nonExistingDirName);
        dirTreeRun.go();
        assertEquals("", dirTreeRun.stdout);
        assertEquals(Main.DIR_NOT_FOUND_MESSAGE + nonExistingDirName, dirTreeRun.stderr);
        assertEquals(FAILURE.intValue, dirTreeRun.exitValue);
    }

    private String getNonExistingDirName() {
        for (int i = 0; i < 100; i++) {
            String candidateName = "foobar" + i;
            File candidate = new File(candidateName);
            if (!candidate.exists()) return candidateName;
        }
        throw new RuntimeException("Could not invent the name of a non-existing file/directory");
    }

    private static class DirTreeProcess {
        private final List<String> commandLine = new ArrayList<>();
        private int exitValue = -1;
        private String stdout;
        private String stderr;

        DirTreeProcess(String... argsToDirTreeCmd) {
            final String fileSeparator = FileSystems.getDefault().getSeparator();
            final String javaExecutable = System.getProperty("java.home") + fileSeparator + "bin" + fileSeparator + "java";
            commandLine.add(javaExecutable);
            commandLine.add("-cp");
            commandLine.add(System.getProperty("java.class.path"));
            commandLine.add(Main.class.getCanonicalName());
            Collections.addAll(commandLine, argsToDirTreeCmd);
        }

        void go() {
            ProcessBuilder procBuilder = new ProcessBuilder(commandLine);
            try {
                Process process = procBuilder.start();
                BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                stdout = out.lines().collect(Collectors.joining(System.lineSeparator()));
                stderr = err.lines().collect(Collectors.joining(System.lineSeparator()));
                out.close();
                err.close();
                exitValue = process.waitFor();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
