package view

import javafx.beans.property.{SimpleIntegerProperty, SimpleStringProperty}
import javafx.collections.ObservableList

case class Rank (user: String, point: Int) {
  var username : SimpleStringProperty = new SimpleStringProperty(user)
  var points : SimpleIntegerProperty = new SimpleIntegerProperty(point)

  def getData: ObservableList[Rank] = ???
}
