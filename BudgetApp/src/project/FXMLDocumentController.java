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
import java.io.FileOutputStream;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author yduong
 */
public class FXMLDocumentController implements Initializable {
    
    private int ascii = 0x2713;
    private String check = Character.toString((char)ascii);

    @FXML
    private TextArea textArea;
    @FXML
    private ContextMenu context;
    @FXML
    private MenuItem copy;
    @FXML
    private Button income;
    @FXML
    private Button bill;
    @FXML
    private Button saving;
    @FXML
    private Button download;
    @FXML
    private Button calculate;
    @FXML
    private Button logOut;
    @FXML
    private Button exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onCopy(ActionEvent event) {
        textArea.copy();
    }

    @FXML
    private void onContext(ActionEvent event) {
    }

    @FXML
    private void onTextArea(MouseEvent event) {

    }

    @FXML
    private void onIncome(ActionEvent event) {
        try {
            Stage stage = (Stage) income.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Income.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onBill(ActionEvent event) {
        try {
            Stage stage = (Stage) bill.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Bill.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSaving(ActionEvent event) {
        try {
            Stage stage = (Stage) saving.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Saving.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void appendText() throws FileNotFoundException {
        BufferedReader file = new BufferedReader(new FileReader("text.txt"));
        Scanner scan = new Scanner(file);
        textArea.setWrapText(true);
        while (scan.hasNext()) {
            textArea.appendText(scan.nextLine() + "\n");
        }
        scan.close();
    }

    @FXML
    private void onDownload(ActionEvent event) throws IOException {
        Stage primary = new Stage();
        saveWindow(primary);
    }

    private void saveWindow(Stage primary) throws IOException {
        String content = textArea.getText();

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (.txt)", "*.txt"));
        File file = chooser.showSaveDialog(null);

        if (file != null) {
            saveFile(content, file);
        }
    }

    @FXML
    private void onCalculate(ActionEvent event) throws FileNotFoundException, IOException {
        File file = new File("text.txt");
        Scanner scan = new Scanner(file);
        int income = 0;
        int bill = 0;
        int saving = 0;
        int num = 0;
        int left = 0;

        while (scan.hasNext()) {
            String text = scan.nextLine();
            int length = text.length();
            String result = "";
            boolean inSeq = text.contains(" month: $ ");
            boolean outSeq = text.contains(" expenses is $ ");
            boolean savSeq = text.contains(" saving per month ");

            if (inSeq == true) {
                for (int i = 0; i < length; i++) {
                    Character character = text.charAt(i);
                    if (Character.isDigit(character)) {
                        result += character;
                        income = Integer.parseInt(result);
                    }
                }
                left += income;
            }

            if (outSeq == true) {
                for (int i = 0; i < length; i++) {
                    Character character = text.charAt(i);
                    if (Character.isDigit(character)) {
                        result += character;
                        bill = Integer.parseInt(result);
                    }
                }
                left -= bill;
            }

            if (savSeq == true) {
                for (int i = 0; i < length; i++) {
                    Character character = text.charAt(i);
                    if (Character.isDigit(character)) {
                        result += character;
                        saving = Integer.parseInt(result);
                    }
                }

                if (saving > left) {
                    textArea.appendText("!! The saving goal cannot be satisfy.\n");
                } else {
                    textArea.appendText(check + " The saving goal can be satisfy.\n");
                }
            }
        }
        textArea.appendText("_ Leftover per month: $ " + left + ".\n ");
        scan.close();
        saveForLater(textArea.getText());
    }

    private void saveFile(String content, File file) throws IOException {
        FileWriter fileWriter;

        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }

    private void saveForLater(String content) throws IOException {
        File file = new File("Save for Later.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        if (file.length() > 0) {
            PrintWriter delete = new PrintWriter(file);
            delete.flush();
            delete.close();
        }

        writer.write(content);
        writer.close();
    }

    public void openScreen() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Save for Later.txt"));
        Scanner scan = new Scanner(reader);
        textArea.setWrapText(true);
        while (scan.hasNext()) {
            textArea.appendText(scan.nextLine() + "\n");
        }
        scan.close();

    }

    @FXML
    private void onLogOut(ActionEvent event) {
        try {
            Stage stage = (Stage) saving.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("login_form.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }
    
}
