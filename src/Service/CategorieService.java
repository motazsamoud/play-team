/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Categorie;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TECHN
 */
public class CategorieService implements ICategorieService <Categorie>{
    
    @Override
    public void ajouterCategorie(Categorie e) {
        
        try {
            String requete= "INSERT INTO categorie (rate,nom,date_event,description_cat)"
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            
            pst.setInt(1, e.getrate());
            pst.setString(2, e.getNom());
            pst.setString(3,e.getDate_event());
            pst.setString(4,e.getDescription_cat());
            pst.executeUpdate();
            System.out.println("Categorie ajoutée");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
    
    @Override
    public void supprimerCategorie(Categorie e) {
        try {
            String requete = "DELETE FROM categorie where id_cat=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, e.getId_cat());
            pst.executeUpdate();
            System.out.println("Categorie supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifierCategorie(Categorie e) {
        try {
            String requete = "UPDATE categorie SET rate=?,nom=?,date_event=?,description_cat=?  WHERE id_cat=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, e.getrate());
            pst.setString(2, e.getNom());
            pst.setString(3, e.getDate_event());
            pst.setString(4,e.getDescription_cat());
            pst.setInt(5,e.getId_cat());
            
            pst.executeUpdate();
            System.out.println("Categorie modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
 
    @Override
     public List<Categorie> afficherCategories() {        
         List<Categorie> CategoriesList = new ArrayList<>();
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
                CategoriesList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CategoriesList;
     
     
     
     }
    
}
