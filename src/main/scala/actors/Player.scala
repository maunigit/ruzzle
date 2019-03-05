package actors

import java.io.File

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}

import scala.concurrent._
import ExecutionContext.Implicits.global
import com.typesafe.config.{Config, ConfigFactory}
import model.{Ranking, Word, WordTag}

import scala.concurrent.duration._

class Player(val name: String, val guiActor: ActorRef) extends Actor {

  var game: Option[ActorRef] = Option.empty
  var singleGame: Boolean = true

  override def receive: Receive = {
    case NewGame(_, time, numberOfPlayers, useSynExtension) =>
      setEmergencyExit(time)
      if(numberOfPlayers != 1) singleGame = false
      val config: Config = ConfigFactory.parseFile(new File(getClass.getResource("/actor_configs/game_config.conf").toURI))
      val system: ActorSystem = ActorSystem.create("ruzzle", config)
      game = Option(system.actorOf(Props(new Game(time, numberOfPlayers, useSynExtension)), "game"))
      game.get ! JoinTheGame(name)
    case TakePartOfAnExistingGame(_, address) =>
      context.actorSelection("akka.tcp://ruzzle@" + address + ":2600/user/game").resolveOne(10 seconds).onComplete(result => {
        if(result.isSuccess) {
          game = Option(result.get)
          println(game.get)
          game.get ! JoinTheGame(name)
        } else {
          guiActor ! WrongGameReference()
          self ! PoisonPill
        }
      })
    case YouAreIn() =>
      guiActor ! YouAreIn()
    case Start(board, time) =>
      guiActor ! Start(board, time)
      context.system.scheduler.scheduleOnce(time minutes, self, Stop())
    case Stop() =>
      game.get ! Stop()
    case GameRanking(ranking) =>
      guiActor ! GameRanking(ranking)
      if(singleGame && !ranking.isEmpty) Ranking += ranking.head
      self ! PoisonPill
    case FoundWord(value, tag) => tag match {
      case "Adjective" => game.get ! WordTyped(name, Word(value, WordTag.Adjective))
      case "Adverb" => game.get ! WordTyped(name, Word(value, WordTag.Adverb))
      case "Verb" => game.get ! WordTyped(name, Word(value, WordTag.Verb))
      case _ => game.get ! WordTyped(name, Word(value, WordTag.Noun))
    }
    case WordOK() =>
      guiActor ! WordOK()
    case WordWrong() =>
      guiActor ! WordWrong()
    case EmergencyExit() =>
      guiActor ! EmergencyExit()
      self ! PoisonPill
  }

  def setEmergencyExit(time: Int) = context.system.scheduler.scheduleOnce(time*3 minutes, self, EmergencyExit())
}
