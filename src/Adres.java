import java.util.List;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String toString() {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
        List<Reiziger> reizigerLijst = rdao.findAll();

        for (var r : reizigerLijst) {
            if (r.getId() == reiziger_id) {

                String tussenvoegsel = r.getTussenvoegsel();

                if (tussenvoegsel == null) {
                    tussenvoegsel = "";
                }
                else {
                    tussenvoegsel = r.getTussenvoegsel() + " ";
                }

                return  "Reiziger " + r.getId() + ": " + r.getVoorletters() + ". " + tussenvoegsel + r.getAchternaam() + " (" + r.getGeboortedatum() + "), woont op " +
                        "adres " + id + ": " + straat + " " + huisnummer + ", " + postcode + ", " + woonplaats;
            }
        }
        return "Adres " + id + " (Reiziger " + reiziger_id + "): " + straat + " " + huisnummer + ", " + postcode + ", " + woonplaats;
    }
}
