package addmul

// file:///Users/dtomm/Downloads/2011WorldFinalProblemSet.pdf

//object ProgramOrdering extends Ordering[String] {
//  def compare(a: String, b: String) = {
//    val cmp = a.length compare b.length
//    if (cmp == 0) a compare b
//    else cmp
//  }
//}

class AddMul {

  def addInstruction(prog: Program, instr: Instruction): Program = {
    val lastInstr = prog.lastOption
    lastInstr match {
      case Some(x) => {
        if (x.op == instr.op) {
          prog.init :+ Instruction(x.count + 1, x.op)
        }
        else {
          prog :+ instr
        }
      }
      case None => prog :+ instr
    }
  }

  def evaluateHelper(program: Program, partial_answer: Int, addend: Int, multiplicand: Int): Int = {
    program match {
      case Nil => partial_answer
      case Instruction(n, 'A') :: t => evaluateHelper(t, partial_answer + n * addend, addend, multiplicand)
      case Instruction(n, 'M') :: t => evaluateHelper(t, partial_answer * math.pow(multiplicand, n).toInt, addend, multiplicand)
      case _ => throw new IllegalArgumentException(s"$program")
    }
  }

  def evaluate(program: Program, input: Int, addend: Int, multiplicand: Int): Int = {
    evaluateHelper(program, input, addend, multiplicand)
  }
}

  def compareInstructions(a: List[(Int, Char)], b: List[(Int, Char)]): Int = {
    if (a.isEmpty) -1
    else if (b.isEmpty) 1
    else if (a == b) 0
    else {
      val ah = a.head
      val bh = b.head

      if (ah == bh) compareInstructions(a.tail, b.tail)
      else if (ah._2 != bh._2) ah._2 compare bh._2
      else if (ah._1 > bh._1) {
        val newhead = (ah._1 - bh._1, ah._2)
        compareInstructions(newhead :: a.tail, b.tail)
      }
      else {
        val newhead = (bh._1 - ah._1, ah._2)
        compareInstructions(a.tail, newhead :: b.tail)
      }
    }
  }

  def compare(that:Program): Int = {
    compareInstructions(instructions, that.instructions)
  }
}

class xxxxAddMul(val addend: Int, val multiplicand: Int, val output_lowend: Int, val output_highend: Int) {
  require(addend > 0)
  require(multiplicand > 0)
  require(output_lowend > 0)
  require(output_highend > 0)
  require(output_lowend <= output_highend)

  def generateHelper(partial_program: List[Char], partial_result: Int, operation: Char, level: Int): List[String] = {
    println(("    " * level) + partial_program.mkString + ", " + partial_result + ", " + operation)

    val result_of_operation = operation match {
      case 'M' => partial_result * multiplicand
      case 'A' => partial_result + addend
    }

    if (result_of_operation < output_lowend) {
      val result_by_mult = generateHelper(partial_program :+ operation, result_of_operation, 'M', level + 1)
      val result_by_add = generateHelper(partial_program :+ operation, result_of_operation, 'A', level + 1)

      List(result_by_mult, result_by_add).flatten
    }
    else if (result_of_operation >= output_lowend && result_of_operation <= output_highend) {
      val new_program = partial_program :+ operation
      List(new_program.mkString)
    }
    else {
      List()
    }
  }

  def generatePrograms(input: Int): List[String] = {
    if (multiplicand == 1) {
      // calculate 'howmany(output_lowend - input, addend)'.  we subtract addend from lowend
      // because we have an initial value of 'input'.
      val adds = ((output_lowend - input) + (addend - 1)) / addend
      val result = input + adds * addend
      if (result > output_highend) List()
      else {
        List(result.toString + "A")
      }
    }
    else {
      val result_by_mult = generateHelper(List(), input, 'M', 1)
      val result_by_add = generateHelper(List(), input, 'A', 1)

      val result = List(result_by_mult, result_by_add).flatten
      result
    }
  }

  def encodeList(prog: List[Char]): List[String] = {
    prog match {
      case Nil => List()
      case h :: t => {
        val (front, back) = prog.span(p => p == h)
        val l = front.length
        val s = f"$l%d$h%c"
        s :: encodeList(back)
      }
    }
  }

  def encode(program: String): String = {
    // run-length encode a string of As and Ms.
    encodeList(program.toList).mkString(" ")
  }

  def runHelper(partial_result: Int, program: List[Char]): Int = {
    program match {
      case 'M' :: t => runHelper(partial_result * multiplicand, t)
      case 'A' :: t => runHelper(partial_result + addend, t)
      case h :: t => throw new IllegalArgumentException(s"wtf is $h?")
      case Nil => partial_result
    }
  }

  def runProgram(input: Int, program: String): Int = {
    val plist = program.toList
    runHelper(input, plist)
  }

  def solve(p: Int, q: Int) = {
    if (output_lowend <= p && q <= output_highend) "empty"
    else {
      var programs = generatePrograms(q)

      // println(programs)

      for (k <- p until q) {
        programs = programs.filter { x =>
          val result = runProgram(k, x)
          result >= output_lowend && result <= output_highend
        }
      }
      if (programs.isEmpty) "impossible"
      else {
        programs.sorted(ProgramOrdering).head
      }
    }
  }
}

// beware m == 1
