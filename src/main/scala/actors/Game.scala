package actors

import akka.actor.{Actor, ActorRef, PoisonPill}
import model.{Board, FakeGenerator, Game}

class Game(val time: Int, val numberOfPlayers: Int, val useSynExtension: Boolean) extends Actor {

  var players: List[(String, ActorRef)] = List()
  var stopReceived: Int = 0
  var game: Option[model.Game] = Option.empty

  override def receive: Receive = {
    case JoinTheGame(player) =>
      players = players :+ (player, sender())
      sender() ! YouAreIn()
      if(players.length == numberOfPlayers) {
        val board: Board = Board(FakeGenerator, 10)
        game = Option(Game(players.map{case (user, _) => user}, board, time))
        players.map{case (_, ref) => ref}.foreach(ref => ref ! Start(board.matrix(), time))
      }
    case WordTyped(player, word) =>
      if(game.get.foundWord(word, player)) sender() ! WordOK() else sender() ! WordWrong()
    case Stop() =>
      stopReceived += 1
      if(stopReceived == numberOfPlayers) {
        val ranking: List[(String, Int)] = game.get.ranking()
        players.map{case (_, ref) => ref}.foreach(ref => ref ! GameRanking(ranking))
        self ! PoisonPill
      }
  }

}
