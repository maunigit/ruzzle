import java.io.File
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Scene
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.Stage

class LauchDashboard extends Application {
  override def start(primaryStage: Stage): Unit = {
    //GUI
    val loaderDashboard: FXMLLoader = new FXMLLoader(getClass.getResource("/view/dashboardView.fxml"))
    val dashboardController = new DashboardController()
    loaderDashboard.setController(dashboardController)
    val sceneDashboard = new Scene(loaderDashboard.load())
    primaryStage.setTitle("Ruzzle")
    primaryStage.setScene(sceneDashboard)
    primaryStage.show()
  }
}

class DashboardController {

}
