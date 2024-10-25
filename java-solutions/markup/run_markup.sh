javac -sourcepath .. MarkupTest.java MarkupListTest.java &&
java -ea -cp .. MarkupTest.java Base &&
java -ea -cp .. MarkupListTest.java DocBookList
