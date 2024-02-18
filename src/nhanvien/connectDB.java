
package nhanvien;
import java.sql.*;
public class connectDB {
    public Connection getConnection(){
        Connection  conn= null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost;database=dangnhap";
            String user = "sa";
             String pwd = "12345";
         conn= DriverManager.getConnection(url, user, pwd);
         if(conn!=null)
             System.out.println("ket noi thanh cong");
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return conn;
    }
    
}
