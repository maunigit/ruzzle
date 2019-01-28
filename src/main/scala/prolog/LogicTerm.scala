package prolog

trait LogicElement {

  def value(): String
}

case class Constant(val _value: String) extends LogicElement {

  require(!_value.isEmpty(), "The value cannot be empty")

  override def value(): String = _value.toString().filterNot(character => character.isWhitespace).toString()

}

case class Variable(val _value: Char) extends LogicElement {

  require(_value.isLetter, "The variable must be a single letter")

  override def value(): String = _value.toString().toUpperCase()
}

