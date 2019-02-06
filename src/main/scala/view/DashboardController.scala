package view

import controller.Controller
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Scene
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.scene.layout.{GridPane, VBox}
import javafx.stage.Stage

class LauchDashboard extends Application {
  override def start(primaryStage: Stage): Unit = {
    //GUI
    val loaderDashboard: FXMLLoader = new FXMLLoader(getClass.getResource("/view/dashboardView.fxml"))
    //val dashboardController = new DashboardController()
    val sceneDashboard = new Scene(loaderDashboard.load())
    primaryStage.setTitle("Ruzzle")
    primaryStage.setScene(sceneDashboard)
    primaryStage.show()
  }
}

class DashboardController {
  @FXML
  var matchMenuItem : MenuItem = _

  @FXML
  var closeMenuItem : MenuItem = _

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
  var typeWordComboBox : ComboBox[_] = _

  @FXML def closeProgram(event: ActionEvent): Unit = {
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
    val board:Array[Array[Char]] = Controller.newSingleGame()
    for (i <- board.indices; j <- board(0).indices) matrixGridPane.add(new Label(board(i)(j).toString()), i, j)
  }

  @FXML def searchWord(event: ActionEvent): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Response")
    alert.setHeaderText(null)
    if (Controller.findWord(inputWordTextField.getText())) alert.setContentText("Good!") else alert.setContentText("Wrong...")
    alert.showAndWait()
  }

  @FXML def showRank(event: ActionEvent): Unit = {
  }
}
