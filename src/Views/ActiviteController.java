/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entities.Categorie;
import Entities.Activite;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import Service.CategorieService;
import Service.ActiviteService;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Interface.IActiviteService;
import static javax.xml.bind.DatatypeConverter.parseString;

/**
 * FXML Controller class
 *
 * @author TECHN
 */
public class ActiviteController implements Initializable {

    @FXML
    private TableView<Activite> tableRes;
    @FXML
    private TableColumn<Activite, Integer> id;
    @FXML
    private TableColumn<Activite, Integer> idactivite;
    @FXML
    private TableColumn<Activite, String> nom_ac;
    @FXML
    private TableColumn<Activite, String> description;
    @FXML
    private TextArea idActivite;
    @FXML
    private TextArea nom_acActivite;
    @FXML
    private TextArea descriptionActivite;
    @FXML
    private ComboBox<Integer> idEvent;
    @FXML
    private Button ajouterRes;
    @FXML
    private Button supprimerRes;
    @FXML
    private Button modifierRes;
    Connection mc;
    PreparedStatement ste;
    ObservableList<Activite>resList;
    @FXML
    private Button fermerRes;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            String req="select id_cat from categorie";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(req);
            ResultSet rs=pst.executeQuery();
            ObservableList<Integer> id = null;
            List<Integer> list = new ArrayList<>();
            while(rs.next()){
                
                list.add(rs.getInt("id_cat"));
                
            }
            id = FXCollections
                    .observableArrayList(list);
            idEvent.setItems(id);
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        afficherActivites();
    }  

    void afficherActivites(){
            mc=MyConnection.getInstance().getCnx();
            resList = FXCollections.observableArrayList();
      
      try {
            String requete = "SELECT * FROM activite r ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Activite r = new Activite();
                r.setId_activite(rs.getInt("id_activite"));
                r.setId_cat(rs.getInt("id_cat"));
                r.setNom_ac(rs.getString("nom_ac"));
                r.setDescription(rs.getString("description"));
                
                System.out.println("the added activites :" +r.toString());
                resList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
  id.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId_cat()));
  idactivite.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId_activite()));
  
  nom_ac.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom_ac()));
  description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
  
  
  
 
   tableRes.setItems(resList);
  
  refresh();
    
  }
  
  
    
    public void refresh(){
            resList.clear();
            mc=MyConnection.getInstance().getCnx();
            resList = FXCollections.observableArrayList();
      
      try {
            String requete = "SELECT * FROM activite r ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Activite r = new Activite();
                r.setId_activite(rs.getInt("id_activite"));
                r.setId_cat(rs.getInt("id_cat"));
                r.setNom_ac(rs.getString("nom_ac"));
                r.setDescription(rs.getString("description"));
                System.out.println("the added activites :" +r.toString());
                resList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        tableRes.setItems(resList);
       
  }
    
    @FXML
    private void selected(MouseEvent res) {
         Activite clicked = tableRes.getSelectionModel().getSelectedItem();
         
         idEvent.setValue(clicked.getId_cat());
         idActivite.setText(String.valueOf(clicked.getId_activite()));
        
        nom_acActivite.setText(String.valueOf(clicked.getNom_ac()));
         descriptionActivite.setText(String.valueOf(clicked.getDescription()));
    }


    @FXML
    private void addRes(MouseEvent event) {
        
        String idactivite =idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
        
         Activite r= new Activite(1,Integer.parseInt(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.ajouterActivite(r);
        refresh();
        
        
        idActivite.setText(null);
        nom_acActivite.setText("");
        descriptionActivite.setText("");
        
    
    }

    @FXML
    private void deleteRes(MouseEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Warning");
       alert.setContentText("Es-tu sûre de supprimer!");
       
       
       String Value1 = idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
    
          Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
        
        Activite r= new Activite(Integer.parseInt(Value1),Integer.parseInt(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.supprimerActivite(r);
        refresh();
        }
    }

    @FXML
    private void updateRes(MouseEvent event) {
        String Value1 = idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
    
        
        
        Activite r= new Activite(Integer.parseInt(Value1),Integer.parseInt(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.modifierActivite(r);
        refresh();
        
        
    }

    
    
    
    
    
    
    @FXML
    private void closeRes(MouseEvent event) {
        Stage stage =(Stage) fermerRes.getScene().getWindow();
        stage.close(); 
    }
    










    
}
