import model.{Board, FakeGenerator}
import org.scalatest._

class BoardTest extends FlatSpec {

  "It" should "check if a word is not present" in {
    val board: Board = Board(FakeGenerator, 5)
    assert(!board.isPresent("abc"))
    assert(board.isPresent("ab"))
  }

}