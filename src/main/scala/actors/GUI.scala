package actors

import akka.actor.{Actor, ActorRef, Props}
import view.DashboardController

class GUI(val view: DashboardController) extends Actor {

  var player: Option[ActorRef] = Option.empty

  override def receive: Receive = {
    case NewGame(username, time, numberOfPlayers, useSynExtension) =>
      player = Option(context.actorOf(Props(new Player(username, self))))
      player.get ! NewGame(username, time, numberOfPlayers, useSynExtension)
    case YouAreIn() =>
      view.showAlert("Well Done! You have joined the game!")
    case Start(board, _) =>
      view.insertBoard(board)
    case FoundWord(value, tag) =>
      player.get ! FoundWord(value, tag)
    case WordOK() =>
      view.showAlert("Perfect! The word is correct.")
    case WordWrong() =>
      view.showAlert("Sorry! Bad word...")
  }

}
