package prolog

import java.io.{File, FileInputStream, InputStream}

import alice.tuprolog.{Prolog, SolveInfo, Theory}

trait PrologEngine {

  def goal(predicate: Predicate): Option[SolutionSet]
  def hasOpenAlternatives(): Boolean
  def getNextAlternative(): Option[SolutionSet]
}

object PrologEngine {

  def apply(): PrologEngine = new PrologEngineImpl()

  def loadTheory(file: File): PrologEngine = {
    val prologEngine: PrologEngineImpl = new PrologEngineImpl()
    prologEngine.loadTheory(new FileInputStream(file))
    prologEngine
  }

  private class PrologEngineImpl extends PrologEngine {

    val engine: Prolog = new Prolog()
    var lastGoal: Option[Predicate] = Option.empty

    override def goal(predicate: Predicate): Option[SolutionSet] = {
      lastGoal = Option(predicate)
      val solveInfo: SolveInfo = engine.solve(predicate.toString())
      if (solveInfo.isSuccess()) {
        Option(SolutionSet(solveInfo, predicate))
      } else {
        Option.empty
      }
    }

    override def hasOpenAlternatives(): Boolean = engine.hasOpenAlternatives()

    override def getNextAlternative(): Option[SolutionSet] = {
      if (hasOpenAlternatives()) {
        val solveInfo: SolveInfo = engine.solveNext()
        if (solveInfo.isSuccess()) {
          Option(SolutionSet(solveInfo, lastGoal.get))
        }
      }
      Option.empty
    }

    def loadTheory(stream: InputStream): Unit = engine.setTheory(new Theory(stream))

  }
}


