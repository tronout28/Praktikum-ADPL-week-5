package model;

/* 
[SRP]: 
Kendaraan hanya bertanggung jawab atas DATA Kendaraan dan aturan bisnis miliknya sendiri (hitung biaya sewa).

INFORMASI:
Masalah dari hanya menggunakan tahap SRP adalah setiap jenis baru memaksa kita membongkar/mengubah logic di method hitungBiaya() dalam blok if-else
dan validasi properti jenis(MOBIL/MOTOR) di Controller.
Solusinya Class Kendaraan dijadikan abstract, dan setiap jenis punya kelas sendiri.
Otomatis class Kendaraan tidak akan digunakan lagi.
*/
/*
public class Kendaraan {
        private final String platNomor;
        private final String merk;
        private final String jenis;
        private final long tarifPerHari;
        private boolean tersedia = true;

        public Kendaraan (
                String platNomor, 
                String merk, 
                String jenis, 
                long tarifPerHari
        ) {
                this.platNomor = platNomor;
                this.merk = merk;
                this.jenis = jenis;
                this.tarifPerHari = tarifPerHari;
        }
        //[BELUM OCP] if-else per jenis masih ada disini
        //setiap ada jenis baru -> kelas ini harus diubah 
        public long hitungBiaya(int hari) {
                long total = tarifPerHari * hari;

                if (jenis.equals("MOBIL")) {
                        total += 50000; //Asuransi
                } else if(jenis.equals("MOTOR")) {
                        if (hari >= 7) total -= total / 10; //Diskon 10%
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
        public long getTarifPerHari() {
                return tarifPerHari;
        } 
        public boolean isTersedia() {
                return tersedia;
        }
        public void setTersedia(boolean tersedia) {
                this.tersedia = tersedia;
        }
}
*/

//[SRP] + [OCP]:
//Kelas dibuat abstract secara tertutup untuk modifikasi (jadi tidak lagi blok if-else untuk menentukan jenis objeknya),
//dengan abstract sifatnya jadi terbuka untuk di extend, jika ada jenis baru maka subclass baru.
public abstract class Kendaraan {
        private final String platNomor;
        private final String merk;
        private final long tarifPerHari;
        private boolean tersedia = true;

        public Kendaraan(String platNomor, String merk, long tarifPerHari) {
                this.platNomor = platNomor;
                this.merk = merk;
                this.tarifPerHari = tarifPerHari;
        }

        //[OCP] Setiap subclass menentukan sendiri nama jenis & cara hitung biayanya.
        public abstract String getJenis();

        //[LSP] Kontrak yang wajib dipatuhi semua subclass:
        //      - Prasyarat: hari >= getMinimalHari()
        //      - Hasil    : biaya > 0, tanpa melempar exception
        //      Subclass boleh menghitung dengan cara berbeda.
        public abstract long hitungBiaya(int hari);

        //[LSP] Aturan minimal sewa dijadikan bagian dari kontrak kelas induk.
        //      Default 1 hari; subclass boleh mengubah nilainya, dan pemanggil
        //      (Controller) cukup bertanya ke sini tanpa perlu tahu jenis kendaraannya.
        public int getMinimalHari() {
                return 1;
        }

        public String getPlatNomor() { return platNomor; }
        public String getMerk() { return merk; }
        public long getTarifPerHari() { return tarifPerHari; }
        public boolean isTersedia() { return tersedia; }
        public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
}