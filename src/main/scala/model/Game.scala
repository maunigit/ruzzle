package model

/**
  * The Ruzzle Game.
  */
abstract class Game {

  /**
    * The time of the game (min).
    * @return
    */
  def time : Int

  /**
    * The list of the players.
    * @return
    */
  def players : List[String]

  /**
    * Notice that a player has found a word.
    * @param word
    * @param player
    * @return
    */
  def foundWord(word: Word, player: String) : Boolean

  /**
    * Get the set of valid words of the player.
    * @param player
    * @return
    */
  def words(player: String): Set[Word]

  /**
    * Count the points gained by a player.
    * @param player
    * @return
    */
  def points(player: String) : Int

  /**
    * Get the ranking of the game.
    * @return
    */
  def ranking() : List[(String,Int)] = players.map(player => (player, points(player))).sortWith(_._2 > _._2)
}

/**
  * The Ruzzle Game companion object.
  */
object Game {

  def apply(players : List[String], board : Board, time : Int): Game = new BasicGame(players, board, time)

  def withSinExtension(players : List[String], board : Board, time : Int): Game = new GameWithSinExtension(players, board, time)

  private class BasicGame(override val players : List[String], board : Board, override val time : Int) extends Game {

    require(!players.isEmpty, "There must be at least one player.")
    val bagOfWords: BagOfWords = BagOfWords(players)

    override def foundWord(word: Word, player: String): Boolean =
      if(Dictionary.isPresent(word) && board.isPresent(word.value)){
        bagOfWords.insert(player, word)
        true
      }  else false

    override def words(player: String): Set[Word] = bagOfWords(player)

    override def points(player: String): Int = words(player)
      .map(word => ScoreManager.wordTypePoints(word) + ScoreManager.wordLengthPoints(word) + ScoreManager.characterTypePoints(word)).sum
  }

  private trait SinExtension extends Game {

    abstract override def points(player: String): Int = {
      var additionalPoints: Int = 0
      words(player).foreach(selectedWord => words(player).filterNot(word => word == selectedWord)
        .filter(wordInSet => Dictionary.areSynonyms(selectedWord, wordInSet))
        .foreach(_ => additionalPoints += ScoreManager.synonymousPoints))
      super.points(player) + additionalPoints
    }
  }

  private class GameWithSinExtension(players : List[String], board : Board, time : Int) extends BasicGame(players, board, time) with SinExtension

}





