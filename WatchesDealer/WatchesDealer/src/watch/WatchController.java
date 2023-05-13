package watch;

import java.sql.ResultSet;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WatchController {
	private static Watch selectedWatchInTable;

	public static Watch getSelectedWatchInTable() {
		return selectedWatchInTable;
	}

	public static void setSelectedWatchInTable(Watch selectedWatchInTable) {
		WatchController.selectedWatchInTable = selectedWatchInTable;
	}

	public static void clearSelectedWatchInTable() {
		WatchController.selectedWatchInTable = null;
	}

	public static boolean isSelectedWatchInTable() {
		return selectedWatchInTable == null ? false : true;
	}

	private static Database connect = Database.getConnection();

	public static ObservableList<Watch> getAllWatch() {
		ObservableList<Watch> listWatch = FXCollections.observableArrayList();

		String query = "SELECT * FROM watch";
		ResultSet rs = connect.executeQuery(query);

		try {
			while (rs.next()) {
				int watchID = rs.getInt("WatchID");
				int brandID = rs.getInt("BrandID");
				String watchName = rs.getString("WatchName");
				int watchPrice = rs.getInt("WatchPrice");
				int watchStock = rs.getInt("WatchStock");

				Watch book = new Watch(watchID, brandID, watchName, watchPrice, watchStock);
				listWatch.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listWatch;
	}

	public static Watch getWatch(int id) {
		Database connect = Database.getConnection();
		String query = String.format("SELECT * FROM watch WHERE WatchID = '%d'", id);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				int watchID = rs.getInt("WatchID");
				int brandID = rs.getInt("BrandID");
				String watchName = rs.getString("WatchName");
				int watchPrice = rs.getInt("WatchPrice");
				int watchStock = rs.getInt("WatchStock");

				Watch w = new Watch(watchID, brandID, watchName, watchPrice, watchStock);
				return w;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void createWatch(int brandID, String watchName, int watchPrice, int watchStock) {
		String query = String
				.format("INSERT INTO `watch`(`WatchID`, `BrandID`, `WatchName`, `WatchPrice`, `WatchStock`) "
						+ "VALUES ('0','%d','%s','%d','%d')", brandID, watchName, watchPrice, watchStock);
		connect.executeUpdate(query);
	}

	public static void deleteWatch(int watchID) {
		Database connect = Database.getConnection();
		String query = String.format("DELETE FROM watch WHERE WatchID = '%d'", watchID);
		connect.executeUpdate(query);
	}

	public static void updateWatch(int watchID, int brandID, String watchName, int watchPrice, int watchStock) {
		Database connect = Database.getConnection();
		String query = String.format(
				"UPDATE `watch` SET `BrandID`='%d',`WatchName`='%s',`WatchPrice`='%d',`WatchStock`='%d' WHERE WatchID = '%d'",
				brandID, watchName, watchPrice, watchStock, watchID);
		connect.executeUpdate(query);
	}

}
