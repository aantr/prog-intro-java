n = 200000
m = 200000

print(n, m)
for i in range(n - 1):
    print(i + 1, i + 2)
print(*list(range(1, n + 1)))