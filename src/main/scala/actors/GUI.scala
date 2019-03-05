package actors

import akka.actor.{Actor, ActorRef, Props}

import view.DashboardController

class GUI(val view: DashboardController) extends Actor {

  var player: Option[ActorRef] = Option.empty

  override def receive: Receive = {
    case NewGame(username, time, numberOfPlayers, useSynExtension) =>
      if(!gameAlreadyExists()) {
        player = Option(context.actorOf(Props(new Player(username, self))))
        player.get ! NewGame(username, time, numberOfPlayers, useSynExtension)
      }
    case TakePartOfAnExistingGame(username, address) =>
      if(!gameAlreadyExists()) {
        player = Option(context.actorOf(Props(new Player(username, self))))
        player.get ! TakePartOfAnExistingGame(username, address)ion)
      }
    case WrongGameReference() =>
      view.showAlert("The inserted address is wrong...")
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
    case GameRanking(ranking) =>
      player = Option.empty
      view.showAlert("Well Done! The match is over...")
      view.showDialogRank(ranking)
  }

  def gameAlreadyExists(): Boolean = {
    if(player.isDefined) {
      view.showAlert("Another game is already running...")
      true
    } else false
  }

}
