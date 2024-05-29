/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import QLVT_BY_LINH.NhaSanXuat;
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
public class DAONhaSanXuat {
    private Connection conn = new connectDB().getConnection();
    
    public ArrayList<NhaSanXuat> getListNSX(){
        ArrayList<NhaSanXuat> listNSX = new ArrayList<>();
        String sql = "select * from dbo.NhaSanXuat";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                NhaSanXuat nsx = new NhaSanXuat();
                nsx.setMaNSX(rs.getString("MaNSX"));
                nsx.setTenNSX(rs.getString("TenNSX"));
                nsx.setTrangThai(rs.getInt("TrangThai"));
                
                listNSX.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNSX;
    }
    
    public void updateTrangThaiNSX(String maNSX){
   
        String sqlUpdateSLT = "update dbo.NhaSanXuat set TrangThai = ? where MaNSX = ?";
        try {
            
            PreparedStatement ps= conn.prepareStatement(sqlUpdateSLT);
            
            ps.setBoolean(1, true);
            ps.setString(2, maNSX);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
