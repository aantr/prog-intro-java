#!/bin/bash
rm -r expression/*.class
rm -r expression/parser/*.class
javac expression/parser/ParserTest.java
java -ea expression/parser/ParserTest