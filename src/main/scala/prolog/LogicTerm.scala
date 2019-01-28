package prolog

trait LogicTerm {

  def value(): String
}

case class Constant(private val _value: String) extends LogicTerm {

  require(!_value.isEmpty(), "The value cannot be empty")

  override def value(): String = _value.toString().filterNot(character => character.isWhitespace).toString()

}

case class Variable(private val _value: Char) extends LogicTerm {

  require(_value.isLetter, "The variable must be a single letter")

  override def value(): String = _value.toString().toUpperCase()
}

case class LogicList(private val terms: LogicTerm*) extends LogicTerm {

  override def value(): String = {
    if(terms.isEmpty) {
      "[]"
    } else {
      "[" + terms.map(logicTerm => logicTerm.value()).mkString(",") + "]"
    }
  }
}