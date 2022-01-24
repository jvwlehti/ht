package shakkiturnaus.Model;

import java.io.PrintStream;

/**
 * @author jvwlehti & leodanbu
 * @version 1.3.2021
 *
 */
public class Osallistuja {
    
    private int           id       =  0;
    private String        etunimi  = "";
    private String        sukunimi = "";
    private String        sposti   = "";
    private String        kerho    = "";
    private String        puhnum   = "";
    private String        vahvuus  = "";
    private int           pisteet  =  0;
    
    private static int    seuraavaId = 1;
    
    /**
     * @param etunimi osallistujan etunimi
     * @param sukunimi osallistujan sukunimi 
     * @param sposti osallistujan sähköposti
     * @param kerho osallistujan kerho
     * @param puhnum osallistujan puhelinnumero
     * @param vahvuus osallistujan vahvuusluku
     * @param pisteet pelaajan saamat pisteet
     */
    public Osallistuja (String etunimi, String sukunimi, String sposti, String kerho, String puhnum,
            String vahvuus, int pisteet) {
        this.id = lisaaId();
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sposti = sposti;
        this.kerho = kerho;
        this.puhnum = puhnum;
        this.vahvuus = vahvuus;
        this.pisteet = pisteet;
    }

    /**
     * parametriton konstruktori
     */
    public Osallistuja() {
        this.id = lisaaId();
    }
    
    /**
     * pitäisi tulostaa osallistuja
     * @param out osallistujan tiedot
     */
    public void tulosta(PrintStream out) {
        out.println("ID: " + id);
        out.println("nimi: " + etunimi + " " + sukunimi);
        out.println("sähköposti: " + sposti);
        out.println("kerho: " + kerho);
        out.println("puhelinnumero: " + puhnum);
        out.println("Vahvuusluku: "+ vahvuus);
        out.println("Pisteet: "+ pisteet);
        out.println();
    }
    
    /**
     * lisää 
     * @return osallistujan id
     */
    public int lisaaId() {
        if(this.id > 0) return id;
        id = seuraavaId;
        seuraavaId++;
        return id;
    }
    
    /**
     * muodostaa tekstirivistä osallistuja-olion
     * @param s tuotava tekstirivi josta muodostetaan olio
     * @example
     * <pre name="test">
     * Osallistuja uusi = new Osallistuja();
     * String sepe = "1|Susi|Sepe|AnkSk|1300|sepe@susi.fi|0123456|0";
     * uusi.parse(sepe);
     * uusi.toString() === "1|Susi|Sepe|AnkSk|1300|sepe@susi.fi|0123456|0";
     * </pre>
     */
    public void parse(String s) {
        String[] osat = s.split("\\|");
        setId(Integer.parseInt(osat[0]));
        setEtunimi(osat[2]);
        setSukunimi(osat[1]);
        setKerho(osat[3]);
        setVahvuus(osat[4]);
        setPuhnum(osat[6]);
        setSposti(osat[5]);
        setPisteet(Integer.parseInt(osat[7]));
    }
    /**
     * @example
     * <pre name="test">
     * Osallistuja markku = new Osallistuja("Markku", "Mainio", "mara@mainio.fi", "JärvSk", "0414121212", "1500", 0);
     * markku.toString() === "1|Mainio|Markku|JärvSk|1500|mara@mainio.fi|0414121212|0";
     * </pre>
     */
    @Override
    public String toString(){
        return id + "|" + sukunimi + "|" + etunimi + "|" + kerho + "|"  + vahvuus + "|" + sposti + "|" + puhnum + "|" + pisteet;
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Osallistuja masa = new Osallistuja("Masa", "Mainio", "masa@mainio.fi", "Jyshak", "041-1212121", "1500", 0);
        masa.tulosta(System.out);
        Osallistuja samu = new Osallistuja("Samu", "Sirkka", "samu@sirkat.fi", "Tushak", "044-1231213", "1900", 0);
        samu.tulosta(System.out);

    }

    /**
     * @return osallistujan ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return pelaajan pisteet
     * </pre>
     */
    public int getPisteet() {
        return pisteet;
    }

    /**
     * asettaa pelaajan pisteet
     * @param pisteet pelaajan saamat pisteet
     */
    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }
    /**
     * @param id asetettava id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return etunimi sukunimi
     */
    public String getNimi() {
        return etunimi + " " + sukunimi;
    }


    /**
     * @return Osallistujan etunimi
     */
    public String getEtunimi() {
        return etunimi;
    }

    /**
     * @param etunimi Asettaa osallistujalle etunimen
     */
    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    /**
     * @return palauttaa osallistujan sukunimen
     */
    public String getSukunimi() {
        return sukunimi;
    }

    /**
     * @param sukunimi osallistujalle asetettava sukunimi
     */
    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    /**
     * @return palauttaa osallistujan sähköpostin
     */
    public String getSposti() {
        return sposti;
    }

    /**
     * @param sposti asettaa osallistujalle sähköpostin
     */
    public void setSposti(String sposti) {
        this.sposti = sposti;
    }

    /**
     * @return tiedon mihin kerhoon osallistuja kuuluu
     */
    public String getKerho() {
        return kerho;
    }

    /**
     * @param kerho asettaa osallistujan kerho tiedon
     */
    public void setKerho(String kerho) {
        this.kerho = kerho;
    }

    /**
     * @return palauttaa osallistujan puhelinnumeron
     */
    public String getPuhnum() {
        return puhnum;
    }

    /**
     * @param puhnum asetetaan osallistujan puhelinnumero
     */
    public void setPuhnum(String puhnum) {
        this.puhnum = puhnum;
    }

    /**
     * @return palauttaa osallistujan vahvuusluvun
     */
    public String getVahvuus() {
        return vahvuus;
    }

    /**
     * @param vahvuus asetetaan osallistujalle vahvuusluku
     */
    public void setVahvuus(String vahvuus) {
        this.vahvuus = vahvuus;
    }

}
