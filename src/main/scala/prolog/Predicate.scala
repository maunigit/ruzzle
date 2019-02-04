package prolog

trait Predicate {

  def name: String
  def apply(index: Int): LogicTerm
  def containVariables(): Boolean
}

trait PredicateBuilder {

  def name: String
  def +=(term: LogicTerm): PredicateBuilder
  def create():Predicate
}

object PredicateBuilder {

  def apply(name: String): PredicateBuilder = new PredicateBuilderImpl(name)

  private class PredicateBuilderImpl(override val name: String) extends PredicateBuilder {

    var terms: List[LogicTerm] = List()

    require(!name.isEmpty(), "The predicate must have a name!")

    override def +=(term: LogicTerm): PredicateBuilder = {
      terms = terms :+ term
      this
    }

    override def create(): Predicate = new PredicateImpl(name.filterNot(character => character.isWhitespace).toString(), terms)
  }

  private class PredicateImpl(override val name: String, val elements:List[LogicTerm]) extends Predicate {

    override def apply(index: Int): LogicTerm = elements(index)

    override def toString: String = name + "(" + elements.map(element => element.value()).mkString(",") + ")"

    override def containVariables(): Boolean = elements.exists(logicTerm => logicTerm.isInstanceOf[Variable])
  }

  implicit def anyValToString(anyVal: AnyVal): String = anyVal.toString()

  implicit def charToVariable(charVariable: Char): Variable = Variable(charVariable)

  implicit def anyValToConstant(constant: AnyVal): Constant = Constant(constant.toString())

  implicit def stringToConstant(variable: String): Constant = Constant(variable)

}




