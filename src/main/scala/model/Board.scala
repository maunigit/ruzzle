package model
import java.io.InputStream
import prolog.PrologEngine

trait Board {

  def isPresent(word: String): Boolean
}

object Board{

  def apply(): Board = new BoardImpl()

  private class BoardImpl extends Board{

    val theory: InputStream = getClass().getResourceAsStream("/board.pl")
    val engine: PrologEngine = PrologEngine.loadTheory(theory)

    override def isPresent(word: String): Boolean = ???
  }
}
