import org.scalatest._
import prolog.{Constant, PredicateBuilder, PrologEngine, Variable}
import prolog.PredicateBuilder._

class PrologEngineTest extends FlatSpec with Matchers {

  "It" should "resolve simple goals" in {
    val engine: PrologEngine = PrologEngine()
    var builder = PredicateBuilder("cell") += 1 += 1 += "alfa"
    val predicate = builder.create()
    assert(engine += predicate)
    builder = PredicateBuilder("cell") += 1 += 1 += 'X'
    val goal = builder.create()
    val result = engine.goal(goal).get
    assert(result('X') == "alfa")
  }

  "It" should "show all the alternatives" in {
    val engine: PrologEngine = PrologEngine()
    var builder = PredicateBuilder("cell") += Constant(1) += Constant(1) += Constant("alfa")
    val predicateOne = builder.create()
    builder = PredicateBuilder("cell") += Constant(1) += Constant(1) += Constant("beta")
    val predicateTwo = builder.create()
    assert(engine += predicateOne)
    assert(engine += predicateTwo)
    builder = PredicateBuilder("cell") += Constant(1) += Constant(1) += Variable('X')
    val goal = builder.create()
    val result = engine.goal(goal).get
    assert(result(Variable('X')) == "beta")
    assert(engine.hasOpenAlternatives())
    val secondResult = engine.getNextAlternative().get
    assert(secondResult(Variable('X')) == "alfa")
    assert(!engine.hasOpenAlternatives())
  }

}
