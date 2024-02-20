/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import QLVT_BY_LINH.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import ttcs.KhachHang;

/**
 *
 * @author Anh Kiet
 */
public class DAOKhachHang {
    private Connection conn = new connectDB().getConnection();
    
    public void insertHoaDon(KhachHang kh){
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
}
