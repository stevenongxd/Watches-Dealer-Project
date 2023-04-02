package page;

import controller.BrandController;
import controller.WatchController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jfxtras.labs.scene.control.window.Window;
import model.Brand;
import model.Watch;

public class ManageWatchPage {
	private static ManageWatchPage manageWatchPage;

	GridPane gp, gp2, gp3;
	TableView<Watch> watchTable;
	Label watchNameLbl, watchStockLbl, watchPriceLbl, watchBrandLbl;
	Spinner<Integer> watchStockSpn;
	ComboBox<Brand> watchBrandCB;
	TextField watchNameTF, watchPriceTF;
	Button insertWatchBtn, updateWatchBtn, deleteWatchBtn;
	Window window;

	public static ManageWatchPage getInstance() {
		if (manageWatchPage == null) {
			manageWatchPage = new ManageWatchPage();
		}
		return manageWatchPage;
	}

	public Window createWindow() {
		initialize();
		setTableColumn();
		addEventListener();
		resetLayout();

		return window;
	}

	private void initialize() {

		gp = new GridPane();
		gp2 = new GridPane();
		gp3 = new GridPane();

		watchTable = new TableView<>();

		watchNameLbl = new Label("Watch Name:");
		watchStockLbl = new Label("Watch Stock:");
		watchPriceLbl = new Label("Watch Price:");
		watchBrandLbl = new Label("Watch Brand:");

		watchNameTF = new TextField();
		watchNameTF.setPromptText("Name");

		watchPriceTF = new TextField();
		watchPriceTF.setPromptText("Price");

		watchStockSpn = new Spinner<>(0, 200000000, 0, 1);

		watchBrandCB = new ComboBox<>();
		watchBrandCB.setItems(BrandController.getAllBrand());
		watchBrandCB.setPromptText("Choose One");

		insertWatchBtn = new Button("Insert Watch");
		updateWatchBtn = new Button("Update Watch");
		deleteWatchBtn = new Button("Delete Watch");

		window = new Window("Manage Watch");

		gp3.add(insertWatchBtn, 0, 0);
		gp3.add(updateWatchBtn, 1, 0);
		gp3.add(deleteWatchBtn, 2, 0);

		gp2.add(watchNameLbl, 0, 0);
		gp2.add(watchNameTF, 1, 0);
		gp2.add(watchPriceLbl, 2, 0);
		gp2.add(watchPriceTF, 3, 0);
		gp2.add(watchStockLbl, 0, 1);
		gp2.add(watchStockSpn, 1, 1);
		gp2.add(watchBrandLbl, 2, 1);
		gp2.add(watchBrandCB, 3, 1);

		gp.add(watchTable, 0, 1);
		gp.add(gp2, 0, 2);
		gp.add(gp3, 0, 3);

		window.getContentPane().getChildren().add(gp);

		gp2.setAlignment(Pos.CENTER);
		gp3.setAlignment(Pos.CENTER);

		gp.setVgap(20);
		gp2.setVgap(20);
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
		refreshTableData();
	}

	public void addEventListener() {
		watchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Watch>() {

			@Override
			public void changed(ObservableValue<? extends Watch> observable, Watch oldValue, Watch newValue) {
				if (newValue != null) {
					WatchController.setSelectedWatchInTable(newValue);
					watchNameTF.setText(WatchController.getSelectedWatchInTable().getWatchName());
					watchPriceTF.setText(Integer.toString(WatchController.getSelectedWatchInTable().getWatchPrice()));
					watchStockSpn.getValueFactory().setValue(WatchController.getSelectedWatchInTable().getWatchStock());
					watchBrandCB.getSelectionModel().select(WatchController.getSelectedWatchInTable().getBrand());
				}
			}
		});

		insertWatchBtn.setOnMouseClicked(e -> {
			String watchName = watchNameTF.getText();
			String watchPriceStr = watchPriceTF.getText();
			int watchStock = watchStockSpn.getValue();
			int watchBrand = watchBrandCB.getSelectionModel().getSelectedItem() != null
					? watchBrandCB.getSelectionModel().getSelectedItem().getBrandID()
					: -1;

			if (!watchName.endsWith(" Watch")) {
				Alert alert = new Alert(AlertType.ERROR, "Watch name must ends with  Watch");
				alert.show();
				return;
			}

			int watchPrice;

			if (watchPriceStr.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Price cannot be empty!");
				alert.show();
				return;
			}
			try {
				watchPrice = Integer.parseInt(watchPriceStr);
			} catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR, "Price must be numeric");
				alert.show();
				return;
			}

			if (watchPrice < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Price must be higher than 0");
				alert.show();
				return;
			}

			if (watchStock < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Stock must be higher than 0");
				alert.show();
				return;
			}

			if (watchBrand == -1) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a brand!");
				alert.show();
				return;
			}

			WatchController.createWatch(watchBrand, watchName, watchPrice, watchStock);
			Alert alert = new Alert(AlertType.INFORMATION, "Watch creation success!");
			alert.show();
			resetLayout();
			refreshTableData();
		});

		updateWatchBtn.setOnMouseClicked(e -> {
			if (!WatchController.isSelectedWatchInTable()) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a watch!");
				alert.show();
				return;
			}

			String watchName = watchNameTF.getText();
			String watchPriceStr = watchPriceTF.getText();
			int watchStock = watchStockSpn.getValue();
			int watchBrand = watchBrandCB.getSelectionModel().getSelectedItem() != null
					? watchBrandCB.getSelectionModel().getSelectedItem().getBrandID()
					: -1;

			if (!watchName.endsWith(" Watch")) {
				Alert alert = new Alert(AlertType.ERROR, "Watch name must ends with  Watch");
				alert.show();
				return;
			}

			int watchPrice;

			if (watchPriceStr.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Price cannot be empty!");
				alert.show();
				return;
			}
			try {
				watchPrice = Integer.parseInt(watchPriceStr);
			} catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR, "Price must be numeric");
				alert.show();
				return;
			}

			if (watchPrice < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Price must be higher than 0");
				alert.show();
				return;
			}

			if (watchStock < 1) {
				Alert alert = new Alert(AlertType.ERROR, "Stock must be higher than 0");
				alert.show();
				return;
			}

			if (watchBrand == -1) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a brand!");
				alert.show();
				return;
			}

			WatchController.updateWatch(WatchController.getSelectedWatchInTable().getWatchID(), watchBrand, watchName,
					watchPrice, watchStock);
			Alert alert = new Alert(AlertType.INFORMATION, "Watch updation success!");
			alert.show();
			resetLayout();
			refreshTableData();
		});

		deleteWatchBtn.setOnMouseClicked(e -> {
			if (!WatchController.isSelectedWatchInTable()) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a watch!");
				alert.show();
				return;
			}

			WatchController.deleteWatch(WatchController.getSelectedWatchInTable().getWatchID());
			Alert alert = new Alert(AlertType.INFORMATION, "Watch updation success!");
			alert.show();
			resetLayout();
			refreshTableData();
		});
	}

	public void refreshTableData() {
		watchTable.getItems().clear();
		watchTable.getItems().addAll(WatchController.getAllWatch());
	}

	public void resetLayout() {
		watchNameTF.setText("");
		watchPriceTF.setText("");
		watchBrandCB.getSelectionModel().select(null);
		watchStockSpn.getValueFactory().setValue(0);
		WatchController.clearSelectedWatchInTable();
		watchTable.getSelectionModel().clearSelection();
	}
}
