#!/bin/bash
javac -sourcepath .. MyScanner.java WordStatInput.java WordStatWordsMiddle.java &&
java -ea -cp .. -jar WordStatTest.jar WordsMiddle