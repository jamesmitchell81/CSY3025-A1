package jm;

public class Place
{
  public String name;
  public double longitude = 0.0;
  public double latitude = 0.0;

  public void setLongitude(String longitudeString)
  {
    this.longitude = Double.parseDouble(longitudeString);
  }

  public void setLatitude(String latitudeString)
  {
    this.latitude = Double.parseDouble(latitudeString);
  }
}