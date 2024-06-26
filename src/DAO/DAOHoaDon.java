/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import QLVT_BY_LINH.connectDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ttcs.HoaDon;

/**
 *
 * @author Anh Kiet
 */
public class DAOHoaDon {
    private Connection conn = new connectDB().getConnection();
    
    public ArrayList<HoaDon> getListHD(){
        ArrayList<HoaDon> listHD = new ArrayList<>();
        String sql = "select * from dbo.HoaDon";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                HoaDon hd = new HoaDon();
                hd.setSoHD(rs.getString("MaHD"));
                hd.setNgayLapHoaDon(rs.getDate("NgayLap"));
                hd.setLoaiHD(rs.getString("Loai"));
                hd.setTenNV(rs.getString("TenNV"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setTenKH(rs.getString("TenKH"));
                hd.setThanhTien(rs.getInt("ThanhTien"));
                hd.setTrangThai(rs.getInt("TrangThai"));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
    
    public static String toMysqlDateStr(java.util.Date date) {
        String dateForMySql = "";
        if (date == null) {
            dateForMySql = null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateForMySql = sdf.format(date);
        }

        return dateForMySql;    
    }
    public void insertHoaDon(HoaDon hd){
        String sql = "insert into dbo.HoaDon(MaHD,NgayLap,Loai,TenNV,MaNV,MaKH,TenKH,ThanhTien,TrangThai) "
                + "values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, hd.getSoHD());
            ps.setString(2,  toMysqlDateStr(hd.getNgayLapHoaDon()));
            ps.setString(3, hd.getLoaiHD());
            ps.setString(4, hd.getTenNV());
            ps.setString(5, hd.getMaNV());
            ps.setString(6, hd.getMaKH());
            ps.setString(7, hd.getTenKH());
            ps.setInt(8,hd.getThanhTien());
            ps.setInt(9, hd.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void updateGiaTriHD(HoaDon hd, int thanhTien){
   
        String sqlUpdateSLT = "update dbo.HoaDon set ThanhTien = ? where MaHD = ?";
        try {
            
            PreparedStatement ps= conn.prepareStatement(sqlUpdateSLT);
            
            ps.setInt(1, thanhTien);
            ps.setString(2, hd.getSoHD());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateTrangThaiHD(HoaDon hd){
   
        String sqlUpdateSLT = "update dbo.HoaDon set TrangThai = ? where MaHD = ?";
        try {
            
            PreparedStatement ps= conn.prepareStatement(sqlUpdateSLT);
            
            ps.setInt(1, hd.getTrangThai());
            ps.setString(2, hd.getSoHD());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new DAOHoaDon();
    } 
}
