package com.github.rpcodelearner.dirtree;

import com.github.rpcodelearner.dirtree.DirTree.ExitStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static com.github.rpcodelearner.dirtree.DirTree.ExitStatus.*;
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
        final String[] args = null;
        final String expectedOutStr = DirTree.USAGE_MESSAGE + "\n";
        final String expectedErrStr = "";
        runInnerMainTest(args, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void noArguments() {
        final String[] args = new String[]{};
        final String expectedOutStr = DirTree.USAGE_MESSAGE + "\n";
        final String expectedErrStr = "";
        runInnerMainTest(args, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void tooManyArguments() {
        final String[] args = new String[]{"foo", "bar"};
        final String expectedOutStr = DirTree.USAGE_MESSAGE + "\n";
        final String expectedErrStr = "";
        runInnerMainTest(args, MALFORMED_ARGS, expectedOutStr, expectedErrStr);
    }

    @Test
    void goodArgument() {
        DirMaker dirMaker = new JustFiles();
        final String rootPath = dirMaker.getTestDirectory().getPath();
        final String[] args = new String[]{rootPath};
        final String expectedOutStr = dirMaker.getExpected() + "\n";
        final String expectedErrStr = "";
        runInnerMainTest(args, SUCCESS, expectedOutStr, expectedErrStr);
    }

    @Test
    void invalidArgumentIsFilename() {
        DirMaker fileMaker = new InvalidDirIsFile();
        final String isAFile = fileMaker.getTestDirectory().getPath();
        final String[] args = new String[]{isAFile};
        final String expectedOutStr = "";
        final String expectedErrStr = DirTree.DIR_IS_FILE_MESSAGE + isAFile + "\n";
        runInnerMainTest(args, FAILURE, expectedOutStr, expectedErrStr);
    }

    @Test
    void invalidArgumentNonExisting() {
        final String doesNotExist = getNonExistingDirName();
        final String[] args = new String[]{doesNotExist};
        final String expectedOutStr = "";
        final String expectedErrStr = DirTree.DIR_NOT_FOUND_MESSAGE + doesNotExist + "\n";
        runInnerMainTest(args, FAILURE, expectedOutStr, expectedErrStr);
    }

    private void runInnerMainTest(String[] args, ExitStatus expectedExit, String expectedOutStr, String expectedErrStr) {
        ExitStatus exitStatus = DirTree.innerMain(args);
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
            String candidateName = "foobar" + i;
            File candidate = new File(candidateName);
            if (!candidate.exists()) return candidateName;
        }
        throw new RuntimeException("Could not invent the name of a non-existing file/directory");
    }

}
