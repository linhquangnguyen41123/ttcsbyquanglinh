/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demottcs;

/**
 *
 * @author nguye
 */
public class vatTu {
    private String maVT;
    private String tenVT;
    private String phanLoai;
    private String donViTinh;
    private int soLuongTon;
    private float giaBan;
    private float donGiaBan;
    private String maNSX;

    public vatTu() {
    }

    public vatTu(String maVT, String tenVT, String phanLoai, String donViTinh, int soLuongTon, float giaBan, float donGiaBan, String maNSX) {
        this.maVT = maVT;
        this.tenVT = tenVT;
        this.phanLoai = phanLoai;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.donGiaBan = donGiaBan;
        this.maNSX = maNSX;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public float getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(float donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public String getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(String maNSX) {
        this.maNSX = maNSX;
    }
    
    
    
}


