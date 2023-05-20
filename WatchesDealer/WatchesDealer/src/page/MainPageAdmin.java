package page;

import controller.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import main.Main;

public class MainPageAdmin {
	private static MainPageAdmin mainPageAdmin;

	Scene scene;
	BorderPane bp;
	MenuBar menuBar;
	Menu menu, managementMenu;
	MenuItem manageWatchMenuItem, manageBrandMenuItem, logoutMenuItem;

	public static MainPageAdmin getInstance() {
		if (mainPageAdmin == null) {
			mainPageAdmin = new MainPageAdmin();
		}
		return mainPageAdmin;
	}

	public void show() {
		createLayout();
		Main.changeScene(scene, "Main Page Admin");
	}

	public void createLayout() {
		initialize();
		addEventListener();
	}

	public void initialize() {
		bp = new BorderPane();

		menuBar = new MenuBar();
		menu = new Menu("Admin");
		managementMenu = new Menu("Manage");

		manageWatchMenuItem = new MenuItem("Manage Watch");
		manageBrandMenuItem = new MenuItem("Manage Brand");
		logoutMenuItem = new MenuItem("Logout");

		managementMenu.getItems().add(manageWatchMenuItem);
		managementMenu.getItems().add(manageBrandMenuItem);
		menu.getItems().add(logoutMenuItem);

		menuBar.getMenus().add(menu);
		menuBar.getMenus().add(managementMenu);
		bp.setTop(menuBar);

		scene = new Scene(bp, 1000, 800);
	}

	public void addEventListener() {
		manageWatchMenuItem.setOnAction(e -> {
			ManageWatch mwp = ManageWatch.getInstance();
			bp.setCenter(mwp.createWindow());
		});

		manageBrandMenuItem.setOnAction(e -> {
				ManageBrand mbp = ManageBrand.getInstance();
				bp.setCenter(mbp.createWindow());
		});

		logoutMenuItem.setOnAction(e -> {
			UserController.logout();
		});
	}

}
