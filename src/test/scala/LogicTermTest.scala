import org.scalatest._
import prolog.{Constant, Predicate, PredicateBuilder, Variable}
import prolog.PredicateBuilder._

class LogicTermTest extends FlatSpec with Matchers {

  "An predicate with Variables and Constants" should "be created" in {
    val builder: PredicateBuilder = PredicateBuilder("myPredicate") += Variable('x') += Constant(55) += Variable('Y')
    val predicate: Predicate = builder.create()
    assert(predicate(0) == "X")
    assert(predicate.toString() == "myPredicate(X,55,Y)")
    assert(predicate(1).toInt == 55)
  }

  "The terms" should "be without spaces" in {
    val builder: PredicateBuilder = PredicateBuilder("my Predicate") += Variable('x') += Constant("my Const") += Variable('Y')
    val predicate: Predicate = builder.create()
    assert(predicate(1) == "myConst")
    assert(predicate.toString() == "myPredicate(X,myConst,Y)")
  }

  "The terms" should "not be empty" in {
    a [IllegalArgumentException] should be thrownBy {
      val constant: Constant = Constant("")
    }
  }

  "The variables" should "be letters" in {
    a [IllegalArgumentException] should be thrownBy {
      val constant: Variable = Variable('5')
    }
  }

}
