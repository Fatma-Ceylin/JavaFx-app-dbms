package org.example.demo1.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    private int id;
    private String cityname;
    private String platecode;

    private final IntegerProperty IDProperty;
    private final StringProperty citynameProperty;
    private final StringProperty platecodeProperty;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getCityname() {return cityname;}
    public void setCityname(String cityname) {this.cityname = cityname;}
    public String getPlatecode() {return platecode;}
    public void setPlatecode(String platecode) {this.platecode = platecode;}

    public City()
    {
        IDProperty=null;
        citynameProperty=null;
        platecodeProperty=null;
    }

    public City(int id, String cityname, String platecode)
    {
        this.id=id;
        this.cityname=cityname;
        this.platecode=platecode;

        this.IDProperty=new SimpleIntegerProperty(id);
        this.citynameProperty=new SimpleStringProperty(cityname);
        this.platecodeProperty=new SimpleStringProperty(platecode);
    }

    public IntegerProperty getIDProperty() {return IDProperty;}
    public StringProperty getCitynameProperty() {return citynameProperty;}
    public StringProperty getPlatecodeProperty() {return platecodeProperty;}

}
