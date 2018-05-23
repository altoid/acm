#!/usr/bin/env python

import unittest
import b

class TestBipartite(unittest.TestCase):
    def testBasic(self):
        self.assertTrue(b.isBipartite(1000))
        self.assertFalse(b.isBipartite(01))
        self.assertFalse(b.isBipartite(001))
        self.assertFalse(b.isBipartite(011))

        self.assertTrue(b.isBipartite(22222223333333))
        self.assertFalse(b.isBipartite(222222243333333))
        self.assertFalse(b.isBipartite(659283746952))
        self.assertFalse(b.isBipartite(2222222222))

if __name__ == '__main__':
    unittest.main()
