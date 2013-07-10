def convert_to_base(n, b):
    """
    returns a list of the digits of the number, in base b
    """

    if b > 16:
        raise Exception('fuck you')

    result = []
    residue = n
    place = 1
    e = 0
    while residue > 0:
        p = residue % (place * b)
        residue -= p
        result.append(p/place)  # p/place is the digit
        place *= b
        e += 1

    print '-' * 44
    print range(e)[::-1]
    print result[::-1]
    return result[::-1]


    

'''
given a number
compute expansion in new base
for each number in the expansion

'''
