package prolog

import java.util.Optional

import alice.tuprolog.{SolveInfo, Var}

/**
  * The tuProlog solution set created as response of specific goal.
  */
trait SolutionSet {

  /**
    * Obtain the value of a specific unified variable.
    * @param variable
    * @return
    */
  def apply(variable: Variable): String

  /**
    * Check if the solution set does not contain variables.
    * @return
    */
  def isEmpty(): Boolean
}

/**
  * The tuProlog solution set companion object.
  */
object SolutionSet {

  def apply(solveInfo: SolveInfo, predicate: Predicate): SolutionSet = new SolutionSetImpl(solveInfo, predicate)

  private class SolutionSetImpl(val solveInfo: SolveInfo, val predicate: Predicate) extends SolutionSet {

    override def apply(variable: Variable): String = {
      val exactVariable: String = variable.value()
      val bindVariable: Optional[Var] = solveInfo.getBindingVars().stream().filter(bindingVar => bindingVar.getName().equals(exactVariable)).findFirst()
      if(bindVariable.isPresent()) {
        bindVariable.get().getTerm().toString()
      } else {
        throw new IllegalArgumentException("The variable is undefined.")
      }
    }

    override def isEmpty(): Boolean = !predicate.containVariables()
  }
}


