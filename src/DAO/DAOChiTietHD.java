/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ttcs.CTHoaDon;
import ttcs.HoaDon;
import ttcs.VatTu;

/**
 *
 * @author Anh Kiet
 */
public class DAOChiTietHD {
    private Connection conn;
    public DAOChiTietHD(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=vtvp;"
                    + "username=sa;password=123;encrypt=false");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<CTHoaDon> getListCTHD(HoaDon hd){
        ArrayList<CTHoaDon> listCTHD = new ArrayList<>();
        String sql = "select * from dbo.CTHoaDon where MaHD = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hd.getSoHD());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                CTHoaDon cthd = new CTHoaDon();
                cthd.setMaHoaDon(rs.getString("MaHD"));
                cthd.setMaVatTu(rs.getString("MaVT"));
                cthd.setTenVatTu(rs.getString("TenVT"));
                cthd.setSoLuong(rs.getInt("SoLuong"));
                cthd.setDonGia( rs.getInt("DonGia"));
                
                listCTHD.add(cthd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTHD;
    }
    
    public void insertChiTietHD(CTHoaDon cthd){
        String sql = "insert into dbo.CTHoaDon(MaHD,MaVT,SoLuong,DonGia,TenVT) "
                + "values (?,?,?,?,?)";
        try {
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, cthd.getMaHoaDon());
            ps.setString(2, cthd.getMaVatTu());
            ps.setString(5, cthd.getTenVatTu());
            ps.setInt(3, cthd.getSoLuong());
            ps.setInt(4, cthd.getDonGia());
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void deleteChiTietHD(CTHoaDon cthd, HoaDon hd){
        String sqlDelete = "delete from dbo.CTHoaDon where MaHD = ? and MaVT = ?";
        
        try {
            PreparedStatement ps= conn.prepareStatement(sqlDelete);
            
            ps.setString(1, hd.getSoHD());
            ps.setString(2, cthd.getMaVatTu());
            ps.executeUpdate();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void updateTraSLT(CTHoaDon cthd, VatTu vt){
   
        String sqlUpdateSLT = "update dbo.VatTu set SoLuongTon = ? where MaVT = ?";
        try {
            
            PreparedStatement ps= conn.prepareStatement(sqlUpdateSLT);
            
            ps.setInt(1, cthd.getSoLuong()+vt.getSoLuongTon());
            ps.setString(2, cthd.getMaVatTu());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new DAOChiTietHD();
    }
}
