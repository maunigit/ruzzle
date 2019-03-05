package actors

import akka.actor.{Actor, ActorRef, PoisonPill}
import model.{Board, FakeGenerator, Game}
import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global

/**
  * The actor that handles a specific ruzzle game.
  * @param time
  * @param numberOfPlayers
  * @param useSynExtension
  */
class Game(val time: Int, val numberOfPlayers: Int, val useSynExtension: Boolean) extends Actor {

  var players: List[(String, ActorRef)] = List()
  var stopReceived: Int = 0
  var game: Option[model.Game] = Option.empty
  setEmergencyExit(time)

  override def receive: Receive = {
    case JoinTheGame(player) =>
      players = players :+ (player, sender())
      sender() ! YouAreIn()
      if(players.length == numberOfPlayers) {
        val board: Board = Board(FakeGenerator, 10)
        if(useSynExtension) game = Option(Game.withSinExtension(players.map{case (user, _) => user}, board, time))
          else game = Option(Game(players.map{case (user, _) => user}, board, time))
        players.map{case (_, ref) => ref}.foreach(ref => ref ! Start(board.matrix(), time))
      }
    case WordTyped(player, word) =>
      if(game.get.foundWord(word, player)) sender() ! WordOK() else sender() ! WordWrong()
    case Stop() =>
      stopReceived += 1
      if(stopReceived == numberOfPlayers) {
        sendRanking()
        context.stop(self)
      }
    case EmergencyExit() =>
      sendRanking()
      self ! PoisonPill
  }

  def sendRanking(): Unit = {
    val ranking: List[(String, Int)] = game.get.ranking()
    players.map{case (_, ref) => ref}.foreach(ref => ref ! GameRanking(ranking))
  }

  def setEmergencyExit(time: Int) = context.system.scheduler.scheduleOnce(time*2 minutes, self, EmergencyExit())

}
