package addmul

import org.scalatest.FunSuite

class AddMulTest extends FunSuite {

  test("test1") {
    val addmul = new AddMul(1, 3, 22, 33)

    val input = 3

    val best = addmul.bestProgram(input)
    println(s"$input:  $best")
  }

  test("sorting") {
    def mySortFunc(a: String, b: String): Boolean = {
      // sort by length then lexicographically
      if (a.length < b.length) true
      else if (b.length < a.length) false
      else a < b
    }
    val t = List("mmm", "aaa", "mm", "am", "ma", "aa")
    println(t.sortWith(mySortFunc))
  }
}
