package com.global.commtech.test.anagramfinder.service;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class AnagramService {

    public void process(File file) {
        try(BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            Map<String, String> wordMap = new HashMap<>();
            String word;
            int processedWordLength = 1;

            // Iterate over each line in the file, deciding what to do based on word length.
            while ((word = reader.readLine()) != null) {
                int wordLength = word.length();
                if (wordLength == processedWordLength) {
                    // Collect words of the same length.
                    collect(wordMap, word);
                } else if(wordLength>processedWordLength) {
                    // Output the words for the previous length, clear the word Map to free up memory, and start afresh
                    // for the next word length.
                    output(wordMap);
                    processedWordLength=wordLength;
                    wordMap.clear();
                    collect(wordMap, word);
                }
            }
            // Output the words for the final length.
            output(wordMap);
        } catch(Exception e) {
            System.err.println("Exception thrown processing file: " + e.getMessage());
        }
    }

    /**
     * Collect the words into a Map.
     *
     * The key is the sorted word, and the value is a comma separated list of the unsorted words that are anagrams of the key.
     */
    private void collect(Map<String, String> wordMap, String word) {
        String sortedWord = Stream.of(word.split(""))
                .sorted()
                .collect(Collectors.joining());

        Optional<String> matchingKey = wordMap.keySet().stream().filter(key -> key.equals(sortedWord)).findFirst();
        if (matchingKey.isPresent()) {
            String matchingWords = wordMap.get(matchingKey.get());
            wordMap.put(sortedWord, matchingWords.concat(",".concat(word)));
        } else {
            wordMap.put(sortedWord, word);
        }
    }

    /**
     * Output the words to System.out.
     */
    private void output(Map<String, String> wordMap) {
        wordMap.values().stream().forEach(words -> System.out.println(words));
    }
}
