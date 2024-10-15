#!/bin/bash
javac Reverse.java &&
javac ReverseMaxAbsOctDec.java MineScanner.java &&
java -ea -jar FastReverseTest.jar MaxAbsOctDec
