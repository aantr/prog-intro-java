#!/bin/bash
javac expression/ExpressionTest.java \
expression/Variable.java expression/Const.java expression/Subtract.java expression/Add.java expression/Multiply.java expression/Divide.java expression/Variable.java expression/Operation.java base/*.java &&
java -ea expression/ExpressionTest hard Base