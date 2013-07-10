def convert_to_base(n, b):
    """
    returns a list of the digits of the number, in base b
    """

    result = []
    residue = n
    place = 1
    e = 0
    while residue > 0:
        p = residue % (place * b)
        residue -= p
        digit = p / place
        if e > b:
            if digit > 0:
                s = convert_to_base(e, b)
                result.append(s)
        else:
            result.append(digit)

        place *= b
        e += 1

#    print range(e)
#    print result
    return result


    

'''
given a number
compute expansion in new base
for each number in the expansion

'''
