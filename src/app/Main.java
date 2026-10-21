package app;

import model.KendaraanFactory;
import model.KendaraanFactoryDefault;
import model.KendaraanRepository;
import model.KendaraanRepositoryArrayList;
import model.Minibus;
import model.Mobil;
import model.MobilPremium;
import model.Motor;
import model.PenyewaanRepository;
import model.PenyewaanRepositoryArrayList;
import view.RentalView;
import view.RentalViewCli;
import view.StrukView;
import view.StrukViewCli;
import controller.RentalController;

public class Main {
        public static void main(String[] args) {

                KendaraanRepository kendaraanRepository = new KendaraanRepositoryArrayList();
                PenyewaanRepository penyewaanRepository = new PenyewaanRepositoryArrayList();
                KendaraanFactory kendaraanFactory = new KendaraanFactoryDefault();
                RentalView view = new RentalViewCli();
                StrukView strukView = new StrukViewCli();

                RentalController controller = new RentalController(
                                kendaraanRepository,
                                penyewaanRepository,
                                view,
                                strukView,
                                kendaraanFactory);

                // Data awal/dummy data
                controller.tambahDataAwal(new Mobil(
                                "L 1234 AB",
                                "Toyota Avanza",
                                350000));
                controller.tambahDataAwal(new Mobil(
                                "L 2468 CD",
                                "Honda Mobilio",
                                300000));
                controller.tambahDataAwal(new Motor(
                                "L 5678 EF",
                                "Honda Vario",
                                75000));
                controller.tambahDataAwal(new Minibus(
                                "L 7777 GH",
                                "Toyota Hiace",
                                900000));
                controller.tambahDataAwal(new MobilPremium(
                                "L 9999 VIP",
                                "Toyota Alphard",
                                1500000));

                controller.jalankan();
        }
}
