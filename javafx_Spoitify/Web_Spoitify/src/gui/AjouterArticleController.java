package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.GapContent;

import Entities.Article;
import Entities.User;
import Services.ServiceArticle;
import Services.ServiceUser;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
   

public class AjouterArticleController implements Initializable {

	@FXML
	private Label nom;
	@FXML
	private Button btnAjouter;
  

	@FXML
	private Button btnChoisir;
	@FXML
	private ImageView imageview;
	@FXML
	private TextField textdesc;
	 

 
	@FXML
	private TextField txtnom;

	@FXML
	private TextField txtnumF;

	@FXML
	private TextField txtnumT;
	@FXML
	private ComboBox<User> comboBoxUser;
	@FXML
	private Stage stage;
	private ServiceUser serviceUser;
	private ObservableList<String>UserList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
		serviceUser = new ServiceUser();
		UserList = FXCollections.observableArrayList();
		setComboBoxItems();
 
		};
		private void setComboBoxItems() {
			// Retrieve all reclamations and their corresponding users
			ObservableList<User> users = serviceUser.afficherTous();

			// Add the user names to the list

			// Set the items of the combo box
			comboBoxUser.setItems(users);
			
			comboBoxUser.setConverter(new StringConverter<>() {
	            @Override
	            public String toString(User object) {
	                if (object != null) {
	                    return object.getFirstName();
	                } else return "";
	            }

	            @Override
	            public User fromString(String string) {
	                return comboBoxUser.getSelectionModel().getSelectedItem();
	            }
	        });
		}

	@FXML
	void ajouter(ActionEvent event) {
		boolean test = true;
		String title;
		String content ;
		title = txtnom.getText();
 
		content = textdesc.getText();
	 
 

		if ((txtnom.getText().isEmpty()) || (textdesc.getText().isEmpty()) ) {
		 
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("oops");
				alert1.setHeaderText(null);
				alert1.setContentText("remplir tous les champs");
				alert1.showAndWait();
				return;

			}
		 

 
	 

		else {

			Article nouvelleArticle = new Article();
			nouvelleArticle.setTitle(title);
 
 			 
			nouvelleArticle.setContent(content);
			nouvelleArticle.setUserId( comboBoxUser.getSelectionModel().getSelectedItem().getId());
			ServiceArticle bs = new ServiceArticle();
			bs.ajouter(nouvelleArticle );
			
			   Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Confirmation");
	            alert.setHeaderText(null);
	            alert.setContentText("Ajouter Article est avec succer   " );
	            alert.showAndWait();

			 
			}
		}

 
	 

	
		
		
		
 
	 

}
