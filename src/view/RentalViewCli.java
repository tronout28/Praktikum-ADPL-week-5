package view;

import java.util.List;
import java.util.Scanner;

import model.Kendaraan;
import model.Penyewaan;

public class RentalViewCli implements RentalView {

    private final Scanner input = new Scanner(System.in);

    @Override
    public String tampilkanMenu() {
        System.out.println("======== RENTAL KENDARAAN =======");
        System.out.println("1. Lihat Daftar Kendaraan");
        System.out.println("2. Tambah Kendaraan");
        System.out.println("3. Sewa Kendaraan");
        System.out.println("4. Kembalikan Kendaraan");
        System.out.println("5. Riwayat Pesanan");
        System.out.println("0. Keluar");
        return bacaTeks("Pilih menu");
    }

    @Override
    public void tampilkanDaftarKendaraan(List<Kendaraan> daftar) {
        System.out.println("======== Daftar Kendaraan ===========");
        if (daftar.isEmpty()) {
            System.out.println("Belum ada kendaraan yang tersedia.");
            return;
        } else {
            for (Kendaraan k : daftar) {
                System.out.println(
                        "Plat Nomor: " + k.getPlatNomor() +
                                " | Merk: " + k.getMerk() +
                                " | Jenis: " + k.getJenis() +
                                " | Tarif Per Hari: " + Format.rupiah(k.getTarifPerHari()) +
                                " | Tersedia: " + (k.isTersedia() ? "Ya" : "Tidak"));
            }
        }
    }

    @Override
    public void tampilkanRiwayat(List<Penyewaan> daftar) {
        System.out.println("======== Riwayat Penyewaan ===========");
        if (daftar.isEmpty()) {
            System.out.println("Belum ada riwayat penyewaan.");
            return;
        } else {
            for (Penyewaan p : daftar) {
                System.out.println(
                        "ID: " + p.getId() +
                                " | Nama Penyewa: " + p.getNamaPenyewa() +
                                " | Kendaraan: " + p.getKendaraan().getMerk() +
                                " (" + p.getKendaraan().getPlatNomor() + ") | Lama Sewa: " + p.getLamaHari() +
                                " hari | Total Biaya: " + Format.rupiah(p.getTotalBiaya()) +
                                " | Aktif: " + (p.isAktif() ? "Berjalan" : "Selesai"));
            }
        }
    }

    @Override
    public String bacaTeks(String label) {
        System.out.print(label + ": ");
        return input.nextLine().trim();
    }

    public long bacaAngka(String label) {
        while (true) {
            String teks = bacaTeks(label);
            try {
                return Long.parseLong(teks);
            } catch (NumberFormatException e) {
                tampilkanPesan("Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    @Override
    public void tampilkanPesan(String pesan) {
        System.out.println(">> " + pesan);
    }

    @Override
    public boolean konfirmasiUangJaminan(long nominal) {
        tampilkanPesan("Kendaraan ini memerlukan uang jaminan sebesar "
                + Format.rupiah(nominal)
                + " (akan dikembalikan saat kendaraan selesai disewa).");

        return bacaTeks("Setuju? (Y/N)").equalsIgnoreCase("Y");
    }
}
