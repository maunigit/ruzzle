package actors

import akka.actor.{Actor, ActorRef}
import model.WordTag
import model.WordTag.WordTag

class Player(val name: String, val guiActor: ActorRef) extends Actor {

  override def receive: Receive = {
    case NewGameMessage(time) =>
    case FoundWordMessage(value, tag) => {
      val wordType: WordTag = tag match {
        case "Noun" => WordTag.Noun
        case "Adjective" => WordTag.Adjective
        case "Adverb" => WordTag.Adverb
        case _ => WordTag.Verb
      }
    }
  }
}
