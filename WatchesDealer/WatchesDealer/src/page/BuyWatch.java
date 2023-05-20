package page;

import java.util.Optional;

import controller.CartController;
import controller.TransactionHeaderController;
import controller.WatchController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jfxtras.labs.scene.control.window.Window;
import model.Cart;
import model.Watch;

public class BuyWatch {
	private static BuyWatch buyWatchPage;

	GridPane gp, gp2, gp3;
	TableView<Watch> watchTable;
	TableView<Cart> cartTable;
	Window window;
	Label selectedWatchLbl, quantityLbl;
	Spinner<Integer> quantitySpn;
	Button addToCartBtn, clearCartBtn, checkoutBtn;

	public static BuyWatch getInstance() {
		if (buyWatchPage == null) {
			buyWatchPage = new BuyWatch();
		}
		return buyWatchPage;
	}

	public Window createWindow() {
		initialize();
		setTableColumn();
		addEventListener();
		resetLayout();

		return window;
	}

	public void initialize() {
		gp = new GridPane();
		gp2 = new GridPane();
		gp3 = new GridPane();

		watchTable = new TableView<>();
		cartTable = new TableView<>();
		cartTable.setSelectionModel(null);
		selectedWatchLbl = new Label("Selected Watch: None");
		quantityLbl = new Label("Quantity: ");
		quantitySpn = new Spinner<>(0, Integer.MAX_VALUE, 0, 1);

		addToCartBtn = new Button("Add Watch To Cart");
		clearCartBtn = new Button("Clear Cart");
		checkoutBtn = new Button("Checkout");

		window = new Window("Buy Watch");

		gp2.add(quantityLbl, 0, 0);
		gp2.add(quantitySpn, 1, 0);
		gp2.add(addToCartBtn, 2, 0);

		gp3.add(clearCartBtn, 0, 0);
		gp3.add(checkoutBtn, 1, 0);

		gp.add(watchTable, 0, 1);
		gp.add(selectedWatchLbl, 0, 2);
		gp.add(gp2, 0, 3);
		gp.add(cartTable, 0, 4);
		gp.add(gp3, 0, 5);

		window.getContentPane().getChildren().add(gp);

		gp2.setAlignment(Pos.CENTER);
		gp3.setAlignment(Pos.CENTER);

		gp.setVgap(20);
		gp2.setHgap(20);
		gp3.setHgap(20);

		window.setPadding(new Insets(10));
	}

	public void setTableColumn() {
		TableColumn<Watch, Integer> watchIDTC = new TableColumn<Watch, Integer>("Watch ID");
		watchIDTC.setCellValueFactory(new PropertyValueFactory<Watch, Integer>("watchID"));
		watchIDTC.setMinWidth(125);

		TableColumn<Watch, String> watchNameTC = new TableColumn<Watch, String>("Watch Name");
		watchNameTC.setCellValueFactory(new PropertyValueFactory<Watch, String>("watchName"));
		watchNameTC.setMinWidth(355);

		TableColumn<Watch, String> watchBrandTC = new TableColumn<Watch, String>("Watch Brand");
		watchBrandTC.setCellValueFactory(new PropertyValueFactory<Watch, String>("brandName"));
		watchBrandTC.setMinWidth(165);

		TableColumn<Watch, Integer> watchPriceTC = new TableColumn<Watch, Integer>("Watch Price");
		watchPriceTC.setCellValueFactory(new PropertyValueFactory<Watch, Integer>("watchPrice"));
		watchPriceTC.setMinWidth(165);

		TableColumn<Watch, Integer> watchStockTC = new TableColumn<Watch, Integer>("Watch Stock");
		watchStockTC.setCellValueFactory(new PropertyValueFactory<Watch, Integer>("watchStock"));
		watchStockTC.setMinWidth(165);

		watchTable.getColumns().add(watchIDTC);
		watchTable.getColumns().add(watchNameTC);
		watchTable.getColumns().add(watchBrandTC);
		watchTable.getColumns().add(watchPriceTC);
		watchTable.getColumns().add(watchStockTC);
		refreshWatchData();

		TableColumn<Cart, Integer> cartUserIDTC = new TableColumn<Cart, Integer>("User ID");
		cartUserIDTC.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("userID"));
		cartUserIDTC.setMinWidth(325);

		TableColumn<Cart, Integer> watchIDTCCart = new TableColumn<Cart, Integer>("Watch ID");
		watchIDTCCart.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("watchID"));
		watchIDTCCart.setMinWidth(325);

		TableColumn<Cart, Integer> quantityTC = new TableColumn<Cart, Integer>("Quantity");
		quantityTC.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("quantity"));
		quantityTC.setMinWidth(325);

		cartTable.getColumns().add(cartUserIDTC);
		cartTable.getColumns().add(watchIDTCCart);
		cartTable.getColumns().add(quantityTC);
		refreshCartData();
	}

	public void addEventListener() {
		watchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Watch>() {

			@Override
			public void changed(ObservableValue<? extends Watch> observable, Watch oldValue, Watch newValue) {
				if (newValue != null) {
					WatchController.setSelectedWatchInTable(newValue);
					selectedWatchLbl
							.setText("Selected Watch: " + WatchController.getSelectedWatchInTable().getWatchName());
				}
			}
		});

		addToCartBtn.setOnMouseClicked(e -> {
			int buyingQuantity = quantitySpn.getValue();
			if (!WatchController.isSelectedWatch()) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a watch!");
				alert.show();
				return;
			}

			if (buyingQuantity > WatchController.getSelectedWatchInTable().getWatchStock() || buyingQuantity < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Quantity must be bigger than the stock!");
				alert.show();
				return;
			}

			if (buyingQuantity < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Quantity must be higher than 0");
				alert.show();
				return;
			}

			CartController.addCart(WatchController.getSelectedWatchInTable().getWatchID(), buyingQuantity);
			Alert alert = new Alert(AlertType.INFORMATION, "Watch added to cart!");
			alert.show();
			refreshCartData();
			resetLayout();
		});

		clearCartBtn.setOnMouseClicked(e -> {

			if (CartController.getAllCart().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Cart cannot be empty!");
				alert.show();
				return;
			}

			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to clear cart?");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) {
				CartController.deleteCart();
				Alert alert2 = new Alert(AlertType.INFORMATION, "Cart cleared!");
				alert2.show();
				refreshCartData();
				resetLayout();
				return;
			}
		});

		checkoutBtn.setOnMouseClicked(e -> {
			if (CartController.getAllCart().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Cart cannot be empty!");
				alert.show();
				return;
			}

			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to checkout?");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) {
				TransactionHeaderController.addHeaderTransaction();
				Alert alert2 = new Alert(AlertType.INFORMATION, "Checkout success!");
				alert2.show();
				refreshCartData();
				resetLayout();
				return;
			}
		});
	}

	public void refreshWatchData() {
		watchTable.getItems().clear();
		watchTable.getItems().addAll(WatchController.getAllWatch());
	}

	public void refreshCartData() {
		cartTable.setItems(CartController.getAllCart());
	}

	public void resetLayout() {
		selectedWatchLbl.setText("Selected Watch: None");
		quantitySpn.getValueFactory().setValue(0);
		WatchController.clearSelectedWatch();
		watchTable.getSelectionModel().clearSelection();
	}
}
