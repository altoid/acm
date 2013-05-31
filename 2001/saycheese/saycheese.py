from __future__ import division
import math
import fileinput

class Sphere:

    def __init__(self, x, y, z, r):

        self.x = x
        self.y = y
        self.z = z
        self.r = r

    def volume(self):
        return (4 / 3) * math.pi * self.r ** 3

def sphere_intersects_line(sphere, line):

    # returns one triple, two triples, or None, depending
    # on how the sphere and line intersect.
    #
    # line is a pair of triples

    """
    the equation for a sphere at origin (x0, y0, z0) with radius r is

    (x - x0) ** 2 + (y - y0) ** 2 + (z - z0) ** 2 = r ** 2

    the parametric equation for a line passing through (xp, yp, zp)
    with direction <vx, vy, vz> is

    x = xp + t * vx
    y = yp + t * vy
    z = zp + t * vz

    so to find the intersection points we plug the parametric
    equations into the sphere equation and solve for t.

    (xp - x0 + t * vx) ** 2 + (yp - y0 + t * vy) ** 2 + (zp - z0 + t * vz) ** 2

    (xp - x0) ** 2 + 2 * t * vx * (xp - x0) + (vx * t) ** 2
    (yp - y0) ** 2 + 2 * t * vy * (yp - y0) + (vy * t) ** 2
    (zp - z0) ** 2 + 2 * t * vz * (zp - z0) + (vz * t) ** 2

    """

    vx = line[1][0] - line[0][0]
    vy = line[1][1] - line[0][1]
    vz = line[1][2] - line[0][2]

    xp, yp, zp = line[0]

    x0 = sphere.x
    y0 = sphere.y
    z0 = sphere.z

    a = vx ** 2 + \
        vy ** 2 + \
        vz ** 2

    b = 2 * vx * (xp - x0) + \
        2 * vy * (yp - y0) + \
        2 * vz * (zp - z0)

    c = (xp - x0) ** 2 + \
        (yp - y0) ** 2 + \
        (zp - z0) ** 2 - \
        sphere.r ** 2

    discriminant = round(b ** 2 - 4 * a * c, 7)

    if discriminant > 0:
        sqrtd = math.sqrt(discriminant)
        t1 = (-b + sqrtd) / (2 * a)
        t2 = (-b - sqrtd) / (2 * a)

        solution = [(xp + t1 * vx,
                     yp + t1 * vy,
                     zp + t1 * vz),
                    (xp + t2 * vx,
                     yp + t2 * vy,
                     zp + t2 * vz)]

        return solution

    if discriminant == 0:
        t = -b / (2 * a)
        solution = [(xp + t * vx,
                     yp + t * vy,
                     zp + t * vz)]

        return solution

def dot_product(s, t):

    # each of the args is a pair of triples of numbers.  return the
    # dot product of the vectors formed by s0,s1 and t0,t1

    v0 = (s[1][0] - s[0][0], s[1][1] - s[0][1], s[1][2] - s[0][2])
    v1 = (t[1][0] - t[0][0], t[1][1] - t[0][1], t[1][2] - t[0][2])

    return v0[0] * v1[0] + v0[1] * v1[1] + v0[2] * v1[2]

def segment_length(s):

    # s is a pair of triples

    return math.sqrt((s[1][0] - s[0][0]) ** 2 + (s[1][1] - s[0][1]) ** 2 + (s[1][2] - s[0][2]) ** 2)

def union(a, b):

    """
    a and b are pairs of triples that define line
    segments in space.  the pairs are lists of length 2.
    the pairs are the endpoints of line segments in space,
    and are assumed to be collinear.  

    returns None if they don't overlap, or the endpoints of the
    combined line segment if they do.

    PRECONDITION:  the two line segments point in the same direction.
    """

    #
    # A *-------------->
    # B                    *----->
    #
    # A *-------------->
    # B             *----->
    #
    # A *-------------->
    # B    *----->
    #
    # A    *-------------->
    # B *----->
    #
    # A           *-------------->
    # B *----->
    #

    #   *-----A-------------->
    #   |                    |
    #   E                    F
    #   |                    |
    #   V                    V
    #   *-----B-------------->

    # these are too hard to draw, so let's just make a list
    #
    # C : A> to B*
    # E : A* to B*
    # F : A> to B>
    # G : A* to B>
    # D : B> to A*

    c = [a[1], b[0]]
    e = [a[0], b[0]]
    f = [a[1], b[1]]
    g = [a[0], b[1]]
    d = [b[1], a[0]]

    dp = dot_product(a, c)
    if dp > 0:
        return None

    dp = dot_product(b, d)
    if dp > 0:
        return None

    if a[1] == b[0]:
        return [a[0], b[1]]

    if b[1] == a[0]:
        return [b[0], a[1]]
        
    dp = dot_product(e, g)
    if dp < 0:
        p0 = b[0]
    elif dp > 0:
        p0 = a[0]
    elif a[0] == b[0]:
        p0 = a[0]
    else:
        raise Exception('1:  didn\'t think of this')

    dp = dot_product(c, f)
    if dp < 0:
        p1 = b[1]
    elif dp > 0:
        p1 = a[1]
    elif a[1] == b[1]:
        p1 = a[1]
    else:
        raise Exception('2:  didn\'t think of this')

    return [p0, p1]

def intersection(a, b):
    
    """
    a and b are line segments.  return b clipped to the bounds of a.
    """

    # take the dot product of the two vectors to determine if they are pointed
    # in the same direction.  flip one of them if not.

    dp = dot_product(a, b)

    if dp < 0:
        b = b[::-1]

    # these are too hard to draw, so let's just make a list
    #
    # C : A> to B*
    # E : A* to B*
    # F : A> to B>
    # G : A* to B>
    # D : B> to A*

    c = [a[1], b[0]]
    e = [a[0], b[0]]
    f = [a[1], b[1]]
    g = [a[0], b[1]]
    d = [b[1], a[0]]

    dp = dot_product(a, c)
    if dp > 0:
        return None

    dp = dot_product(b, d)
    if dp > 0:
        return None

    """
    if EG < 0:
        A* is the end
    if EG > 0:
        B* is the end
    if B* = A*
        A* is the end
    if B> = A*
        return no intersection

    if CF < 0:
        A> is the end
    if CF > 0:
        B> is the end
    if A> = B*
        return no intersection
    if A> = B>
        A> is the end
    """

    if b[1] == a[0]:
        return None

    if a[1] == b[0]:
        return None

    eg = dot_product(e, g)
    if eg < 0:
        p0 = a[0]
    elif eg > 0:
        p0 = b[0]
    elif b[0] == a[0]:
        p0 = a[0]
    else:
        raise Exception('3:  didn\'t think of this')

    cf = dot_product(c, f)
    if cf < 0:
        p1 = a[1]
    elif cf > 0:
        p1 = b[1]
    elif a[1] == b[1]:
        p1 = a[1]
    else:
        raise Exception('4:  didn\'t think of this')

    return [p0, p1]

def one_pass(pairs):

    i = 0
    j = 0
    l = len(pairs)

    changes = False
    while i < l - 1:
        j = i + 1
        while j < l:
            u = union(pairs[i], pairs[j])
            if u is not None:

                pairs.remove(pairs[j])
                pairs.remove(pairs[i])
                pairs.append(u)
                return True

            j += 1
        i += 1

    return False

def union_multiple(pairs):

    """
    algorithm (until i come up with a better one)
    
    for each line segment in the set
        union it with the others
        if there is a union
            remove the operands from the set and replace it with the union
            start over

    repeat until nothing changes.
    like bubble sort.
    """

    while True:

        r = one_pass(pairs)
        if r == False:
            break

    return pairs

def process(line_segment, spheres):

    chords = []
    for s in spheres:
        chord = sphere_intersects_line(s, line_segment)
        if chord is None:
            continue

        if len(chord) < 2:
            continue

        # make sure that the chord points the same direction as the line
        if dot_product(line_segment, chord) < 0:
            chord = chord[::-1]

        chords.append(chord)

    clipped_chords = union_multiple(chords)

    final = []
    for c in clipped_chords:
        x = intersection(line_segment, c)
        if x:
            final.append(x)

    l = 0
    for f in final:
        l += segment_length(f)

    # burrowing rate is 1mm every 10s
    total_time = 10 * (segment_length(line_segment) - l)

    return total_time

# the gist of the algorithm is to find all the chords formed by the
# line and the spheres.  we'll take the union of these segments and
# compute the length(s) of the resulting segments.

"""
The input file contains descriptions of several cheese mite test
cases. Each test case starts with a line containing a single integer n
(0 <= n <= 100), the number of holes in the cheese. This is followed
by n lines containing four integers xi, yi, zi, ri each. These
describe the centers (xi, yi, zi) and radii ri (ri > 0) of the
holes. All values here (and in the following) are given in
millimeters.

The description concludes with two lines containing three integers
each. The first line contains the values xA, yA, zA, giving Amelia's
position in the cheese, the second line containing xO, yO, zO, gives
the position of the other mite.  The input file is terminated by a
line containing the number -1.
"""

if __name__ == '__main__':

    filename = 'input1.txt'

    f = open(filename, 'r')

    cheesecount = 1  # start at 1

    while True:
        l = f.readline()
        holes = int(l)

        if holes == -1:
            break

        spheres = []
        for i in range(holes):
            l = f.readline()
            x,y,z,r = l.split()
            s = Sphere(int(x), int(y), int(z), int(r))
            spheres.append(s)

        l = f.readline()
        x,y,z = l.split()
        p0 = (int(x), int(y), int(z))

        l = f.readline()
        x,y,z = l.split()
        p1 = (int(x), int(y), int(z))

        line_segment = [p0, p1]
        t = process(line_segment, spheres)
        print 'Cheese %d: Travel time = %d sec' % (cheesecount, t)

        cheesecount += 1

