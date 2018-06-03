package addmul

// file:///Users/dtomm/Downloads/2011WorldFinalProblemSet.pdf

object ProgramOrdering extends Ordering[String] {
  def compare(a:String, b:String) = {
    val cmp = a.length compare b.length
    if (cmp == 0) a compare b
    else cmp
  }
}

final class Program {
  var instructions: List[(Int, Char)] = List[(Int, Char)]()

  def isEmpty: Boolean = {
    instructions.isEmpty
  }

  override def toString(): String = {
    instructions.map(p => p._1.toString + p._2).mkString(" ")
  }

  def addInstructions(count: Int, instr: Char) = {
    assert(instr == 'M' || instr == 'A')
    assert(count > 0)

    val lastInstr = instructions.lastOption
    lastInstr match {
      case Some(x) => {
        if (x._2 == instr) {
          val newlast = (x._1 + 1, x._2)
          instructions = instructions.init :+ newlast
        }
        else {
          instructions = instructions :+ (count, instr)
        }
      }
      case None => instructions = instructions :+ (count, instr)
    }
  }

  def evaluateHelper(i: Int, addend: Int, multiplicand: Int, tuples: List[(Int, Char)]): Int = {
    tuples match {
      case Nil => i
      case (n, 'A') :: t => evaluateHelper(i + n * addend, addend, multiplicand, t)
      case (n, 'M') :: t => evaluateHelper(i * math.pow(multiplicand, n).toInt, addend, multiplicand, t)
      case _ => throw new IllegalArgumentException(tuples.toString())
    }
  }

  def evaluate(input: Int, addend: Int, multiplicand: Int): Int = {
    evaluateHelper(input, addend, multiplicand, instructions)
  }
}

class AddMul(val addend: Int, val multiplicand: Int, val output_lowend: Int, val output_highend: Int) {
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
      // calculate 'howmany(output_lowend - input, addend).  we subtract addend from lowend
      // because we have an initial value of addend.
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
