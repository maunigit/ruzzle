package model
import java.io.InputStream

import prolog.{PredicateBuilder, PrologEngine}
import prolog.PredicateBuilder._

trait Board {

  def size: Int
  def isPresent(word: String): Boolean
  def matrix(): Array[Array[Char]]
}

object Board{

  def apply(generator: () => Char, size: Int): Board = new BoardImpl(generator, size)

  private class BoardImpl(val generator: () => Char, override val size: Int) extends Board{

    val theory: InputStream = getClass().getResourceAsStream("/board.pl")
    val engine: PrologEngine = PrologEngine.loadTheory(theory)
    buildBoard()

    def buildBoard() = {
      for(i <- 0 until size; j <- 0 until size) {
        val builder:PredicateBuilder = PredicateBuilder("cell") += i += j += generator().toString()
        engine += builder.create()
      }
    }

    override def isPresent(word: String): Boolean = ???

    override def matrix(): Array[Array[Char]] = ???
  }

}
