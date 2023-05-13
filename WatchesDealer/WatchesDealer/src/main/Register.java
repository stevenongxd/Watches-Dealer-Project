package main;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import user.UserController;

public class Register {
	private static Register registerPage;

	Scene scene;
	BorderPane bp, bp2;
	GridPane gp;
	Label titleLbl, nameLbl, genderLbl, emailLbl, passwordLbl, confirmPasswordLbl;
	TextField nameTF, emailTF;
	HBox genderHBox;
	ToggleGroup genderTG;
	RadioButton maleRB, femaleRB;
	PasswordField passwordPF, confirmPasswordPF;
	Button loginBtn, registerBtn;

	public static Register getInstance() {
		if (registerPage == null) {
			registerPage = new Register();
		}
		return registerPage;
	}

	public void show() {
		createLayout();
		Main.changeScene(scene, "Watches Dealer Register");
	}

	public void createLayout() {
		initialize();
		addEventListener();
	}

	private void initialize() {

		bp = new BorderPane();
		bp2 = new BorderPane();
		gp = new GridPane();

		titleLbl = new Label("Watches Dealer Register");
		titleLbl.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold;");

		nameLbl = new Label("Name:");
		genderLbl = new Label("Gender:");
		emailLbl = new Label("Email:");
		passwordLbl = new Label("Password:");
		confirmPasswordLbl = new Label("Confirm Password:");

		nameTF = new TextField();
		emailTF = new TextField();
		passwordPF = new PasswordField();
		confirmPasswordPF = new PasswordField();

		nameTF.setPromptText("Name");
		emailTF.setPromptText("Email Address");
		passwordPF.setPromptText("Password");
		confirmPasswordPF.setPromptText("Confirm Password");
		genderHBox = new HBox(20);
		genderTG = new ToggleGroup();
		maleRB = new RadioButton("Male");
		femaleRB = new RadioButton("Female");
		maleRB.setToggleGroup(genderTG);
		femaleRB.setToggleGroup(genderTG);

		loginBtn = new Button("Back to Login");
		registerBtn = new Button("Register");

		bp2.setTop(titleLbl);

		genderHBox.getChildren().add(maleRB);
		genderHBox.getChildren().add(femaleRB);

		gp.add(bp2, 0, 0);
		gp.add(nameLbl, 0, 1);
		gp.add(nameTF, 0, 2);
		gp.add(genderLbl, 0, 3);
		gp.add(genderHBox, 0, 4);
		gp.add(emailLbl, 0, 5);
		gp.add(emailTF, 0, 6);
		gp.add(passwordLbl, 0, 7);
		gp.add(passwordPF, 0, 8);
		gp.add(confirmPasswordLbl, 0, 9);
		gp.add(confirmPasswordPF, 0, 10);
		gp.add(registerBtn, 0, 11);
		gp.add(loginBtn, 0, 12);
		bp.setCenter(gp);

		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(20);

		loginBtn.setMinWidth(200);
		registerBtn.setMinWidth(200);

		scene = new Scene(bp, 700, 700);
	}

	private void addEventListener() {

		registerBtn.setOnMouseClicked(event -> {
			String nameInput = nameTF.getText();
			String genderInput = maleRB.isSelected() ? maleRB.getText()
					: femaleRB.isSelected() ? femaleRB.getText() : null;
			String emailInput = emailTF.getText();
			String passwordInput = passwordPF.getText();
			String confPassInput = confirmPasswordPF.getText();

			if (nameInput.length() < 5 || nameInput.length() > 40) {
				Alert alert = new Alert(AlertType.ERROR, "Name must be 5 - 40 characters!");
				alert.showAndWait();
				return;
			}

			if (genderInput == null) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a gender!");
				alert.showAndWait();
				return;
			}

			int atEmailCount = 0;
			int dotEmailCount = 0;

			for (int i = 0; i < emailInput.length(); i++) {
				if (emailInput.charAt(i) == '@') {
					atEmailCount++;
					if(atEmailCount > 1) {
						Alert alert = new Alert(AlertType.ERROR, "@ must one");
						alert.showAndWait();
						return;
					}
				} else if (emailInput.charAt(i) == '.') {
					dotEmailCount++;
					if(dotEmailCount > 1) {
						Alert alert = new Alert(AlertType.ERROR, ". must one");
						alert.showAndWait();
						return;
					}
				}

				if (emailInput.charAt(i) == '.' && atEmailCount < 1) {
					Alert alert = new Alert(AlertType.ERROR, ". must be after @");
					alert.showAndWait();
					return;
				}

				if (emailInput.charAt(i) == '@' && emailInput.length() > 1 && emailInput.charAt(i + 1) == '.') {
					Alert alert = new Alert(AlertType.ERROR, "@ cannot be next to .");
					alert.showAndWait();
					return;
				}
			}
			
			if (emailInput.startsWith("@") || emailInput.endsWith("@")) {
				Alert alert = new Alert(AlertType.ERROR, "Email cannot starts and ends with @");
				alert.showAndWait();
				return;
			}

			if (emailInput.startsWith(".") || emailInput.endsWith(".")) {
				Alert alert = new Alert(AlertType.ERROR, "Email cannot starts and ends with .");
				alert.showAndWait();
				return;
			}
			
			if(!emailInput.endsWith(".com")) {
				Alert alert = new Alert(AlertType.ERROR, "Email must ends with .com");
				alert.showAndWait();
				return;
			}

			if (passwordInput.length() < 6 || passwordInput.length() > 20) {
				Alert alert = new Alert(AlertType.ERROR, "Password must be between 6 - 20 characters");
				alert.showAndWait();
				return;
			}

			if (!confPassInput.equals(confPassInput)) {
				Alert alert = new Alert(AlertType.ERROR, "Password must be the same as confirm password");
				alert.showAndWait();
				return;
			}
			
			UserController.registerUser(nameInput, emailInput, passwordInput, genderInput);
		});

		loginBtn.setOnMouseClicked(event -> {
			Login lg = Login.getInstance();
			lg.show();
		});
	}
}
