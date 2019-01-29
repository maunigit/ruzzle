package prolog

import java.io.{File, FileInputStream, InputStream}

import alice.tuprolog.{Prolog, SolveInfo, Theory}

trait PrologEngine {

  def goal(predicate: Predicate): Option[SolutionSet]
  def +=(predicate: Predicate): Boolean
  def hasOpenAlternatives(): Boolean
  def getNextAlternative(): Option[SolutionSet]
}

object PrologEngine {

  def apply(): PrologEngine = new PrologEngineImpl()

  def loadTheory(theory: InputStream): PrologEngine = {
    val prologEngine: PrologEngineImpl = new PrologEngineImpl()
    prologEngine.loadTheory(theory)
    prologEngine
  }

  private class PrologEngineImpl extends PrologEngine {

    val goalPlaceholder: String = "%s."
    val assertPlaceholder: String = "asserta(%s)."
    val engine: Prolog = new Prolog()
    var lastGoal: Option[Predicate] = Option.empty

    override def goal(predicate: Predicate): Option[SolutionSet] = {
      lastGoal = Option(predicate)
      val solveInfo: SolveInfo = engine.solve(goalPlaceholder.format(predicate.toString()))
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
        } else {
          Option.empty
        }
      } else {
        Option.empty
      }
    }

    def loadTheory(stream: InputStream): Unit = engine.setTheory(new Theory(stream))

    override def +=(predicate: Predicate): Boolean = {
      val solveInfo: SolveInfo = engine.solve(assertPlaceholder.format(predicate.toString()))
      if(solveInfo.isSuccess()) true else false
    }

  }
}


