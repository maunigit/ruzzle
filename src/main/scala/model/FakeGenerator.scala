package model

/**
  * A generator that creates a specific and known board.
  */
object FakeGenerator extends Function0[Char] {

  val fakeBoard: Array[Array[Char]] = Array(Array('w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'),
                                            Array('w', 'd', 'o', 'g', 'w', 'a', 'w', 'w', 'w', 'w'),
                                            Array('w', 'w', 'b', 'w', 'w', 'r', 'w', 'c', 'a', 't'),
                                            Array('w', 'w', 'e', 'w', 'w', 'w', 'w', 'o', 'w', 'w'),
                                            Array('w', 'w', 's', 'w', 'w', 'w', 'w', 'r', 'w', 'w'),
                                            Array('w', 'w', 'e', 'w', 'w', 'w', 'w', 'w', 'p', 'w'),
                                            Array('w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'u', 'w'),
                                            Array('w', 'w', 'w', 'w', 'd', 'w', 'w', 'w', 'w', 'l'),
                                            Array('w', 'w', 'w', 'u', 'n', 'w', 'w', 'w', 'e', 'w'),
                                            Array('r', 'o', 't', 'w', 'w', 'w', 'w', 't', 'n', 'w'))

  var rowIndex: Int = 0
  var colIndex: Int = 0

  override def apply(): Char = {
    val charToReturn: Char = fakeBoard(rowIndex)(colIndex)
    colIndex = (colIndex + 1) % fakeBoard.length
    if(colIndex == 0)
      rowIndex = (rowIndex + 1) % fakeBoard.length
    charToReturn
  }

}
