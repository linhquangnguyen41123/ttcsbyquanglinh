/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ttcs;

/**
 *
 * @author nguye
 */

public class vatTu {
    private String maVT;
    private String tenVT;
    private String phanLoai;
    private String donViTinh;
    private String soLuongTon;
    private String giaBan;

    public vatTu() {
    }

    public vatTu(String maVT, String tenVT, String phanLoai, String donViTinh, String soLuongTon, String giaBan) {
        this.maVT = maVT;
        this.tenVT = tenVT;
        this.phanLoai = phanLoai;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
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

    public String getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(String soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }
}

