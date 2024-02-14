/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ttcs;

import java.util.Date;

/**
 *
 * @author Anh Kiet
 */
public class HoaDon {
    private String SoHD;
    private Date NgayLapHoaDon;
    private String LoaiHD;
    private String MaNV;
    private String MaKH;
    private float ThanhTien;
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


    public Date getNgayLapHoaDon() {
        return NgayLapHoaDon;
    }

    public void setNgayLapHoaDon(Date ngayLapHoaDon) {
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

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(float ThanhTien) {
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
