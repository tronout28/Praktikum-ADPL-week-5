package model;

import java.util.ArrayList;
import java.util.List;

public class KendaraanRepositoryArrayList implements KendaraanRepository{

    private final List<Kendaraan> data = new ArrayList<>();

    @Override 
    public void tambah(Kendaraan kendaraan) {
        data.add(kendaraan);
    }

    @Override 
    public List<Kendaraan> semua() {
        return new ArrayList<>(data);
    }

    @Override 
    public Kendaraan cariByPlat(String platNomor) {
        for (Kendaraan k : data) {
            if (k.getPlatNomor().equalsIgnoreCase(platNomor)) {
                return k;
            }
        }
        return null;
    }

}
