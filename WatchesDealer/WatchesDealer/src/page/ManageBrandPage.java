package page;

import controller.BrandController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jfxtras.labs.scene.control.window.Window;
import model.Brand;

public class ManageBrandPage {
	private static ManageBrandPage manageBrandPage;

	GridPane gp, gp2, gp3;
	TableView<Brand> brandTable;
	Label brandNameLbl;
	TextField brandNameTF;
	Button insertBrandBtn, updateBrandBtn, deleteBrandBtn;
	Window window;

	public static ManageBrandPage getInstance() {
		if (manageBrandPage == null) {
			manageBrandPage = new ManageBrandPage();
		}
		return manageBrandPage;
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

		brandTable = new TableView<>();

		brandNameLbl = new Label("Brand Name: ");
		brandNameTF = new TextField();
		brandNameTF.setPromptText("Name");

		insertBrandBtn = new Button("Insert Brand");
		updateBrandBtn = new Button("Update Brand");
		deleteBrandBtn = new Button("Delete Brand");

		window = new Window("Manage Brand");

		gp3.add(insertBrandBtn, 0, 0);
		gp3.add(updateBrandBtn, 1, 0);
		gp3.add(deleteBrandBtn, 2, 0);

		gp2.add(brandNameLbl, 0, 0);
		gp2.add(brandNameTF, 1, 0);

		gp.add(brandTable, 0, 1);
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
		TableColumn<Brand, Integer> brandIDTC = new TableColumn<Brand, Integer>("Brand ID");
		brandIDTC.setCellValueFactory(new PropertyValueFactory<Brand, Integer>("brandID"));
		brandIDTC.setMinWidth(487);

		TableColumn<Brand, String> brandNameTC = new TableColumn<Brand, String>("Brand Name");
		brandNameTC.setCellValueFactory(new PropertyValueFactory<Brand, String>("brandName"));
		brandNameTC.setMinWidth(488);

		brandTable.getColumns().add(brandIDTC);
		brandTable.getColumns().add(brandNameTC);
		refreshBrandTableData();
	}

	public void addEventListener() {
		brandTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Brand>() {

			@Override
			public void changed(ObservableValue<? extends Brand> observable, Brand oldValue, Brand newValue) {
				if (newValue != null) {
					BrandController.setSelectedBrandInTable(newValue);
					brandNameTF.setText(newValue.getBrandName());
				}
			}
		});

		insertBrandBtn.setOnMouseClicked(e -> {
			String brandName = brandNameTF.getText();

			if (brandName.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Brand name cannot be empty!");
				alert.show();
				return;
			}

			BrandController.createBrand(brandName);
			Alert alert = new Alert(AlertType.INFORMATION, "Brand creation success!");
			alert.show();
			resetLayout();
			refreshBrandTableData();
		});

		updateBrandBtn.setOnMouseClicked(e -> {
			if (!BrandController.isSelectedBrandInTable()) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a brand!");
				alert.show();
				return;
			}

			String brandName = brandNameTF.getText();

			if (brandName.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Brand name cannot be empty!");
				alert.show();
				return;
			}

			BrandController.updateBrand(BrandController.getSelectedBrandInTable().getBrandID(), brandName);
			Alert alert = new Alert(AlertType.INFORMATION, "Brand updation success!");
			alert.show();
			resetLayout();
			refreshBrandTableData();
		});

		deleteBrandBtn.setOnMouseClicked(e -> {
			if (!BrandController.isSelectedBrandInTable()) {
				Alert alert = new Alert(AlertType.ERROR, "You must select a brand!");
				alert.show();
				return;
			}

			BrandController.deleteBrand(BrandController.getSelectedBrandInTable().getBrandID());
			Alert alert = new Alert(AlertType.INFORMATION, "Brand deletion success!");
			alert.show();
			resetLayout();
			refreshBrandTableData();
		});
	}

	public void refreshBrandTableData() {
		brandTable.getItems().clear();
		brandTable.getItems().addAll(BrandController.getAllBrand());
	}

	public void resetLayout() {
		brandNameTF.setText("");
		BrandController.clearSelectedBrandInTable();;
		brandTable.getSelectionModel().clearSelection();
	}
}
