package actors

import akka.actor.{Actor, ActorRef, Props}
import view.DashboardController

class GUI(val view: DashboardController) extends Actor {

  var player: Option[ActorRef] = Option.empty

  override def receive: Receive = {
    case NewGame(username, time, numberOfPlayers, useSynExtension) =>
      player = Option(context.actorOf(Props(new Player(username, self))))
      player.get ! NewGame(username, time, numberOfPlayers, useSynExtension)
  }

}
