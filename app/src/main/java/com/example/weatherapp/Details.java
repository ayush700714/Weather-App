package com.example.weatherapp;

public class Details {
    private String description;
    private double temp,humidity,wind_speed,wind_degree;
    public void setDescription(String description)
    {
        this.description=description;
    }
    public void setTemp(double temp)
    {
        this.temp=temp;
    }
    public void setHumidity(double humidity)
    {
        this.humidity=humidity;
    }
    public void setWind_speed(double wind_speed)
    {
        this.wind_speed=wind_speed;
    }
    public void setWind_degree(double wind_degree)
    {
        this.wind_degree=wind_degree;
    }
    public String getDescription()
    {
        return description;
    }
    public double getTemp()
    {
        return temp;
    }
    public double getHumidity()
    {
        return humidity;
    }
    public double getWind_speed()
    {
        return wind_speed;
    }
    public double getWind_degree()
    {
        return wind_degree;
    }
}
