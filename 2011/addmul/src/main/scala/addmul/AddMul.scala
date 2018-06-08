package addmul

// file:///Users/dtomm/Downloads/2011WorldFinalProblemSet.pdf

object ProgramOrdering extends Ordering[Program]
{
  // sort a collection of programs as though they were just strings of As and Ms.
  def compare(a: Program, b: Program): Int = {
    val alen = AddMul.length(a)
    val blen = AddMul.length(b)
    if (alen != blen) alen - blen
    else {
      val ah = a.head
      val bh = b.head

      if (ah == bh) compare(a.tail, b.tail)
      else if (ah.op != bh.op) ah.op compare bh.op
      else if (ah.count > bh.count) {
        val newhead = Instruction(ah.count - bh.count, ah.op)
        compare(newhead :: a.tail, b.tail)
      }
      else {
        val newhead = Instruction(bh.count - ah.count, ah.op)
        compare(a.tail, newhead :: b.tail)
      }
    }
  }
}

class AddMul(addend: Int, multiplicand: Int, p: Int, q: Int, r: Int, s: Int) {
  require(addend > 0)
  require(multiplicand > 0)
  require(r > 0)
  require(s > 0)
  require(r <= s)
  require(p > 0)
  require(q > 0)
  require(p <= q)

  def evaluateHelper(program: Program, partial_answer: Int, addend: Int, multiplicand: Int): Int = {
    program match {
      case Nil => partial_answer
      case Instruction(n, 'A') :: t => evaluateHelper(t, partial_answer + n * addend, addend, multiplicand)
      case Instruction(n, 'M') :: t => evaluateHelper(t, partial_answer * math.pow(multiplicand, n).toInt, addend, multiplicand)
      case _ => throw new IllegalArgumentException(s"$program")
    }
  }

  def evaluate(program: Program, input: Int): Int = {
    evaluateHelper(program, input, addend, multiplicand)
  }

  def generateHelper(partial_program: Program, partial_result: Int, operation: Char, level: Int): List[Program] = {
    // println(("    " * level) + partial_program mkString " " + ", " + partial_result + ", " + operation)

    val result_of_operation = operation match {
      case 'M' => partial_result * multiplicand
      case 'A' => partial_result + addend
    }

    if (result_of_operation < r) {
      val newProgram = AddMul.addInstruction(partial_program, (1, operation))
      val result_by_mult = generateHelper(newProgram, result_of_operation, 'M', level + 1)
      val result_by_add = generateHelper(newProgram, result_of_operation, 'A', level + 1)

      List(result_by_mult, result_by_add).flatten
    }
    else if (result_of_operation >= r && result_of_operation <= s) {
      List(AddMul.addInstruction(partial_program, Instruction(1, operation)))
    }
    else {
      List()
    }
  }

  def generatePrograms(input: Int): List[Program] = {
    if (multiplicand == 1) {
      // calculate 'howmany(output_lowend - input, addend)'.  we subtract addend from lowend
      // because we have an initial value of 'input'.
      val adds = ((r - input) + (addend - 1)) / addend
      val result = input + adds * addend
      if (result > s) List()
      else {
        List(AddMul.addInstruction(Program(), Instruction(result, 'A')))
      }
    }
    else {
      val result_by_mult = generateHelper(Program(), input, 'M', 1)
      val result_by_add = generateHelper(Program(), input, 'A', 1)

      val result = List(result_by_mult, result_by_add).flatten
      result
    }
  }

}

object AddMul {
  def display(s: String) = println(s)

  def addInstruction(prog: Program, instr: Instruction): Program = {
    val lastInstr = prog.lastOption
    lastInstr match {
      case Some(x) => {
        if (x.op == instr.op) {
          prog.init :+ Instruction(x.count + instr.count, x.op)
        }
        else {
          prog :+ instr
        }
      }
      case None => prog :+ instr
    }
  }

  def length(p: Program): Int = {
    p.map(i => i.count).sum
  }
}


//class xxxxAddMul(val addend: Int, val multiplicand: Int, val output_lowend: Int, val output_highend: Int) {
//  require(addend > 0)
//  require(multiplicand > 0)
//  require(output_lowend > 0)
//  require(output_highend > 0)
//  require(output_lowend <= output_highend)
//
//
//  def encodeList(prog: List[Char]): List[String] = {
//    prog match {
//      case Nil => List()
//      case h :: t => {
//        val (front, back) = prog.span(p => p == h)
//        val l = front.length
//        val s = f"$l%d$h%c"
//        s :: encodeList(back)
//      }
//    }
//  }
//
//  def encode(program: String): String = {
//    // run-length encode a string of As and Ms.
//    encodeList(program.toList).mkString(" ")
//  }
//
//  def runHelper(partial_result: Int, program: List[Char]): Int = {
//    program match {
//      case 'M' :: t => runHelper(partial_result * multiplicand, t)
//      case 'A' :: t => runHelper(partial_result + addend, t)
//      case h :: t => throw new IllegalArgumentException(s"wtf is $h?")
//      case Nil => partial_result
//    }
//  }
//
//  def runProgram(input: Int, program: String): Int = {
//    val plist = program.toList
//    runHelper(input, plist)
//  }
//
//  def solve(p: Int, q: Int) = {
//    if (output_lowend <= p && q <= output_highend) "empty"
//    else {
//      var programs = generatePrograms(q)
//
//      // println(programs)
//
//      for (k <- p until q) {
//        programs = programs.filter { x =>
//          val result = runProgram(k, x)
//          result >= output_lowend && result <= output_highend
//        }
//      }
//      if (programs.isEmpty) "impossible"
//      else {
//        programs.sorted(ProgramOrdering).head
//      }
//    }
//  }
//}

// beware m == 1
