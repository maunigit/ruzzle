package actors

import model.Word

/**
  * The message used to create a new ruzzle game.
  * @param player the username of the player
  * @param time the time of the game (min)
  * @param numberOfPlayers number of players (me included)
  * @param useSynExtension
  */
case class NewGame(player: String, time: Int, numberOfPlayers: Int, useSynExtension: Boolean)

/**
  * The message used to communicate the address of a multiplayer game.
  * @param address ip_address and port
  */
case class GameAddress(address: String)

/**
  * The message used to connect to a specific game.
  * @param player the username of the player
  * @param address game address (ip_address and port)
  */
case class TakePartOfAnExistingGame(player: String, address: String)

/**
  * The message used to join an existing game.
  * @param player
  */
case class JoinTheGame(player: String)

/**
  * The message that indicates that the reference of a particular game is wrong.
  */
case class WrongGameReference()

/**
  * The confirm message that tells that the player has joined the game.
  */
case class YouAreIn()

/**
  * The message that starts the game.
  * @param board the board of the game
  * @param time
  */
case class Start(board: Array[Array[Char]], time: Int)

/**
  * This message reports that the time is run out.
  */
case class Stop()

/**
  * The message that contains the ranking of the game just finished.
  * @param ranking
  */
case class GameRanking(ranking: List[(String, Int)])

/**
  * The message that catch a found word by a player.
  * @param value
  * @param tag noun, adjective, verb or adverb
  */
case class FoundWord(value: String, tag: String)

/**
  * This message communicates that the user has found a specific word.
  * @param player
  * @param word
  */
case class WordTyped(player: String, word: Word)

/**
  * The message that confirms the goodness of the word.
  */
case class WordOK()

/**
  * The message that confirms the badness of the word.
  */
case class WordWrong()

/**
  * The emergency message caused by a shutdown of an agent.
  */
case class EmergencyExit()