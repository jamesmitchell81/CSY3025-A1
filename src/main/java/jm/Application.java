package jm;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Application
{

  // throws IOException
  public static void main(String... args)
  {
    FamilyTreeBuilder royalFamilyTree = new FamilyTreeBuilder();
    Person person = royalFamilyTree.find("http://dbpedia.org/page/Elizabeth_II");
    // Person person = royalFamilyTree.find("http://dbpedia.org/page/Edward_IV_of_England");

    FamilyTreePrinter ftp = new FamilyTreePrinter();
    ftp.printJSON(person, "/Users/jm/Sites/AI/CSY3025-A1/familytree.json");

    BirthPlacePrinter bpp = new BirthPlacePrinter();
    bpp.printJSON(person, "/Users/jm/Sites/AI/CSY3025-A1/birthplaces.json");

  }

}