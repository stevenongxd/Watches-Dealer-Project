package page;

import controller.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import main.Main;

public class MainPageCustomer {
	private static MainPageCustomer mainPageCustomer;

	Scene scene;
	BorderPane bp;
	MenuBar menuBar;
	Menu menu, transactionMenu;
	MenuItem buyWatchMenuItem, transactionHistoryMenuItem, logoutMenuItem;

	public static MainPageCustomer getInstance() {
		if (mainPageCustomer == null) {
			mainPageCustomer = new MainPageCustomer();
		}
		return mainPageCustomer;
	}

	public void show() {
		createLayout();
		Main.changeScene(scene, "Main Page Customer");
	}

	public void createLayout() {
		initialize();
		addEventListener();
	}

	public void initialize() {
		bp = new BorderPane();

		menuBar = new MenuBar();
		menu = new Menu("Customer");
		transactionMenu = new Menu("Transaction");

		buyWatchMenuItem = new MenuItem("Buy Watch");
		transactionHistoryMenuItem = new MenuItem("Transaction History");
		logoutMenuItem = new MenuItem("Logout");

		transactionMenu.getItems().add(buyWatchMenuItem);
		transactionMenu.getItems().add(transactionHistoryMenuItem);
		menu.getItems().add(logoutMenuItem);

		menuBar.getMenus().add(menu);
		menuBar.getMenus().add(transactionMenu);
		bp.setTop(menuBar);

		scene = new Scene(bp, 1000, 800);
	}

	public void addEventListener() {
		buyWatchMenuItem.setOnAction(e -> {
			BuyWatchPage bwp = BuyWatchPage.getInstance();
			bp.setCenter(bwp.createWindow());
		});

		transactionHistoryMenuItem.setOnAction(e -> {
			TransactionHistoryPage thp = TransactionHistoryPage.getInstance();
			bp.setCenter(thp.createWindow());
		});

		logoutMenuItem.setOnAction(e -> {
			UserController.logout();
		});
	}
}
