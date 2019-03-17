package model

import java.io.File
import java.net.URL

import scala.collection.JavaConverters._
import edu.mit.jwi
import edu.mit.jwi.IDictionary
import edu.mit.jwi.morph.WordnetStemmer

/**
  * An English dictionary that aims to check if a word is correct.
  */
trait Dictionary {

  /**
    * Check if a word is present in the dictionary.
    * @param word
    * @return
    */
  def isPresent(word: Word): Boolean

  /**
    * Obtain the lemma of the word, if exists.
    * @param word
    * @return
    */
  def lemma(word: Word): Option[String]

  /**
    * Obtain the synonyms of the word.
    * @param word
    * @return
    */
  def synset(word: Word): Set[String]

  /**
    * Check if 2 words are synonyms.
    * @param word1
    * @param word2
    * @return
    */
  def areSynonyms(word1: Word, word2: Word): Boolean
}

/**
  * The Dictionary object.
  */
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

  override def areSynonyms(word1: Word, word2: Word): Boolean =
    isPresent(word1) && isPresent(word2) && synset(word1).contains(lemma(word2).get)
}


