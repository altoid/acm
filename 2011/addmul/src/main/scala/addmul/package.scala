package object addmul {
  case class Instruction(count: Int, op: Char) {
    assert(count > 0)
    assert(op == 'M' || op == 'A')

    override def toString = "%s%s" format(count, op)
  }

  type Program = List[Instruction]

  def Program(x: Instruction*) = List(x: _*) // wtf is this and why does it work?

  implicit def prog2String(prog: Program): String = prog.map(p => p.toString).mkString(" ")
  implicit def tuple2Instr(t: (Int, Char)) = Instruction(t._1, t._2)
}
