package addmul

import org.scalatest.FunSuite

class AddMulTest extends FunSuite {

  ignore("running") {
    val addmul = new AddMul(1, 3, 22, 33)

    val input = 3

    var result = addmul.runProgram(input, "MM")
    assert(result == 27)
    result = addmul.runProgram(input, "AA")
    assert(result == 5)
    result = addmul.runProgram(input, "AAMM")
    assert(result == 45)
  }

  test("sorting") {
    val t = List("mmm", "aaa", "mm", "am", "ma", "aa")
    assert(List("aa", "am", "ma", "mm", "aaa", "mmm") == t.sorted(ProgramOrdering))
  }

  test("encode") {
    val addmul = new AddMul(1, 2, 10, 20)
    val uncompiled = List("AMM", "MAM", "AAAM", "AMAM", "MAAM", "AAAAM", "MAAAM", "AAAAAM", "AAAAAAM")
    val test = uncompiled.map(x => addmul.encode(x))
    assert(List("1A 2M", "1M 1A 1M", "3A 1M", "1A 1M 1A 1M", "1M 2A 1M", "4A 1M", "1M 3A 1M", "5A 1M", "6A 1M") == test)
  }

  ignore("run programs on all inputs") {
    val addmul = new AddMul(1, 2, 10, 20)

    val p = 2
    val q = 3
    var programs = addmul.generatePrograms(q)

    println(programs)

//    // gimme all the programs that work for 2
//    val x = programs.filter { p =>
//      val result = addmul.runProgram(2, p)
//      result >= addmul.output_lowend && result <= addmul.output_highend
//    }
//    println(x)

    for (k <- p until q) {
      programs = programs.filter { x =>
        val result = addmul.runProgram(k, x)
        result >= addmul.output_lowend && result <= addmul.output_highend
      }
    }
    println(">>>>>>>>>>>>>")
    println(programs.sorted(ProgramOrdering))
    println("<<<<<<<<<<<<<")
  }

  test("m == 1") {
    val addmul = new AddMul(1, 1, 10, 20)
    val solution = addmul.solve(2, 3)
    println(solution)
  }

  ignore("should be empty program") {
    val addmul = new AddMul(5, 3, 2, 3)
    val solution = addmul.solve(2, 3)
    println(solution)
  }

  ignore("impossible") {
    val addmul = new AddMul(3, 2, 4, 5)
    val solution = addmul.solve(2, 3)
    println(solution)
  }

  ignore("example 1") {
    val addmul = new AddMul(1, 2, 10, 20)
    val solution = addmul.solve(2, 3)
    println(solution)
  }

  ignore("example 2") {
    val addmul = new AddMul(1, 3, 22, 33)
    val solution = addmul.solve(2, 3)
    println(solution)
  }
}
