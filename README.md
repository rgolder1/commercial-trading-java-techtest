# Anagram Finder
A simple command line utility for finding anagrams in a specified file

## Software required to run this
* Java 17

## Building and Running the tests
```
./gradlew clean build
```

## Running the program
```
./gradlew bootRun --args="example2.txt" 
```
where example2.txt is the text file that we want to search for anagrams

Alternatively run the Spring Boot application:
```
java -jar ./build/libs/anagram-finder-0.0.1-SNAPSHOT.jar ./example2.txt
```

## Big O Analysis

The running time of the algorithm should increase linearly with the size of the input: O(n).

## Solution

1) Iterate over each line in the file, deciding what to do based on word length.
2) If word is same length as previous word, then 'collect'.
3) If word is a new longer length, output the previous length words, clear the map to free memory, then 'collect' the new word.
4) 'collect' refers to sorting the word, checking if this sorted word is already a key in the Map:
- if not: add the sorted word as a key in the Map, and the unsorted word as the value.
- if yes: append the unsorted word to the existing Map value for the key, separated by a comma.
5) Output the words of the final length.

## Assumptions

1) Handling out of order words:

Instructions state:
```
The words in the input file are ordered by size
```

There is no requirement how to handle words that are found out of order (as found in example2.txt).

The requirement here would need to be understood by discussing with the relevant stakeholder.

For now, the solution ignores words found out of order and continues processing.

2) Assume all characters are permissible in a word, including white space.

3) No requirements on error handling, so just printing error to System out.

4) If words contain letters but with a different case sensitivity, they are not considered the same.  e.g. 'ABC' and 'abc'.

## Data Structures

1) A Map is used to collect the words.  The key is the sorted word, and the value is a comma separated list of those words that are anagrams of the key.

## With More Time

1) Address assumptions (e.g. error handling requirement).

2) Introduce proper logging.
