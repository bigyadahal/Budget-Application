/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sophmore.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class SavingController implements Initializable {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField textField;
    @FXML
    private Button submit;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onStartDate(ActionEvent event) {
    }

    @FXML
    private void onEndDate(ActionEvent event) {
    }

    @FXML
    private void onTextField(ActionEvent event) {
    }

    @FXML
    private void onSubmit(ActionEvent event) {
        try {
            LocalDate start = startDate.getValue();
            LocalDate end = endDate.getValue();
            BufferedWriter writer = new BufferedWriter(new FileWriter("text.txt", true));
            writer.newLine();

            if (start != null && end != null) {
                writer.write("_ The saving period will start on " + start + " and end on " + end);
                writer.write("\n_ Average saving per month is $ " + textField.getText());

            } else if (start != null && end == null) {
                writer.write("_ The saving period will start on " + start);
                writer.write("\n_ Average saving per month is $ " + textField.getText());

            } else if (start == null && end != null) {
                writer.write("_ The saving period will end on " + end);
                writer.write("\n_ Average saving per month is $ " + textField.getText());

            } else {
                writer.write("_ Average saving per month is $ " + textField.getText());
            }

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
