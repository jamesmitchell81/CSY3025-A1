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
  private Document doc = new Document("");
  private int k = 0;
  private JSONObject json = new JSONObject();

  public void print(Person person, String to)
  {

      Element html = doc.createElement("html");
      Element head = doc.createElement("head");
      Element title = doc.createElement("title");
      Element body = doc.createElement("body");
      Element svg = doc.createElement("svg");

      Element path = doc.createElement("path");

      svg.appendChild(path);

      doc.appendChild(html);
      html.appendChild(head);
      head.appendChild(title);
      html.appendChild(body);

      body.appendChild(svg);

      body.appendChild(this.add(person));
      // Element span = doc.createElement("span");
      // span.appendText("James");
      // body.appendChild(span);
    try {

      PrintWriter printer = new PrintWriter(to);
      printer.println(doc.html());
      printer.close();

    } catch ( Exception e ) {
      System.out.println(e.getMessage());
    }

  }

  private Element add(Person person)
  {
    Element ul = doc.createElement("ul"); // base ul
    Element name = doc.createElement("li");

    Element span = doc.createElement("span");
    span.appendText(person.name);
    name.appendChild(span);
    ul.appendChild(name);

    // k++;

    // if ( k > 6 ) return ul;
    if ( person.parents.size() == 0 ) return ul;

    for ( int i = 0; i < person.parents.size(); i++)
    {
      Person parent = person.parents.get(i);
      Element parentListItem = doc.createElement("li");
      parentListItem.appendChild(this.add(parent));
      ul.appendChild(parentListItem);
    }

    return ul;
  }

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

    if ( p.parents.size() == 0 ) return person;

    for ( int i = 0; i < p.parents.size(); i++ )
    {
      Person parent = p.parents.get(i);
      parents.put(this.addPerson(parent));
    }

    person.put("children", parents); // yes i know!

    return person;
  }




}