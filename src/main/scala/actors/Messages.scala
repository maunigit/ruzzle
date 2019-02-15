package actors

import akka.actor.Address

case class NewGameMessage()

case class NewDistributedGameMessage()

case class PartecipateMessage(player: String, gameActor: Address)
