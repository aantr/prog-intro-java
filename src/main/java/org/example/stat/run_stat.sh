#!/bin/bash
javac WordStatInput.java &&
javac WordStatWordsMiddle.java MyScanner.java &&
java -ea -jar "WordStatTest.jar" WordsMiddle
