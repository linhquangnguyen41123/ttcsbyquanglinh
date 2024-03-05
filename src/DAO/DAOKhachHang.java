/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import QLVT_BY_LINH.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttcs.HoaDon;
import ttcs.KhachHang;

/**
 *
 * @author Anh Kiet
 */
public class DAOKhachHang {
    private Connection conn = new connectDB().getConnection();
    
    public void insertKhachHang(KhachHang kh){
        String sql = "insert into dbo.KhachHang(MaKH,TenKH)"
                + "values (?,?)";
        try {
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public int getRowCount(){
        try {
            String sql = "select count(*) from dbo.KhachHang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOKhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
