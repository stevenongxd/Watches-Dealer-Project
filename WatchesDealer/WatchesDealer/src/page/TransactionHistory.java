package page;

import controller.TransactionDetailController;
import controller.TransactionHeaderController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jfxtras.labs.scene.control.window.Window;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionHistory {
	private static TransactionHistory transactionHistoryPage;

	private GridPane gp;
	private TableView<TransactionHeader> headerTransactionTable;
	private TableView<TransactionDetail> detailTransactionTable;
	private Label selectedHeaderTransactionLbl;
	private Window window;

	public static TransactionHistory getInstance() {
		if (transactionHistoryPage == null) {
			transactionHistoryPage = new TransactionHistory();
		}
		return transactionHistoryPage;
	}

	public Window createWindow() {
		initialize();
		setTableColumn();
		addEventListener();
		TransactionHeaderController.clearSelectedWatch();

		return window;
	}

	private void initialize() {
		gp = new GridPane();
		headerTransactionTable = new TableView<>();
		detailTransactionTable = new TableView<>();
		detailTransactionTable.setSelectionModel(null);
		selectedHeaderTransactionLbl = new Label("Selected Transaction: None");

		window = new Window("View Transaction History");
		
		gp.add(headerTransactionTable, 0, 1);
		gp.add(selectedHeaderTransactionLbl, 0, 2);
		gp.add(detailTransactionTable, 0, 3);

		window.getContentPane().getChildren().add(gp);
		
		gp.setVgap(20);
		
		window.setPadding(new Insets(10));
	}

	private void setTableColumn() {
		TableColumn<TransactionHeader, Integer> transactionIDTC = new TableColumn<TransactionHeader, Integer>(
				"Transaction ID");
		transactionIDTC.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("transactionID"));
		transactionIDTC.setMinWidth(325);

		TableColumn<TransactionHeader, Integer> userIDTC = new TableColumn<TransactionHeader, Integer>("User ID");
		userIDTC.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("userID"));
		userIDTC.setMinWidth(325);

		TableColumn<TransactionHeader, String> transactionDateTC = new TableColumn<TransactionHeader, String>(
				"Transaction Date");
		transactionDateTC.setCellValueFactory(new PropertyValueFactory<TransactionHeader, String>("transactionDate"));
		transactionDateTC.setMinWidth(325);

		headerTransactionTable.getColumns().add(transactionIDTC);
		headerTransactionTable.getColumns().add(userIDTC);
		headerTransactionTable.getColumns().add(transactionDateTC);
		refreshTransactionHeaderData();
		
		TableColumn<TransactionDetail, Integer> transactionIDTCTD = new TableColumn<TransactionDetail, Integer>(
				"Transaction ID");
		transactionIDTCTD.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("transactionID"));
		transactionIDTCTD.setMinWidth(120);

		TableColumn<TransactionDetail, Integer> watchIDTC = new TableColumn<TransactionDetail, Integer>("Watch ID");
		watchIDTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("watchID"));
		watchIDTC.setMinWidth(130);

		TableColumn<TransactionDetail, String> watchNameTC = new TableColumn<TransactionDetail, String>("Watch Name");
		watchNameTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, String>("watchName"));
		watchNameTC.setMinWidth(215);

		TableColumn<TransactionDetail, String> watchBrandTC = new TableColumn<TransactionDetail, String>("Watch Brand");
		watchBrandTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, String>("watchBrand"));
		watchBrandTC.setMinWidth(155);

		TableColumn<TransactionDetail, Integer> watchPriceTC = new TableColumn<TransactionDetail, Integer>("Watch Price");
		watchPriceTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("watchPrice"));
		watchPriceTC.setMinWidth(135);

		TableColumn<TransactionDetail, Integer> watchQuantityTC = new TableColumn<TransactionDetail, Integer>(
				"Quantity");
		watchQuantityTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("quantity"));
		watchQuantityTC.setMinWidth(110);

		TableColumn<TransactionDetail, Integer> subTotalTC = new TableColumn<TransactionDetail, Integer>("Sub Total");
		subTotalTC.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("subTotal"));
		subTotalTC.setMinWidth(110);

		detailTransactionTable.getColumns().add(transactionIDTCTD);
		detailTransactionTable.getColumns().add(watchIDTC);
		detailTransactionTable.getColumns().add(watchNameTC);
		detailTransactionTable.getColumns().add(watchBrandTC);
		detailTransactionTable.getColumns().add(watchPriceTC);
		detailTransactionTable.getColumns().add(watchQuantityTC);
		detailTransactionTable.getColumns().add(subTotalTC);
	}
	
	private void addEventListener() {
		headerTransactionTable.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TransactionHeader>() {

					@Override
					public void changed(ObservableValue<? extends TransactionHeader> observable,
							TransactionHeader oldValue, TransactionHeader newValue) {
						if (newValue != null) {
							TransactionHeaderController.setSelectedTransactionHeaderInTable(newValue);
							int transactionID = TransactionHeaderController.getSelectedTransactionHeaderInTable()
									.getTransactionID();
							selectedHeaderTransactionLbl.setText("Selected Transaction: " + transactionID);
							refreshTransactionDetailData(transactionID);
						}
					}
				});
	}

	private void refreshTransactionHeaderData() {
		headerTransactionTable.setItems(TransactionHeaderController.getAllTransactionHeader());
	}

	private void refreshTransactionDetailData(int transactionID) {
		detailTransactionTable.setItems(TransactionDetailController.getAllTransactionDetail(transactionID));
	}
}
