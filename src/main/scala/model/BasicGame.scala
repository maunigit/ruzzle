package model

class BasicGame(playersList : List[String], board : Board, timeValue : Int) extends Game{
  override def time: Int = ???

  override def players: List[String] = ???

  override def foundWord(word: Word, player: String): Unit = ???

  override def points(player: String): Int = ???
}
