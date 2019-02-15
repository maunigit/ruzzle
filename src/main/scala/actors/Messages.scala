package actors

import akka.actor.Address
import model.Word

case class NewGameMessage(time: Int)

case class NewDistributedGameMessage(time: Int)

case class IAmReadyMessage(player: String)

case class StartGame()

case class PartecipateMessage(player: String, gameActor: Address)

case class FoundWordMessage(value: String, tag: String)

case class AppendWordMessage(player: String, word: Word)