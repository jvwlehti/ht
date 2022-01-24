package shakkiturnaus.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author 35845
 * @version 2 Apr 2021
 *
 */
public class Tulokset implements Iterable<Tulos> {

    private String tiedostoT = "tulos.txt";
    private boolean muokattu = false;
    private Collection<Tulos> tulokset = new ArrayList<Tulos>();

    /**
     * default
     */
    public Tulokset() {

    }


    /**
     * @example
     * <pre name="test">
     * Tulokset tuloksia = new Tulokset();
     * tuloksia.lisaa(new Tulos(1, 2));
     * tuloksia.getLkm() === 1;
     * </pre>
     * @param t lisättävä tulos
     */
    public void lisaa(Tulos t) {
        tulokset.add(t);
        muokattu = true;
    }
    
    /**
     * poistaa virheellisen tuloksen
     * @param poistId poistettavan pelin ID
     */
    public void poista(int poistId) {
        Collection<Tulos> uusi = new ArrayList<Tulos>();
        
        for(Tulos peli : tulokset) {
            int peliId = peli.getPeliId();
            if(peliId != poistId) {
                uusi.add(peli);
            }
        }
        tulokset = uusi;
     }


    /**
     * @return listan koko
     */
    public int getLkm() {
        return tulokset.size();
    }
    


    /**
     *  kirjoitetaan tulokset tiedostoon
     * @example
     * <pre name="test">
     * #THROWS IOException, PoikkeusException
     * #import java.util.Iterator;
     * #import java.io.IOException;
     * Tulokset tul = new Tulokset();
     * Tulos eka = new Tulos();
     * eka.setPelaaja1(1);
     * eka.setPelaaja2(2);
     * tul.lisaa(eka);
     * tul.kirjoitaTulos();
     * Iterator<Tulos> it = tul.iterator();
     * it.next().toString() === eka.toString();
     * it.hasNext() === false;
     *  
     */
    public void kirjoitaTulos() {
        
        if (!muokattu) return;
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedostoT))){
            Iterator<Tulos> it = this.iterator();
            while(it.hasNext()) {
                fo.println(it.next());
            }
        } catch (FileNotFoundException ex) {
            Dialogs.showMessageDialog("VIRHE: " + ex.getMessage());
            return;
        }

        muokattu = false;
    }
    
    /**
     * Lukee tiedoston sisällön ja luo niistä olioita
     * @example
	 * <pre name="test">
	 * Tulokset tul = new Tulokset();
	 *	tul.lueTied();
	 * tul.getLkm() === 1;
	 * </pre>
     */
    public void lueTied()  {
        try (Scanner fi = new Scanner(new FileInputStream(new File(tiedostoT)))){
            while (fi.hasNext()) {
                try {
                    String s = fi.nextLine();
                    Tulos peli = new Tulos();
                    peli.parse(s);
                    lisaa(peli);

                } catch (NullPointerException ex) {
                    //
                }
            }
        } catch (FileNotFoundException e) {
            Dialogs.showMessageDialog("ei voida avata: " + e.getMessage());
        }
    }
    
    /**
     * laskee pelaajan pelaamat pelit
     * @param id pelaaja jonka pelejä lasketaan
     * @return pelien määrä
     * @example
     * <pre name="test">
     * Tulokset games = new Tulokset();
     * Tulos game = new Tulos();
     * game.setPelaaja1(1);
     * game.setPelaaja2(2);
     * games.lisaa(game);
     * 
     * Tulos game2 = new Tulos();
     * game2.setPelaaja1(1);
     * game2.setPelaaja2(3);
     * games.lisaa(game2);
     * 
     * games.laskePelit(1) === 2;
     * games.laskePelit(3) === 1;
     * games.laskePelit(4) === 0;
     * 
     * </pre>
     */
    public int laskePelit(int id) {
        int pelit = 0;
        for (Tulos tulo : tulokset) {
            if (tulo.getPelaaja1() == id) {
                pelit++;
            }
            
            if (tulo.getPelaaja2() == id) {
                pelit++;
            }
        }
        return pelit;
    }


    /**
     * @return tulokset
     */
    public Collection<Tulos> getTulokset() {
        return tulokset;
    }
    


    @Override
    public Iterator<Tulos> iterator() {
        return tulokset.iterator();
    }
   
}
