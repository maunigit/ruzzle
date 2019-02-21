package model

@SerialVersionUID(100L)
class Ranking extends Serializable {
  var rankList: List[(String, Int)] = List(("Mario", 150), ("Luigi", 25))

  def getItemList(): List[(String, Int)] = rankList
}