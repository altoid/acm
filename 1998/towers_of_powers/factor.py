def convert_to_base(n, b):
    """
    returns a string representation of the number, in base b
    """

    if b > 16:
        raise Exception('fuck you')

    result = []
    residue = n
    place = 1
    while residue > 0:
        p = residue % (place * b)
        residue -= p
        result.append(str(p/place))  # p/place is the digit
        place *= b
    
    return ','.join(result[::-1])


    

