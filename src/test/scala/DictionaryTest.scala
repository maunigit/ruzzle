import model.{Dictionary, Word, WordTag}
import org.scalatest._

class DictionaryTest extends FlatSpec {

  "The correct words" should "be present" in {
    val dog:Word = Word("dog", WordTag.Noun)
    assert(Dictionary.isPresent(dog))
    val waitresses:Word = Word("waitresses", WordTag.Noun)
    assert(Dictionary.isPresent(waitresses))
  }

  "A non-sense word" should "not be present" in {
    val random:Word = Word("abcde", WordTag.Noun)
    assert(!Dictionary.isPresent(random))
  }

  "The tag inserted" should "be right" in {
    val rain:Word = Word("rain", WordTag.Noun)
    assert(Dictionary.isPresent(rain))
    val wrongDog:Word = Word("dog", WordTag.Adverb)
    assert(!Dictionary.isPresent(wrongDog))
  }

  "The dictionary" should "catch right synonymous" in {
    val obese:Word = Word("obese", WordTag.Adjective)
    val rotund:Word = Word("rotund", WordTag.Adjective)
    assert(Dictionary.areSynonyms(obese, rotund))
  }

  "The dictionary" should "catch wrong synonymous" in {
    val dog:Word = Word("dog", WordTag.Noun)
    val cat:Word = Word("cate", WordTag.Noun)
    assert(!Dictionary.areSynonyms(dog, cat))
  }

}
