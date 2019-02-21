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

  "The dictionary" should "catch right synonyms" in {
    val obese:Word = Word("obese", WordTag.Adjective)
    assert(Dictionary.synset(obese).contains("rotund"))
    assert(!Dictionary.synset(obese).contains("thin"))
  }

  "The dictionary" should "check if 2 words are synonyms" in {
    val obese:Word = Word("obese", WordTag.Adjective)
    val corpulent:Word = Word("Corpulent", WordTag.Adjective)
    assert(Dictionary.areSynonyms(obese, corpulent))
  }

  "Two words" should "be equals if they have the same value" in {
    val word1:Word = Word("abc", WordTag.Adjective)
    val word2:Word = Word("abc", WordTag.Noun)
    assert(word1 == word2)
  }

}
