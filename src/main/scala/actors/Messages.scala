package actors

import model.Word

case class NewGame(player: String, time: Int, numberOfPlayers: Int, useSynExtension: Boolean)

case class JoinTheGame(player: String)

case class YouAreIn()

case class Start(board: Array[Array[Char]], time: Int)

case class Stop()

case class GameRanking(ranking: List[(String, Int)])

case class WordTyped(word: Word)

case class FoundWord(player: String, word: Word)

case class WordOK()

case class WordWrong()