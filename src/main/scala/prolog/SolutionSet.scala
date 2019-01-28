package prolog

import java.util.Optional

import alice.tuprolog.{SolveInfo, Var}

trait SolutionSet {
  
  def apply(variable: Variable): String
  def isEmpty(): Boolean
}

object SolutionSet {

  def apply(solveInfo: SolveInfo, predicate: Predicate): SolutionSet = new SolutionSetImpl(solveInfo, predicate)

  private class SolutionSetImpl(val solveInfo: SolveInfo, val predicate: Predicate) extends SolutionSet {

    override def apply(variable: Variable): String = {
      val exactVariable: String = variable.value()
      val bindVariable: Optional[Var] = solveInfo.getBindingVars().stream().filter(bindingVar => bindingVar.getName() == exactVariable).findFirst()
      if(bindVariable.isPresent()) {
        bindVariable.get().getTerm().toString()
      }
      throw new IllegalArgumentException("The variable is undefined.")
    }

    override def isEmpty(): Boolean = !predicate.containVariables()
  }
}


