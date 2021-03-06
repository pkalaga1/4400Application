package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Database.DBModel;
import Model.City;
import Model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class WelcomeController extends BasicController{
	
	/**All the user input variables */
	@FXML
	private TextField txtAttraction;
	@FXML
	private ComboBox<City> cmbCity;
	@FXML
	private ComboBox<Category> cmbCategory;
	@FXML
	private Label lblName;
	
	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {
		
		//Set the name of the page
		lblName.setText(mainModel.getUser().getEmail());
		
		//Populate the comboboxes
		
		List<Category> catList = new ArrayList<>();
		//Populate catList with our categories
		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CATEGORY order by Cname ASC";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("CName");
				Category c = new Category(name);
				catList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<City> cityList = new ArrayList<>();
		//Populate cityList with our cities


		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CITY as C, REVIEWABLE_ENTITY as E WHERE C.CityID = E.EntityID AND E.IsPending = FALSE order by name ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Name");
				String country = resultSet.getString("Country");
				String state = resultSet.getString("State");
				int cityID = resultSet.getInt("CityID");
				City c = new City(name, cityID, country, state);
				cityList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Category> cmbCats = FXCollections.observableList(catList);
		ObservableList<City> cmbCities = FXCollections.observableList(cityList);

		cmbCategory.setItems(cmbCats);
		cmbCity.setItems(cmbCities);
		
	}
	/**
	 * This will filter results based on the text fields
	 * that the user enters
	 */
	@FXML
	public void handleSearchPressed() {
		City city;
		String attr = "";
		String cat = "";



		//Parse through txtCategory for each one and add to categories
		if (cmbCity.getValue() == null
				&& txtAttraction.getText().trim().equals("")
				&& cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please put a city, attraction, or category");
			alert.showAndWait();

		} else {


			if (cmbCity.getValue() == null) {
				city = new City("null City");
			} else {
				city = cmbCity.getValue();
			}
			if (txtAttraction.getText().trim().equals("")) {
				attr = "";
			} else {
				attr = txtAttraction.getText();
			}
			if (cmbCategory.getValue() != null) {
				cat = cmbCategory.getValue().getName();
			} else {
				cat = "";
			}

			mainModel.setFilter(city, attr, cat, true);

			showScreen("../View/AttractionsListFiltered.fxml", "Search Results");

		}
	}
	/**
	 * Show the list of all cities
	 */
	@FXML
	public final void handleViewCitiesPressed() {
		showScreen("../View/CityList.fxml", "Cities List");
	}
	
	/**
	 * Shows unfiltered Attractions
	 */
	@FXML
	public final void handleViewAttractionsPressed() {
		showScreen("../View/AttractionsList.fxml", "Attractions");
		
	}
	
	/**
	 * Shows the users reviews
	 */
	@FXML
	public final void handleMyReviewsPressed() {
		showScreen("../View/UserReviews.fxml","Your Reviews" + mainModel.getUser().getEmail());
	}
	
	/**
	 * Returns to the home screen
	 */
	@FXML
	public final void handleLogoutPressed() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you would like to log out?");

		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType okay = new ButtonType("Okay");

		alert.getButtonTypes().setAll(cancel, okay);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okay) {
			showScreen("../view/Login.fxml", "Login");
		} else {
			alert.close();
		}

	}
	
	/**
	 * Shows the dialog before an account is deleted
	 * If delete - sends to login screen
	 * Else returns to main screen
	 */
	@FXML
	public final void handleDeletePressed() {
		//Delete the account from the database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Deleting this account will delete all the data that this account made or is related to");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "DELETE FROM USER WHERE Email = ?;";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getUser().getEmail());
				stmnt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			showScreen("../view/Login.fxml", "Login");
		} else{
			alert.close();
		}
		
	}
}
