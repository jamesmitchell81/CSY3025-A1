package jm;


import org.json.*;
import java.io.PrintWriter;

public class BirthPlacePrinter
{

  public void printJSON(Person person, String to)
  {
    JSONArray json = new JSONArray();

    json.put(this.get(person));

    try {
      PrintWriter printer = new PrintWriter(to);
      printer.println(json.toString(2));
      printer.close();
    } catch ( Exception e ) {
      System.out.println(e.getMessage());
    }


  }

  private JSONArray get(Person p)
  {
    JSONArray people = new JSONArray();

    this.addPerson(p, people);

    return people;
  }

  private void addPerson(Person p, JSONArray to)
  {
    JSONObject person = new JSONObject();

    if ( !(p.birthPlace == null) ) {

      person.put("name", p.name);
      person.put("birthDate", p.getBirthDate());
      person.put("long", p.birthPlace.longitude);
      person.put("lat", p.birthPlace.latitude);
      person.put("birthPlace", p.birthPlace.name);
      person.put("age", p.getAge());

      to.put(person);
    }


    if ( !(p.parents.size() == 0) ) {
      for ( int i = 0; i < p.parents.size(); i++ )
      {
        Person parent = p.parents.get(i);
        this.addPerson(parent, to);
      }
    }

  }


}

