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
	w = ['a', 'a' * 100, 'b', 'b' * 100, '1']
	w1 = ['a', 'b']
	s = ''
	sep = ''
	for i in range(choice([10000])):
		sep += choice(w1)
	for i in range(randint(1, 50000)):
		s += choice(w)
	print(f's, sep: {len(s)}, {len(sep)}')
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
	tests = 10
	seed(tests)
	for i in range(tests):
	    test()
	    print(f'ok {i + 1}')
	print(f'{tests} passed')