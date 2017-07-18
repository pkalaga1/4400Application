package Controller;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserReviewsController extends BasicController{
	@FXML
	ComboBox<String> cmbSort;
	
	@FXML
	TableView tblReviews;
		
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<String>();
		//Populate with items
		list.add("A-Z", "Z-A", "Rating:Best First", "Rating:Best Last");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
		
		//Do the inital population by A-Z sort
	}
	
	@FXML
	public void handleSortPressed() {
		String content = cmbSort.getPromptText();
		//Use content in SQL query to sort the table.
		if (content.equals("A-Z")) {
			
		} else if (content.equals("Z-A")){
			
		} else if (content.equals("Rating:Best First")) {
			
		} else if (content.equals("Rating:Best Last")) {
			
		}

	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/Welcome.fxml", "Welcome");
	}
}
