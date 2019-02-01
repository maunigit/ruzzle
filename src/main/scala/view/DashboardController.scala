package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Scene
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

  @FXML def closeProgram(event: ActionEvent): Unit = {
  }

  @FXML def newGameMatch(event: ActionEvent): Unit = {
  }

  @FXML def searchWord(event: ActionEvent): Unit = {
  }

  @FXML def showRank(event: ActionEvent): Unit = {
  }
}
