package app;

import model.Kendaraan;
import controller.RentalController;

public class Main {
    public static void main(String[] args) {
        RentalController c = new RentalController();

        c.tambahDataAwal(new Kendaraan("AB 1234 CD", "Toyota", "MOBIL", 500000));
        c.tambahDataAwal(new Kendaraan("EF 5678 GH", "Honda", "MOTOR", 200000));
        c.jalankan();

    }
}
