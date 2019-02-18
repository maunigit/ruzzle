package prolog

/**
  * A Logic Predicate (FOL).
  */
trait Predicate {

  /**
    * Get the predicate name.
    * @return
    */
  def name: String

  /**
    * Obtain the element at index.
    * @param index
    * @return
    */
  def apply(index: Int): LogicTerm

  /**
    * Check if a predicate contains variables.
    * @return
    */
  def containVariables(): Boolean
}

/**
  * The Predicate Builder.
  */
trait PredicateBuilder {

  /**
    * The predicate name.
    * @return
    */
  def name: String

  /**
    * Add a Logic Term inside the predicate.
    * @param term
    * @return
    */
  def +=(term: LogicTerm): PredicateBuilder

  /**
    * Create the ultimate predicate.
    * @return
    */
  def create():Predicate
}

/**
  * The Predicate Builder companion object.
  */
object PredicateBuilder {

  def apply(predicateName: String): PredicateBuilder = new PredicateBuilderImpl(predicateName)

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




