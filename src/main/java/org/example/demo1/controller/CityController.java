package org.example.demo1.controller;

import org.example.demo1.model.City;
import org.example.demo1.db.CityDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

public class CityController {
    private static final Logger logger = Logger.getLogger(CityController.class.getName());



    @FXML
    private TextField city_id;

    @FXML
    private TextField city_name;

    @FXML
    private TextField plate_code;

    @FXML
    private Button close_btn;

    @FXML
    private TableColumn<City, Integer> city_table_id;

    @FXML
    private TableColumn<City, String> city_table_name;

    @FXML
    private TableColumn<City, String> city_table_platecode;

    @FXML
    private TableView<City> city_table;



    @FXML
    public void initialize(){

        city_table_id.setCellValueFactory(studentModelIntegerCellDataFeatures -> studentModelIntegerCellDataFeatures.getValue().getIDProperty().asObject());
        city_table_name.setCellValueFactory(cellData -> cellData.getValue().getCitynameProperty());
        city_table_platecode.setCellValueFactory(cellData -> cellData.getValue().getPlatecodeProperty());

        ObservableList<City> cities = FXCollections.observableArrayList();
        cities = CityDAO.getAllCities();
        city_table.setItems(cities);
    }

    @FXML
    public void cleanCity() {
        clearFields();
    }


    public void clearFields() {
        city_id.clear();
        city_name.clear();
        plate_code.clear();
    }



    @FXML
    public void getCity(ActionEvent actionEvent) {
        if(city_id.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter city id!");
            alert.show();

        }
        else {
            int id = Integer.parseInt(city_id.getText());
            CityDAO cityDAO = new CityDAO();
            try{
                City city = cityDAO.getCity(id);
                if(city == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("City not found!");
                    alert.show();
                }
                else {
                    city_name.setText(city.getCityname());
                    plate_code.setText(city.getPlatecode());


                }


            }
            catch (SQLException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    public void createCity(ActionEvent actionEvent) {
        if(city_name.getText().isEmpty() || plate_code.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both city name and plate code!");
            alert.show();
        }
        else {
            City newcity = new City();
            newcity.setCityname(city_name.getText());
            newcity.setPlatecode(plate_code.getText());

            CityDAO cityDAO = new CityDAO();

            try{
                boolean isCreated = cityDAO.createCity(newcity);
                if(isCreated) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("City is created!");
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("City is not created!");
                    alert.show();
                }

            }
            catch (SQLException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
            cityFillTable();
        }
    }


    public void updateCity(){
        if (city_id.getText().isEmpty() || city_name.getText().isEmpty() || plate_code.getText().isEmpty()) {
            showAlert("Warning", "⚠️ Please fill in all fields!");
            return;
        }

        int id = Integer.parseInt(city_id.getText());
        String cityname = city_name.getText();
        String platecode = plate_code.getText();

        try {
            CityDAO cityDAO = new CityDAO();
            boolean isUpdated = cityDAO.updateCity(id, cityname, platecode);
            if (isUpdated) {
                showAlert("Success", "City updated successfully\n!");
            } else {
                showAlert("Error", "Update failed! The specified ID was not found\n.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "❌ update error\n: " + e.getMessage());
        }
        cityFillTable();
    }

    public void deleteCity(){
        if (city_id.getText().isEmpty()) {
            showAlert("Error", "Please enter an ID before deleting.");
            return;
        }

        int id = Integer.parseInt(city_id.getText());
        CityDAO cityDAO = new CityDAO();

        try {
            boolean deleted = cityDAO.deleteCity(id);
            if (deleted) {
                showAlert("Success", "City deleted successfully!");
                clearFields();
            } else {
                showAlert("Error", "No city found with this ID.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Delete Error: " + e.getMessage());
            showAlert("Error", "An error occurred while deleting: " + e.getMessage());
        }
        cityFillTable();

    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void close(){
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
        Platform.exit();
    }


    public void cityFillTable(){
        ObservableList<City> cities = FXCollections.observableArrayList();
        cities = CityDAO.getAllCities();
        city_table.setItems(cities);
    }

}





