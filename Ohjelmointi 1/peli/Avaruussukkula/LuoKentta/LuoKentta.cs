using System;
using System.Collections.Generic;
using Jypeli;
using Jypeli.Assets;
using Jypeli.Controls;
using Jypeli.Widgets;

public class LuoKentta : PhysicsGame
{

    private PhysicsObject pelaaja;
    IntMeter pisteLaskuri;

    public override void Begin()
    {

        LuoTaso();
        Nappaimet();

    }
  

    private void LuoTaso()
    {
        SetWindowSize(360, 740, false);
        Level.Background.Color = Color.Red;

        LuoPelaaja();
    }

    private void LuoPelaaja()
    {
        pelaaja = new PlatformCharacter(10, 10);
        Vector paikka = new Vector(10, Level.Bottom + 10);
        pelaaja.Position = paikka;
        pelaaja.Shape = Shape.Diamond;
        pelaaja.Color = Color.Yellow;
        pelaaja.Tag = "pelaaja";
        Add(pelaaja);
    }
    private static void LiikutaPelaajaa(PhysicsObject pelaaja, Vector suunta)
    {
        pelaaja.Hit (suunta);
    }
    private void Nappaimet()
    {
        Keyboard.Listen(Key.F1, ButtonState.Pressed, ShowControlHelp, "Näytä avustus");
        Keyboard.Listen(Key.Left, ButtonState.Down, LiikutaPelaajaa, "Alus vasemmalle", pelaaja, new Vector(-200, 0));
        Keyboard.Listen(Key.Right, ButtonState.Down, LiikutaPelaajaa, "Alus oikealle", pelaaja, new Vector(200, 0));

        PhoneBackButton.Listen(ConfirmExit, "Lopeta peli");
        Keyboard.Listen(Key.Escape, ButtonState.Pressed, ConfirmExit, "Lopeta peli");
    }
}

