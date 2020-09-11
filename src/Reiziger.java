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

        if (adres != null) {
            return  "Reiziger " + id + ": " + voorletters + ". " + tussenvoegsel + achternaam + " (" + geboortedatum + "), woont op " +
                    "adres " + adres.getId() + ": " + adres.getStraat() + " " + adres.getHuisnummer() + ", " + adres.getPostcode() + ", " + adres.getWoonplaats();
        }
        return "Reiziger " + id + ": " + voorletters + ". " + tussenvoegsel + achternaam + " (" + geboortedatum + ")";
    }
}
