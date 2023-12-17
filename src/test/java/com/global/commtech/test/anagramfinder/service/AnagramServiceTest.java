package com.global.commtech.test.anagramfinder.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnagramServiceTest {

    private AnagramService anagramService = new AnagramService();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void shouldProcessFile_Simple() {
        File file = new File("src/test/resources/test_simple.txt");
        anagramService.process(file);
        assertEquals("abc,bac\nfun\nhello\n", outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    void shouldProcessFile_DifferentCharacters() {
        File file = new File("src/test/resources/test_diff_chars.txt");
        anagramService.process(file);
        assertEquals("fu n2\n@ bc3,b@ 3c\nhello1\n", outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    void shouldProcessFile_DifferentSensitivity() {
        File file = new File("src/test/resources/test_sensitivity.txt");
        anagramService.process(file);
        assertEquals("abc\nbAc\naBc\n", outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    /**
     * Words that have a length that is out of order are ignored.
     */
    @Test
    void shouldProcessFile_OutOfOrder() {
        File file = new File("src/test/resources/test_out_of_order.txt");
        anagramService.process(file);
        assertEquals("abc\nhello,olleh\n", outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    /**
     * Duplicate words are handled correctly.
     */
    @Test
    void shouldProcessFile_Duplicate() {
        File file = new File("src/test/resources/test_duplicate.txt");
        anagramService.process(file);
        assertEquals("abc,abc,bac,bac\nhello\n", outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    void shouldNotProcessFile_FileNotFound() {
        File file = new File("missing.txt");
        anagramService.process(file);
        assertTrue(outContent.toString().isEmpty());
        assertEquals("Exception thrown processing file: missing.txt\n", errContent.toString());
    }
}
