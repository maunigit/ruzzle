package actors

import akka.actor.Address
import model.Word

case class NewGame(time: Int, numberOfPlayers: Int)

case class JoinTheGame(player: String)

case class YouAreIn()

case class Start(board: Array[Array[Char]], time: Int)

case class Stop()

case class GameRanking(ranking: List[(String, Int)])

case class FoundWord(word: Word)

case class WordOK()

case class WordWrong()