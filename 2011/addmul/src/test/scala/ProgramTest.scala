package addmul

import org.scalatest.FunSuite

class ProgramTest extends FunSuite {
  test("isempty") {
    val p = new Program()
    assert(p.isEmpty)
  }

  test("addinstruction") {
    val p = new Program()
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
}
