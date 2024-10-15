#!/bin/bash
javac Wspp.java MyScanner.java MyArray.java &&
javac WsppEvenCurrency.java MyScanner.java MyArray.java &&
java -ea -jar "WsppTest.jar" EvenCurrency
