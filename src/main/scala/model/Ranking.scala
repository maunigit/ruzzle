package model

import java.io._

/**
  * The Ranking object that allows to manage the username-score informations and memorize them over time.
  */
object Ranking {
  private var rankList: List[(String, Int)] = List()
  val fileName: String = System.getProperty("user.dir") + System.getProperty("file.separator") + "Ranking.bin"

  checkIfExists()

  /**
    * Check if the ranking file has been created.
    *
    * @return
    */
  private def checkIfExists() = {
    def readFile(): Unit = {
      val ois = new ObjectInputStream(new FileInputStream(fileName))
      rankList = ois.readObject.asInstanceOf[List[(String, Int)]]
      ois.close
    }

    val rankingFile = new File(fileName)
    if (!rankingFile.exists()) rankingFile.createNewFile() else readFile()
  }

  /**
    * Get the list of the top ten people with the highest score.
    *
    * @return
    */
  def getItemList(): List[(String, Int)] = rankList.sortWith(_._2 > _._2).take(10)

  /**
    * Writes username-score informations into the ranking file.
    *
    * @param scores
    * @return
    */
  def +=(scores: (String, Int)*): Unit = {
    def writeFile(): Unit = {
      val oos = new ObjectOutputStream(new FileOutputStream(fileName))
      oos.writeObject(rankList)
      oos.close
    }

    scores.foreach { case (username, points) => rankList = rankList :+ (username, points) }
    writeFile()
  }

}
