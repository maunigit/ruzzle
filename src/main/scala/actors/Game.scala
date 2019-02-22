package actors

import akka.actor.Actor

class Game(val time: Int, val numberOfPlayers: Int) extends Actor {

  override def receive: Receive = ???

}
