package actors

import java.io.File

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.duration._

class Player(val name: String, val guiActor: ActorRef) extends Actor {

  var game: Option[ActorRef] = Option.empty

  override def receive: Receive = {
    case NewGame(time, numberOfPlayers) =>
      val config: Config = ConfigFactory.parseFile(new File(getClass.getResource("/actor_configs/game_config.conf").toURI))
      val system: ActorSystem = ActorSystem.create("ruzzle", config)
      game = Option(system.actorOf(Props(new Game(time, numberOfPlayers))))
      game.get ! JoinTheGame(name)
    case YouAreIn() =>
      // avvisa la Gui che la partita è stata creata
    case Start(board, time) =>
      // spedisci alla gui il board della partita
      context.system.scheduler.scheduleOnce(time minutes, self, Stop())
    case Stop() =>
      // avvisa GUI e Game che la tua partita è terminata
    case GameRanking(ranking) =>
      // spedisci il ranking alla GUI che lo visualizza
    case WordTyped(word) =>
      // spedisci la parola a Game
    case WordOK() =>
      // avvisa la GUI che la parola specificata è corretta
    case WordWrong() =>
      // avvia la GUI che la parola è errata
  }
}
