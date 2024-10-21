#!/bin/bash
javac MyScanner.java MineScanner.java &&
javac Reverse.java MyScanner.java &&
javac ReverseSumAbsMod.java &&
javac ReverseMaxAbsModOctDec.java &&
java -ea -jar FastReverseTest.jar MaxAbsModOctDec
