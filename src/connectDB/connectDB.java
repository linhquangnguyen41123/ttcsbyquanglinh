
package QLVT_BY_LINH;
import java.sql.*;
public class connectDB {
    public Connection getConnection(){
        Connection  conn= null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=vtvp;"
                    + "username=sa;password=123;encrypt=false");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
}
    

