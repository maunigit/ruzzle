package model

import java.net.URL
import scala.collection.JavaConverters._

import edu.mit.jwi
import edu.mit.jwi.IDictionary
import edu.mit.jwi.morph.WordnetStemmer

trait Dictionary {

  def isPresent(word: Word): Boolean
  def areSynonyms(word1: Word, word2: Word): Boolean
  def areAntonyms(word1: Word, word2: Word): Boolean
}

object Dictionary {

  def apply(): Dictionary = DictionaryAdapter

  object DictionaryAdapter extends Dictionary {

    val path: URL = getClass.getResource("/dict")
    val dictionary: IDictionary = new jwi.Dictionary(path)

    override def isPresent(word: Word): Boolean = ???

    def stem(word: Word): Option[String] = {
      val stemmer: WordnetStemmer = new WordnetStemmer(dictionary)
      stemmer.findStems(word.value, word.tag).asScala.toList match {
        case h::_ => Option(h)
        case Nil => Option.empty
      }
    }

    override def areSynonyms(word1: Word, word2: Word): Boolean = ???

    override def areAntonyms(word1: Word, word2: Word): Boolean = ???
  }

}


