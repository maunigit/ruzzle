package model

import model.WordTag._

object ScoreManager {

  var nounPoints: Int = 3
  var adjectivePoints: Int = 4
  var adverbPoints: Int = 4
  var verbPoints: Int = 3
  var charPoints: Int = 1

  def wordTypePoints(word: Word): Int = word.tag match {
    case Noun => nounPoints
    case Adjective => adjectivePoints
    case Adverb => adverbPoints
    case Verb => verbPoints
    case _ => 0
  }

  def wordLengthPoints(word: Word): Int = word.value.length * charPoints

}
