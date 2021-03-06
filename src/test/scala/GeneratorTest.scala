import model.{Board, RandomGenerator}
import org.scalatest._

class GeneratorTest extends FlatSpec {

  "The generation of letters" should "be almost uniform" in {
    val countA: Int = (0 until 270).map(_ => RandomGenerator()).count(generatedChar => generatedChar == 'a')
    val countB: Int = (0 until 270).map(_ => RandomGenerator()).count(generatedChar => generatedChar == 'b')
    assert(countA >= 4 && countA <= 25)
    assert(countB >= 4 && countB <= 19)
  }

}
