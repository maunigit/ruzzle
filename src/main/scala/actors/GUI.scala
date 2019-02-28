package actors

import akka.actor.Actor
import view.DashboardController

class GUI(val view: DashboardController) extends Actor {

  override def receive: Receive = {
    case "a" => println("WORKS")
  }

}
