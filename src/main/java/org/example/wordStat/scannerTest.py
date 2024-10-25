import os
from random import randint, seed, choice


# seems like: stupid solutions by korneev gerorgiy
def get_words(s):
    w = []
    while '1' in s:
        f = s.find('1')
        if s[:f]:
            w.append(s[:f])
        s = s[f + len('1'):]
    if s:
        w.append(s)
    return w

def stupid(s, sep):
	words = []
	while sep in s:
		f = s.find(sep)	
		words.extend(get_words(s[:f]))
		words.append('\n')
		s = s[f + len(sep):]
	words.extend(get_words(s))
	return ''.join(words)

def gen():
	w = ['a', 'aa', 'b', 'bb', '1']
	w1 = ['a', 'b']
	s = ''
	sep = ''
	for i in range(choice([1, 2, 3, 5, 10])):
		sep += choice(w1)
	for i in range(randint(1, 100)):
		s += choice(w)
	return s, sep

def test():
	f = open('input.txt', 'w')
	s, sep = gen()
	f.write(sep + '\n' + s)
	f.close()
	os.system('java -ea MyTest < input.txt > output.txt')
	f = open('output.txt', 'r')
	solution = f.read()
	correct = stupid(s, sep)
	open('correct.txt', 'w').write(correct)
	assert solution == correct

if __name__ == '__main__':
	tests = 15
	seed(tests)
	for i in range(tests):
	    test()
	print(f'{tests} passed')