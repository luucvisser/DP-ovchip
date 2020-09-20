package domein;

import java.sql.Date;

public class OVChipkaart {
    private int nummer;
    private Date geldig_tot;
    private int klasse;
    private Double saldo;

    private Reiziger reiziger;

    // Maakt een OV Chipkaart object aan
    public OVChipkaart(int nummer, Date geldig_tot, int klasse, Double saldo, Reiziger reiziger) {
        this.nummer = nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getNummer() { return nummer; }

    public Date getGeldig_tot() { return geldig_tot; }

    public int getKlasse() { return klasse; }

    public Double getSaldo() { return saldo; }

    public Reiziger getReiziger() { return reiziger; }

    public void setNummer(int nummer) { this.nummer = nummer; }

    public void setGeldig_tot(Date geldig_tot) { this.geldig_tot = geldig_tot; }

    public void setKlasse(int klasse) { this.klasse = klasse; }

    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public void setReiziger(Reiziger reiziger) { this.reiziger = reiziger; }

    public String toString() {
        String tussenvoegsel = reiziger.getTussenvoegsel();

        if (tussenvoegsel == null) {
            tussenvoegsel = "";
        } else {
            tussenvoegsel += " ";
        }

        return "OV Chipkaart " + nummer + " (geldig tot: " + geldig_tot + ") heeft een saldo van " + saldo + " en mag in klasse " + klasse + ". " +
                "De eigenaar is reiziger " + reiziger.getId() + ": " + reiziger.getVoorletters() + ". " + tussenvoegsel + reiziger.getAchternaam() + " (" + reiziger.getGeboortedatum() + ")";
    }
}
