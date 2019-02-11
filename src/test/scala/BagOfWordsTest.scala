import model.{BagOfWords, Word, WordTag}
import org.scalatest._

class BagOfWordsTest extends FlatSpec {

  "The Bag of Words" should "contains inserted words" in {
    val players = List("marco", "luca")
    val bga: BagOfWords = BagOfWords(players)
    bga.insert("marco", Word("dog", WordTag.Noun))
    bga.insert("marco", Word("cat", WordTag.Noun))
    assert(bga("marco").get == Set(Word("dog", WordTag.Noun)), Word("cat", WordTag.Noun))
  }

  "Without an existing user, It" should "return an empty" in {
    val players = List("marco", "luca")
    val bga: BagOfWords = BagOfWords(players)
    bga.insert("luca", Word("dog", WordTag.Noun))
    assert(bga("stefano").isEmpty)
  }

  "If a word is double, It" should "not be returned" in {
    val players = List("marco", "luca")
    val bga: BagOfWords = BagOfWords(players)
    bga.insert("marco", Word("dog", WordTag.Noun))
    bga.insert("marco", Word("cat", WordTag.Noun))
    bga.insert("luca", Word("dog", WordTag.Noun))
    assert(bga("marco").get == Set(Word("cat", WordTag.Noun)))
  }

}
