#!/bin/bash
javac -cp md2html md2html/Md2Html.java &&
java -ea -jar Md2htmlTest.jar InsDel
