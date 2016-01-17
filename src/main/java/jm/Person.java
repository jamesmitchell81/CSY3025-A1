package jm;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Person
{
  public String name = "";
  public ArrayList<Person> parents = new ArrayList<Person>();
  public LocalDate birthDate = LocalDate.now();
  public LocalDate deathDate = LocalDate.now();
  public Place birthPlace; // = new Place();
  public int age;

  private LocalDate setDate(String dateString)
  {
    String[] parts = dateString.split("-");
    int year = Integer.parseInt(parts[0]);
    int month = Integer.parseInt(parts[1]);
    int day = Integer.parseInt(parts[2]);
    return LocalDate.of(year, month, day);
  }

  public void setBirthDate(String birthDate)
  {
    this.birthDate = this.setDate(birthDate);
  }

  public void setDeathDate(String deathDate)
  {
    this.deathDate = this.setDate(deathDate);
  }

  public String getBirthDate()
  {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return this.birthDate.format(format);
  }

  public String getDeathDate()
  {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return this.deathDate.format(format);
  }

  public int getAge()
  {
    int years = Period.between(this.birthDate, this.deathDate).getYears();
    return years > 0 ? years : 0;
  }
}