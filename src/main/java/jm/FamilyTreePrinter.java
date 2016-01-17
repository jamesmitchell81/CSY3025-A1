package jm;

import org.json.*;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.PrintWriter;

public class FamilyTreePrinter
{
  private int k = 0;
  private JSONObject json = new JSONObject();

  public void printJSON(Person person, String to)
  {
    json = this.addPerson(person);

    try {
      PrintWriter printer = new PrintWriter(to);
      printer.println(json.toString(2));
      printer.close();
    } catch ( Exception e ) {
      System.out.println(e.getMessage());
    }
  }

  public JSONObject addPerson(Person p)
  {
    JSONObject person = new JSONObject();
    JSONArray parents = new JSONArray();

    if ( p.name.equals("") ) return person;

    person.put("name", p.name);
    person.put("birthDate", p.getBirthDate());
    person.put("deathDate", p.getDeathDate());
    person.put("age", p.getAge());

    if ( !(p.birthPlace == null) )
    {
      person.put("birthPlace", this.addPlace(p.birthPlace));
    }

    if ( p.parents.size() == 0 ) return person;

    for ( int i = 0; i < p.parents.size(); i++ )
    {
      Person parent = p.parents.get(i);
      parents.put(this.addPerson(parent));
    }

    person.put("children", parents); // yes i know!

    return person;
  }

  public JSONObject addPlace(Place p)
  {
    JSONObject place = new JSONObject();

    place.put("placeName", p.name);
    place.put("longitude", p.longitude);
    place.put("latitude", p.latitude);

    return place;
  }




}