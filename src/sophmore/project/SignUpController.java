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
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField genderField;

    @FXML
    private Button signupButton;
    
    @FXML
    private Button gobackButton;

    @FXML
    public void signup(ActionEvent event) throws SQLException {

        Window owner = signupButton.getScene().getWindow();
        // get the info from the form
        //System.out.println(firstNameField.getText());
        //System.out.println(lastNameField.getText());
        //System.out.println(emailAddressField.getText());
        //System.out.println(addressField.getText());
        //System.out.println(phoneField.getText());
        // System.out.println(passwordField.getText());
        // System.out.println(genderField.getText());

        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your First Name");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Last Name");
            return;
        }
        if (emailAddressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email address");
            return;
        }
        if (addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Address");
            return;
        }
        if (phoneField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Phone");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }
        if (genderField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Gender");
            return;
        }
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String emailId = emailAddressField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String gender = genderField.getText();

        /*
        JdbcConn jdbcConn = new JdbcConn();
        // check if the user already exists by his email address
        // boolean flag = jdbcConn.validate_signup(emailId);

        if (!flag) {
            infoBox("User already exists!", null, "Failed");
        } else {
            /*
            // insert the new user into user_information table
            flag = jdbcConn.insert_user(firstName, lastName, emailId, password, address, phone, gender);
            if (flag) {
                infoBox("User SignUp Successfull!", null, "Passed");
            }
        }
         */
        // file based user registration
       // file_signup(firstName, lastName, emailId, address, phone, gender, password);
        file_signup(firstName, lastName, emailId, password, address, phone, gender);
    }
    
    @FXML
    public void existingUser(ActionEvent event) throws SQLException {
        // get the new user singn up form
        try {

            Stage stage = (Stage) gobackButton.getScene().getWindow();
            FXMLLoader main = new FXMLLoader(getClass().getResource("login_form.fxml"));
            Parent root = main.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // file based login
    private void file_signup(String firstName, String lastName, String emailId, String password, String address, String phone, String gender) {
        try {
            File users_file = new File("users.txt");
            File login_file = new File("login.txt");

            FileWriter users_fw = new FileWriter(users_file, true);
            BufferedWriter users_bw = new BufferedWriter(users_fw);
            PrintWriter users_pw = new PrintWriter(users_bw);
            users_pw.println(firstName);
            users_pw.println(lastName);
            users_pw.println(emailId);
            users_pw.println(password);
            users_pw.println(address);
            users_pw.println(phone);
            users_pw.println(gender);
            users_pw.close();
            infoBox("Create Account Successfull!", null, "Passed");
            // save username and passoword to login file
            FileWriter login_fw = new FileWriter(login_file, true);
            BufferedWriter login_bw = new BufferedWriter(login_fw);
            PrintWriter login_pw = new PrintWriter(login_bw);
            login_pw.println(emailId);
            login_pw.println(password);
            login_pw.close();

            Stage stage = (Stage) signupButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("login_form.fxml"));
        
            Scene scene = new Scene(root);
        
             stage.setScene(scene);
             stage.show();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
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
