package shakkiturnaus.Model.test;
// Generated by ComTest BEGIN
import javafx.util.Pair;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.*;
import shakkiturnaus.Model.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.14 12:11:35 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TurnausTest {



  // Generated by ComTest BEGIN
  /** 
   * testString181 
   * @throws PoikkeusException when error
   */
  @Test
  public void testString181() throws PoikkeusException {    // Turnaus: 181
    Turnaus testi = new Turnaus(); 
    Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
    "Jyshak", "041-1212121", "1500", 0); 
    Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
    "Tushak", "044-1231213", "1900", 0); 
    testi.lisaaOsallistuja(masa); 
    testi.lisaaOsallistuja(samu); 
    testi.lisaaTulos(new Tulos(1, 2)); 
    List<Pair<Tulos, String>> tuloste = testi.tulokset(); 
    assertEquals("From: Turnaus line: 196", "Masa Mainio 1-0 Samu Sirkka", tuloste.get(0).getValue()); 
  } // Generated by ComTest END
}