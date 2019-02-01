package model

import java.net.URL

import scala.collection.JavaConverters._
import edu.mit.jwi
import edu.mit.jwi.IDictionary
import edu.mit.jwi.item.IWordID
import edu.mit.jwi.morph.WordnetStemmer

object Dictionary {

  val path: URL = getClass.getResource("/dict")
  val dictionary: IDictionary = new jwi.Dictionary(path)
  dictionary.open()

  def isPresent(word: Word): Boolean = lemma(word).isDefined

  def lemma(word: Word): Option[String] = {
    val stemmer: WordnetStemmer = new WordnetStemmer(dictionary)
    stemmer.findStems(word.value, word.tag).asScala.toList match {
      case h::_ => Option(dictionary.getIndexWord(h, word.tag).getLemma())
      case Nil => Option.empty
    }
  }

  def areSynonyms(word1: Word, word2: Word): Boolean = if(isPresent(word1) && isPresent(word2)) {
    meanings(word1).foreach(wordID => dictionary.getWord(wordID).getSynset().getWords()
      .forEach(word => {println(word.getLemma()); if(word.getLemma().equals(lemma(word2).get)) return true}))
    false
  } else false

  private def meanings(word: Word): List[IWordID] = if(isPresent(word)) {
    dictionary.getIndexWord(lemma(word).get, word.tag).getWordIDs().asScala.toList
  } else List()

}


