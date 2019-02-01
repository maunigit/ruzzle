package model

import edu.mit.jwi.item.POS
import model.WordTag.WordTag

object WordTag extends Enumeration {

  type WordTag = Value
  val Noun, Adverb, Adjective, Verb = Value

  implicit def WordTagToPos(tag: WordTag): POS = tag match {
    case Noun => POS.NOUN
    case Adverb => POS.ADVERB
    case Adjective => POS.ADJECTIVE
    case Verb => POS.VERB
  }
  
}

case class Word(val value: String, val tag: WordTag) {

  require(!value.isEmpty, "The word cannot be empty.")
}

