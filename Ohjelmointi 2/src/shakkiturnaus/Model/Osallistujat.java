package shakkiturnaus.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author jvwlehti & leodanbu
 * @version 1.3.2021
 *
 */
public class Osallistujat implements Iterable<Osallistuja> {
    private static final int MAX_OSALLISTUJAT = 3;
    private int lkm = 0;
    private String tiedostoO = "osallistujat.txt";
    private boolean muokattu = false;
    private Osallistuja alkio[] = new Osallistuja[MAX_OSALLISTUJAT];

    /**
     * muodostaja
     */
    public Osallistujat() {
        //
    }
    
    /**
     * korvaa osallistujan muokatulla osallistujalla
     * @param osal muokattu osallistuja
     * @example
     * <pre name="test">
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi", "Tushak", "044-1231213", "1900", 4);
     * Osallistujat o = new Osallistujat();
     * o.lisaa(samu);
     * samu.setEtunimi("Sakke");
     * o.korvaa(samu);
     * Osallistuja muokattu = o.anna(0);
     * muokattu.getEtunimi() === "Sakke";
     * </pre>
     */
    public void korvaa(Osallistuja osal){
    	int korvattava = osal.getId();
    	for ( int i = 0; i <getLkm(); i++ ) {
    		if ( alkio[i].getId() == korvattava) {
    		 alkio[i] = osal;	
    		}
    	}
    	muokattu = true;
    }


    /**
     * @example
     * <pre name="test">
     * Osallistujat osallistujat1 = new Osallistujat();
     * Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
     * "Jyshak", "041-1212121", "1500", 0);
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
     * "Tushak", "044-1231213", "1900", 0);
     * osallistujat1.lisaa(masa);
     * osallistujat1.lisaa(samu);
     * osallistujat1.getLkm() === 2;
     * </pre>
     * Lisää turnaukseen osallistujan
     * @param osallistuja on turnaukseen osallistuva
     */
    public void lisaa(Osallistuja osallistuja) {
        if (lkm >= alkio.length)
            kasvata();
        alkio[lkm] = osallistuja;
        lkm++;
        muokattu = true;
    }
    
    /**
     * poistaa id:n perusteella osallistujan
     * @param poistId poistettavan osallistujan id
     * @example
     * <pre name="test">
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi", "Tushak", "044-1231213", "1900", 4);
     * Osallistujat o = new Osallistujat();
     * o.lisaa(samu);
     * o.poista(1);
     * o.getLkm() === 0;
     * 
     * </pre>
     * 
     */
    public void poista(int poistId) {
       int kerrat = 0;
       Osallistuja[] uusi = new Osallistuja[lkm];
       
       for(int i = 0; i < lkm; i++) {
           int osalNum = alkio[i].getId();
           if(osalNum != poistId) {
               uusi[kerrat] = alkio[i];
               kerrat++;
           }
       }
       alkio = uusi;
       lkm--;
    }


    /**
     * kasvattaa taulukon kokoa, jos taulukkon maksimikoko ylittyy
     */
    public void kasvata() {
        Osallistuja[] taul = new Osallistuja[MAX_OSALLISTUJAT + 5];
        for (int i = 0; i < alkio.length; i++) {
            taul[i] = alkio[i];
        }
        alkio = taul;

    }


    /**
     * @example
     * <pre name="test">
     * Osallistujat osallistujat2 = new Osallistujat();
     * Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
     * "Jyshak", "041-1212121", "1500",0);
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
     * "Tushak", "044-1231213", "1900",0);
     * osallistujat2.lisaa(masa);
     * osallistujat2.lisaa(samu);
     * Osallistuja haettu = osallistujat2.anna(1);
     * haettu.toString() === "2|Sirkka|Samu|Tushak|1900|samu@sirkat.fi|044-1231213|0"
     * </pre>
     * Palauttaa osallistujan tiedot
     * @param i haettu indeksi
     * @return Palauttaa i viitteestä löytyvän osallistujan tiedot
     * @throws IndexOutOfBoundsException jos i virheellinen
     */
    public Osallistuja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException(
                    "Syötit virheellisen indeksin: " + i);
        return alkio[i];
    }
    
    /**
     * etsii hakuehtoa vastaavat osallistujat, jos tyhjä antaa kaikki
     * @param ehto millä haetaan
     * @return haettu
     */
    public List<Osallistuja> etsi(String ehto){
        List<Osallistuja> loydetyt = new ArrayList<Osallistuja>();
        for (int i = 0; i < getLkm(); i++) {
            if (ehto.isEmpty() || alkio[i].getNimi().equals(ehto)) { //matches
                loydetyt.add(alkio[i]);
            }
        }
        return loydetyt;
    }


    /**
     * @param i indeksi
     * @return indeksistä löytyvä id
     * @throws IndexOutOfBoundsException jos indeksi ei mitään järkevää
     * @example
     * <pre name="test">
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi", "Tushak", "044-1231213", "1900", 4);
     * Osallistujat o = new Osallistujat();
     * o.lisaa(samu);
     * o.annaId(0) === 11;
     * </pre>
     */
    public int annaId(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException(
                    "Syötit virheellisen indeksin: " + i);
        return alkio[i].getId();
    }
    
    /**
     * kasvattaa voittajan pisteitä
     * @param id pelaajan id
     * @example
     * <pre name="test">
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi", "Tushak", "044-1231213", "1900", 4);
     * Osallistujat o = new Osallistujat();
     * o.lisaa(samu);
     * o.lisaaPiste(4);
     * Osallistuja k = o.anna(0);
     * k.getPisteet() === 5;
     * </pre>
     */
    public void lisaaPiste(int id) {
        for (int i = 0; i < getLkm(); i++) {
            if (alkio[i].getId() == id) {
                int pisteet = alkio[i].getPisteet();
                int uusi = pisteet + 1;
                alkio[i].setPisteet(uusi);
            }
        }
    }

    /**
     * antaa osallistujien lukumäärän
     * @return osallistujien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    /**
     * Kirjoittaa osallistujat tiedostoon
     * @example
     * <pre name="test">
     * #THROWS IOException, PoikkeusException
     * #import java.util.Iterator;
     * #import java.io.IOException;
     * Osallistujat osallistujat3 = new Osallistujat();
     * Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
     * "Jyshak", "041-1212121", "1500", 0);
     * osallistujat3.lisaa(masa);
     * osallistujat3.kirjoitaTied();
     * Osallistujat osal = new Osallistujat();
     * osal.lueTied();
     * osal.getLkm() === 1;
     * Iterator<Osallistuja> it = osal.iterator();
     * it.next().toString() === masa.toString();
     * it.hasNext() === false;
     */
    public void kirjoitaTied() {

        if (!muokattu)
            return;

        int i = 0;
        try (PrintStream fo = new PrintStream(
                new FileOutputStream(tiedostoO))) {
            while (i < alkio.length) {
                if (alkio[i] != null) {
                    fo.println(alkio[i].toString());
                }
                i++;
            }
        } catch (FileNotFoundException ex) {
            Dialogs.showMessageDialog("VIRHE: " + ex.getMessage());
            return;
        }

        muokattu = false;
    }

    /**
    * Lukee tiedoston sisällön ja luo niistä olioita
    * @throws PoikkeusException jos osallistujia liikaa
    * @example
    * <pre name="test">
    * #THROWS PoikkeusException
    * Osallistujat osal = new Osallistujat();
    * try {
	*	osal.lueTied();
	* } catch (PoikkeusException e) {
	* 	// TODO Auto-generated catch block
	* 	e.printStackTrace();
	* } 
    * osal.getLkm() === 1;
    * </pre>
    */
    public void lueTied() throws PoikkeusException {
        try (Scanner fi = new Scanner(
                new FileInputStream(new File(tiedostoO)))) {
            while (fi.hasNext()) {
                try {
                    String s = fi.nextLine();
                    Osallistuja uusi = new Osallistuja();
                    uusi.parse(s);
                    lisaa(uusi);

                } catch (NullPointerException ex) {
                    //
                }
            }
        } catch (FileNotFoundException e) {
            Dialogs.showMessageDialog("ei voida avata: " + e.getMessage());
            }
    }


    @Override
    public Iterator<Osallistuja> iterator() {
        return new osallistujatIterator();
    }

    /**
     * @author lehti
     * @version 19.4.2021
     *
     */
    public class osallistujatIterator implements Iterator<Osallistuja> {
        private int osal = 0;

        /**
         * Tarkistaa seuraavan osallistujan olemassa olon
         * @return true jos yhä osallistujia
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return osal < getLkm();
        }


        /**
         * Palauttaa seuraavan osallistujan
         * @return seuraava osallistuja
         * @throws NoSuchElementException jos ei ole enempää osallistujia
         * @see java.util.Iterator#next()
         */
        @Override
        public Osallistuja next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("Ei löydetty");
            return anna(osal++);
        }


        /**
         * Poistoa ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }

    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Osallistujat osallistujat = new Osallistujat();

        Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
                "Jyshak", "041-1212121", "1500", 0);
        Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
                "Tushak", "044-1231213", "1900", 0);

        osallistujat.lisaa(masa);
        osallistujat.lisaa(samu);

        System.out
                .println("============= Osallistujat testi =================");

        for (int i = 0; i < osallistujat.getLkm(); i++) {
            Osallistuja osallistuja = osallistujat.anna(i);
            System.out.println("Osallistuja nro: " + i);
            osallistuja.tulosta(System.out);

        }

    }

}
