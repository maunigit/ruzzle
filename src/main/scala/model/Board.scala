package model
import java.io.InputStream

import prolog._
import prolog.PredicateBuilder._

trait Board {

  def size: Int
  def isPresent(word: String): Boolean
  def matrix(): Array[Array[Char]]
}

object Board{

  def apply(generator: () => Char, size: Int): Board = new BoardImpl(generator, size)

  private class BoardImpl(val generator: () => Char, override val size: Int) extends Board {

    val theory: InputStream = getClass().getResourceAsStream("/board.pl")
    val engine: PrologEngine = PrologEngine.loadTheory(theory)
    buildBoard()

    def buildBoard() = {
      for(i <- 0 until size; j <- 0 until size) {
        val builder:PredicateBuilder = PredicateBuilder("cell") += i += j += generator().toString()
        engine += builder.create()
      }
    }

    override def isPresent(word: String): Boolean = {
      val builder:PredicateBuilder = PredicateBuilder("is_present") += LogicList(word.toLowerCase().toCharArray().map(letter => Constant(letter)))
      engine.goal(builder.create()).isDefined
    }

    override def matrix(): Array[Array[Char]] = {
      val matrix: Array[Array[Char]] = Array.ofDim(size, size)
      for(i <- 0 until size; j <- 0 until size) {
        val builder:PredicateBuilder = PredicateBuilder("cell") += i += j += 'K'
        val solution: SolutionSet = engine.goal(builder.create()).get
        matrix(i)(j) = solution('X').toCharArray()(0)
      }
      matrix
    }
  }

}
