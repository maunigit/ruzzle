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
  var synonymousPoints: Int = 1
  var vowelPoints: Int = 2
  var consonantPoints: Int = 1

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
    * Get the score for the word's vowels and consonants.
    * @param word
    * @return
    */
  def characterTypePoints(word: Word): Int = {
    def computeVowelAndConsonantPoints(charList: List[Char], accumulator: Int): Int = charList match {
      case h::t => if(h.isVowel) computeVowelAndConsonantPoints(t, accumulator + vowelPoints) else computeVowelAndConsonantPoints(t, accumulator + consonantPoints)
      case Nil => accumulator
    }
    computeVowelAndConsonantPoints(word.value.toList, 0)
  }

  /**
    * Set the standard values.
    */
  def standard(): Unit = {
    nounPoints = 3
    adjectivePoints = 4
    adverbPoints = 4
    verbPoints = 3
    charPoints = 1
    synonymousPoints = 1
    vowelPoints = 2
    consonantPoints = 1
  }

  /**
    * The implicit class that adds methods to discover if a char is a vowel or consonant.
    * @param char
    */
  implicit class VowelConsonantExtension(char: Char) {

    /**
      * Check if a char is vowel.
      * @return
      */
    def isVowel: Boolean = char.isLetter && (char.toLower match {
      case 'a' => true
      case 'e' => true
      case 'i' => true
      case 'o' => true
      case 'u' => true
      case _ => false
    })

    /**
      * Check if a char is consonant.
      * @return
      */
    def isConsonant: Boolean = char.isLetter && !char.isVowel
  }

}


