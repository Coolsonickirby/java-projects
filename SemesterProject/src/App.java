import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    public static final int SCREEN_WIDTH = 512;
    public static final int SCREEN_HEIGHT = 512;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
