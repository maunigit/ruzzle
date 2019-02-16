package model

abstract class Game {
  def time : Int
  def players : List[String]
  def foundWord(word: Word, player: String) : Unit
  def points(player: String) : Int
  def ranking() : List[(String,Int)] = ???
}
