package view

import java.io._
import java.net.URL
import java.util.{Optional, ResourceBundle}
import scala.collection.JavaConverters._
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
import model.Ranking

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
  var pointsMenuItem : MenuItem = _

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
  var searchedWordsListView  : ListView[String] = new ListView()

  @FXML
  var typeWordComboBox : ComboBox[String] = new ComboBox[String]()

  val fileSeparator : String = System.getProperty("file.separator")
  val fileName : String = System.getProperty("user.dir") + fileSeparator + "res" + fileSeparator + "Ranking"
  var rankTable : TableView[Rank] = new TableView[Rank]()
  var userName : String = new String()
  var userPoints : Int = 0

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    typeWordComboBox.getItems.clear()
    typeWordComboBox.getItems.addAll("Noun", "Adjective", "Adverb", "Verb")
    typeWordComboBox.getSelectionModel.select(0)
    searchButton.setDisable(true)
    inputWordTextField.setEditable(false)

    //username input dialog
    val dialog = new TextInputDialog()
    dialog.setTitle("Welcome!!!")
    dialog.setHeaderText("Take part in the Ruzzle Ranking")
    dialog.setContentText("Please enter your name:")
    var result : Optional[String] = dialog.showAndWait()
    while(!result.isPresent() || result.get() == "") {
      result = dialog.showAndWait()
    }
    userName = result.get()
  }

  @FXML def changePoints(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Points")
    alert.setHeaderText("Change points")
    alert.setResizable(true)
    alert.showAndWait()
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
    val board:Array[Array[Char]] = Controller.newSingleGame()
    matrixGridPane.getChildren().retainAll(matrixGridPane.getChildren().get(0))
    for (i <- board.indices; j <- board(0).indices) matrixGridPane.add(new Label(board(i)(j).toString()), i, j)
    searchButton.setDisable(false)
    inputWordTextField.setEditable(true)
    searchedWordsListView.getItems().clear()
    inputWordTextField.clear()
  }

  @FXML def searchWord(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Response")
    alert.setHeaderText(null)
    val inputWord : String = inputWordTextField.getText()
    val points: Int = Controller.findWord(inputWord, typeWordComboBox.getValue())
    searchedWordsListView.getItems().add(0, inputWord)
    inputWordTextField.clear()
    alert.setContentText("Points achieved " + points)
    alert.showAndWait()
  }

  @FXML def showRank(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Show Ranking")
    alert.setHeaderText("Ruzzle Ranking")
    alert.setResizable(true)
    var fileRanking : File = new File(fileName)
    Ranking.checkFile(fileRanking)

    //rank table
    rankTable.getColumns().clear()
    val userNameCol : TableColumn[Rank,String] = new TableColumn("USERNAME")
    val pointsCol : TableColumn[Rank,Int] = new TableColumn("POINTS")
    userNameCol.setCellValueFactory(new PropertyValueFactory[Rank,String]("username"))
    pointsCol.setCellValueFactory(new PropertyValueFactory[Rank,Int]("points"))
    Ranking.write(fileName)
    Ranking.read(fileName)
    rankTable.setItems(FXCollections.observableArrayList(Ranking.getItemList().map(tuple => new Rank(tuple._1, tuple._2)).asJava))
    rankTable.getColumns().addAll(userNameCol, pointsCol)
    rankTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
    var vboxTable = new VBox()
    vboxTable.setSpacing(5)
    vboxTable.getChildren().addAll(rankTable)
    alert.getDialogPane().setContent(vboxTable)
    alert.showAndWait()
  }
}
