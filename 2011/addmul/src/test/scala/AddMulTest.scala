package addmul

import org.scalatest.FunSuite

class AddMulTest extends FunSuite {

  test("tostring") {
    val x = Instruction(2, 'M')
    var a = Program()

    a = AddMul.addInstruction(a, (3, 'M'))

    a = AddMul.addInstruction(a, (2, 'M'))

    a = AddMul.addInstruction(a, (3, 'A'))

    a = AddMul.addInstruction(a, x)
//    println(a)
//    println(a mkString " ")
//    AddMul.display(a)
    assert("5M 3A 2M" === (a mkString " "))

    a = AddMul.addInstruction(Program(), Instruction(1, 'A'))
    a = AddMul.addInstruction(a, Instruction(2, 'A'))
    println(a)
  }

  test("running") {
    val addmul = new AddMul(1, 3, 2, 3, 22, 33)

    val input = 3
    var p = Program()
    p = AddMul.addInstruction(p, (2, 'M'))

    var result = addmul.evaluate(p, input)
    assert(result == 27)

    p = Program()
    p = AddMul.addInstruction(p, (2, 'A'))

    result = addmul.evaluate(p, input)
    assert(result == 5)

    p = AddMul.addInstruction(p, (2, 'M'))

    result = addmul.evaluate(p, input)
    assert(result == 45)
  }

  test("generatePrograms") {
    val addmul = new AddMul(1, 3, 2, 3, 22, 33)

    val ps = addmul.generatePrograms(3)
    println(ps)
    val ps_sorted = ps.sorted(ProgramOrdering)
    println(ps_sorted)
  }

//  ignore("sorting") {
//    val t = List("mmm", "aaa", "mm", "am", "ma", "aa")
//    assert(List("aa", "am", "ma", "mm", "aaa", "mmm") == t.sorted(ProgramOrdering))
//  }
//
//  ignore("encode") {
//    val addmul = new AddMul(1, 2, 10, 20)
//    val uncompiled = List("AMM", "MAM", "AAAM", "AMAM", "MAAM", "AAAAM", "MAAAM", "AAAAAM", "AAAAAAM")
//    val test = uncompiled.map(x => addmul.encode(x))
//    assert(List("1A 2M", "1M 1A 1M", "3A 1M", "1A 1M 1A 1M", "1M 2A 1M", "4A 1M", "1M 3A 1M", "5A 1M", "6A 1M") == test)
//  }
//
//  ignore("run programs on all inputs") {
//    val addmul = new AddMul(1, 2, 10, 20)
//
//    val p = 2
//    val q = 3
//    var programs = addmul.generatePrograms(q)
//
//    println(programs)
//
////    // gimme all the programs that work for 2
////    val x = programs.filter { p =>
////      val result = addmul.runProgram(2, p)
////      result >= addmul.output_lowend && result <= addmul.output_highend
////    }
////    println(x)
//
//    for (k <- p until q) {
//      programs = programs.filter { x =>
//        val result = addmul.runProgram(k, x)
//        result >= addmul.output_lowend && result <= addmul.output_highend
//      }
//    }
//    println(">>>>>>>>>>>>>")
//    println(programs.sorted(ProgramOrdering))
//    println("<<<<<<<<<<<<<")
//  }
//
//  ignore("m == 1") {
//    val addmul = new AddMul(1, 1, 10, 20)
//    val solution = addmul.solve(2, 3)
//    println(solution)
//  }
//
//  ignore("should be empty program") {
//    val addmul = new AddMul(5, 3, 2, 3)
//    val solution = addmul.solve(2, 3)
//    println(solution)
//  }
//
//  ignore("impossible") {
//    val addmul = new AddMul(3, 2, 4, 5)
//    val solution = addmul.solve(2, 3)
//    println(solution)
//  }
//
//  ignore("example 1") {
//    val addmul = new AddMul(1, 2, 10, 20)
//    val solution = addmul.solve(2, 3)
//    println(solution)
//  }
//
//  ignore("example 2") {
//    val addmul = new AddMul(1, 3, 22, 33)
//    val solution = addmul.solve(2, 3)
//    println(solution)
//  }
}
