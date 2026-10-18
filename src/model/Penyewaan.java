package model;

public class Penyewaan {
    private final String id;
    private final String namaPenyewa;
    private final Kendaraan kendaraan;
    private final int lamaHari;
    private final long totalBiaya;
    private boolean aktif = true;

    public Penyewaan(String id, String namaPenyewa, Kendaraan kendaraan, int lamaHari, long totalBiaya) {
        this.id = id;
        this.namaPenyewa = namaPenyewa;
        this.kendaraan = kendaraan;
        this.lamaHari = lamaHari;
        this.totalBiaya = totalBiaya;
    }

    public String getId() {
        return id;
    }

    public String getNamaPenyewa() {
        return namaPenyewa;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public int getLamaHari() {
        return lamaHari;
    }

    public long getTotalBiaya() {
        return totalBiaya;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void selesai() {
        this.aktif = false;
        this.kendaraan.setTersedia(true);
    }
    
}
