package model

/**
  * The Bag of Words contains the valid and unique words chosen by the players that contribute to generate the score.
  */
trait BagOfWords {

  /**
    * Add a specific word about that player.
    * @param player
    * @param word
    */
  def insert(player: String, word: Word): Unit

  /**
    * Obtain the set of unique words about that specific player.
    * @param player
    * @return
    */
  def apply(player: String): Set[Word]
}

/**
  * The Bag of Words companion object.
  */
object BagOfWords {

  def apply(players: List[String]): BagOfWords = new BagOfWordsImpl(players)

  private class BagOfWordsImpl(val players: List[String]) extends BagOfWords {

    var playerWords: Map[String, Set[Word]] = players.map(player => (player, Set[Word]())).toMap

    override def insert(player: String, word: Word): Unit = playerWords.get(player) match {
      case Some(wordSet) => playerWords = playerWords + (player -> (wordSet + Word(word.value.toLowerCase, word.tag)))
      case None =>
    }

    override def apply(player: String): Set[Word] = {
      def obtainUniqueWords(player: String, wordList: List[Word], uniqueWordsList: List[Word]): Set[Word] = wordList match {
        case Nil => uniqueWordsList.toSet
        case h::t => if (playerWords.filterKeys(playerInList => playerInList != player).forall{ case (_, wordSet) => !wordSet.contains(h)})
          obtainUniqueWords(player, t, uniqueWordsList :+ h) else obtainUniqueWords(player, t, uniqueWordsList)
      }
      if(playerWords.contains(player))
        obtainUniqueWords(player, playerWords.getOrElse(player, Set()).toList, List())
      else Set()
    }
  }
}
