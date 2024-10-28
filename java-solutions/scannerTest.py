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

def gen(huge):
	w = ['a', 'a' * 2, 'b', 'b' * 2, '1']
	w1 = ['a', 'b']
	if huge:
	    w = ['a', 'a' * 2, 'b', 'b' * 2, '1']
	    w1 = ['a' * 5000, 'b' * 5000]
	s = ''
	sep = ''
	for i in range(choice([1, 2, 3, 4])):
		sep += choice(w1)
	for i in range(randint(1, 10 + huge * 50000000)):
		s += choice(w)
	print(f'String length, Sep length: {len(s)}, {len(sep)}')
	return s, sep

def test(huge=False):
	s, sep = gen(huge)
	open('input.txt', 'w').write(sep + '\n' + s)
	os.system('java -ea MyTest < input.txt > output.txt')
	solution = open('output.txt', 'r').read()
	if not huge:
	    correct = stupid(s, sep)
	    open('correct.txt', 'w').write(correct)
	    assert solution == correct

if __name__ == '__main__':
	tests = 50
	seed(tests)
	for i in range(tests):
	    test()
	    print(f'ok {i + 1}')
	test(huge=True)
	print(f'{tests} passed')