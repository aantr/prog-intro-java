# Тесты к курсу «Введение в программирование»

[Условия домашних заданий](https://www.kgeorgiy.info/courses/prog-intro/homeworks.html)


## Домашнее задание 7. Разметка

Модификации
 * *Base*
    * Исходный код тестов:
        * [MarkupTester.java](java/markup/MarkupTester.java)
        * [MarkupTest.java](java/markup/MarkupTest.java)
        * Аргументы командной строки: модификации
    * Откомпилированных тестов не существует,
      так как они зависят от вашего кода
 * *DocBookList* (36-39)
    * Дополнительно реализуйте метод `toDocBook`, генерирующий [DocBook](https://docbook.org/)-разметку:
      * абзацы окружаются тегом `para`
      * выделеный текст окружается тегом `emphasis`
      * сильно выделеный текст окружается тегом `emphasis` c `role='bold'`
      * зачеркнутый текст окружается тегом `emphasis` c `role='strikeout'`.
    * Добавьте поддержку:
      * Нумерованных списков (класс `OrderedList`, тег `orderedlist`): последовательность элементов
      * Ненумерованных списков (класс `UnorderedList`, тег `itemizedlist`): последовательность элементов
      * Элементов списка (класс `ListItem`, тег `listitem`): последовательность абзацев и списков
    * Для новых классов поддержка Markdown не требуется
    * [Исходный код тестов](java/markup/MarkupListTest.java)


## Домашнее задание 6. Подсчет слов++

Модификации
 * *Base*
    * Класс должен иметь имя `Wspp`
    * Исходный код тестов:
        [WsppTest.java](java/wspp/WsppTest.java),
        [WsppTester.java](java/wspp/WsppTester.java)
    * Откомпилированные тесты: [WsppTest.jar](artifacts/WsppTest.jar)
        * Аргументы командной строки: модификации
 * *EvenDigits* (36, 37)
    * Вместо номеров вхождений во всем файле надо указывать
      только чётные вхождения в каждой строке
    * В словах могут дополнительно встречаться
      цифры
    * Класс должен иметь имя `WsppEvenDigits`
 * *CountEvenDigits* (38, 39)
    * В выходном файле слова должны быть упорядочены
      по возрастанию числа вхождений, а при равном числе вхождений –
      по порядку первого вхождения во входном файле
    * Вместо номеров вхождений во всем файле надо указывать
      только чётные вхождения в каждой строке
    * В словах могут дополнительно встречаться
      цифры
    * Класс должен иметь имя `WsppCountEvenDigits`
 * *EvenCurrency* (41, 42)
    * Вместо номеров вхождений во всем файле надо указывать
      только чётные вхождения в каждой строке
    * В словах могут дополнительно встречаться
      [знаки валют](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#CURRENCY_SYMBOL)
    * Класс должен иметь имя `WsppEvenCurrency`
 * *CountPosition* (34, 35)
    * В выходном файле слова должны быть упорядочены
      по возрастанию числа вхождений, а при равном числе вхождений –
      по порядку первого вхождения во входном файле
    * Вместо номеров вхождений во всем файле надо указывать
      `<номер строки>:<номер в строке>`
    * Класс должен иметь имя `WsppCountPosition`
 * *Position* (32, 33)
    * Вместо номеров вхождений во всем файле надо указывать
      `<номер строки>:<номер в строке>`
    * Класс должен иметь имя `WsppPosition`


## Домашнее задание 5. Свой сканнер

В этой домашней работе специальные тесты только для ДЗ-3.
Для ДЗ-4 используйте тесты от него.

Модификации
 * *Base*
    * Исходный код тестов: [FastReverseTest.java](java/reverse/FastReverseTest.java)
    * Откомпилированные тесты: [FastReverseTest.jar](artifacts/FastReverseTest.jar)
        * Аргументы командной строки: модификации
 * *MaxAbsOctDec* (36, 37)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите максимальное по абсолютному значению число
      в его столбце и строке
    * На вход подаются десятичные и восьмеричные числа
    * Восьмеричные числа имеют суффикс `o`
    * Выведите все числа, используя формат восьмеричных чисел
    * Класс должен иметь имя `ReverseMaxAbsOctDec`
 * *MaxModOctDec* (38, 39)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите максимальное по остатку от деления на `1_000_000_007`
      среди чисел в его столбце и строке
    * На вход подаются десятичные и восьмеричные числа
    * Восьмеричные числа имеют суффикс `o`
    * Выведите все числа, используя формат восьмеричных чисел
    * Класс должен иметь имя `ReverseMaxModOctDec`
 * *MaxAbsModOctDec* (41, 42)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите максимальное по остатку от деления на `1_000_000_007`
      абсолютного значения среди чисел в его столбце и строке
    * На вход подаются десятичные и восьмеричные числа
    * Восьмеричные числа имеют суффикс `o`
    * Выведите все числа, используя формат восьмеричных чисел
    * Класс должен иметь имя `ReverseMaxAbsModOctDec`
 * *MaxOct* (34, 35)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите максимум из чисел
      в его столбце и строке
    * Во вводе и выводе используются числа в восьмеричной системе счисления
    * Класс должен иметь имя `ReverseMaxOct`
 * *OddOct* (32, 33)
    * Выведите (в реверсивном порядке) только нечетные числа
    * Во вводе и выводе используются числа в восьмеричной системе счисления
    * Класс должен иметь имя `ReverseOddOct`


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
 * *FastSort* (36-42)
    * Пусть _n_ – число слов во входном файле,
      тогда программа должна работать за O(_n_ log _n_).
 * *WordsSuffix* (36, 37)
    * Выходной файл должен содержать все различные суффиксы длины 3
      слов, встречающихся во входном файле,
      в обратном лексикографическом порядке.
      Слова длины меньшей 3 используются как есть.
    * Класс должен иметь имя `WordStatWordsSuffix`
 * *WordsShingles* (38, 39)
    * Выходной файл должен содержать все различные подстроки длины 3
      слов, встречающихся во входном файле,
      в обратном лексикографическом порядке.
      Слова длины меньшей 3 используются как есть.
    * Класс должен иметь имя `WordStatWordsShingles`
 * *WordsMiddle* (41, 42)
    * Назовём _серединой слова_ подстроку, полученную удалением
      первых и последних 3 символов слова.
      Слова длины меньшей 7 используются как есть.
    * Выходной файл должен содержать все различные середины слов,
      встречающихся во входном файле,
      в обратном лексикографическом порядке.
    * Класс должен иметь имя `WordStatWordsMiddle`
 * *Words* (32, 33)
    * В выходном файле слова должны быть упорядочены
      в обратном лексикографическом порядке
    * Класс должен иметь имя `WordStatWords`
 * *WordsPrefix* (34, 35)
    * Выходной файл должен содержать все различные префиксы длины 3
      слов, встречающихся во входном файле,
      в обратном лексикографическом порядке.
      Слова длины меньшей 3 используются как есть.
    * Класс должен иметь имя `WordStatWordsPrefix`


## Домашнее задание 3. Реверс

Модификации
 * *Base*
    * Исходный код тестов:
        [ReverseTest.java](java/reverse/ReverseTest.java),
        [ReverseTester.java](java/reverse/ReverseTester.java)
    * Откомпилированные тесты: [ReverseTest.jar](artifacts/ReverseTest.jar)
        * Аргументы командной строки: модификации
 * *Memory* (36-42)
    * Программа должна сначала считывать все данные в память,
      и только потом обрабатывать их
    * Пусть _M_ – объём памяти, необходимый для сохранения ввода
      в двумерном массиве `int` минимального размера.
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
 * *Odd* (32, 33)
    * Выведите (в реверсивном порядке) только нечетные числа
    * Класс должен иметь имя `ReverseOdd`
 * *Transpose* (34, 35)
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      выведите только нечётные числа из её транспонированного вида
    * Класс должен иметь имя `ReverseTransp`


## Домашнее задание 2. Сумма чисел

Модификации
 * *Base*
    * Исходный код тестов:
        [SumTest.java](java/sum/SumTest.java),
        [SumTester.java](java/sum/SumTester.java),
        [базовые классы](java/base/)
    * Откомпилированные тесты: [SumTest.jar](artifacts/SumTest.jar)
        * Аргументы командной строки: модификации
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
    1. Запустите `java --version` и проверьте, что версия
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
 