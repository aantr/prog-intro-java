#!/bin/bash
javac -sourcepath .. Reverse.java ReverseMaxAbsModOctDec.java FastReverseTest.java &&
java -ea -cp .. FastReverseTest.java MaxAbsModOctDec