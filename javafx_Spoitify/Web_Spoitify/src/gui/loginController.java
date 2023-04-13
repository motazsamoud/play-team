package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class loginController  implements Initializable{
	private ServiceUser serviceUser;

    @FXML
    private TextField login;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button log;

    @FXML
    private Button regs;

    private  ServiceUser userService;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Associer un gestionnaire d'événements au bouton de connexion
       
    }
   
	@FXML
	void login(ActionEvent event)  {
		if ((login.getText().isEmpty()) || (passwordField.getText().isEmpty()) ) {
			 
			Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("oops");
			alert1.setHeaderText(null);
			alert1.setContentText("remplir tous les champs");
			alert1.showAndWait();
			return;

		}
		
        // Récupérer les informations de l'utilisateur et du mot de passe
        String username = login.getText();
        String password = passwordField.getText();
		serviceUser = new ServiceUser();

        // Vérifier si l'utilisateur existe dans la base de données
        boolean isAuthenticated = serviceUser.login(username, password);

        if (isAuthenticated) {
			   Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Confirmation");
	            alert.setHeaderText(null);
	            alert.setContentText("login avec succes   " );
	            alert.showAndWait();
	            try {
	            	
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherUser.fxml"));
	                Parent root = loader.load();

	                // Show the Modif.fxml interface
	                Scene scene = new Scene(root);
	                Stage stage = new Stage();
	                stage.setScene(scene);
	                stage.show();
	                } catch (IOException ex) {
	                    ex.printStackTrace();

	                 }
             
        } else {
        	Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("oops");
			alert1.setHeaderText(null);
			alert1.setContentText("Verifier !!!!! ");
			alert1.showAndWait();
			return;
        }
    }
	@FXML
	void regsitre(ActionEvent event)  {
		
		   try {
           	
               FXMLLoader loader = new FXMLLoader(getClass().getResource("registreUser.fxml"));
               Parent root = loader.load();

               // Show the Modif.fxml interface
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
               } catch (IOException ex) {
                   ex.printStackTrace();

                }
}
 






}
