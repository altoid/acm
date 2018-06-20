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

  test("running a program") {
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

  ignore("generatePrograms") {
    val addmul = new AddMul(1, 3, 2, 3, 22, 33)

    val ps = addmul.generatePrograms(3)
    println(ps)
    val ps_sorted = ps.sorted(ProgramOrdering)
    println(ps_sorted)
  }

  test("case 1") {
    val addmul = new AddMul(1, 2, 2, 3, 10, 20)

    val ps = addmul.generatePrograms(1)
    println(ps)
    val ps_sorted = ps.sorted(ProgramOrdering)
    println(ps_sorted)

    val result = addmul.solve()
    println(result)
  }

  test("case 2") {
    val addmul = new AddMul(1, 3, 2, 3, 22, 33)

    val result = addmul.solve()
    println(result)
  }

  test("case 3") {
    val addmul = new AddMul(3,2,2,3,4,5)

    val result = addmul.solve()
    println(result)
  }

  test("case 4") {
    val addmul = new AddMul(5,3,2,3,2,3)

    val result = addmul.solve()
    println(result)
  }

  ignore("case 5, blows stack") {
    val addmul = new AddMul(4, 13, 2, 6, 478958010, 729952078)

    val result = addmul.solve()
    println(result)
  }

  test("case 20") {
    val addmul = new AddMul(10, 9, 57, 65, 817, 950)

    val result = addmul.solve()
    println(result)
  }

  ignore("case 34, blows stack") {
    val addmul = new AddMul(5, 14, 98, 100, 428229, 6894790)

    val result = addmul.solve()
    println(result)
  }

  ignore("case 41") {
    val addmul = new AddMul(1, 1000000000, 1, 1, 999999999, 999999999)

    val ps = addmul.generatePrograms(1)
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
