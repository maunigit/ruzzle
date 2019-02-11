package model

trait BagOfWords {

  def insert(player: String, word: Word): Unit
  def apply(player: String): Option[Set[Word]]
}

object BagOfWords {

  def apply(players: List[String]): BagOfWords = new BagOfWordsImpl(players)

  private class BagOfWordsImpl(val players: List[String]) extends BagOfWords {

    var playerWords: Map[String, Set[Word]] = players.map(player => (player, Set[Word]())).toMap

    override def insert(player: String, word: Word): Unit = playerWords.get(player) match {
      case Some(wordSet) => playerWords = playerWords + (player -> (playerWords(player) + word))
      case None =>
    }

    override def apply(player: String): Option[Set[Word]] = {
      if(playerWords.contains(player)) Option(obtainUniqueWords(player, playerWords.getOrElse(player, Set()).toList, List())) else Option.empty
      def obtainUniqueWords(player: String, wordList: List[Word], uniqueWordsList: List[Word]): Set[Word] = wordList match {
        case Nil => uniqueWordsList.toSet
        case h::t => if (playerWords.filterKeys(playerInList => playerInList != player).forall(keyValuTuple => !keyValuTuple._2.contains(h)))
          obtainUniqueWords(player, t, uniqueWordsList :+ h) else obtainUniqueWords(player, t, uniqueWordsList)
      }
    }
  }
}
