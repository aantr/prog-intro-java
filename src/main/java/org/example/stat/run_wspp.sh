#!/bin/bash
javac WsppEvenCurrency.java Wspp.java MyScanner.java &&
java -ea -jar "WsppTest.jar" EvenCurrency
