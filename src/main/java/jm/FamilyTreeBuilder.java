package jm;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.IOException;
import java.io.File;

import java.util.ArrayList;

public class FamilyTreeBuilder
{
  private int i = 0;
  private int k = 0;
  private ArrayList<String> findForPlaceName = new ArrayList<String>();
  private ArrayList<String> findForLat = new ArrayList<String>();
  private ArrayList<String> findForLong = new ArrayList<String>();

  public FamilyTreeBuilder()
  {
    findForPlaceName.add("dbp:name");
    findForPlaceName.add("dbp:officialName");

    findForLat.add("dbp:latitude");
    findForLat.add("dbp:latd");
    findForLat.add("geo:lat");
    findForLat.add("dbp:latDegrees");

    findForLong.add("dbp:longitude");
    findForLong.add("dbp:longd");
    findForLong.add("geo:long");
    findForLong.add("dbp:longDegrees");
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
    ArrayList<String> placeHref = new ArrayList<String>();

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

          if ( (attr.getKey().equals("rel")) && (attr.getValue().equals("dbo:birthPlace")) )
          {
            placeHref.add(elem.attributes().get("href"));
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

    if ( placeHref.size() > 0 )
    {
      Place birthPlace = new Place();

      for ( int j = 0; j < placeHref.size(); j++ )
      {
        // happy if we have the long, lat.
        if ( (birthPlace.longitude == 0.0) && (birthPlace.latitude == 0.0) )
        {
          person.birthPlace = this.findPlace(placeHref.get(j));
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

  /*
   *
   *
   */
  private Place findPlace(String href)
  {
    Place place = new Place();

    Document doc = new Document("");

    try {
      doc = Jsoup.connect(href).userAgent("Mozilla").get();
    } catch ( IOException e ) {
      return place;
    }

    Elements elems = doc.select("*");

    for (Element elem : elems)
    {
      for (Attribute attr : elem.attributes())
      {

          if ( (attr.getKey().equals("property")) && (findForPlaceName.contains(attr.getValue())) )
          {
            place.name = elem.ownText();
          }

          if ( (attr.getKey().equals("property")) && (findForLong.contains(attr.getValue())) )
          {
            place.setLongitude(elem.ownText());
          }

          if ( (attr.getKey().equals("property")) && (findForLat.contains(attr.getValue())) )
          {
            place.setLatitude(elem.ownText());
          }
      }
    }

    return place;
  }

}