#!/bin/bash
javac -cp . MyScanner.java ReverseMaxAbsModOctDec.java Reverse.java &&
java -cp . -ea -jar FastReverseTest.jar MaxAbsModOctDec
