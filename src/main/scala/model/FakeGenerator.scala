package model

object FakeGenerator extends Function0[Char] {

  var generations: Int = -1

  override def apply(): Char = {
    generations += 1
    if(generations%2 == 0) 'a' else 'b'
  }

}
