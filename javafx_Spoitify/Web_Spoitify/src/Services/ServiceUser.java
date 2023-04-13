package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entities.Article;
import Entities.User;
import db.DataBaseConnection;
 import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceUser {

	private Connection connection = DataBaseConnection.getInstance().getConnection();;

	public ServiceUser() {
		// TODO Auto-generated constructor stub
	}
	
	public void ajouter(User user) {
        String query = "INSERT INTO user (id, email, role, password, firstname, lastname, phonenumber, image, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getFirstName());
            statement.setString(6, user.getLastName());
            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, user.getImage());
            statement.setString(9, user.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
	
    public void supprimer(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    
    public void modifier(User user, int id) {
        String query = "UPDATE user SET email = ?, role = ?, password = ?, firstname = ?, lastname = ?, phonenumber = ?, image = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getImage());
            statement.setString(8, user.getStatus());
            statement.setInt(9, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    
    public ObservableList<User> afficherTous() {
         
		ObservableList<User> users = FXCollections.observableArrayList();

        try   { String query = "SELECT * FROM user";
        	Statement statement = connection.createStatement();

		 
		ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String phonenumber = resultSet.getString("phonenumber");
                String image = resultSet.getString("image");
                String status = resultSet.getString("status");
                User user = new User(id, email, role, password, firstname, lastname, phonenumber, image, status);
                users.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return users;
    }
    public boolean login(String username, String password) {
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;

        try {
            // Etape 1 : se connecter � la base de donn�es
 
            // Etape 2 : Pr�parer la requ�te SQL
            String sql = "SELECT * FROM user WHERE firstname = ? AND password = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Etape 3 : Ex�cuter la requ�te SQL
            rs = stmt.executeQuery();

            // Etape 4 : V�rifier si l'utilisateur existe et que le mot de passe correspond
            if (rs.next()) {
                isAuthenticated = true;
            }
        } catch (SQLException e) {
            // G�rer les erreurs de connexion � la base de donn�es
            e.printStackTrace();
        }   
      

        return isAuthenticated;
    }
    public User rechercherUserParId(int id) {
        try {
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setPhoneNumber(rs.getString("phonenumber"));
                user.setImage(rs.getString("image"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    
}
