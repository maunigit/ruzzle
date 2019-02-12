package controller

import model.WordTag.WordTag
import model._

object Controller {

  var board: Board = Board(RandomGenerator, 10)

  def newSingleGame(): Array[Array[Char]] = {
    board = Board(RandomGenerator, 10)
    board.matrix()
  }

  def findWord(wordValue: String, tag: String): Int = {
    val wordType: WordTag = tag match {
      case "Noun" => WordTag.Noun
      case "Adjective" => WordTag.Adjective
      case "Adverb" => WordTag.Adverb
      case _ => WordTag.Verb
    }
    val word = Word(wordValue, wordType)
    if(board.isPresent(word.value)) {
      ScoreManager.wordLengthPoints(word) + ScoreManager.wordTypePoints(word)
    } else 0
  }

}
