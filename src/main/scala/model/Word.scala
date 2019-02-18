package model

import edu.mit.jwi.item.POS
import model.WordTag.WordTag

/**
  * The enumeration that explain the type of the word (noun, adverb, adjective or verb).
  */
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

/**
  * The word defined by the player.
  * @param value
  * @param tag
  */
case class Word(value: String, tag: WordTag) extends Serializable {

  require(!value.isEmpty, "The word cannot be empty.")

  override def equals(obj: scala.Any): Boolean = obj match {
    case Word(objValue, _) => value.toLowerCase == objValue.toLowerCase
    case _ => false
  }
}

