package view;

import java.util.List;

import model.Kendaraan;
import model.Penyewaan;

public interface RentalView {
     String tampilkanMenu();

     void tampilkanDaftarKendaraan(List<Kendaraan> daftar);
    
     void tampilkanRiwayat(List<Penyewaan> daftar);

     String bacaTeks(String label);

     long bacaAngka(String label);

     void tampilkanPesan(String pesan);

     boolean konfirmasiUangJaminan(long nominal);
} 