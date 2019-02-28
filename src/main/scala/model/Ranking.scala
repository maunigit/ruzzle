package model

import java.io._

object Ranking {

  private var rankList: List[(String, Int)] = List()
  val fileName: String = System.getProperty("user.dir") + System.getProperty("file.separator") + "res" + System.getProperty("file.separator") + "Ranking.bin"

  checkIfExists()

  private def checkIfExists() = {
    def readFile(): Unit = {
      val ois = new ObjectInputStream(new FileInputStream(fileName))
      rankList = ois.readObject.asInstanceOf[List[(String, Int)]]
      ois.close
    }
    val rankingFile = new File(fileName)
    if(!rankingFile.exists()) rankingFile.createNewFile() else readFile()
  }


  def getItemList(): List[(String, Int)] = rankList

  def +=(scores: (String, Int)*): Unit = {
    def writeFile(): Unit = {
      val oos = new ObjectOutputStream(new FileOutputStream(fileName))
      oos.writeObject(rankList)
      oos.close
    }
    scores.foreach{ case (username, points) => rankList = rankList :+ (username, points)}
    writeFile()
  }



}