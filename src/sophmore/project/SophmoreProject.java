/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sophmore.project;

import java.io.File;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author yduong
 */
public class SophmoreProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        File text = new File ("text.txt");
        File save = new File ("Save for Later");
        
        if (text.length() > 0){
            PrintWriter writer = new PrintWriter (text);
            writer.flush();
            writer.close();
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("login_form.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
