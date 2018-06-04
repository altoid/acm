package object addmul {
  case class Instruction(count: Int, op: Char) {
    assert(count > 0)
    assert(op == 'M' || op == 'A')
  }

  type Program = List[Instruction]

  implicit def prog2String(prog: Program) = prog.map(p => p.count.toString + p.op).mkString(" ")
}
