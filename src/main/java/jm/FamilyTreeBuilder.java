package jm;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.IOException;
import java.io.File;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class FamilyTreeBuilder
{
  private String urlStart =  "";
  private Set<String> findInAttr;
  private Set<String> findInPerson;
  private Set<String> findInPlace;
  private int i = 0;
  private int k = 0;

  public FamilyTreeBuilder()
  {
    findInAttr = new HashSet<String>();
    findInAttr.add("rel");
    findInAttr.add("property");
    findInAttr.add("href");

    findInPerson = new HashSet<String>();
    findInPerson.add("dbp:name"); // -> Person.name
    findInPerson.add("dbo:parent"); // -> Person.parents[]
    findInPerson.add("dbo:birthDate"); // -> Person.birthDate
    findInPerson.add("dbo:birthPlace"); // -> Person.birthPlace

    findInPlace = new HashSet<String>();
    findInPlace.add("dbp:name");
    findInPlace.add("dbp:longitude");
    findInPlace.add("dbp:latitude");
  }

  public Person find(String url)
  {
    Person person = new Person();
    Document doc = new Document("");

    try {
      doc = Jsoup.connect(url).userAgent("Mozilla").get();
    } catch ( IOException e ) {
      return person;
    }

    Elements elems = doc.select("*");
    ArrayList<String> parentHref = new ArrayList<String>();

    for (Element elem : elems)
    {

      for (Attribute attr : elem.attributes())
      {

          if ( (attr.getKey().equals("property")) && (attr.getValue().equals("dbp:name")) )
          {
            person.name = elem.ownText();
          }

          if ( (attr.getKey().equals("property")) && (attr.getValue().equals("dbo:birthDate")) )
          {
            person.setBirthDate(elem.ownText());
          }

          if ( (attr.getKey().equals("property")) && (attr.getValue().equals("dbo:deathDate")) )
          {
            person.setDeathDate(elem.ownText());
          }

          if ( attr.getKey().equals("rel") && attr.getValue().equals("dbo:parent") )
          {
            i++;

            if ( i > 4 ) {
              i = 0;
              k++;
              break;
            }

            parentHref.add(elem.attributes().get("href"));
          }
      }
    }

    if ( k > 100 ) return person;

    for (int j = 0; j < parentHref.size(); j++ )
    {
      Person parent = this.find(parentHref.get(j));
      person.parents.add(parent);
    }

    return person;
  }
}