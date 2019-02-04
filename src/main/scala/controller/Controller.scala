package controller

import model.{Board, RandomGenerator}

object Controller {

  var board: Board = Board(RandomGenerator, 10)

  def newSingleGame(): Array[Array[Char]] = {
    board = Board(RandomGenerator, 10)
    board.matrix()
  }

  def findWord(word: String): Boolean = board.isPresent(word)

}
