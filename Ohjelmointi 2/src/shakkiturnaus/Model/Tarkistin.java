package shakkiturnaus.Model;

/**
 * @author lehti
 * @version 6.5.2021
 *
 */
public class Tarkistin {
    
    /**
     * tarkistaa laatikoiden tietojen oikeellisuuden
     * @param vah pelaajan vahvuusluku
     * @return null jos ok, teksti jos ei
     * @throws NumberFormatException jos ei ole kyse numeroista
     * @example
     * <pre name="test">
     * #THROWS NumberFormatException
     * tarkistusVah("1400") === null;
     * tarkistusVah("-30")  === "Virhe vahvuusluvussa\noltava väliltä 0-3000";
     * tarkistusVah("4000") === "Virhe vahvuusluvussa\noltava väliltä 0-3000";
     * tarkistusVah("morjesta") === "Virhe vahvuusluvussa\noltava väliltä 0-3000";
     * </pre>
     */
    public static String tarkistusVah(String vah) throws NumberFormatException{
        //tarkistaa onko vahvuusluku välillä 0-3000
        int virhe = 0;
     
        try {
            int vahv = Integer.parseInt(vah);
            if (vahv >= 3000 || vahv < 0) {
                virhe++;
            }
        } catch(NumberFormatException e) {
            virhe++;
        }
        
        if (virhe > 0) {
            return "Virhe vahvuusluvussa\noltava väliltä 0-3000";
        }
        return null;
    }
    
    /**
     * tarkistaa puhelinnumeron oikeellisuuden
     * @param puh tarkistettava puhelinnumero
     * @return null jos ok, teksti jos ei
     * @throws NumberFormatException jos ei ole kyse numeroista
     * @example
     * <pre name="test">
     * #THROWS NumberFormatException
     * tarkistusPuh("0441234567") === null;
     * tarkistusPuh("044123")     === "Virhe puhelinnumerossa\noltava 10 numeroa pitkä\nesim. 0501234567";
     * tarkistusPuh("asd")        === "Virhe puhelinnumerossa\noltava 10 numeroa pitkä\nesim. 0501234567";
     * </pre>
     * 
     */
    public static String tarkistusPuh(String puh) throws NumberFormatException{
        int virhe = 0;
        try {
            if (puh.length() != 10) {
                virhe++;
        }
        
        } catch (NumberFormatException e) {
            virhe++;
        }
        if (virhe > 0) {
            return "Virhe puhelinnumerossa\noltava 10 numeroa pitkä\nesim. 0501234567";
        }
        return null;
    }

}
