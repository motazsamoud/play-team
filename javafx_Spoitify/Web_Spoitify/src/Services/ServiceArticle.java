package Services;
 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 

import Entities.Article;
import db.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class ServiceArticle {
	
	private Connection connection = DataBaseConnection.getInstance().getConnection();;
	
	 
	public ServiceArticle(){
	}
	
	public void ajouter(Article article) {
		try {
			String query = "INSERT INTO article (user_id, title, content,likes) VALUES (?, ?, ?, ?);";
				 
			PreparedStatement preparedStatement = connection.prepareStatement(query);
		 
			preparedStatement.setInt(1, article.getUserId());
			preparedStatement.setString(2, article.getTitle());
			preparedStatement.setString(3, article.getContent());
			preparedStatement.setString(4, article.getLike());
			preparedStatement.executeUpdate();
			System.out.println("L'article a été ajouté avec succès !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de l'ajout de l'article.");
		}
	}
	
	public void supprimer(int id) {
		try {
			String query = "DELETE FROM article WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println("L'article a été supprimé avec succès !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la suppression de l'article.");
		}
	}
	
	public void modifier(Article article, int id) {
		try {
			String query = "UPDATE article SET user_id = ?, title = ?, content = ?, likes = ? WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, article.getUserId());
			preparedStatement.setString(2, article.getTitle());
			preparedStatement.setString(3, article.getContent());
			preparedStatement.setString(4, article.getLike());
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			System.out.println("L'article a été modifié avec succès !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la modification de l'article.");
		}
	}
	
	public ObservableList<Article> afficherTous() {
		ObservableList<Article> articles = FXCollections.observableArrayList();
	    try {
 
	        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM article");
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            int userId = resultSet.getInt("user_id");
	            String title = resultSet.getString("title");
	            String content = resultSet.getString("content");
	            String like = resultSet.getString("likes");
	            Article article = new Article(id, userId, title, content, like);
	            articles.add(article);
	            System.out.println(article.toString());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return articles;
	}

	public Article rechercherUserParId(int id) throws SQLException {
 	    Article article = null;
	    String query = "SELECT * FROM article WHERE id = ?";

	    try {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id);
	        ResultSet rs = statement.executeQuery();

	        if (rs.next()) {
	            int userId = rs.getInt("user_id");
	            String title = rs.getString("title");
	            String content = rs.getString("content");
	            String like = rs.getString("likes");
	            article = new Article(id, userId, title, content, like);
	        }

	        rs.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }  

	    return article;
	}


}
