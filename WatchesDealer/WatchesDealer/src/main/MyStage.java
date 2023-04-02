package main;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyStage {
	private Stage mainStage;

	private static MyStage myStage;

	public static MyStage getInstance() {
		if (myStage == null) {
			myStage = new MyStage();
		}
		return myStage;
	}

	public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	
	public void setTitle(String title) {
		this.mainStage.setTitle(title);
	}
	
	public void setScene(Scene scene) {
		this.mainStage.setScene(scene);
	}
	
	public void show() {
		this.mainStage.setResizable(false);
		this.mainStage.show();
	}

}
