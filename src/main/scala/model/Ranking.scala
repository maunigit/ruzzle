package model

import java.io._

@SerialVersionUID(100L)
object Ranking extends Serializable {
  var rankList: List[(String, Int)] = List(("Mario", 150), ("Luigi", 25))

  def checkFile(file: File): Unit = {
    if (!file.exists() || file.isDirectory()) {
      file.createNewFile()
    }
  }

  def getItemList(): List[(String, Int)] = rankList

  def read(fileName: String): Unit = {
    val ois = new ObjectInputStream(new FileInputStream(fileName))
    val rank = ois.readObject.asInstanceOf[Ranking.type]
    ois.close
  }

  def write(fileName: String): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(fileName))
    oos.writeObject(Ranking)
    oos.close
  }

}