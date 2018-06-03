package addmul

import org.scalatest.FunSuite

class ProgramTest extends FunSuite {
  test("isempty") {
    val p = new Program
    assert(p.isEmpty)
  }

  test("addinstruction") {
    val p = new Program
    assert(p.toString() == "")
    p.addInstructions(2, 'M')
    assert(p.toString() == "2M")
    p.addInstructions(3, 'M')
    assert(p.toString() == "3M")
    p.addInstructions(1, 'A')
    assert(p.toString() == "3M 1A")
  }

  test("evaluate") {
    val p = new Program

    p.addInstructions(3, 'M')
    p.addInstructions(1, 'A')

    assert(p.evaluate(3, 2, 3) == 83)

    p.addInstructions(1, 'M')

    assert(p.evaluate(3, 2, 3) == 249)
  }

  test("compare") {
    val p1 = new Program
    p1.addInstructions(9, 'A')  // 9A

    val p2 = new Program
    p2.addInstructions(100, 'A')  // 100A

    assert(p1 < p2)

    val p3 = new Program
    p3.addInstructions(9, 'A')
    p3.addInstructions(1, 'M')  // 9A 1M

    assert(p3 > p2)

    val emptyProgram = new Program

    assert(emptyProgram < p1)
    assert(p1 > emptyProgram)
    assert(p1 === p1)
  }

  test("equality") {
    val list1 = List((2, 'M'), (3, 'A'))
    val list2 = List((2, 'M'), (3, 'A'))

    assert(list1 === list2)

    val p1 = new Program
    p1.addInstructions(9, 'A')

    val p2 = new Program
    p2.addInstructions(100, 'A')

    var sortMe = List(p2, p1)

    assert(List(p1, p2) === sortMe.sorted)

    val p3 = new Program
    p3.addInstructions(9, 'A')
    p3.addInstructions(1, 'M')

    sortMe = List(p2, p3)

    assert(List(p2, p3) === sortMe.sorted)
  }
}
