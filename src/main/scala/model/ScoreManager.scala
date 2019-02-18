package model

import model.WordTag._

/**
  * The score manager object that allows to count the players' score.
  */
object ScoreManager {

  var nounPoints: Int = 3
  var adjectivePoints: Int = 4
  var adverbPoints: Int = 4
  var verbPoints: Int = 3
  var charPoints: Int = 1

  /**
    * Get the score about a specific word type.
    * @param word
    * @return
    */
  def wordTypePoints(word: Word): Int = word.tag match {
    case Noun => nounPoints
    case Adjective => adjectivePoints
    case Adverb => adverbPoints
    case Verb => verbPoints
    case _ => 0
  }

  /**
    * Get the score for the word length.
    * @param word
    * @return
    */
  def wordLengthPoints(word: Word): Int = word.value.length * charPoints

  /**
    * Set the standard values.
    */
  def standard(): Unit = {
    nounPoints = 3
    adjectivePoints = 4
    adverbPoints = 4
    verbPoints = 3
    charPoints = 1
  }

}
