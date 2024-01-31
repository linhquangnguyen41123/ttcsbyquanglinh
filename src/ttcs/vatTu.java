/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demottcs;

/**
 *
 * @author nguye
 */
public class VatTu {
    private String MaVT;
    private String TenVT;
    private String PhanLoai;
    private String DonViTinh;
    private int SoLuongTon;
    private float DonGiaNhap;
    private float DonGiaBan;
    private String MaNSX;

    public VatTu() {
    }

    public VatTu(String MaVT, String TenVT, String PhanLoai, String DonViTinh, int SoLuongTon, float DonGiaNhap, float DonGiaBan, String MaNSX) {
        this.MaVT = MaVT;
        this.TenVT = TenVT;
        this.PhanLoai = PhanLoai;
        this.DonViTinh = DonViTinh;
        this.SoLuongTon = SoLuongTon;
        this.DonGiaNhap = DonGiaNhap;
        this.DonGiaBan = DonGiaBan;
        this.MaNSX = MaNSX;
    }

    public String getMaVT() {
        return MaVT;
    }

    public void setMaVT(String MaVT) {
        this.MaVT = MaVT;
    }

    public String getTenVT() {
        return TenVT;
    }

    public void setTenVT(String TenVT) {
        this.TenVT = TenVT;
    }

    public String getPhanLoai() {
        return PhanLoai;
    }

    public void setPhanLoai(String PhanLoai) {
        this.PhanLoai = PhanLoai;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public float getDonGiaNhap() {
        return DonGiaNhap;
    }

    public void setDonGiaNhap(float DonGiaNhap) {
        this.DonGiaNhap = DonGiaNhap;
    }

    public float getDonGiaBan() {
        return DonGiaBan;
    }

    public void setDonGiaBan(float DonGiaBan) {
        this.DonGiaBan = DonGiaBan;
    }

    public String getMaNSX() {
        return MaNSX;
    }

    public void setMaNSX(String MaNSX) {
        this.MaNSX = MaNSX;
    }

    
   
    
}


