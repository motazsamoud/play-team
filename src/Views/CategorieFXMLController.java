/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entities.Categorie;
import Entities.StarRating;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import Service.CategorieService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TECHN
 */
public class CategorieFXMLController implements Initializable {

    @FXML
    private TableView<Categorie> tableEvent;
    @FXML
    private TableColumn<Categorie, Integer> id;
    @FXML
    private TableColumn<Categorie, Integer> rate;
    @FXML
    private TableColumn<Categorie, String> nom;
    @FXML
    private TableColumn<Categorie, String> date;
    @FXML
    private TableColumn<Categorie, String> description;
    @FXML
    private TextArea idEvent;
    
    @FXML
    private TextArea rateEvent;
    @FXML
    private TextArea nomEvent;
    @FXML
    private TextArea dateEvent;
    @FXML
    private TextArea descriptionEvent;
    @FXML
    private Button ajouterEvent;
    @FXML
    private Button fermerEvent;
    @FXML
    private Button supprimerEvent;
    @FXML
    private Button modifierEvent;
    
    Connection mc;
    PreparedStatement ste;
    ObservableList<Categorie>eventList;
    
    @FXML
    private TextArea rechercher;
    
    
    
    
    
    class StarRatingCell extends TableCell<Categorie, Integer> {  //pour mettre les etoiles au graphique
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            StarRating starRating = new StarRating(item); // est appelée pour mettre à jour la cellule avec l'élément et l'état fournis par la superclasse TableCell.
            setText(starRating.display());
        } else {
            setText(null);
        }
    }
}
    
    
    
  
  
  public static boolean isValidDate(String dateString) { //pour valider la date
    String[] dateParts = dateString.split("/");
    if (dateParts.length != 3) {
      return false;
    }
    
    int day;
    int month;
    int year;
    try {
      day = Integer.parseInt(dateParts[0]);
      month = Integer.parseInt(dateParts[1]);
      year = Integer.parseInt(dateParts[2]);
    } catch (NumberFormatException e) {
      return false;
    }
    
    if (month < 1 || month > 12) {
      return false;
    }
    if(year <2022) 
    { 
     return false;
    }
    int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int maxDays = daysInMonth[month - 1];
    if (month == 2 && isLeapYear(year)) {
      maxDays = 29;
    }
    
    if (day < 1 || day > maxDays) {
      return false;
    }
    
    return true;
  }
  //année est bissextile, 
  
  public static boolean isLeapYear(int year) {
    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
      return true;
    }
    return false;
  }

    
  
  
  
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       afficherCategories();
    }
     
 
    
    
    
    void afficherCategories(){
            mc=MyConnection.getInstance().getCnx();
            eventList = FXCollections.observableArrayList();
      
      try {
            String requete = "SELECT * FROM categorie e ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Categorie e = new Categorie();
                e.setId_cat(rs.getInt("id_cat"));
                e.setrate(rs.getInt("rate"));
                e.setNom(rs.getString("nom"));
                e.setDate_event(rs.getString("date_event"));
                e.setDescription_cat(rs.getString("description_cat")); 
                System.out.println("the added events :" +e.toString());
                eventList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
  id.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId_cat()));
  
  rate.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getrate())); //rate avec les etoiles
  rate.setCellFactory(col -> new StarRatingCell());
  
  nom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
  date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate_event()));
  description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription_cat()));
  
  tableEvent.setItems(eventList);
  
  refresh();
  
  }
  
  public void refresh(){
            eventList.clear();
            mc=MyConnection.getInstance().getCnx();
            eventList = FXCollections.observableArrayList();
      
      try {
            String requete = "SELECT * FROM categorie e ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Categorie e = new Categorie();
                e.setId_cat(rs.getInt("id_cat"));
                e.setrate(rs.getInt("rate"));
                e.setNom(rs.getString("nom"));
                
                e.setDate_event(rs.getString("date_event"));
                e.setDescription_cat(rs.getString("description_cat")); 
                System.out.println("the added events :" +e.toString());
                
                eventList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        tableEvent.setItems(eventList);
        search();
  }




    

    @FXML
    private void addEvent(MouseEvent event) {
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
        String date =dateEvent.getText();
        String description =descriptionEvent.getText();
        
        if (rate.isEmpty() || nom.isEmpty()|| date.isEmpty()|| description.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Il y a un champ vide !!"); // controle de saisie vide
             alert.showAndWait();          
         }
        
        else if(isValidDate(date)==false)
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("votre date est invalide !!"); // controle de saisie aal date
             alert.showAndWait();
        }
        
        
        else if(Integer.parseInt(rate)>5)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("MAL RATING !!"); // controle de saisie aal rate 
             alert.showAndWait();
        
        }
        
         else{
        
        
        Categorie e= new Categorie(1,Integer.parseInt(rate),nom,date,description);
        
        ICategorieService es= new CategorieService();
        es.ajouterCategorie(e);                                //appel de la fonction ajouter        
        
        rateEvent.setText(null);
        nomEvent.setText(null);                               //yrodlik les text area vide baad AJOUT
        dateEvent.setText(null);
        descriptionEvent.setText(null);
        
        
        refresh();
    }
    }
    
    
    
    @FXML
    private void closeEvent(MouseEvent event) {
        Stage stage =(Stage) fermerEvent.getScene().getWindow();
        stage.close();  
        }
    
    
    
    @FXML
    private void deleteEvent(MouseEvent event) {
      
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Warning");
       alert.setContentText("Es-tu sûre de supprimer!");

        String Value1 = idEvent.getText();
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
        String date =dateEvent.getText();
        String description =descriptionEvent.getText();
        
        
        Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
      
         Categorie e= new Categorie(Integer.parseInt(Value1),Integer.parseInt(rate),nom,date,description);
        ICategorieService es= new CategorieService();
        es.supprimerCategorie(e);
        refresh();
      
        rateEvent.setText(null);
        nomEvent.setText(null);
        dateEvent.setText(null);
        descriptionEvent.setText(null);
                
        }
        
        }
    
  
    
    
    
    @FXML
    private void updateEvent(MouseEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de modifier!");
        
        
        
        String Value1 = idEvent.getText();
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
        String date =dateEvent.getText();
        String description =descriptionEvent.getText();
        
        if (rate.isEmpty() || nom.isEmpty()|| date.isEmpty()|| description.isEmpty()){
            
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Il y a un champ vide !!"); // controle de saisie
             alert.showAndWait();          
         }
        
        else if(isValidDate(date)==false)
        {
         alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("votre date est invalide !!"); // controle de saisie
             alert.showAndWait();
        }
        
        
        else if(Integer.parseInt(rate)>5)
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("MAL RATING !!"); // controle de saisie
             alert.showAndWait();
        
        }
        
         else{
            Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
         Categorie e= new Categorie(Integer.parseInt(Value1),Integer.parseInt(rate),nom,date,description);
        ICategorieService es= new CategorieService();
        es.modifierCategorie(e);
        
        
        refresh();
    }
    }
    }
    
    
    
    @FXML
    private void selected(MouseEvent event) {
        Categorie clicked = tableEvent.getSelectionModel().getSelectedItem();
        idEvent.setText(String.valueOf(clicked.getId_cat()));
        rateEvent.setText(String.valueOf(clicked.getrate()));
        nomEvent.setText(String.valueOf(clicked.getNom()));
        dateEvent.setText(String.valueOf(clicked.getDate_event()));
        descriptionEvent.setText(String.valueOf(clicked.getDescription_cat()));
    }
    
    
   
    
    
    private void search() {      
        FilteredList<Categorie>filteredData = new FilteredList<>(eventList, b->true);
        rechercher.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(Categorie->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
               
                String lowerCaseFilter = newValue.toLowerCase(); 
                 if(String.valueOf(Categorie.getNom()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                  else if(String.valueOf(Categorie.getDate_event()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                return false;
                }
            });          
        });
        SortedList<Categorie>sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableEvent.comparatorProperty());
        tableEvent.setItems(sortedData);
    }
    
    
}
