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
        player.get ! TakePartOfAnExistingGame(username, address)
      }
    case GameAddress(address) =>
      view.showAddress(address)
    case WrongGameReference() =>
      view.wrongAddress()
    case YouAreIn() =>
      view.youAreInTheGame()
    case Start(board, _) =>
      view.insertBoard(board)
    case FoundWord(value, tag) =>
      player.get ! FoundWord(value, tag)
    case WordOK() =>
      view.warnForAGoodWord()
    case WordWrong() =>
      view.warnForABadWord()
    case GameRanking(ranking) =>
      player = Option.empty
      view.gameFinished()
      view.showDialogRank(ranking)
    case EmergencyExit() =>
      view.gameBroken()
      player = Option.empty
  }

  def gameAlreadyExists(): Boolean = {
    if(player.isDefined) {
      view.warnForAnExistingGame()
      true
    } else false
  }

}
