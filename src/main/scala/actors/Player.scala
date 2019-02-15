package actors

import akka.actor.{Actor, ActorRef}

class Player(val name: String, val guiActor: ActorRef) extends Actor {

  override def receive: Receive = {
    case "test" =>
  }
}
