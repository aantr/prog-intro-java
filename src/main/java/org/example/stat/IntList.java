//package org.example.stat;

import java.util.Arrays;

class IntList {
    private int len = 0;
    private int countOccurances = 0;
    private int[] positions = new int[1];
    private int occurrencesInCurrentLine = 0;
    private int currentLine = -1;
    public void add(int position) {
        if (positions.length <= len) {
            positions = Arrays.copyOf(positions, 2 * positions.length);
        }
        positions[len++] = position;
    }
    public void increaseCountOccurances() {
        countOccurances++;
    }
    public int getLength() {
        return len;
    }
    public int occurrenceInLine(int line) {
        if (line != currentLine) {
            currentLine = line;
            occurrencesInCurrentLine = 0;
        }
        occurrencesInCurrentLine++;
        return occurrencesInCurrentLine;
    }
    public int[] getInts() {
        return Arrays.copyOf(positions, len);
    }
    public int getCountOccurances() {
        return countOccurances;
    }
}
