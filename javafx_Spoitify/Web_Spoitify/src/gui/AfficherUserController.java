package gui;
 
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Article;
import Services.ServiceArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
 

public class AfficherUserController implements Initializable {
 
    @FXML private ListView<Article> listView;
    
 	    public void initialize(URL location, ResourceBundle resources) {
 	     ServiceArticle es = new ServiceArticle();
 	        List<Article> Article = es.afficherTous();
 	   

 	       listView.setCellFactory((Callback<ListView<Article>, ListCell<Article>>) new Callback<ListView<Article>, ListCell<Article>>() {
 	    	    public ListCell<Article> call(ListView<Article> param) {
 	    	        return new ListCell<Article>() {
 	    	            @Override
 	    	            protected void updateItem(Article item, boolean empty) {
 	    	                super.updateItem(item, empty);

 	    	                if (empty || item == null) {
 	    	                    setText(null);
 	    	                    setGraphic(null);
 	    	                } else {
 	    	                    HBox hbox = new HBox();
 	    	                    hbox.setAlignment(Pos.CENTER_LEFT);
 	    	                    hbox.setSpacing(10);

 	    	                    Label nomLabel = new Label(item.getTitle());
 	    	                    nomLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

 	    	                    Label typeLabel = new Label(item.getContent() + " - Likes: " + item.getLike());
 	    	                    typeLabel.setStyle("-fx-font-size: 14;");

 	    	                   Button likeButton = new Button("Like");
 	    	                  likeButton.setOnAction(event -> {
 	    	                      // update like count for the article
 	    	                      item.incrementLikeCount();

 	    	                      // update UI to reflect the change
 	    	                      typeLabel.setText(item.getContent() + " - Likes: " + item.getLikeCount());
 	    	                     item.setLike(String.valueOf(item.getLikeCount()));
 	    	                  });

 	    	                 Button commentButton = new Button("Commentaire");
 	    	                commentButton.setOnAction(event -> {
 	    	                    // create a new stage for the comment dialog
 	    	                    Stage commentStage = new Stage();
 	    	                    commentStage.initModality(Modality.APPLICATION_MODAL);
 	    	                    commentStage.setTitle("Ajouter un commentaire");

 	    	                    // create a text area for entering the comment
 	    	                    TextArea commentTextArea = new TextArea();
 	    	                    commentTextArea.setWrapText(true);

 	    	                    // create a button to submit the comment
 	    	                    Button submitButton = new Button("Envoyer");
 	    	                    submitButton.setOnAction(submitEvent -> {
 	    	                        // add the comment to the article
 	    	                        String comment = commentTextArea.getText();
 	    	                        item.setContent(comment);

 	    	                        // update the UI to reflect the change
 	    	                        typeLabel.setText(item.getContent() + " - Likes: " + item.getLikeCount() + " - Commentaires: " + item.getContent());
   	    	                      ServiceArticle serviceArticle = new ServiceArticle();
   	    	                   serviceArticle.modifier(item, item.getId());
 	    	                        // close the comment dialog
 	    	                        commentStage.close();
 	    	                    });

 	    	                    // add the text area and submit button to a VBox
 	    	                    VBox commentBox = new VBox();
 	    	                    commentBox.getChildren().addAll(commentTextArea, submitButton);

 	    	                    // add the VBox to the comment stage
 	    	                    commentStage.setScene(new Scene(commentBox));
 	    	                    commentStage.show();
 	    	                });

 	    	                hbox.getChildren().addAll(nomLabel, typeLabel, likeButton, commentButton);

 	    	                    setGraphic(hbox);
 	    	                }
 	    	                
 	    	            }
 	    	            
 	    	        };
 	    	        
 	    	    }
 	    	});
 	       

 	    	// Ajouter les événements à la liste
 	    	listView.getItems().addAll(Article);
}
}

 	   
 
