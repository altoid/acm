package addmul

// file:///Users/dtomm/Downloads/2011WorldFinalProblemSet.pdf

class AddMul(val addend: Int, val multiplicand: Int, val output_lowend: Int, val output_highend: Int) {
  require(addend > 0)
  require(multiplicand > 0)
  require(output_lowend > 0)
  require(output_highend > 0)
  require(output_lowend <= output_highend)

  def helper(partial_program: List[Char], partial_result: Int, operation: Char, level: Int): List[String] = {
    println(("    " * level) + partial_program.mkString + ", " + partial_result + ", " + operation)

    val result_of_operation = operation match {
      case 'M' => partial_result * multiplicand
      case 'A' => partial_result + addend
    }

    if (result_of_operation < output_lowend) {
      val result_by_mult = helper(partial_program :+ operation, result_of_operation, 'M', level + 1)
      val result_by_add = helper(partial_program :+ operation, result_of_operation, 'A', level + 1)

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
    val result_by_mult = helper(List(), input, 'M', 1)
    val result_by_add = helper(List(), input, 'A', 1)

    val result = List(result_by_mult, result_by_add).flatten
//    println(result)
    result
  }

  def bestProgram(input: Int): String = {
    def mySortFunc(a: String, b: String): Boolean = {
      // sort by length then lexicographically
      if (a.length < b.length) true
      else if (b.length < a.length) false
      else a < b
    }

    generatePrograms(input).sortWith(mySortFunc).head
  }
}

// beware m == 1
