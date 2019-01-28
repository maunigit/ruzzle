import org.scalatest._
import prolog.{Constant, PredicateBuilder, PrologEngine, Variable}
import prolog.PredicateBuilder._

class PrologEngineTest extends FlatSpec with Matchers {

  "It" should "resolve simple goals" in {
    val engine: PrologEngine = PrologEngine()
    var builder = PredicateBuilder("cell") += Constant(1) += Constant(1) += Constant("alfa")
    val predicate = builder.create()
    assert(engine += predicate)
    builder = PredicateBuilder("cell") += Constant(1) += Constant(1) += Variable('X')
    val goal = builder.create()
    val result = engine.goal(goal).get
    assert(result(Variable('X')) == "alfa")
  }

}
