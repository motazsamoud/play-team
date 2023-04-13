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
public class Categorie {
   private int id_cat,rate;
   private String nom,
    date_event,
    description_cat;

    public Categorie(int id_cat, int rate, String nom, String date_event, String description_event) {
        this.id_cat = id_cat;
        this.rate = rate;
        this.nom = nom;
        this.date_event = date_event;
        this.description_cat = description_event;
    }

    public Categorie() {
        
    }

   
    

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getrate() {
        return rate;
    }

    public void setrate(int rate) {
        this.rate = rate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public String getDescription_cat() {
        return description_cat;
    }

    public void setDescription_cat(String description_cat) {
        this.description_cat = description_cat;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id_cat=" + id_cat + ", rate=" + rate + ", nom=" + nom + ", date_event=" + date_event + ", description_cat=" + description_cat + '}';
    }
    




}
