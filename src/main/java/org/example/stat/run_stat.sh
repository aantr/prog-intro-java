#!/bin/bash
javac -sourcepath .. WordStatInput.java WordStatWordsMiddle.java &&
java -ea -cp .. -jar WordStatTest.jar WordsMiddle