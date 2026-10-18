package model;

public class Kendaraan {
    private final String platNomor;
    private final String merk;
    private final String jenis;
    private final long tarifPerhari;
    private boolean tersedia = true;

    public Kendaraan(String platNomor, String merk, String jenis, long tarifPerhari) {
        this.platNomor = platNomor;
        this.merk = merk;
        this.jenis = jenis;
        this.tarifPerhari = tarifPerhari;
    }

    public long hitungBiayaSewa(int hari) {
        long total = tarifPerhari * hari;

        if (jenis.equals("MOBIL")) {
            total += 50000; 

        } else if (jenis.equals("MOTOR")) {
            if (hari >= 7) total -= total /10; 

        }

        return total;
        
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public String getJenis() {
        return jenis;
    }

    public long getTarifPerhari() {
        return tarifPerhari;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }
}
