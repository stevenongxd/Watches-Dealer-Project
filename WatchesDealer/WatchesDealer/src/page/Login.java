package page;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;

public class Login {

	private static Login loginForm;

	Scene scene;
	BorderPane bp, bp2;
	GridPane gp;
	Label titleLbl, emailLbl, passwordLbl;
	TextField emailTF;
	PasswordField passwordPF;
	Button loginBtn, registerBtn;

	public static Login getInstance() {
		if (loginForm == null) {
			loginForm = new Login();
		}
		return loginForm;
	}

	public void show() {
		createLayout();
		Main.changeScene(scene, "Login");
	}

	public void createLayout() {
		initialize();
		addEventListener();
	}

	public void initialize() {
		bp = new BorderPane();
		bp2 = new BorderPane();
		gp = new GridPane();
		titleLbl = new Label("Watches Dealer Login");
		emailLbl = new Label("Email:");
		passwordLbl = new Label("Password:");
		emailTF = new TextField();
		emailTF.setPromptText("Email");
		passwordPF = new PasswordField();
		passwordPF.setPromptText("Password");
		loginBtn = new Button("Login");
		registerBtn = new Button("Register Instead");
		
		titleLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		bp2.setTop(titleLbl);
		gp.add(bp2, 0, 0);
		gp.add(emailLbl, 0, 1);
		gp.add(emailTF, 0, 2);
		gp.add(passwordLbl, 0, 3);
		gp.add(passwordPF, 0, 4);
		gp.add(loginBtn, 0, 5);
		gp.add(registerBtn, 0, 6);
		bp.setCenter(gp);
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		gp.setVgap(20);
		gp.setAlignment(Pos.CENTER);
		loginBtn.setMinWidth(200);
		registerBtn.setMinWidth(200);
		
		scene = new Scene(bp, 700, 700);
	}

	public void addEventListener() {
		loginBtn.setOnMouseClicked(e -> {
			String emailInput = emailTF.getText();
			String passwordInput = passwordPF.getText();

			if (emailInput.isEmpty() || passwordInput.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Fields cannot be empty!");
				alert.showAndWait();
			} else {
				UserController.login(emailInput, passwordInput);
			}

		});

		registerBtn.setOnMouseClicked(e -> {
			Register rp = Register.getInstance();
			rp.show();
		});
	}
}
