package model

import java.net.URL

import scala.collection.JavaConverters._
import edu.mit.jwi
import edu.mit.jwi.IDictionary
import edu.mit.jwi.morph.WordnetStemmer

trait Dictionary {

  def isPresent(word: Word): Boolean
  def lemma(word: Word): Option[String]
  def synset(word: Word): Set[String]
}

object Dictionary extends Dictionary {

  val path: URL = getClass.getResource("/dict")
  val dictionary: IDictionary = new jwi.Dictionary(path)
  dictionary.open()

  override def isPresent(word: Word): Boolean = lemma(word).isDefined

  override def lemma(word: Word): Option[String] = {
    val stemmer: WordnetStemmer = new WordnetStemmer(dictionary)
    stemmer.findStems(word.value, word.tag).asScala.toList match {
      case h::_ => Option(dictionary.getIndexWord(h, word.tag).getLemma())
      case Nil => Option.empty
    }
  }

  override def synset(word: Word): Set[String] = if(isPresent(word)) {
    var synset: Set[String] = Set()
    dictionary.getIndexWord(lemma(word).get, word.tag)
      .getWordIDs().asScala.toList
      .foreach(wordId => dictionary.getWord(wordId)
        .getSynset().getWords()
          .forEach(wordId => synset = synset + wordId.getLemma()))
    synset.filterNot(syn => syn == lemma(word).get)
  } else Set()

}


