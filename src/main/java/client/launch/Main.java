package client.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client.fxml"));
        primaryStage.setTitle("DrawingBoard");
        primaryStage.setScene(new Scene(root, 980, 750));
        primaryStage.show();
    }


    /**
     * 项目启动入口
     */
    public static void main(String[] args) {
        launch(args);
    }
}
