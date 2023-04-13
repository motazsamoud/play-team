/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author TECHN
 */
public class Activite {
             private int id_activite;
             private int id_cat;
             private String nom_ac;
             private String description;

    public Activite(int id_activite, int id_cat, String nom_ac, String description) {
        this.id_activite = id_activite;
        this.id_cat = id_cat;
        this.nom_ac = nom_ac;
        this.description=description;
    }

    public Activite() {
    }

    public int getId_activite() {
        return id_activite;
    }

    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNom_ac() {
        return nom_ac;
    }
    public String getDescription() {
        return description;
    }

    public void setNom_ac(String nom_ac) {
        this.nom_ac = nom_ac;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activite{" + "id_activite=" + id_activite + ", id_cat=" + id_cat + ", nom_ac=" + nom_ac + ", description=" + description + '}';
    }
             
    
             
    
            
}
