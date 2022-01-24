package shakkiturnaus.Model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * @author jvwlehti & leodanbu
 * @version 3.3.2021
 *
 */
public class Turnaus {
    
    private String       tiedostoT     = "tulos.txt";
    private String       tiedostoO     = "osallistujat.txt";
    private Osallistujat osallistujat  = new Osallistujat();
    private Tulokset     tulokset      = new Tulokset();
    
    
    
    
    /**
     * korvaa vanhan osallistuja olion uudella muokatulla
     * @param osal korvaava
     */
    public void korvaa(Osallistuja osal) {
    	osallistujat.korvaa(osal);
    }

    /**
     * @param i indeksi josta haetaan
     * @return osallistuja
     */
    public Osallistuja haeOsallistuja(int i) {

        return osallistujat.anna(i);
    }


    /**
     * @param osallistuja lisättävä osallistuja
     */
    public void lisaaOsallistuja(Osallistuja osallistuja) {
        osallistujat.lisaa(osallistuja);
    }
    
    /**
     * lukee tiedoston sisällön 
     * @throws PoikkeusException jos osallistujia liikaa tiedostossa
     */
    public void lueTied() throws PoikkeusException {
        osallistujat = new Osallistujat();
        osallistujat.lueTied();
    }

    /**
     * kirjoittaa turnauksen tiedot tiedoston
     */
    public void kirjoitaTiedO() {
        osallistujat.kirjoitaTied();
    }
    
    /**
     * laskee osallistujan pelit
     * @param osa osallistuja jonka pelit lasketaan
     * @return pelattujen pelien määrä
     */
    public int laskePelit(Osallistuja osa) {
        int id = osa.getId();
        int pelit = tulokset.laskePelit(id);
        return pelit;
    }
    
    /**
     * ohjaa poiston oikeaan luokkaan
     * @param osa poistettavan osallistujan
     */
    public void poista(Osallistuja osa) {
        int id = osa.getId();
        osallistujat.poista(id);
    }
    
    /**
     * ohjaa poiston oikeaan luokkaan
     * @param tulo tulos joka poistetaan
     */
    public void poistaPeli(Tulos tulo) {
        int id = tulo.getPeliId();
        tulokset.poista(id);
    }

    /**
     * Kirjoittaa tulokset tiedostoon
     */
    public void kirjoitaTulos() {
        tulokset.kirjoitaTulos();
    }
    
    /**
     * Lukee tulokset tiedostosta
     * @throws PoikkeusException jos liikaa
     */
    public void lueTulos() throws PoikkeusException {
        tulokset = new Tulokset();
        tulokset.lueTied();
    }
    
    /**
     * @param tulos lisättävä tulos
     */
    public void lisaaTulos(Tulos tulos) {
        tulokset.lisaa(tulos);
    }


    /**
     * Listaa tekstimuotoon kierrostulokset
     * @return String-tyyppinen lista kierrosten tuloksista
     */
    public List<Pair<Tulos, String>> tulokset() {
        List<Pair<Tulos, String>> tuloste = new ArrayList<Pair<Tulos, String>>();
        tuloste = kasittele(osallistujat);
        return tuloste;
    }


    /**
     * @return taulukon pituus
     */
    public int getLkm() {
        return osallistujat.getLkm();
    }
    
    /**
     * @return palauttaa turnauksessa pelattujen pelien määrän
     */
    public int getPelitLkm() {
        return tulokset.getLkm();
    }


    /**
     * @param i osallistujan indeksi
     * @return osallistujan id
     */
    public int haeOsallistujaId(int i) {

        return osallistujat.annaId(i);
    }

    
    /**
     * tyhjentää tiedostot
     */
    @SuppressWarnings("resource")
    public void poista() {
        try {
            new PrintWriter(tiedostoT).close();
            new PrintWriter(tiedostoO).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * lisää voittajalle pisteen
     * @param id voittajan id
     */
    public void lisaaPiste(int id) {
        osallistujat.lisaaPiste(id);
    }

    /**
     * @param osa osallistujalista jota käydään läpi jotta saadaan selville pelin osallistujat
     * @return String-muotoinen Lista peleistä
     * @example
     * <pre name="test">
     * #THROWS PoikkeusException
     * #import javafx.util.Pair;
     * #import java.util.List;
     * Turnaus testi = new Turnaus();
     * Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi",
     * "Jyshak", "041-1212121", "1500", 0);
     * Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
     * "Tushak", "044-1231213", "1900", 0);
     * testi.lisaaOsallistuja(masa);
     * testi.lisaaOsallistuja(samu);
     * 
     * testi.lisaaTulos(new Tulos(1, 2));
     * List<Pair<Tulos, String>> tuloste = testi.tulokset();
     * 
     * tuloste.get(0).getValue() === "Masa Mainio 1-0 Samu Sirkka";
     * 
     * </pre>
     */
    public List<Pair<Tulos, String>> kasittele(Osallistujat osa) { 
                                                      
        List<Pair<Tulos, String>> pelit = new ArrayList<Pair<Tulos, String>>();
        for (Tulos tulo : tulokset) {
            String pelaaja1 = ""; 
            String pelaaja2 = "";

            String s = tulo.toString();
            String[] osat = s.split("\\|");
            for (int j = 0; j < osa.getLkm(); j++) {
                if (osa.annaId(j) == Integer.parseInt(osat[3])) {
                    pelaaja1 = osa.anna(j).getNimi();
                }
            }

            for (int j = 0; j < osa.getLkm(); j++) {
                if (osa.annaId(j) == Integer.parseInt(osat[2])) {
                    pelaaja2 = osa.anna(j).getNimi();
                }
            }

            pelit.add(new Pair<Tulos, String>(tulo, pelaaja1 + " 1-0 " + pelaaja2));
        }

        return pelit;
    }
    
    /**
     * @param args ei ole
     */
    public static void main(String[] args) {

        Turnaus turnaus = new Turnaus();
        Osallistuja masa = new Osallistuja("Vesa", "Mainio", "vesa@mainio.fi",
                "Jyshak", "041-1212121", "9000", 0);
        Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi",
                 "Tushak", "044-1231213", "1900", 0);

        turnaus.lisaaOsallistuja(masa);
        turnaus.lisaaOsallistuja(samu);
        
        Osallistuja joku = turnaus.haeOsallistuja(0);
        joku.tulosta(System.out);
        
        Osallistuja joku2 = turnaus.haeOsallistuja(1);
        joku2.tulosta(System.out);
        
        turnaus.lisaaTulos(new Tulos(1, 2));
        turnaus.lisaaTulos(new Tulos(2, 1));
        
        var taulu = turnaus.tulokset();
        for (int i = 0; i < taulu.size(); i++) {
            taulu.get(i).toString();
        }

    }

    /**
     * hakee osallistujat
     * @param string hakuehto
     * @return hakuehtoa vastaavat osallistujat
     */
    public List<Osallistuja> etsiOsallistujat(String string) {
        return osallistujat.etsi(string);
    }

}
