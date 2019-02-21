import model._
import org.scalatest._

class BasicGameTest extends FlatSpec{

  "The player" should "know if a word is correct" in {
    val game: Game = Game(List("luca"), Board(FakeGenerator, 10), 1)
    assert(game.foundWord(Word("Dog", WordTag.Noun), "luca"))
    assert(!game.foundWord(Word("duck", WordTag.Noun), "luca"))
  }

  "The player" should "obtain his set of good words" in {
    val game: Game = Game(List("luca"), Board(FakeGenerator, 10), 1)
    game.foundWord(Word("Dog", WordTag.Noun), "luca")
    game.foundWord(Word("duck", WordTag.Noun), "luca")
    game.foundWord(Word("obese", WordTag.Adjective), "luca")
    assert(game.words("luca") == Set(Word("dog", WordTag.Noun), Word("obese", WordTag.Adjective)))
  }

  "The player" should "obtain his score" in {
    ScoreManager.standard()
    val game: Game = Game(List("luca"), Board(FakeGenerator, 10), 1)
    game.foundWord(Word("Dog", WordTag.Noun), "luca")
    game.foundWord(Word("duck", WordTag.Noun), "luca")
    game.foundWord(Word("obese", WordTag.Adjective), "luca")
    assert(game.points("luca") == 15)
  }

  "If the same word has been chosen by more than one player, It" should "be refused" in {
    ScoreManager.standard()
    val game: Game = Game(List("luca", "marco"), Board(FakeGenerator, 10), 1)
    game.foundWord(Word("Dog", WordTag.Noun), "luca")
    game.foundWord(Word("dog", WordTag.Noun), "marco")
    game.foundWord(Word("obese", WordTag.Adjective), "luca")
    game.foundWord(Word("cat", WordTag.Noun), "marco")
    assert(game.points("luca") == 9)
    assert(game.points("marco") == 6)
  }

  "The game" should "return the exact ranking" in {
    ScoreManager.standard()
    val game: Game = Game(List("luca", "marco"), Board(FakeGenerator, 10), 1)
    game.foundWord(Word("Dog", WordTag.Noun), "luca")
    game.foundWord(Word("dog", WordTag.Noun), "marco")
    game.foundWord(Word("obese", WordTag.Adjective), "luca")
    game.foundWord(Word("cat", WordTag.Noun), "marco")
    assert(game.ranking() == List(("luca", 9), ("marco", 6)))
  }

}