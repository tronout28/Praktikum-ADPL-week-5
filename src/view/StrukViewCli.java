package view;

import model.Penyewaan;

public class StrukViewCli implements StrukView {

    @Override 
       public void cetak(Penyewaan p) {
        System.out.println("================ Struk Penyewaan ==============");
        System.out.println("No Sewa         : " + p.getId());
        System.out.println("Nama Penyewa    : " + p.getNamaPenyewa());
        System.out.println(
                "Kendaraan       : " + p.getKendaraan().getMerk() + " " + " (" + p.getKendaraan().getPlatNomor() + ")");
        System.out.println("Jenis Kendaraan : " + p.getKendaraan().getJenis());
        System.out.println("Lama Sewa (hari): " + p.getLamaHari() + " hari");
        System.out.println("Total Biaya     : " + Format.rupiah(p.getTotalBiaya()));
        System.out.println("===============================================");
    }
}
