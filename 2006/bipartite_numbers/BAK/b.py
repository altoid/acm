#!/usr/bin/env python

import math

# bipartite numbers have EXACTLY 2 distinct digits, sss...ttt....   s != 0

allpairs = []

def factorize(n):
    rt = math.sqrt(n)
    c = 2
    k = n
    result = []
    while c <= rt:
        if n % c == 0:
            result.append(c)
            k = n / c
            break
        c += 1

    if k < n:
        x = factorize(k)
        result += x
    else:
        result.append(n)
    return result

def bipartite_ndigits(n):
    global allpairs

    m = []

    for p in allpairs:
        # if we make this distinction then we don't have to sort m.
        if p[0] < p[1]:
            for i in xrange(1, n):
                m.append(int(p[0] * (n - i) + p[1] * i))

        else:
            for i in xrange(1, n):
                m.append(int(p[0] * i + p[1] * (n - i)))

    return m

def bipartite_numbers():
    ndigits = 2

    while True:
        s = bipartite_ndigits(ndigits)
        for n in s:
            yield n

        ndigits += 1
    

def init():
    global allpairs

    l = ['0','1','2','3','4','5','6','7','8','9']
    m = []
    for i in l:
        for j in l:
            if i != '0':
                if j != i:
                    m.append(i + j)

    allpairs = sorted(m)

def privkey(pubkey):
    for n in bipartite_numbers():
        if n <= pubkey:
            continue

        if n % pubkey == 0:
            return n

if __name__ == '__main__':
    init()

#    pubkey = 1; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 2; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 3; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 4; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 5; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 6; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 7; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 170; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 377; print "%d: %d" % (pubkey, privkey(pubkey))
#    pubkey = 13 * 13 * 13; print "%d: %d" % (pubkey, privkey(pubkey))

    for pubkey in xrange(2, 100000):
        print "%d: %d" % (pubkey, privkey(pubkey))

#    print factorize(543)
#    pubkey = 9930; print "%d: %d" % (pubkey, privkey(pubkey))

