import model.{ScoreManager, Word, WordTag}
import org.scalatest._

class ScoreManagerTest extends FlatSpec {

  "The score of a word" should "be correct" in {
    ScoreManager.standard()
    val dog:Word = Word("dog", WordTag.Noun)
    assert(ScoreManager.wordTypePoints(dog) == 3)
    assert(ScoreManager.wordLengthPoints(dog) == 3)
  }

  "The score of a word" should "be correct even when It changes" in {
    ScoreManager.standard()
    val dog:Word = Word("dog", WordTag.Noun)
    ScoreManager.nounPoints = 5
    ScoreManager.charPoints = 2
    assert(ScoreManager.wordTypePoints(dog) == 5)
    assert(ScoreManager.wordLengthPoints(dog) == 6)
  }

}
