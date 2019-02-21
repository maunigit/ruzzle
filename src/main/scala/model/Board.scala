package model
import java.io.InputStream

import prolog._
import prolog.PredicateBuilder._

/**
  * The board of the game.
  */
trait Board {

  /**
    * @return the board size
    */
  def size: Int

  /**
    * Check if a word is inside the board.
    * @param word
    * @return
    */
  def isPresent(word: String): Boolean

  /**
    * The board showed as matrix.
    * @return
    */
  def matrix(): Array[Array[Char]]
}

/**
  * The Board companion object.
  */
object Board{

  def apply(generator: () => Char, size: Int): Board = new BoardImpl(generator, size)

  private class BoardImpl(val generator: () => Char, override val size: Int) extends Board {

    require(size > 0, "The size of the board must be greater than zero.")

    val theory: InputStream = getClass.getResourceAsStream("/board.pl")
    val engine: PrologEngine = PrologEngine.loadTheory(theory)
    buildBoard()

    def buildBoard(): Unit = {
      for(i <- 0 until size; j <- 0 until size) {
        val builder:PredicateBuilder = PredicateBuilder("cell") += i += j += Constant(generator())
        engine += builder.create()
      }
    }

    override def isPresent(word: String): Boolean = {
      val chars: LogicTerm = LogicList.from(word.toLowerCase().map(letter => Constant(letter)))
      val builder:PredicateBuilder = PredicateBuilder("is_present") += chars
      engine.goal(builder.create()).isDefined
    }

    override def matrix(): Array[Array[Char]] = {
      val matrix: Array[Array[Char]] = Array.ofDim(size, size)
      for(i <- 0 until size; j <- 0 until size) {
        val builder:PredicateBuilder = PredicateBuilder("cell") += i += j += 'K'
        val solution: SolutionSet = engine.goal(builder.create()).get
        matrix(i)(j) = solution('K').toCharArray()(0)
      }
      matrix
    }

    override def toString: String = {
      val board: Array[Array[Char]] = matrix()
      var description: String = ""
      for(i <- 0 until size; j <- 0 until size) {
        description += board(i)(j)
        if(j == size - 1 ) description += "\n"
      }
      description
    }

  }

}
