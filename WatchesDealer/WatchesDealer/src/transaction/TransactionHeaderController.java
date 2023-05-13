package transaction;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import user.UserController;

public class TransactionHeaderController {
	private static TransactionHeader selectedTransactionHeaderInTable;

	public static TransactionHeader getSelectedTransactionHeaderInTable() {
		return selectedTransactionHeaderInTable;
	}

	public static void setSelectedTransactionHeaderInTable(TransactionHeader selectedTransactionHeaderInTable) {
		TransactionHeaderController.selectedTransactionHeaderInTable = selectedTransactionHeaderInTable;
	}

	public static void clearSelectedWatchInTable() {
		TransactionHeaderController.selectedTransactionHeaderInTable = null;
	}

	public static boolean isSelectedWatchInTable() {
		return selectedTransactionHeaderInTable == null ? false : true;
	}

	private static Database connect = Database.getConnection();

	public static Integer getNextID() {
		String queryNextID = "SHOW TABLE STATUS WHERE name = 'headertransaction'";
		ResultSet rs = connect.executeQuery(queryNextID);
		Integer nextID = -1;
		try {
			while (rs.next()) {
				nextID = rs.getInt("Auto_increment");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextID;
	}

	public static void addHeaderTransaction() {
		String query = String.format(
				"INSERT INTO `headertransaction`(`TransactionID`, `UserID`, `TransactionDate`) VALUES ('%d','%d','%s')",
				0, UserController.getCurrUser().getUserID(), getDateString());
		connect.executeUpdate(query);

		for (Cart c : CartController.getAllCart()) {
			TransactionDetailController.addDetailTransaction(getNextID() - 1, c.getWatchID(), c.getQuantity());
			CartController.deleteCart();
		}
	}

	public static ObservableList<TransactionHeader> getAllTransactionHeader() {
		ObservableList<TransactionHeader> listTransactionHeader = FXCollections.observableArrayList();
		String query = String.format("SELECT * FROM headertransaction WHERE UserID = '%d'", UserController.getCurrUser().getUserID());
		ResultSet rs = connect.executeQuery(query);

		try {
			while (rs.next()) {
				Integer transactionID = rs.getInt("TransactionID");
				Integer userID = rs.getInt("UserID");
				String transactionDate = rs.getString("TransactionDate");
				TransactionHeader tr = new TransactionHeader(transactionID, userID, transactionDate);
				listTransactionHeader.add(tr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listTransactionHeader;
	}

	public static String getDateString() {
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStr = dateObj.format(formatter);
		return dateStr;
	}

}
