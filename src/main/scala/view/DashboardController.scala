package view

import java.io.File
import java.net.URL
import java.util.regex.{Matcher, Pattern}
import java.util.{Optional, ResourceBundle}
import actors.{GUI, Game}
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import javafx.application.{Application, Platform}
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.Scene
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonBar.ButtonData
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
  var existGameMenuItem: MenuItem = _

  @FXML
  var matchMenuItem: MenuItem = _

  @FXML
  var matchMultiplayerMenuItem: MenuItem = _

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

  val config: Config = ConfigFactory.parseFile(new File(getClass.getResource("/actor_configs/player_config.conf").toURI))
  val system: ActorSystem = ActorSystem.create("ruzzle", config)
  val a = system.actorOf(Props(new GUI(this)))
  a ! "a"

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
    val vowelLabel: Label = new Label("Vowel Points: ")
    val consonantLabel: Label = new Label("Consonant Points: ")
    val synonymousLabel: Label = new Label("Synonymous Points: ")
    var nounSlider: Slider = new Slider(1, 9, 3)
    var adjectiveSlider: Slider = new Slider(1, 9, 4)
    var adverbSlider: Slider = new Slider(1, 9, 4)
    var verbSlider: Slider = new Slider(1, 9, 3)
    var vowelSlider: Slider = new Slider(1, 9, 2)
    var consonantSlider: Slider = new Slider(1, 9, 1)
    var synonymousSlider: Slider = new Slider(1, 9, 1)
    var nounValue = new Label(nounSlider.getValue().toInt.toString())
    var adjectiveValue = new Label(adjectiveSlider.getValue().toInt.toString())
    var adverbValue = new Label(adverbSlider.getValue().toInt.toString())
    var verbValue = new Label(verbSlider.getValue().toInt.toString())
    var vowelValue = new Label(vowelSlider.getValue().toInt.toString())
    var consonantValue = new Label(consonantSlider.getValue().toInt.toString())
    var synonymousValue = new Label(synonymousSlider.getValue().toInt.toString())
    val grid: GridPane = new GridPane()
    GridPane.setConstraints(nounLabel, 0, 0)
    GridPane.setConstraints(adjectiveLabel, 0, 1)
    GridPane.setConstraints(adverbLabel, 0, 2)
    GridPane.setConstraints(verbLabel, 0, 3)
    GridPane.setConstraints(vowelLabel, 0, 4)
    GridPane.setConstraints(consonantLabel, 0, 5)
    GridPane.setConstraints(synonymousLabel, 0, 6)
    grid.getChildren().add(nounLabel)
    grid.getChildren().add(adjectiveLabel)
    grid.getChildren().add(adverbLabel)
    grid.getChildren().add(verbLabel)
    grid.getChildren().add(vowelLabel)
    grid.getChildren().add(consonantLabel)
    grid.getChildren().add(synonymousLabel)
    GridPane.setConstraints(nounSlider, 1, 0)
    GridPane.setConstraints(adjectiveSlider, 1, 1)
    GridPane.setConstraints(adverbSlider, 1, 2)
    GridPane.setConstraints(verbSlider, 1, 3)
    GridPane.setConstraints(vowelSlider, 1, 4)
    GridPane.setConstraints(consonantSlider, 1, 5)
    GridPane.setConstraints(synonymousSlider, 1, 6)
    grid.getChildren.add(nounSlider)
    grid.getChildren.add(adjectiveSlider)
    grid.getChildren.add(adverbSlider)
    grid.getChildren.add(verbSlider)
    grid.getChildren.add(vowelSlider)
    grid.getChildren.add(consonantSlider)
    grid.getChildren.add(synonymousSlider)
    GridPane.setConstraints(nounValue, 2, 0)
    GridPane.setConstraints(adjectiveValue, 2, 1)
    GridPane.setConstraints(adverbValue, 2, 2)
    GridPane.setConstraints(verbValue, 2, 3)
    GridPane.setConstraints(vowelValue, 2, 4)
    GridPane.setConstraints(consonantValue, 2, 5)
    GridPane.setConstraints(synonymousValue, 2, 6)
    grid.getChildren().add(nounValue)
    grid.getChildren().add(adjectiveValue)
    grid.getChildren().add(adverbValue)
    grid.getChildren().add(verbValue)
    grid.getChildren().add(vowelValue)
    grid.getChildren().add(consonantValue)
    grid.getChildren().add(synonymousValue)
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
    vowelSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.vowelPoints = newValue.intValue()
        vowelValue.setText(newValue.intValue().toString())
      }
    })
    consonantSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.consonantPoints = newValue.intValue()
        consonantValue.setText(newValue.intValue().toString())
      }
    })
    synonymousSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        ScoreManager.synonymousPoints = newValue.intValue()
        synonymousValue.setText(newValue.intValue().toString())
      }
    })
    alert.getDialogPane().setContent(grid)
    alert.showAndWait()
  }

  @FXML def joinExistGame(event: ActionEvent): Unit = {
    val dialog : Dialog[Tuple2[String, String]] = new Dialog()
    dialog.setTitle("Existing Game")
    dialog.setHeaderText("Join An Existing Game")
    dialog.setResizable(true)

    val grid: GridPane = new GridPane()
    val usernameTextField: TextField = new TextField()
    val ipAddressTextField: TextField = new TextField()
    var okButton : ButtonType = new ButtonType("Ok", ButtonData.OK_DONE)
    dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL)
    grid.add(new Label("Username: "), 0, 0)
    grid.add(new Label("IP Address: "), 0, 1)
    grid.add(usernameTextField, 1, 0)
    grid.add(ipAddressTextField, 1, 1)
    dialog.getDialogPane().setContent(grid)

    //dialog.setResultConverter()

    if (!validateIP(ipAddressTextField.getText())) {
      val alert = new Alert(AlertType.ERROR)
      alert.setTitle("Error")
      alert.setHeaderText("Invalid IP Address")
    }

    dialog.showAndWait()
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
    val username: Optional[String] = showUsernameDialog()
    if (!username.isPresent || (username.isPresent && username.get().isEmpty)) {
      showAlert("E' obbligatorio inserire uno username!")
    } else {

    }
  }

  @FXML def newGameMatchMultiplayer(event: ActionEvent): Unit = {

  }

  @FXML def searchWord(event: ActionEvent): Unit = {

  }

  @FXML def showRank(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Show Ranking")
    alert.setHeaderText("Ruzzle Ranking")
    alert.setResizable(true)

    val rankTable: TableView[Rank] = new TableView[Rank]()
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

  def validateIP(ip: String): Boolean = {
    var ipAddressPattern: String = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
    var pattern: Pattern = Pattern.compile(ipAddressPattern)
    var matcher: Matcher = pattern.matcher(ip)
    matcher.matches()
  }

}
