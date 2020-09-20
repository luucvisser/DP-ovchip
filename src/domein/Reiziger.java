package domein;

import domein.Adres;
import domein.OVChipkaart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres adres;

    List<OVChipkaart> OVChipkaarten = new ArrayList<>();

    // Maakt een reiziger object aan
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
            this.id = id;
            this.voorletters = voorletters;
            this.tussenvoegsel = tussenvoegsel;
            this.achternaam = achternaam;
            this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Adres getAdres() { return adres; }

    public List<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsels) {
        this.tussenvoegsel = tussenvoegsels;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setAdres(Adres adres) { this.adres = adres; }

    public void setOVChipkaarten(OVChipkaart ov) { this.OVChipkaarten.add(ov); }

    public String toString() {
        if (tussenvoegsel == null) {
            tussenvoegsel = "";
        }
        else {
            tussenvoegsel += " ";
        }

        String output = "Reiziger " + id + ": " + voorletters + ". " + tussenvoegsel + achternaam + " (" + geboortedatum + ")";

        if (adres != null) {
            output += ", woont op adres " + adres.getId() + ": " + adres.getStraat() + " " + adres.getHuisnummer() + ", " + adres.getPostcode() + ", " + adres.getWoonplaats();
        }

        if (!OVChipkaarten.isEmpty()) {
            output += " en heeft:";
            for (OVChipkaart ov : OVChipkaarten) {
                output += "\n      OV Chipkaart " + ov.getNummer() + " (geldig tot: " + ov.getGeldig_tot() + ") voor klasse " + ov.getKlasse() + ", met een saldo van " + ov.getSaldo();
            }
        }

        return output;
    }
}
