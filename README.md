# Тесты к курсу «Введение в программирование»

[Условия домашних заданий](https://www.kgeorgiy.info/courses/prog-intro/homeworks.html)


## Домашнее задание 4. Подсчет слов

Модификации
 * *Base*
    * Класс должен иметь имя `WordStatInput`
    * Исходный код тестов:
        [WordStatTest.java](java/wordStat/WordStatTest.java),
        [WordStatTester.java](java/wordStat/WordStatTester.java),
        [WordStatChecker.java](java/wordStat/WordStatChecker.java)
    * Откомпилированные тесты: [WordStatTest.jar](artifacts/WordStatTest.jar)
        * Аргументы командной строки: модификации


## Домашнее задание 3. Реверс

Модификации
 * *Base*
    * Исходный код тестов:
        [ReverseTest.java](java/reverse/ReverseTest.java),
        [ReverseTester.java](java/reverse/ReverseTester.java)
    * Откомпилированные тесты: [ReverseTest.jar](artifacts/ReverseTest.jar)
        * Аргументы командной строки: модификации
 * *Transpose* (34, 35)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      выведите только нечётные числа из её транспонированого вида
    * Класс должен иметь имя `ReverseTranspose`
 * *Memory* (36-42)
    * Программа должна сначала считывать все данные в память,
      и только потом обрабатывать их
    * Пусть _M_ – объём памяти, необходимый для сохранения ввода
      в двумерном массиве `int` минимального размера
      Ваша программа должна использовать не более 4_M_ + 1024 байт памяти
    * Накладные расходы на запуск вашей программы JVM не учитываются
 * *SumAbs* (36, 37)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите сумму абсолютных значений чисел
      в его столбце и строке
    * Класс должен иметь имя `ReverseSumAbs`
 * *SumMod* (38, 39)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите остаток от деления на `1_000_000_007` суммы чисел
      в его столбце и строке
    * Класс должен иметь имя `ReverseSumMod`
 * *SumAbsMod* (41, 42)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите остаток от деления на `1_000_000_007` суммы абсолютных значений чисел
      в его столбце и строке
    * Класс должен иметь имя `ReverseSumAbsMod`


## Домашнее задание 2. Сумма чисел

Модификации
 * *Base*
    * Исходный код тестов:
        [SumTest.java](java/sum/SumTest.java),
        [SumTester.java](java/sum/SumTester.java),
        [базовые классы](java/base/)
    * Откомпилированные тесты: [SumTest.jar](artifacts/SumTest.jar)
        * Аргументы командной строки: модификации
 * *Float* (32, 33)
    * Входные данные являются 32-битными числами с формате с плавающей точкой
    * Класс должен иметь имя `SumFloat`
 * *FloatPunct* (34, 35)
    * Входные данные являются 32-битными числами с формате с плавающей точкой
    * Числа дополнительно могут разделяться 
      [открывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#START_PUNCTUATION)
      и [закрывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#END_PUNCTUATION)
      скобками
    * Класс должен иметь имя `SumFloatPunct`
 * *LongPunct* (36, 37)
    * Входные данные являются 64-битными целыми числами
    * Числа дополнительно могут разделяться 
      [открывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#START_PUNCTUATION)
      и [закрывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#END_PUNCTUATION)
      скобками
    * Класс должен иметь имя `SumLongPunct`
 * *BigIntegerPunct* (38, 39)
    * Входные данные помещаются в тип [BigInteger](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html)
    * Числа дополнительно могут разделяться 
      [открывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#START_PUNCTUATION)
      и [закрывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#END_PUNCTUATION)
      скобками
    * Класс должен иметь имя `SumBigIntegerPunct`
 * *LongPunctHex* (41, 42)
    * Входные данные являются 64-битными целыми числами
    * Часть чисел во вводе могут быть шестнадцатеричными.
      Они имеют префикс `0x` и являются беззнаковыми.
    * Числа дополнительно могут разделяться 
      [открывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#START_PUNCTUATION)
      и [закрывающими](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html#END_PUNCTUATION)
      скобками
    * Класс должен иметь имя `SumLongPunctHex`


Для того, чтобы протестировать программу:

 1. Скачайте откомпилированные тесты ([SumTest.jar](artifacts/SumTest.jar))
 1. Откомпилируйте `Sum.java`
 1. Проверьте, что создался `Sum.class`
 1. В каталоге, в котором находится `Sum.class`, выполните команду
    ```
       java -ea -jar <путь к SumTest.jar> Base
    ```
    * Например, если `SumTest.jar` находится в текущем каталоге, выполните команду
    ```
        java -ea -jar SumTest.jar Base
    ```
 1. Для ускорения отладки рекомендуется сделать скрипт, выполняющий шаги 2−4.



## Домашнее задание 1. Запусти меня!

Модификации
  * *RunMe*
    1. Скачайте исходный код [RunMe.java](java/RunMe.java).
    1. Создайте скрипт, компилирующий и запускающий `RunMe` из командной строки
       с выданными вам аргументами командной строки.
    1. Следуйте выведенной инструкции.

Рекомендации по выполнению модификации

1. Проверьте версию Java:
    1. Запустите `javac --version` и проверьте, что версия
       находится в диапазоне 17..22.
    1. Запустите `java --version` и проверьте, что версия,
       такая же как и у `javac`.
1. Скачайте [RunMe.java](java/RunMe.java)
1. Откомпилируйте `RunMe.java`:
    1. Запустите `javac RunMe.java`
    1. Убедитесь, что компиляция завершилась без ошибок
    1. Проверьте, что появился `RunMe.class`
1. Запустите `RunMe`:
    1. Запустите `java RunMe [шесть] [слов] [пароля] [пришедшего] [на] [email]`
    1. При правильном исполнении вы должны получить ссылку.
       Если получено сообщение об ошибке — исправьте её и запустите повторно
    1. Зайдите по полученной ссылке и убедитесь, что она правильная
1. Напишите и протестируйте скрипт:
    1. Напишите скрипт, включающий команды компиляции и запуска.
       Если вы не умеете писать скрипты, воспользуйтесь одной из инструкций:
       [Windows](https://tutorialreference.com/batch-scripting/batch-script-files),
       [Linux](https://www.freecodecamp.org/news/shell-scripting-crash-course-how-to-write-bash-scripts-in-linux/),
       [macOS](https://rowannicholls.github.io/bash/intro/myscript.html)
    1. Запустите и проверьте, что вы получили ту же ссылку, что и в предыдущем пункте
    1. Сдайте скрипт преподавателю
1. Вы можете получить больше плюсиков, модифицируя код `RunMe.java`
 