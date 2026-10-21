package controller;

import model.Penyewaan;
import model.PenyewaanRepository;
import model.WajibJaminan;
import model.WajibSIM;

import java.util.List;

import model.Kendaraan;
import model.KendaraanFactory;
import model.KendaraanRepository;

import view.RentalView;
import view.StrukView;
import view.Format;

public class RentalController {

    private final KendaraanRepository kendaraanRepo;
    private final PenyewaanRepository penyewaanRepo;
    private final RentalView view;
    private final StrukView strukView;
    private final KendaraanFactory kendaraanFactory;

    public RentalController(KendaraanRepository kendaraanRepository,
            PenyewaanRepository penyewaanRepository,
            RentalView view,
            StrukView strukView,
            KendaraanFactory kendaraanFactory) {
        this.kendaraanRepo = kendaraanRepository;
        this.penyewaanRepo = penyewaanRepository;
        this.view = view;
        this.strukView = strukView;
        this.kendaraanFactory = kendaraanFactory;
    }

    public void tambahDataAwal(Kendaraan kendaraan) {
        kendaraanRepo.tambah(kendaraan);
    }

    public void jalankan() {
        while (true) {
            view.tampilkanMenu();
            String pilihan = view.tampilkanMenu();
            switch (pilihan) {
                case "1" -> view.tampilkanDaftarKendaraan(kendaraanRepo.semua());
                case "2" -> tambahKendaraan();
                case "3" -> sewaKendaraan();
                case "4" -> kembalikanKendaraan();
                case "5" -> view.tampilkanRiwayat(penyewaanRepo.semua());
                case "0" -> {
                    view.tampilkanPesan("Terima kasih telah menggunakan aplikasi ini.");
                    return;
                }
                default -> view.tampilkanPesan("Pilihan tidak valid.");
            }
        }
    }

    public void tambahKendaraan() {
        String plat = view.bacaTeks("Plat Nomor");
        if (plat.isEmpty() || kendaraanRepo.cariByPlat(plat) != null) {
            view.tampilkanPesan("Plat nomor kosong atau sudah terdaftar.");
            return;
        }
        String merk = view.bacaTeks("Merk");
        // [OCP] Daftar jenis diambil dari Factory, bukan ditulis manual di Controller.
        String jenis = view.bacaTeks("Jenis (" + String.join("/", kendaraanFactory.daftarJenis()) + ")").toUpperCase();
        if (!kendaraanFactory.daftarJenis().contains(jenis)) {
            view.tampilkanPesan("Jenis tidak dikenal.");
            return;
        }
        long tarif = view.bacaAngka("Tarif per hari");
        if (tarif <= 0) {
            view.tampilkanPesan("Tarif harus lebih dari 0.");
            return;
        }
        kendaraanRepo.tambah(kendaraanFactory.buat(jenis, plat, merk, tarif));
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

        if (kendaraan instanceof WajibSIM wajibSIM) {
            String sim = view.bacaTeks("Golongan SIM Penyewa");

            if (sim.equalsIgnoreCase(wajibSIM.getGolonganSIM())) {
                view.tampilkanPesan(kendaraan.getMerk()
                        + "Wajib SIM"
                        + wajibSIM.getGolonganSIM());
            }
            ;
            return;

        }
        int hari = (int) view.bacaAngka("Masukkan Lama Sewa (dalam hari)");

        if (kendaraan instanceof WajibJaminan wajibJaminan) {
            if (!view.konfirmasiUangJaminan(wajibJaminan.getUangJaminan())) {
                view.tampilkanPesan("Penyewaan dibatalkan");
                return;
            }
        }
        if (hari < kendaraan.getMinimalHari()) {
            view.tampilkanPesan(kendaraan.getMerk() + " minimal disewa "
                    + kendaraan.getMinimalHari() + " hari.");
            return;
        }

        long totalBiaya = kendaraan.hitungBiaya(hari);
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
