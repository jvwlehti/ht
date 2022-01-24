package shakkiturnaus.Model.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import static shakkiturnaus.Model.Tarkistin.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.14 10:59:29 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TarkistinTest {



  // Generated by ComTest BEGIN
  /** 
   * testTarkistusVah16 
   * @throws NumberFormatException when error
   */
  @Test
  public void testTarkistusVah16() throws NumberFormatException {    // Tarkistin: 16
    assertEquals("From: Tarkistin line: 18", null, tarkistusVah("1400")); 
    assertEquals("From: Tarkistin line: 19", "Virhe vahvuusluvussa\noltava väliltä 0-3000", tarkistusVah("-30")); 
    assertEquals("From: Tarkistin line: 20", "Virhe vahvuusluvussa\noltava väliltä 0-3000", tarkistusVah("4000")); 
    assertEquals("From: Tarkistin line: 21", "Virhe vahvuusluvussa\noltava väliltä 0-3000", tarkistusVah("morjesta")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testTarkistusPuh49 
   * @throws NumberFormatException when error
   */
  @Test
  public void testTarkistusPuh49() throws NumberFormatException {    // Tarkistin: 49
    assertEquals("From: Tarkistin line: 51", null, tarkistusPuh("0441234567")); 
    assertEquals("From: Tarkistin line: 52", "Virhe puhelinnumerossa\noltava 10 numeroa pitkä\nesim. 0501234567", tarkistusPuh("044123")); 
    assertEquals("From: Tarkistin line: 53", "Virhe puhelinnumerossa\noltava 10 numeroa pitkä\nesim. 0501234567", tarkistusPuh("asd")); 
  } // Generated by ComTest END
}