# -*- python -*-

import unittest
import factor
import math
import random

class TestFactor(unittest.TestCase):

    def test_convert(self):
        
        print '-' * 44
        r = factor.convert_to_base(20000, 3)
#        self.assertEquals(r, [1,0,0,0,1,0,2,2,0,2])
        print r

        print '-' * 44
        r = factor.convert_to_base(9511216148, 13)
        print r

        print '-' * 44
        r = factor.convert_to_base(9, 3)
        print r

if __name__ == '__main__':
    unittest.main()
