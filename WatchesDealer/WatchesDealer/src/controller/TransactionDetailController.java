package controller;

import java.sql.ResultSet;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TransactionDetail;

public class TransactionDetailController {

	private static Connect connect = Connect.getConnection();

	public static void addDetailTransaction(Integer transactionID, Integer watchID, Integer quantity) {
		String query = String.format(
				"INSERT INTO `detailtransaction`(`TransactionID`, `WatchID`, `Quantity`) VALUES ('%d','%d','%d')",
				transactionID, watchID, quantity);
		connect.executeUpdate(query);
	}

	public static ObservableList<TransactionDetail> getAllTransactionDetail(Integer transactionID) {
		ObservableList<TransactionDetail> listTrasanctionDetail = FXCollections.observableArrayList();
		String query = String.format("SELECT * FROM detailtransaction WHERE TransactionID = '%d'", transactionID);
		ResultSet rs = connect.executeQuery(query);

		try {
			while (rs.next()) {
				Integer watchID = rs.getInt("WatchID");
				Integer quantity = rs.getInt("Quantity");

				TransactionDetail td = new TransactionDetail(transactionID, watchID, quantity);
				listTrasanctionDetail.add(td);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listTrasanctionDetail;
	}
}
