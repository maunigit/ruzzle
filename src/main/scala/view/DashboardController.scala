package view

import java.io._
import java.net.URL
import java.util.{Optional, ResourceBundle}

import scala.collection.JavaConverters._
import controller.Controller
import javafx.application.Application
import javafx.beans.value.{ChangeListener, ObservableValue}
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
import model.{Ranking, ScoreManager}

class LauchDashboard extends Application {

  override def start(primaryStage: Stage): Unit = {
    val loaderDashboard: FXMLLoader = new FXMLLoader(getClass.getResource("/view/dashboardView.fxml"))
    val sceneDashboard = new Scene(loaderDashboard.load())
    primaryStage.setTitle("Ruzzle")
    primaryStage.setScene(sceneDashboard)
    primaryStage.show()
  }

}

class DashboardController extends Initializable {

  @FXML
  var matchMenuItem: MenuItem = _

  @FXML
  var pointsMenuItem: MenuItem = _

  @FXML
  var rankMenuItem: MenuItem = _

  @FXML
  var matrixGridPane: GridPane = _

  @FXML
  var inputWordTextField: TextField = _

  @FXML
  var searchButton: Button = _

  @FXML
  var resultLabel: Label = _

  @FXML
  var searchedWordsLabel: Label = _

  @FXML
  var searchedWordsListView: ListView[String] = new ListView()

  @FXML
  var typeWordComboBox: ComboBox[String] = new ComboBox[String]()

  var rankTable: TableView[Rank] = new TableView[Rank]()
  var userName: String = new String()
  var userPoints: Int = 0

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    typeWordComboBox.getItems.clear()
    typeWordComboBox.getItems.addAll("Noun", "Adjective", "Adverb", "Verb")
    typeWordComboBox.getSelectionModel.select(0)
    searchButton.setDisable(true)
    inputWordTextField.setEditable(false)
  }


  @FXML def changePoints(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Points")
    alert.setHeaderText("Change points")
    alert.setResizable(true)
    val nounLabel: Label = new Label("Noun Points: ")
    val adjectiveLabel: Label = new Label("Adjective Points: ")
    val adverbLabel: Label = new Label("Adverb Points: ")
    val verbLabel: Label = new Label("Verb Points: ")
    var nounSlider: Slider = new Slider(1, 9, 3)
    var adjectiveSlider: Slider = new Slider(1, 9, 4)
    var adverbSlider: Slider = new Slider(1, 9, 4)
    var verbSlider: Slider = new Slider(1, 9, 3)
    var nounValue = new Label(nounSlider.getValue().toInt.toString())
    var adjectiveValue = new Label(adjectiveSlider.getValue().toInt.toString())
    var adverbValue = new Label(adverbSlider.getValue().toInt.toString())
    var verbValue = new Label(verbSlider.getValue().toInt.toString())

    val grid: GridPane = new GridPane()
    GridPane.setConstraints(nounLabel, 0, 0)
    GridPane.setConstraints(adjectiveLabel, 0, 1)
    GridPane.setConstraints(adverbLabel, 0, 2)
    GridPane.setConstraints(verbLabel, 0, 3)
    grid.getChildren().add(nounLabel)
    grid.getChildren().add(adjectiveLabel)
    grid.getChildren().add(adverbLabel)
    grid.getChildren().add(verbLabel)
    GridPane.setConstraints(nounSlider, 1, 0)
    GridPane.setConstraints(adjectiveSlider, 1, 1)
    GridPane.setConstraints(adverbSlider, 1, 2)
    GridPane.setConstraints(verbSlider, 1, 3)
    grid.getChildren.add(nounSlider)
    grid.getChildren.add(adjectiveSlider)
    grid.getChildren.add(adverbSlider)
    grid.getChildren.add(verbSlider)
    GridPane.setConstraints(nounValue, 2, 0)
    GridPane.setConstraints(adjectiveValue, 2, 1)
    GridPane.setConstraints(adverbValue, 2, 2)
    GridPane.setConstraints(verbValue, 2, 3)
    grid.getChildren().add(nounValue)
    grid.getChildren().add(adjectiveValue)
    grid.getChildren().add(adverbValue)
    grid.getChildren().add(verbValue)
    nounSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.nounPoints = newValue.intValue()
        nounValue.setText(newValue.intValue().toString())
      }
    })
    adjectiveSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.adjectivePoints = newValue.intValue()
        adjectiveValue.setText(newValue.intValue().toString())
      }
    })
    adverbSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.adverbPoints = newValue.intValue()
        adverbValue.setText(newValue.intValue().toString())
      }
    })
    verbSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.verbPoints = newValue.intValue()
        verbValue.setText(newValue.intValue().toString())
      }
    })
    alert.getDialogPane().setContent(grid)
    alert.showAndWait()
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
    val username: Optional[String] = showUsernameDialog()
    if(!username.isPresent || (username.isPresent && username.get().isEmpty)) {
      showAlert("E' obbligatorio inserire uno username!")
    } else {
      // crea l'agente giocatore
    }
  }

  @FXML def searchWord(event: ActionEvent): Unit = {

  }

  @FXML def showRank(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Show Ranking")
    alert.setHeaderText("Ruzzle Ranking")
    alert.setResizable(true)

    //rank table
    rankTable.getColumns().clear()
    val userNameCol: TableColumn[Rank, String] = new TableColumn("USERNAME")
    val pointsCol: TableColumn[Rank, Int] = new TableColumn("POINTS")
    userNameCol.setCellValueFactory(new PropertyValueFactory[Rank, String]("username"))
    pointsCol.setCellValueFactory(new PropertyValueFactory[Rank, Int]("points"))
    rankTable.setItems(FXCollections.observableArrayList(Ranking.getItemList().map(tuple => new Rank(tuple._1, tuple._2)).asJava))
    rankTable.getColumns().addAll(userNameCol, pointsCol)
    rankTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
    var vboxTable = new VBox()
    vboxTable.setSpacing(5)
    vboxTable.getChildren().addAll(rankTable)
    alert.getDialogPane().setContent(vboxTable)
    alert.showAndWait()
  }

  def showUsernameDialog(): Optional[String] = {
    val dialog = new TextInputDialog()
    dialog.setTitle("Welcome!!!")
    dialog.setHeaderText("Take part in the Ruzzle Ranking")
    dialog.setContentText("Please enter your name:")
    dialog.showAndWait()
  }

  def showAlert(text: String): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Message")
    alert.setHeaderText(null)
    alert.setContentText(text)
    alert.showAndWait()
  }

  def insertBoard(board: Array[Array[Char]]): Unit = {
    matrixGridPane.getChildren().retainAll(matrixGridPane.getChildren().get(0))
    for (i <- board.indices; j <- board(0).indices) matrixGridPane.add(new Label(board(i)(j).toString()), i, j)
    searchButton.setDisable(false)
    inputWordTextField.setEditable(true)
    searchedWordsListView.getItems().clear()
    inputWordTextField.clear()
  }

}
