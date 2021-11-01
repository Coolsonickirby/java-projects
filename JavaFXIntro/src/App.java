import javax.swing.Action;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class App extends Application {
    public static boolean OPTIONS_OPEN = false;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mario Party CLI");

        StackPane root = new StackPane();

        Button btn = new Button();

        btn.setText("Options");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                // New window
                App.ShowOptions();
            }
        });

        root.getChildren().add(btn);

        primaryStage.setScene(new Scene(root, 256, 128));
        primaryStage.show();
    }

    public static void ShowOptions(){
        if(OPTIONS_OPEN){ return; }

        OPTIONS_OPEN = true;
        Stage testStage = new Stage();
        StackPane root = new StackPane();

        Button btn = new Button();

        btn.setText("Test");

        root.getChildren().add(btn);
        testStage.setScene(new Scene(root));
        testStage.show();
        testStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event){
                OPTIONS_OPEN = false;
            }
        });
    }
}
 
// public class App extends Application {
//     public static void main(String[] args) {
//         launch(args);
//     }
    
//     @Override
//     public void start(Stage primaryStage) {
//         primaryStage.setTitle("Hello World!");
//         Button btn = new Button();
//         btn.setText("Say 'Hello World'");
//         btn.setOnAction(new EventHandler<ActionEvent>() {
//             @Override
//             public void handle(ActionEvent event) {
//                 System.out.println("Hello World!");
//             }
//         });
//         StackPane root = new StackPane();
//         root.getChildren().add(btn);
//         primaryStage.setScene(new Scene(root, 300, 250));
//         primaryStage.show();
//     }
// }