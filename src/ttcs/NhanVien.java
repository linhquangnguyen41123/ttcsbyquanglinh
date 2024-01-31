
package BT;

public class NhanVien extends DanhSachHoaDon {
    private   String maNV;
    private  String hotenNV;
    private  String phai;

    public NhanVien(String maNV, String hotenNV, String phai) {
        this.maNV = maNV;
        this.hotenNV = hotenNV;
        this.phai = phai;
    }

    public NhanVien() {
        
    }

    public String getMaNV() {
        return maNV;
    }

    public String getHotenNV() {
        return hotenNV;
    }

    public String getPhai() {
        return phai;
    }

    public void setHoNV(String hoNV) {
        this.hotenNV = hotenNV;
    }


    public void setPhai(String phai) {
        this.phai = phai;
    }
    
}

