package view

import java.net.URL
import java.util.{Optional, ResourceBundle}
import controller.Controller
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.Scene
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.scene.layout.{GridPane, VBox}
import javafx.stage.Stage
import javafx.scene.control.TextInputDialog
import javafx.scene.control.cell.PropertyValueFactory
import model.Rank
import scala.io.Source

class LauchDashboard extends Application {
  override def start(primaryStage: Stage): Unit = {
    val loaderDashboard: FXMLLoader = new FXMLLoader(getClass.getResource("/view/dashboardView.fxml"))
    val sceneDashboard = new Scene(loaderDashboard.load())
    primaryStage.setTitle("Ruzzle")
    primaryStage.setScene(sceneDashboard)
    primaryStage.show()
  }
}

class DashboardController extends Initializable{

  @FXML
  var matchMenuItem : MenuItem = _

  @FXML
  var rankMenuItem : MenuItem = _

  @FXML
  var matrixGridPane : GridPane = _

  @FXML
  var inputWordTextField : TextField = _

  @FXML
  var searchButton : Button = _

  @FXML
  var resultLabel : Label = _

  @FXML
  var searchedWordsLabel : Label = _

  @FXML
  var searchedWordsListView  : ListView[VBox] = _

  @FXML
  var typeWordComboBox : ComboBox[String] = new ComboBox[String]()

  var rankTable : TableView[Rank] = new TableView[Rank]()
  var userName : String = new String("Unknown")
  var userPoints : Int = 0

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    typeWordComboBox.getItems.clear()
    typeWordComboBox.getItems.addAll("Noun", "Adjective", "Adverb", "Verb")
    typeWordComboBox.getSelectionModel.select(0)
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
    val board:Array[Array[Char]] = Controller.newSingleGame()
    for (i <- board.indices; j <- board(0).indices) matrixGridPane.add(new Label(board(i)(j).toString()), i, j)

    //username input dialog
    val dialog = new TextInputDialog()
    dialog.setTitle("Welcome!!!")
    dialog.setHeaderText("Take part in the Ruzzle Ranking")
    dialog.setContentText("Please enter your name:")
    val result : Optional[String] = dialog.showAndWait
    userName = result.get()
  }

  @FXML def searchWord(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Response")
    alert.setHeaderText(null)
    if (Controller.findWord(inputWordTextField.getText())) alert.setContentText("Good!") else alert.setContentText("Wrong...")
    alert.showAndWait()
  }

  @FXML def showRank(event: ActionEvent): Unit = {
    val filename = System.getProperty("user.dir")+ System.getProperty("file.separator") +
      "res" + System.getProperty("file.separator") +"Ranking.txt"
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Show Ranking")
    alert.setHeaderText("Ruzzle Ranking")
    alert.setResizable(true)

    //rank table
    rankTable.getColumns().clear()
    val userNameCol : TableColumn[Rank,String] = new TableColumn("USERNAME")
    val pointsCol : TableColumn[Rank,Int] = new TableColumn("POINTS")
    userNameCol.setCellValueFactory(new PropertyValueFactory[Rank,String]("username"))
    pointsCol.setCellValueFactory(new PropertyValueFactory[Rank,Int]("points"))
    rankTable.setItems(FXCollections.observableArrayList(new Rank("Gianni", 50), new Rank("Vittorio", 70)))
    rankTable.getColumns().addAll(userNameCol, pointsCol)
    alert.getDialogPane().setContent(rankTable)

    readTextFile(filename) match {
      case Some(lines) => lines.foreach(println)
      case None => alert.setContentText("Couldn't read file")
    }
    alert.showAndWait()
  }

  def readTextFile(filename: String): Option[List[String]] = {
    try {
      val lines = using(Source.fromFile(filename)) { source =>
        (for (line <- source.getLines) yield line).toList
      }
      Some(lines)
    } catch {
      case e: Exception => None
    }
  }

  //The resource is closed automatically
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
