package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import page.LoginPage;

public class Main extends Application {
	
	public static void changeScene(Scene scene, String title) {
		MyStage mainStage = MyStage.getInstance();
		mainStage.setTitle(title);
		mainStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MyStage myStage = MyStage.getInstance();
		myStage.setMainStage(primaryStage);
		
		LoginPage lp = LoginPage.getInstance();
		lp.show();
		
		myStage.show();
	}
	
}
