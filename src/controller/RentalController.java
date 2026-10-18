package controller;

import model.Penyewaan;
import model.PenyewaanRepository;
import model.Kendaraan;
import model.KendaraanRepository;

import view.RentalView;
import view.StrukView;
import view.Format;

public class RentalController {

    private final KendaraanRepository kendaraanRepo = new KendaraanRepository();
    private final PenyewaanRepository penyewaanRepo = new PenyewaanRepository();
    private final RentalView view = new RentalView();
    private final StrukView strukView = new StrukView();

    public void tambahDataAwal(Kendaraan kendaraan) {
        kendaraanRepo.tambah(kendaraan);
    }

    public void jalankan() {
        while (true) {
            view.tampilkanMenu();
            int pilihan = (int) view.bacaAngka("Pilih menu");
            switch (pilihan) {
                case 1 -> view.tampilkanDaftarKendaraan(kendaraanRepo.semua());
                case 2 -> tambahKendaraan();
                case 3 -> sewaKendaraan();
                case 4 -> kembalikanKendaraan();
                case 5 -> view.tampilkanRiwayat(penyewaanRepo.semua());
                case 0 -> {
                    view.tampilkanPesan("Terima kasih telah menggunakan aplikasi ini.");
                    return;
                }
                default -> view.tampilkanPesan("Pilihan tidak valid.");
            }
        }
    }

    public void tambahKendaraan() {
        String platNomor = view.bacaTeks("Masukkan Plat Nomor");
        if (platNomor.isEmpty() || kendaraanRepo.cariByPlat(platNomor) != null) {
            view.tampilkanPesan("Plat Nomor tidak valid atau sudah ada.");
            return;
        }

        String merk = view.bacaTeks("Masukkan Merk Kendaraan");
        String jenis = view.bacaTeks("Masukkan Jenis (MOBIL/MOTOR)");

        if (!jenis.equalsIgnoreCase("MOBIL") && !jenis.equalsIgnoreCase("MOTOR")) {
            view.tampilkanPesan("Jenis kendaraan harus MOBIL atau MOTOR.");
            return;
        }

        long tarifPerhari = view.bacaAngka("Masukkan Tarif Per Hari");
        if (tarifPerhari <= 0) {
            view.tampilkanPesan("Tarif per hari harus lebih dari 0.");
            return;
        }

        Kendaraan kendaraan = new Kendaraan(platNomor, merk, jenis, tarifPerhari);
        kendaraanRepo.tambah(kendaraan);
        view.tampilkanPesan("Kendaraan berhasil ditambahkan.");
    }

    public void sewaKendaraan() {
        String platNomor = view.bacaTeks("Masukkan Plat Nomor Kendaraan yang ingin disewa");
        Kendaraan kendaraan = kendaraanRepo.cariByPlat(platNomor);

        if (kendaraan == null || !kendaraan.isTersedia()) {
            view.tampilkanPesan("Kendaraan dengan plat nomor tersebut tidak ditemukan atau tidak tersedia.");
            return;
        }

        String nama = view.bacaTeks("Masukkan Nama Penyewa");
        int hari = (int) view.bacaAngka("Masukkan Lama Sewa (dalam hari)");
        if (hari < 1) {
            view.tampilkanPesan("Lama sewa minimal 1 hari.");
            return;
        }

        long totalBiaya = kendaraan.hitungBiayaSewa(hari);
        Penyewaan sewa = new Penyewaan(penyewaanRepo.buatIdBaru(), nama, kendaraan, hari, totalBiaya);
        penyewaanRepo.tambah(sewa);
        kendaraan.setTersedia(false);
        strukView.cetak(sewa);

    }

    public void kembalikanKendaraan() {
        String platNomor = view.bacaTeks("Masukkan Plat Nomor Kendaraan yang dikembalikan");

        Penyewaan sewa = penyewaanRepo.cariAktifByPlat(platNomor);

        if (sewa == null) {
            view.tampilkanPesan("Tidak ada penyewaan aktif untuk kendaraan dengan plat nomor tersebut.");
            return;
        }

        sewa.selesai();
        sewa.getKendaraan().setTersedia(true);
        view.tampilkanPesan("Kendaraan " + sewa.getKendaraan().getMerk() + " berhasil dikembalikan. Total biaya sewa: "
                + Format.rupiah(sewa.getTotalBiaya()));

    }

}
