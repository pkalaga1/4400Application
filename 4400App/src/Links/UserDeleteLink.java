package Links;

import Database.DBModel;
import Model.City;
import Model.User;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * Created by alyssatan on 7/24/17.
 */
public class UserDeleteLink extends Hyperlink{
    private User deleteUser;

    public UserDeleteLink(User user) {
        deleteUser = user;
        super.setText("Delete");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete");
                alert.setContentText("Deleting this user will delete all reviews/cities/attractions associated with this user");

                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType delete = new ButtonType("Delete");
                alert.getButtonTypes().setAll(cancel, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete) {
                    try {
                        Connection con = DBModel.getInstance().getConnection();
                        String query = "DELETE FROM USER WHERE Email = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setString(1, deleteUser.getEmail());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/UsersList.fxml");
                } else {
                    alert.close();
                }
            }
        });
    }
}
