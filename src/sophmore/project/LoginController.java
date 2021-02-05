/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sophmore.project;

/**
 *
 * @author bigya
 */
import java.io.FileInputStream;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
    
    public static int totalAttempts = 3;
    
    @FXML
    private TextField emailIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Button NewSignUpButton;
    
    @FXML
    private Button gobackButton;

    @FXML
    public void newsignup(ActionEvent event) throws SQLException {
        // get the new user singn up form
        try {

            Stage stage = (Stage) NewSignUpButton.getScene().getWindow();
            FXMLLoader main = new FXMLLoader(getClass().getResource("signup_user.fxml"));
            Parent root = main.load();
            Scene scene = new Scene(root, 700, 700);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void login(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        //System.out.println(emailIdField.getText());
        //System.out.println(passwordField.getText());
        if (emailIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = emailIdField.getText();
        String password = passwordField.getText();
        file_login(emailId, password);
        /*
        JdbcConn jdbcConn = new JdbcConn();
        boolean flag = jdbcConn.validate_login(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            //infoBox("Login Successful!", null, "Failed");
            // now call the main window
            try {
            
            Stage stage = (Stage) submitButton.getScene().getWindow();
            FXMLLoader main = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = main.load();

            FXMLDocumentController controller = main.getController();
            controller.appendText();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
         */
    }

    // file based login
    private void file_login(String user_email_address, String user_password) {
        System.out.println("Checking the user existance...");
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream("login.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream)); // java class to read the text from an input stream (like files)

            String strLine1, strLine2;
            String file_email = "";
            String file_pass = "";
            boolean login_status = false;

            //Read File Line By Line - 2 line a time for username and password
            while (((strLine1 = br.readLine()) != null) && (strLine2 = br.readLine()) != null) {
                // capture the email and pass
                file_email = strLine1;
                file_pass = strLine2;
                // remove all spaces if any
                file_email = file_email.replaceAll("\\s+", "");
                file_pass = file_pass.replaceAll("\\s+", "");

                // check the username and passsord and match with the saved record from file
                System.out.println(file_email);
                System.out.println(file_pass);
                int check_email = user_email_address.compareTo(file_email); // used string compareTo function to compare the user inputs and file stored user name and passport
                int check_pass = user_password.compareTo(file_pass);
                //System.out.println(check_email);
                //System.out.println(check_pass);
                if (totalAttempts != 0) {
                    if (check_email == 0 && check_pass == 0) {
                        // login successful
                        System.out.println("Login Successful");
                        login_status = true;
                        try {
                            Stage stage = (Stage) submitButton.getScene().getWindow();
                            FXMLLoader main = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = main.load();

                            FXMLDocumentController controller = main.getController();
                            controller.openScreen();

                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                }
            }
            
            if (login_status == false) {
                
                // login unsuccessful
                if(totalAttempts == 0)
                {
                    infoBox("Only Maximum 3 Attempts allowed!", null, "Failed");
                    System.exit(0);
                }
                System.out.println("Login Failed!");
                infoBox("Please enter correct Email and Password", null, "Failed");
                totalAttempts--;
                //System.out.println("-----------" + totalAttempts);
            }
            //Close the input stream
            fstream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
