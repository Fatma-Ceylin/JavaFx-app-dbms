package org.example.demo1.db;

import org.example.demo1.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityDAO {

    private static final Logger logger = Logger.getLogger(CityDAO.class.getName());


    public City getCity(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        City city = null;

        try {
            conn = Database.getDBConnection();

            String sql = "SELECT * FROM cities WHERE city_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                city = new City(
                        rs.getInt("city_id"),
                        rs.getString("city_name"),
                        rs.getString("plate_code")
                );
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return city;
    }


    public boolean createCity(City city) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = Database.getDBConnection();
            String sql = "INSERT INTO cities (city_name, plate_code) VALUES (?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, city.getCityname());
            ps.setString(2, city.getPlatecode());

            success = (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return success;
    }


    public boolean updateCity(int id, String cityname, String platecode) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            conn = Database.getDBConnection();
            String sql = "UPDATE cities SET city_name = ?, plate_code = ? WHERE city_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, cityname);
            ps.setString(2, platecode);
            ps.setInt(3, id);

            updated = (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return updated;
    }


    public boolean deleteCity(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean deleted = false;

        try {
            conn = Database.getDBConnection();
            String sql = "DELETE FROM cities WHERE city_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            deleted = (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return deleted;
    }


    public static ObservableList<City> getAllCities() {
        ObservableList<City> cities = FXCollections.observableArrayList();

        try (Connection conn = Database.getDBConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM cities");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("city_id"),
                        rs.getString("city_name"),
                        rs.getString("plate_code")
                ));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return cities;
    }
}
