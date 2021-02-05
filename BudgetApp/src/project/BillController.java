/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class BillController implements Initializable {

    private int sum = 0;

    @FXML
    private TextField textField;
    @FXML
    private Button submit;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private TextArea textArea;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setType(type);
        //image.setCache(true);
    }

    @FXML
    private void onSubmit(ActionEvent event) {
        try {
            //sum += Integer.parseInt(textField.getText());
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("text.txt", true));
            writer.newLine();
            writer.write("_ Total amount of living expenses is $ " + sum);
            writer.close();

            Stage stage = (Stage) submit.getScene().getWindow();
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

    @FXML
    private void onAdd(ActionEvent event) {
        getType(type, textField);
        type.getSelectionModel().clearSelection();
        textField.clear();
    }
    
    private void setType(ChoiceBox<String> choice) {
        choice.getItems().addAll("Mortgage", "Electric", "Water", "Phone", "Internet", "Others");
    }

    private void getType(ChoiceBox<String> choice, TextField field) {
        String text = choice.getValue().toString();
        textArea.appendText("_ " + text + " bill with average amount of $" + field.getText() + "\n");
        calculate(field);
    }

    private void calculate(TextField textfield) {
        sum += Integer.parseInt(textField.getText());
    }

    @FXML
    private void onClear(ActionEvent event) throws FileNotFoundException {
        textArea.clear();
    }

    @FXML
    private void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
