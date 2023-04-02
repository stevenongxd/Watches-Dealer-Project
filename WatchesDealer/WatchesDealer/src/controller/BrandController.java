package controller;

import java.sql.ResultSet;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Brand;

public class BrandController {

	private static Brand selectedBrandInTable;

	public static Brand getSelectedBrandInTable() {
		return selectedBrandInTable;
	}

	public static void setSelectedBrandInTable(Brand selectedBrandInTable) {
		BrandController.selectedBrandInTable = selectedBrandInTable;
	}
	
	public static void clearSelectedBrandInTable() {
		BrandController.selectedBrandInTable = null;
	}

	public static boolean isSelectedBrandInTable() {
		return selectedBrandInTable == null ? false : true;
	}

	private static Connect connect = Connect.getConnection();

	public static Brand getBrand(Integer brandID) {
		String query = String.format("SELECT * FROM brand WHERE BrandID = '%d'", brandID);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				String brandName = rs.getString("BrandName");

				Brand b = new Brand(brandID, brandName);
				return b;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ObservableList<Brand> getAllBrand() {
		ObservableList<Brand> brandList = FXCollections.observableArrayList();

		String query = "SELECT * FROM brand";
		ResultSet rs = connect.executeQuery(query);

		try {
			while (rs.next()) {
				Integer brandID = rs.getInt("BrandID");
				String brandName = rs.getString("BrandName");

				Brand b = new Brand(brandID, brandName);
				brandList.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return brandList;
	}

	public static void createBrand(String brandName) {
		String query = String.format("INSERT INTO `brand`(`BrandID`, `BrandName`) VALUES ('0','%s')", brandName);
		connect.executeUpdate(query);
	}

	public static void deleteBrand(Integer brandID) {
		String query = String.format("DELETE FROM brand WHERE BrandID = '%d'", brandID);
		connect.executeUpdate(query);
	}

	public static void updateBrand(Integer brandID, String brandName) {
		String query = String.format("UPDATE `brand` SET `BrandName`='%s' WHERE BrandID = '%d'", brandName, brandID);
		connect.executeUpdate(query);
	}

}
