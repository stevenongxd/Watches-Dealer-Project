package controller;

import java.sql.ResultSet;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Brand;

public class BrandController {

	private static Brand selectedBrand;

	public static Brand getSelectedBrand() {
		return selectedBrand;
	}

	public static void setSelectedBrand(Brand setSelectedBrand) {
		BrandController.selectedBrand = setSelectedBrand;
	}
	
	public static void clearSelectedBrand() {
		BrandController.selectedBrand = null;
	}

	public static boolean isSelectedBrand() {
		return selectedBrand == null ? false : true;
	}

	private static Database connect = Database.getConnection();

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
