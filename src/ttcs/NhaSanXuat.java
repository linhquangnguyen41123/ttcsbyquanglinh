
package QLVT_BY_LINH;

public class NhaSanXuat {
    private String MaNSX;
    private String TenNSX;
    private int TrangThai;
    public NhaSanXuat() {
    }

    public NhaSanXuat(String MaNSX, String TenNSX, int TrangThai) {
        this.MaNSX = MaNSX;
        this.TenNSX = TenNSX;
    }

    public String getMaNSX() {
        return MaNSX;
    }

    public void setMaNSX(String MaNSX) {
        this.MaNSX = MaNSX;
    }

    public String getTenNSX() {
        return TenNSX;
    }

    public void setTenNSX(String TenNSX) {
        this.TenNSX = TenNSX;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
   
    
   
    
}