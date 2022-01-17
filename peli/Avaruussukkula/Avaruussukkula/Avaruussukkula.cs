using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices.ComTypes;
using Jypeli;
using Jypeli.Assets;
using Jypeli.Controls;
using Jypeli.Widgets;
using Physics2DDotNet.PhysicsLogics;

/// @author Jere Lehtinen
/// @version 25.11.2020
/// <summary>
/// Avaruussukkulan seikkailussa tulee lennellä sukkulalla poimien matkalta timantteja väistellen eteen tulevia esteitä
/// </summary>

///TODO:https://tim.jyu.fi/view/kurssit/tie/ohj1/2020s/demot/demo9#teht%C3%A4v%C3%A4-6-7.-palapeli

public class Avaruussukkula : PhysicsGame
{
    private readonly Image timanttiKuva = LoadImage("timantti.png");
    private readonly Image taustaKuva = LoadImage("avaruuskuva360x740.png");
    private readonly Image rakettiKuva = LoadImage("Raketti.png");
    private static readonly String[] lines =
  {
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
        "                          ",
    };
    private static readonly int tileWidth = 360 / lines[0].Length;
    private static readonly int tileHeight = 740 / lines.Length;

    private double leveys = tileWidth;
    private double korkeus = tileHeight;
    private Vector nopeusAlaspain = new Vector(0, -100);

    private PhysicsObject pelaaja;
    private IntMeter pisteLaskuri;
    private Timer esteenAjastin;

    private Shape timanttiMuoto;
    private Shape rakettiMuoto;

    private EasyHighScore tulokset = new EasyHighScore(); 


    public override void Begin()
    {
        timanttiMuoto = Shape.FromImage(timanttiKuva);
        rakettiMuoto = Shape.FromImage(rakettiKuva);

        LuoTaso();
        Nappaimet();
        LuoPistelaskuri();
        LuoAjastin();
    }


    /// <summary>
    /// aliohjelmassa luodaan kenttä
    /// </summary>
    private void LuoTaso()
    {
        SetWindowSize(360, 740, false);
        Level.Background.Image = taustaKuva;

        TileMap tiles = TileMap.FromStringArray(lines);
        LuoPelaaja(tileWidth, tileHeight);
        tiles.Execute(tileWidth, tileHeight);
    }


    /// <summary>
    /// aliohjelmassa luodaan pelattava hahmo
    /// </summary>
    private void LuoPelaaja(double leveys, double korkeus)
    {
        pelaaja = new PhysicsObject(leveys * 5 , korkeus);
        Vector paikka = new Vector(0, Level.Bottom + korkeus);
        pelaaja.Position = paikka;
        pelaaja.Image = rakettiKuva;
        pelaaja.Shape = rakettiMuoto;
        pelaaja.Tag = "pelaaja";
        pelaaja.CanRotate = false;
        Add(pelaaja);

        AddCollisionHandler(pelaaja, "oikeaEste", Tormays);
        AddCollisionHandler(pelaaja, "vasenEste", Tormays);
        AddCollisionHandler(pelaaja, "timantti", TormaaTimanttiin);
    }


    /// <summary>
    /// aliohjelma antaa pelaajalle liikkeen
    /// </summary>
    /// <param name="pelaaja">mitä liikutetaan</param>
    /// <param name="liike">mitä aliohjelma antaa</param>
    private void LiikutaPelaajaa(PhysicsObject pelaaja, Vector liike)
    {
        pelaaja.Hit(liike);
    }


    /// <summary>
    /// mitä ohjaimia ohjelmalle annetaan
    /// </summary>
    private void Nappaimet()
    {
        Keyboard.Listen(Key.F1, ButtonState.Pressed, ShowControlHelp, "Näytä avustus");
        Keyboard.Listen(Key.Left, ButtonState.Down, LiikutaPelaajaa, "Alus vasemmalle", pelaaja, new Vector(-10, 0));
        Keyboard.Listen(Key.Right, ButtonState.Down, LiikutaPelaajaa, "Alus oikealle", pelaaja, new Vector(10, 0));

        PhoneBackButton.Listen(ConfirmExit, "Lopeta peli");
        Keyboard.Listen(Key.Escape, ButtonState.Pressed, ConfirmExit, "Lopeta peli");
    }


    /// <summary>
    /// lisätään ohjelmaan törmäys ja sen seuraaamukset
    /// </summary>
    /// <param name="pelaaja">mikä törmää</param>
    /// <param name="palkki">mihin törmää</param>
    private void Tormays(PhysicsObject pelaaja, PhysicsObject palkki)
    {
        Explosion rajahdys = new Explosion(pelaaja.Width * 2);
        rajahdys.Position = pelaaja.Position;
        rajahdys.UseShockWave = false;
        Add(rajahdys);
        pelaaja.Destroy();
        TulosTaulukko();

    }
    

    /// <summary>
    /// avaa pelin päätyttyä tulostaulukon 
    /// </summary>
    private void TulosTaulukko ()
    {
        tulokset.EnterAndShow(pisteLaskuri.Value);
        tulokset.HighScoreWindow.Closed += LopetaPeli;
    }


    /// <summary>
    /// määritettään mitä tapahtuu kun ennätysvalikko suljetaan
    /// </summary>
    /// <param name="sender"></param>
    private void LopetaPeli(Window sender)
    {
        Exit();
    }


    /// <summary>
    /// luo kerättävän esineen
    /// </summary>
    /// <param name="paikka">kohta mihin timantin pitäisi tulla</param>
    private void LuoTimantti(Vector paikka, double leveys , double korkeus) 
    {
        PhysicsObject timantti = new PhysicsObject( leveys * 5 , korkeus * 1.5 );
        timantti.Position = paikka;
        Vector vauhti = nopeusAlaspain;
        timantti.Hit(vauhti);
        timantti.Shape = Shape.Rectangle;
        timantti.Image = timanttiKuva;
        timantti.Tag = "timantti";
        Add(timantti);

    }


    /// <summary>
    /// poimii kerättävän esineen
    /// </summary>
    /// <param name="pelaaja">kerääjä</param>
    /// <param name="timantti">kerättävä</param>
    private void TormaaTimanttiin(PhysicsObject pelaaja, PhysicsObject timantti)
    {
        pisteLaskuri.Value += 100;
        if (esteenAjastin != null)
        {
            esteenAjastin.Interval = esteenAjastin.Interval * 0.90;
        }
        timantti.Destroy();
        
    }


    /// <summary>
    /// aliohjelmassa luodaan pelille pistelaskuri joka lisää pisteitä kun tähtiä kerätään
    /// </summary>
    private void LuoPistelaskuri ()
    {
        pisteLaskuri = new IntMeter(0);

        Label pisteNaytto = new Label();
        pisteNaytto.X = Level.Left + 1.5 * leveys ;
        pisteNaytto.Y = Screen.Top - korkeus;
        pisteNaytto.TextColor = Color.Green;

        pisteNaytto.BindTo(pisteLaskuri);
        Add(pisteNaytto);
    }
    

    /// <summary>
    /// luodaan ajastin jonka mukaan peliin tulee uusia esteitä
    /// </summary>
    private void LuoAjastin()
    {
        esteenAjastin = new Timer();
        esteenAjastin.Interval = 2.0;
        esteenAjastin.Timeout += LuoUusiEste;
        esteenAjastin.Start();
    }
   

    /// <summary>
    /// luodaan väisteltävät esteet siten että niillä on tietty väli sekä määritetään kerättävän esineen sijainti
    /// </summary>
    private void LuoUusiEste()
    {
        double x = RandomGen.NextDouble(Level.Left, Level.Right - leveys * 6);
        double x2 = x + leveys * 6;
        double x3 = x + ((leveys * 6) / 2);

        double vasemmanLeveys = Vector.Distance(new Vector(Level.Left, 0), new Vector(x ,0));
        double oikeanLeveys = Vector.Distance(new Vector(Level.Right, 0), new Vector(x2, 0));

        PhysicsObject oikeaEste =new PhysicsObject(oikeanLeveys, korkeus / 4);
        PhysicsObject vasenEste = new PhysicsObject(vasemmanLeveys, korkeus / 4);

        Vector sijainti = new Vector(Level.Left + vasemmanLeveys / 2 ,Level.Top + korkeus);
        Vector sijaintiO = new Vector(Level.Right - oikeanLeveys / 2 , Level.Top + korkeus);
        oikeaEste.Position = sijaintiO;
        vasenEste.Position = sijainti;

        LuoTimantti(new Vector(x3, oikeaEste.Y), tileWidth, tileHeight);

        Add(oikeaEste);
        Add(vasenEste);
        oikeaEste.Hit(nopeusAlaspain);
        vasenEste.Hit(nopeusAlaspain);

        oikeaEste.Tag = "oikeaEste";
        vasenEste.Tag = "vasenEste";
    }


    /// <summary>
    /// pitää huolen että raketti pysyy paikallaan y-akselin mukaan, eikä karkaa pelialueen ulkopuolelle
    /// </summary>
    /// <param name="time"></param>
    protected override void Update(Time time)
    {
        if ( pelaaja != null && Math.Abs(pelaaja.Y) > Level.Bottom + korkeus)
        {
            pelaaja.Y = Level.Bottom + korkeus;
        }

        if (pelaaja != null && pelaaja.Left < Level.Left)
        {
            pelaaja.Left = Level.Left;
        }

        if (pelaaja != null && pelaaja.Right > Level.Right)
        {
            pelaaja.Right = Level.Right;
        }

        base.Update(time);
    }

}

