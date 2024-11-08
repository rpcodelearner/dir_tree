package com.github.rpcodelearner.dirtree;

import com.github.rpcodelearner.dirtree.Main.ExitStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static com.github.rpcodelearner.dirtree.Main.ExitStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestInnerMain {
    private final ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
    private PrintStream oldOut;
    private PrintStream oldErr;

    @BeforeEach
    void setUp() {
        oldOut = System.out;
        System.setOut(new PrintStream(baosOut));
        oldErr = System.err;
        System.setErr(new PrintStream(baosErr));
    }

    @AfterEach
    void tearDown() {
        System.setOut(oldOut);
        System.setErr(oldErr);
    }

    /**
     * Tests for the case when the array of String arguments is null.
     * The author(s) don't expect this to happen in a real environment, but
     * the test (and a related check in the code) is added for completeness.
     */
    @Test
    void nullArgs() {
        final String expectedOutStr = Main.USAGE_MESSAGE + System.lineSeparator();
        final String expectedErrStr = "";
        runInnerMainTest(null, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void noArguments() {
        final String[] args = new String[]{};
        final String expectedOutStr = Main.USAGE_MESSAGE + System.lineSeparator();
        final String expectedErrStr = "";
        runInnerMainTest(args, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void tooManyArguments() {
        final String[] args = new String[]{"foo", "bar"};
        final String expectedOutStr = Main.USAGE_MESSAGE + System.lineSeparator();
        final String expectedErrStr = "";
        runInnerMainTest(args, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void goodArgument() {
        DirMaker dirMaker = new JustFiles();
        final String rootPath = dirMaker.getTestDirectoryPath();
        final String[] args = new String[]{"-f", rootPath};
        final String expectedOutStr = dirMaker.getExpected();
        final String expectedErrStr = "";
        runInnerMainTest(args, SUCCESS, expectedOutStr, expectedErrStr);
    }

    @Test
    void invalidArgumentIsFilename() {
        DirMaker fileMaker = new InvalidDirIsFile();
        final String isAFile = fileMaker.getTestDirectoryPath();
        final String[] args = new String[]{isAFile};
        final String expectedOutStr = "";
        final String expectedErrStr = DirectoryScanner.DIR_IS_FILE_MESSAGE + isAFile + System.lineSeparator();
        runInnerMainTest(args, FAILURE, expectedOutStr, expectedErrStr);
    }

    @Test
    void invalidArgumentNonExistingDir() {
        final String doesNotExist = getNonExistingDirName();
        final String[] args = new String[]{doesNotExist};
        final String expectedOutStr = "";
        final String expectedErrStr = DirectoryScanner.DIR_NOT_FOUND_MESSAGE + doesNotExist + System.lineSeparator();
        runInnerMainTest(args, FAILURE, expectedOutStr, expectedErrStr);
    }

    private void runInnerMainTest(String[] args, ExitStatus expectedExit, String expectedOutStr, String expectedErrStr) {
        ExitStatus exitStatus = Main.innerMain(args);
        System.out.flush();
        System.err.flush();
        String stdout = baosOut.toString();
        String stderr = baosErr.toString();
        assertEquals(expectedExit, exitStatus);
        assertEquals(expectedOutStr, stdout);
        assertEquals(expectedErrStr, stderr);
    }

    private String getNonExistingDirName() {
        for (int i = 0; i < 100; i++) {
            String candidateName = "dirFoobar" + i;
            File candidate = new File(candidateName);
            if (!candidate.exists()) return candidateName;
        }
        throw new RuntimeException("Could not invent the name of a non-existing file/directory");
    }

}
