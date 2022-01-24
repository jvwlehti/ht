package shakkiturnaus.Model;

import java.time.LocalDate;

/**
 * @author 35845
 * @version 2 Apr 2021
 *
 */
public class Tulos {
    
    private int pelaaja1;
    private int pelaaja2;
    private int tulos;


    private String pvm;
    private int peliId;
    
    private static int seuraavaId = 1;
    
    /**
     * muodostaja 
     */
    public Tulos() {
        
        this.peliId = lisaaId();
        this.pvm = uusiPvm();
    }
    
    /**
     * @param p1 eka pelaaja
     * @param p2 toka pelaaja

     */
    public Tulos(int p1, int p2) {
        this.pelaaja1 = p1;
        this.pelaaja2 = p2;
        this.tulos = p1;
        this.peliId = lisaaId();
        this.pvm = uusiPvm();
    }
    
    /**
     * @return automaattinen id pelille
     */
    public int lisaaId() {
        peliId = seuraavaId;
        seuraavaId++;
        return peliId;
    }
    

    /**
     * Lisää tulokseen päivämäärän
     * @return tämä päivämäärä String-muodossa
     */
    private String uusiPvm() {
        LocalDate nyt = LocalDate.now();
        return nyt.toString();
    }

    /**
     * muodostaa tulos olion teksirivistä
     * @param s annettu tekstirivi
     * @example
     * <pre name="test">
     * String t = "2|1|3|1|2021-05-14";
     * Tulos t1 = new Tulos();
     * t1.parse(t);
     * t1.toString() === "2|1|3|1|2021-05-14";
     * 
     * </pre>
     */
    public void parse (String s) {
        String[] osat = s.split("\\|");
        setPeliId(Integer.parseInt(osat[0]));
        setPelaaja1(Integer.parseInt(osat[1]));
        setPelaaja2(Integer.parseInt(osat[2]));
        setTulos(Integer.parseInt(osat[3]));
        setPvm(osat[4]);
    }
    
    /**
     * @return pelin tulos string muodossa
     * @example
     * <pre name="test">
     * Tulos t = new Tulos(1, 3);
     * Tulos t2 = new Tulos(3, 1);
     * t.toString() === "2|1|3|1|2021-05-14"
     * t2.toString() === "3|3|1|3|2021-05-14"
     * </pre>
     */
    @Override
    public String toString() {
        return peliId + "|" + pelaaja1 + "|" + pelaaja2 + "|" + tulos + "|" + pvm;
    }
    
    /**
     * @return palauttaa pelin ID:n
     */
    public int getPeliId() {
        return peliId;
    }

    /**
     * asettaa pelille ID:n
     * @param peliId asetettava id
     */
    public void setPeliId(int peliId) {
        this.peliId = peliId;
    }
    
    /**
     * @return pelin päivämäärä
     */
    public String getPvm() {
        return pvm;
    }

    /**
     * asettaa pelille päivämäärän
     * @param pvm asetettava päivämäärä pelille
     */
    public void setPvm(String pvm) {
        this.pvm = pvm;
    }

    /**
     * @return pelaaja1 id
     */
    public int getPelaaja1() {
        return pelaaja1;
    }

    /**
     * Asettaa Pelaaja1 ID:n
     * @param pelaaja1 asetuksen kohde
     */
    public void setPelaaja1(int pelaaja1) {
        this.pelaaja1 = pelaaja1;
    }


    /**
     * @return pelaaja2 id
     */
    public int getPelaaja2() {
        return pelaaja2;
    }

    /**
     * Asettaa Pelaaja2 ID:n
     * @param pelaaja2 asetuksen kohde
     */
    public void setPelaaja2(int pelaaja2) {
        this.pelaaja2 = pelaaja2;
    }
    
    /**
     * Asettaa pelin voittajan
     * @param tulos pelin voittaja
     */
    public void setTulos(int tulos) {
        this.tulos = tulos;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        LocalDate nyt = LocalDate.now();
        System.out.println(nyt);
    }

}
