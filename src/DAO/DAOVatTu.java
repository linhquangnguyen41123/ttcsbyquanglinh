/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import QLVT_BY_LINH.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ttcs.VatTu;

/**
 *
 * @author Anh Kiet
 */
public class DAOVatTu {
    private Connection conn = new connectDB().getConnection();
    
    public ArrayList<VatTu> getListVT(){
        ArrayList<VatTu> listVT = new ArrayList<>();
        String sql = "select * from dbo.VatTu";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                VatTu vt = new VatTu();
                vt.setMaVT(rs.getString("MaVT"));
                vt.setTenVT(rs.getString("TenVT"));
                vt.setDonViTinh(rs.getString("DonViTinh"));
                vt.setDonGiaNhap(rs.getInt("DonGiaNhap"));
                vt.setDonGiaBan(rs.getInt("DonGiaBan"));
                vt.setSoLuongTon(rs.getInt("SoLuongTon"));
                vt.setPhanLoai(rs.getString("PhanLoai"));
                vt.setTrangThai(rs.getInt("TrangThai"));
                
                listVT.add(vt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVT;
    }
    public void updateTrangThaiVT(String maVatTu){
   
        String sqlUpdateSLT = "update dbo.VatTu set TrangThai = ? where MaVT = ?";
        try {
            
            PreparedStatement ps= conn.prepareStatement(sqlUpdateSLT);
            
            ps.setBoolean(1, true);
            ps.setString(2, maVatTu);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
