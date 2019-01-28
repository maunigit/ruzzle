package prolog

trait Predicate {

  def name: String
  def apply(index: Int): String
}

trait PredicateBuilder {

  def name: String
  def +=(element: LogicElement): PredicateBuilder
  def create(): Predicate
}

object PredicateBuilder {

  def apply(name: String): PredicateBuilder = new PredicateBuilderImpl(name)

  private class PredicateBuilderImpl(override val name: String) extends PredicateBuilder {

    var elements: List[LogicElement] = List()

    require(!name.isEmpty(), "The predicate must have a name!")

    override def +=(element: LogicElement): PredicateBuilder = {
      elements = elements :+ element
      this
    }

    override def create(): Predicate = new PredicateImpl(name.filterNot(character => character.isWhitespace).toString(), elements)
  }

  private class PredicateImpl(override val name: String, val elements:List[LogicElement]) extends Predicate {

    override def apply(index: Int): String = elements(index).value()

    override def toString: String = name + "(" + elements.map(element => element.value()).mkString(",") + ")"

  }

  implicit def anyValToString(anyVal: AnyVal): String = anyVal.toString()

}




