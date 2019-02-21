import model._
import org.scalatest._

class SynExtensionGameTest extends FlatSpec {

  "The presence of synonyms" should "increase the score of the player" in {
    ScoreManager.standard()
    val game: Game = Game.withSinExtension(List("luca"), Board(FakeGenerator, 10), 1)
    game.foundWord(Word("Dog", WordTag.Noun), "luca")
    game.foundWord(Word("rotund", WordTag.Adjective), "luca")
    game.foundWord(Word("Obese", WordTag.Adjective), "luca")
    game.foundWord(Word("blablabla", WordTag.Verb), "luca")
    assert(game.points("luca") == 27)
  }

}