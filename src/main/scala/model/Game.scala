package model

abstract class Game {

  def time : Int
  def players : List[String]
  def foundWord(word: Word, player: String) : Boolean
  def words(player: String): Set[Word]
  def points(player: String) : Int
  def ranking() : List[(String,Int)] = players.map(player => (player, points(player)))
}

object Game {

  def apply(players : List[String], board : Board, dictionary: Dictionary, time : Int): Game = new BasicGame(players, board, dictionary, time)

  //def withSinExtension(players : List[String], board : Board, dictionary: Dictionary, time : Int): Game = new GameWithSinExtension(players, board, dictionary, time)

  private class BasicGame(override val players : List[String], board : Board, val dictionary: Dictionary, override val time : Int) extends Game {

    require(!players.isEmpty, "There must be at least one player.")
    val bagOfWords: BagOfWords = BagOfWords(players)

    override def foundWord(word: Word, player: String): Boolean = ???

    override def words(player: String): Set[Word] = ???

    override def points(player: String): Int = ???
  }

  private trait SinExtension extends Game {

    def points(player: String): Int = ???
  }

  //private class GameWithSinExtension(players : List[String], board : Board, dictionary: Dictionary, time : Int) extends BasicGame(players, board, dictionary, time) with SinExtension

}





