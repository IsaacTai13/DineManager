package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {

    private Scene mainScene;   // Main page
    private Scene page1Scene;  // Page 1
    private Scene page2Scene;  // Page 2
    private Scene page3Scene;  // Page 3

    @Override
    public void start(Stage primaryStage) {
        // -------------------------------------------------------
        // Main page with three button
        // -------------------------------------------------------
        Button btn1 = new Button("");
        Button btn2 = new Button("Go to Page 2");
        Button btn3 = new Button("Go to Page 3");

        VBox mainLayout = new VBox(20, btn1, btn2, btn3);
        mainLayout.setAlignment(Pos.CENTER);

        mainScene = new Scene(mainLayout, 800, 600);


        // -------------------------------------------------------
        // Page 1
        // -------------------------------------------------------
        Button back1 = new Button("⬅ Back to Home");
        Text p1Text = new Text("This is Page 1");

        VBox page1Layout = new VBox(20, p1Text, back1);
        page1Layout.setAlignment(Pos.CENTER);

        page1Scene = new Scene(page1Layout, 800, 600);


        // -------------------------------------------------------
        // Page 2
        // -------------------------------------------------------
        Button back2 = new Button("⬅ Back to Home");
        Text p2Text = new Text("This is Page 2");

        VBox page2Layout = new VBox(20, p2Text, back2);
        page2Layout.setAlignment(Pos.CENTER);

        page2Scene = new Scene(page2Layout, 800, 600);


        // -------------------------------------------------------
        // Page 3
        // -------------------------------------------------------
        Button back3 = new Button("⬅ Back to Home");
        Text p3Text = new Text("This is Page 3");

        VBox page3Layout = new VBox(20, p3Text, back3);
        page3Layout.setAlignment(Pos.CENTER);

        page3Scene = new Scene(page3Layout, 800, 600);


        // -------------------------------------------------------
        // Link button to respective pages
        // -------------------------------------------------------
        btn1.setOnAction(e -> primaryStage.setScene(page1Scene));
        btn2.setOnAction(e -> primaryStage.setScene(page2Scene));
        btn3.setOnAction(e -> primaryStage.setScene(page3Scene));

        back1.setOnAction(e -> primaryStage.setScene(mainScene));
        back2.setOnAction(e -> primaryStage.setScene(mainScene));
        back3.setOnAction(e -> primaryStage.setScene(mainScene));


        // -------------------------------------------------------
        // Show main page
        // -------------------------------------------------------
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}