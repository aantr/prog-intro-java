#!/bin/bash
javac -d out stat/WordStatInput.java &&
javac -d out stat/WordStatWordsMiddle.java stat/MyScanner.java &&
cp stat/WordStatTest.jar out/org/example/stat &&

java -cp out/org/example -ea -jar stat/WordStatTest.jar WordsMiddle
