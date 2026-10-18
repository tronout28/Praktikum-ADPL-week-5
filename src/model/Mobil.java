package model;

//[OCP] Ekstensi dari Kendaraan: aturan biaya mobil tinggal di kelasnya sendiri.
public class Mobil extends Kendaraan {
        private static final long ASURANSI = 50000;

        public Mobil(String platNomor, String merk, long tarifPerHari) {
                super(platNomor, merk, tarifPerHari);
        }

        @Override
        public String getJenis() {
                return "MOBIL";
        }

        @Override
        public long hitungBiaya(int hari) {
                return getTarifPerHari() * hari + ASURANSI;
        }
}