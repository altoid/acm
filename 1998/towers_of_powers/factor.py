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
        d = residue % (place * b)
        residue -= d
        result.append(str(d/place))
        place *= b
    
    return ''.join(result[::-1])


    

