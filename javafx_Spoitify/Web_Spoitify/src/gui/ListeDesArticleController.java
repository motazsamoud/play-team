package gui;

 
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Article;
import Services.ServiceArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
 
 

public class ListeDesArticleController implements Initializable{
     
	  
  

    @FXML
    private Button btn1;
    @FXML
    private TableColumn<Article, String> commentaire;

    @FXML
    private TableColumn<Article, String> emailcolumn;

    @FXML
    private TableColumn<Article, String> nom;
    @FXML
    private TableColumn<Article, String> date;
    @FXML
    private TableColumn<Article, String> respond;

 

    @FXML
    private TableView<Article> table;
 
   
 
    @FXML
    private Button btn11;
    
    

    
     
    
    
     
    
    ObservableList<Article> listeB = FXCollections.observableArrayList();
    @FXML
    public void show(){
    ServiceArticle bs=new ServiceArticle();
    listeB=bs.afficherTous();
        nom.setCellValueFactory(new PropertyValueFactory<>("id"));
        commentaire.setCellValueFactory(new PropertyValueFactory<>("title"));
        date.setCellValueFactory(new PropertyValueFactory<>("content"));
     
       

        respond.setCellValueFactory(new PropertyValueFactory<>("like"));
  
    
 
        table.setItems(listeB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    show();
}

 
 
    
    @FXML
    void Creer(ActionEvent event) {

    }

   
}

