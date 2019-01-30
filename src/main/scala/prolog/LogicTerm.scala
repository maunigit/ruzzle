package prolog

trait LogicTerm {

  def value(): String
}

case class Constant(private val _value: String) extends LogicTerm {

  require(!_value.isEmpty(), "The value cannot be empty")

  override def value(): String = _value.toString().filterNot(character => character.isWhitespace).toList match {
    case h::Nil => h.toLower.toString()
    case h::t => h.toLower.toString() + t.mkString
  }

}

case class Variable(private val _value: Char) extends LogicTerm {

  require(_value.isLetter, "The variable must be a single letter")

  override def value(): String = _value.toString().toUpperCase()
}

object LogicList {

  def apply(terms: LogicTerm*): LogicTerm = LogicList(terms.toIterable)

  def from(terms: Iterable[LogicTerm]): LogicTerm = LogicList(terms)

  private case class LogicList(private val terms: Iterable[LogicTerm]) extends LogicTerm {

    override def value(): String = {
      if(terms.isEmpty) {
        "[]"
      } else {
        "[" + terms.map(logicTerm => logicTerm.value()).mkString(",") + "]"
      }
    }
  }

}

