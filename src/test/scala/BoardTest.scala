import model.{Board, FakeGenerator}
import org.scalatest._

class BoardTest extends FlatSpec with Matchers {

  "The size of the board" should "be greater than zero" in {
    a [IllegalArgumentException] should be thrownBy {
      val board: Board = Board(FakeGenerator, 0)
    }
  }

  "It" should "check if a word is not present" in {
    val board: Board = Board(FakeGenerator, 5)
    assert(!board.isPresent("collution"))
    assert(board.isPresent("dog"))
  }

}