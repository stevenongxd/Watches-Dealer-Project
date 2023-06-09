package controller;

import java.sql.ResultSet;

import database.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;
import page.Login;
import page.MainPageAdmin;
import page.MainPageCustomer;

public class UserController {

	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrUser(User currentUser) {
		UserController.currentUser = currentUser;
	}

	private static Database connect = Database.getConnection();

	public static void login(String emailInput, String passwordInput) {
		String query = String.format("SELECT * FROM user WHERE UserEmail = '%s' AND UserPassword = '%s'", emailInput,
				passwordInput);
		ResultSet rs = connect.executeQuery(query);
		try {
			if (rs.next()) {
				Integer userID = rs.getInt("UserID");
				String userName = rs.getString("UserName");
				String userEmail = rs.getString("UserEmail");
				String userPassword = rs.getString("UserPassword");
				String userGender = rs.getString("UserGender");
				String userRole = rs.getString("UserRole");

				User u = new User(userID, userName, userEmail, userPassword, userGender, userRole);
				setCurrUser(u);
				if (u.getUserRole().equals("Customer")) {
					MainPageCustomer mpc = MainPageCustomer.getInstance();
					mpc.show();
				} else {
					MainPageAdmin mpa = MainPageAdmin.getInstance();
					mpa.show();
				}
				Alert alert = new Alert(AlertType.INFORMATION, "Login success!");
				alert.showAndWait();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.ERROR, "Credential invalid!");
		alert.showAndWait();
	}

	public static void registerUser(String nameInput, String emailInput, String passwordInput, String genderInput) {
		Database connect = Database.getConnection();
		String query = String.format(
				"INSERT INTO user (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserRole`) VALUES ('0','%s','%s','%s','%s','Customer')", nameInput, emailInput, passwordInput, genderInput);
		connect.executeUpdate(query);
		Alert alert = new Alert(AlertType.INFORMATION, "Register success!");
		alert.showAndWait();
		Login lg = Login.getInstance();
		lg.show();
	}

	public static void logout() {
		Alert alert = new Alert(AlertType.INFORMATION, "Logout success!");
		alert.showAndWait();
		UserController.setCurrUser(null);
		Login lg = Login.getInstance();
		lg.show();
	}
}
