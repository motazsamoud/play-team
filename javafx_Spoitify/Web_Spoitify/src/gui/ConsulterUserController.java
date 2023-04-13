/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

 
import Entities.User;
import Services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

 import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
 import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author daly
 */
public class ConsulterUserController implements Initializable {

 @FXML
    private Button btn1;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<User, String> id;

   

    @FXML
    private TableColumn<User, String> first;
    @FXML
    private TableColumn<User, String> last;
  
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> phone ;
    @FXML
    private TableView<User> table;
    @FXML
    private TextField filtree;
  
    @FXML
    private Button btn11;

    @FXML
    void Creer(ActionEvent event) {
        try {
         
            
              Parent page1 = FXMLLoader.load(getClass().getResource("../gui/AdminUser.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
             }

    }
    @FXML
    void Modifier(ActionEvent event) {
     
     User selectedUser = table.getSelectionModel().getSelectedItem();
if (selectedUser == null) {
    // Afficher un message d'erreur
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Impossible de modifier");
    alert.setContentText("Veuillez sélectionner un utilisateur pour modifier !");
    alert.showAndWait();
} else {
    // Show an input dialog to get the new user details.
    Dialog<User> dialog = new Dialog<>();
    dialog.setTitle("Modifier un utilisateur");
    dialog.setHeaderText("Modifier les champs");

    // Set the default value of the input fields to the current user details.
    TextField firstNameField = new TextField(selectedUser.getFirstName());
    TextField lastNameField = new TextField(selectedUser.getLastName());
    TextField phoneNumberField = new TextField(selectedUser.getPhoneNumber());

    // Add the input fields to the dialog pane.
    GridPane grid = new GridPane();
    grid.add(new Label("Nom:"), 1, 1);
    grid.add(firstNameField, 2, 1);
    grid.add(new Label("Nom de famille:"), 1, 2);
    grid.add(lastNameField, 2, 2);
    grid.add(new Label("Numéro de téléphone:"), 1, 3);
    grid.add(phoneNumberField, 2, 3);
    dialog.getDialogPane().setContent(grid);

    // Add buttons to the dialog pane.
    ButtonType modifierButtonType = new ButtonType("Modifier", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

    // Convert the result to a user object when the modify button is clicked.
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == modifierButtonType) {
            return new User(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    phoneNumberField.getText()
            );
        }
        return null;
    });

    Optional<User> result = dialog.showAndWait();
    if (result.isPresent()) {
        // Update the selected user with the new values.
        selectedUser.setFirstName(result.get().getFirstName());
        selectedUser.setLastName(result.get().getLastName());
        selectedUser.setPhoneNumber(result.get().getPhoneNumber());

        // Call the service method to update the user in the database.
        ServiceUser bs = new ServiceUser();
        bs.modifier(selectedUser, selectedUser.getId());

        // Show a confirmation alert.
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Utilisateur a été modifié avec succès");
        alert.setContentText("Les modifications ont été enregistrées.");
        alert.showAndWait();
    }

}
table.refresh();
 
    }
    @FXML
    void Supprimer(ActionEvent event) {
       User selectedLN =  table.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer  ");
            alert.setContentText("Veuillez sélectionner pour supprimer !");
            alert.showAndWait();
        } else {
    ServiceUser bs=new ServiceUser();
System.out.println(selectedLN.getId());
            bs.supprimer(selectedLN.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("information");
            alert.setHeaderText(null);
            alert.setContentText("Evenements supprimée!");
            alert.showAndWait();

            // Actualiser le TableView
            show();
    }     
    }
    ObservableList<User> listeB = FXCollections.observableArrayList();

    public void show(){
    ServiceUser bs=new ServiceUser();
    listeB=bs.afficherTous();
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
      
            table.setItems(listeB);
          
 };

 


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    show();
}  
    
}
