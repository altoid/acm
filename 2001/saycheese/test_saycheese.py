# -*- python -*-

import unittest
from saycheese import *
import math
import pdb

class TestSayCheese(unittest.TestCase):

    def test_nointersection(self):

        sph = Sphere(0, 0, 0, 1)
        line = [(2, 0, 0), (2, 0, 2)]

        solution = sphere_intersects_line(sph, line)
        self.assertIsNone(solution)

    def test_tangent(self):

        s = Sphere(0, 0, 0, 1)
        line = [(1, 0, 0), (1, 0, 1)]

        solution = sphere_intersects_line(s, line)
        self.assertEquals([(1, 0, 0)], solution)

        s = Sphere(0, 0, 0, 1 / math.sqrt(2))
        line = [(1, 0, 0), (0, 1, 0)]

        solution = sphere_intersects_line(s, line)
        print solution

    def test_union(self):

        ###################### a encloses b

        a = [(0,0,0), (0,0,10)]
        b = [(0,0,1), (0,0,9)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (0, 0, 10)], u)

        ###################### no overlap

        a = [(0,0,0), (0,0,1)]
        b = [(0,0,2), (0,0,3)]

#        pdb.set_trace()
        u = union(a, b)
        self.assertIsNone(u)

        ###################### no intersection again

        a = [(0,0,2), (0,0,3)]
        b = [(0,0,0), (0,0,1)]

        u = union(a, b)
        self.assertIsNone(u)

        ######################

        a = [(0,0,0), (0,0,8)]
        b = [(0,0,2), (0,0,10)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (0, 0, 10)], u)

        ######################

        a = [(0,0,2), (0,0,10)]
        b = [(0,0,0), (0,0,8)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (0, 0, 10)], u)

    def test_union_2(self):

        # test cases where chords have one common endpoint.

        ######################

        a = [(0,0,0), (0,0,5)]
        b = [(0,0,4), (0,0,5)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (0, 0, 5)], u)

        ######################

        a = [(0,0,0), (0,0,5)]
        b = [(0,0,5), (0,0,10)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (0, 0, 10)], u)

        ######################

        a = [(0,0,0), (1,1,1)]
        b = [(1,1,1), (5,5,5)]

        u = union(a, b)
        self.assertEquals([(0, 0, 0), (5,5,5)], u)
        
    def test_intersection(self):

        ###################### a encloses b

        a = [(0,0,0), (0,0,10)]
        b = [(0,0,9), (0,0,1)]

        u = intersection(a, b)
        self.assertEquals([(0, 0, 1), (0, 0, 9)], u)

        ###################### no intersection

        a = [(0,0,0), (0,0,1)]
        b = [(0,0,2), (0,0,3)]

        u = intersection(a, b)
        self.assertIsNone(u)

        ###################### no intersection again

        a = [(0,0,2), (0,0,3)]
        b = [(0,0,0), (0,0,1)]

        u = intersection(a, b)
        self.assertIsNone(u)

        ######################

        a = [(0,0,0), (0,0,8)]
        b = [(0,0,2), (0,0,10)]

        u = intersection(a, b)
        self.assertEquals([(0, 0, 2), (0, 0, 8)], u)

        ######################

        a = [(0,0,2), (0,0,10)]
        b = [(0,0,0), (0,0,8)]

        u = intersection(a, b)
        self.assertEquals([(0, 0, 2), (0, 0, 8)], u)

    def test_intersection_2(self):

        # test cases where chords have one common endpoint.

        ######################

        a = [(0,0,0), (0,0,5)]
        b = [(0,0,4), (0,0,5)]

        u = intersection(a, b)
        self.assertEquals([(0, 0, 4), (0, 0, 5)], u)

        ######################

        a = [(0,0,0), (0,0,5)]
        b = [(0,0,5), (0,0,10)]

        u = intersection(a, b)
        self.assertIsNone(u)

        ######################

        a = [(0,0,0), (1,1,1)]
        b = [(1,1,1), (5,5,5)]

        u = intersection(a, b)
        self.assertIsNone(u)
        
    def test_overlapping_chords(self):

        s1 = Sphere(0, 0, 5, 3)
        s2 = Sphere(0, 0, 10, 3)

        line = Line(0, 0, 0, 0, 0, 10)
        sol1 = sphere_intersects_line(s1, line)
        sol2 = sphere_intersects_line(s2, line)

        print sol1
        print sol2

    def test_union_multiple(self):

        a = [(0,0,0), (0,0,2)]
        b = [(0,0,3), (0,0,6)]
        c = [(0,0,7), (0,0,10)]

        d = [(0,0,1), (0,0,4)]
        e = [(0,0,5), (0,0,8)]

        l = [a,b,c,d,e]

        result = union_multiple(l)
        self.assertEquals([[(0, 0, 0), (0, 0, 10)]], result)

    def test_union_multiple_2(self):

        # common endpoints

        a = [(0,0,0), (0,0,2)]
        b = [(0,0,2), (0,0,5)]
        c = [(0,0,5), (0,0,7)]
        d = [(0,0,7), (0,0,10)]

        l = [a,b,c,d]

        result = union_multiple(l)
        self.assertEquals([[(0, 0, 0), (0, 0, 10)]], result)

if __name__ == '__main__':
    unittest.main()

