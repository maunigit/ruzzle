package actors

import akka.actor.{Actor, ActorRef}
import model.{Board, FakeGenerator, Game}

class Game(val time: Int, val numberOfPlayers: Int) extends Actor {

  var players: List[(String, ActorRef)] = List()
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
  }

}
