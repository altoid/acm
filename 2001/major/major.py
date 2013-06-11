MAJOR_SCALES = {
    'Cb': ['Cb', 'Db', 'Eb', 'Fb', 'Gb', 'Ab', 'Bb', 'Cb'],
    'C':  ['C',  'D', 'E', 'F', 'G', 'A', 'B', 'C'],
    'C#': ['C#', 'D#', 'E#', 'F#', 'G#', 'A#', 'B#', 'C#'],
    'Db': ['Db', 'Eb', 'F', 'Gb', 'Ab', 'Bb', 'C', 'Db'],
    'D':  ['D',  'E', 'F#', 'G', 'A', 'B', 'C#', 'D'],
    'Eb': ['Eb', 'F', 'G', 'Ab', 'Bb', 'C', 'D', 'Eb'],
    'E':  ['E',  'F#', 'G#', 'A', 'B', 'C#', 'D#', 'E'],
    'F':  ['F',  'G', 'A', 'Bb', 'C', 'D', 'E', 'F'],
    'F#': ['F#', 'G#', 'A#', 'B', 'C#', 'D#', 'E#', 'F#'],
    'Gb': ['Gb', 'Ab', 'Bb', 'Cb', 'Db', 'Eb', 'F', 'Gb'],
    'G':  ['G',  'A', 'B', 'C', 'D', 'E', 'F#', 'G'],
    'Ab': ['Ab', 'Bb', 'C', 'Db', 'Eb', 'F', 'G', 'Ab'],
    'A':  ['A',  'B', 'C#', 'D', 'E', 'F#', 'G#', 'A'],
    'Bb': ['Bb', 'C', 'D', 'Eb', 'F', 'G', 'A', 'Bb'],
    'B':  ['B', 'C#', 'D#', 'E', 'F#', 'G#', 'A#', 'B']
}

'''
The
input consists of multiple test cases, with one test case per line. Each line starts with a source key, followed by a
target key, and then followed by a list of notes to be transposed from the major scale of the source key to the major
scale of the target
key. Each list is terminated by a single asterisk character. All notes on a line and the terminating
asterisk are delimited by a single space.
The final line of the input contains only a single asterisk which is not to be processed as a test case .
'''

if __name__  == '__main__':
    filename = 'test1.txt'

    f = open(filename, 'r')

    while True:
        l = f.readline()

        data = l.split()
        if data[0] == '*':
            break

        sourcekey = data[0].strip()
        targetkey = data[1].strip()

        if sourcekey not in MAJOR_SCALES:
            print 'Key of %s is not valid' % sourcekey
            continue

        if targetkey not in MAJOR_SCALES:
            print 'Key of %s is not valid' % targetkey
            continue

        sourcescale = MAJOR_SCALES[sourcekey]
        targetscale = MAJOR_SCALES[targetkey]

        print 'Transposing %s to %s' % (sourcekey, targetkey)
        for d in data[2:]:

            n = d.strip()

            if n == '*':
                break

            if n not in sourcescale:
                print '  %s is not valid in %s major scale' % (n, sourcekey)
                continue

            i = sourcescale.index(n)

            print '  %s transposes to %s' % (n, targetscale[i])

