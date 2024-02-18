/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ttcs;

import java.sql.Date;

/**
 *
 * @author Anh Kiet
 */
public class HoaDon {
    private String SoHD;
    private String NgayLapHoaDon;
    private String LoaiHD;
    private String MaNV;
    private String MaKH;
    private int ThanhTien;
    private int TrangThai;

    public HoaDon(){
        this.TrangThai = 0;
    }

    public String getSoHD() {
        return SoHD;
    }

    public void setSoHD(String SoHD) {
        this.SoHD = SoHD;
    }


    public String getNgayLapHoaDon() {
        return NgayLapHoaDon;
    }

    public void setNgayLapHoaDon(String NgayLapHoaDon) {
        this.NgayLapHoaDon = NgayLapHoaDon;
    }

    public String getLoaiHD() {
        return LoaiHD;
    }

    public void setLoaiHD(String loaiHD) {
        this.LoaiHD = loaiHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

}
