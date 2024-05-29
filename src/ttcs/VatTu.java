
package ttcs;

public class VatTu {
    private String MaVT;
    private String TenVT;
    private String PhanLoai;
    private String DonViTinh;
    private int SoLuongTon;
    private int DonGiaNhap;
    private int DonGiaBan;
    private String MaNSX;
    private int TrangThai;
    public VatTu() {
    }

    public VatTu(String MaVT, String TenVT, String PhanLoai, String DonViTinh, int SoLuongTon, int DonGiaNhap, int DonGiaBan, String MaNSX, int TrangThai) {
        this.MaVT = MaVT;
        this.TenVT = TenVT;
        this.PhanLoai = PhanLoai;
        this.DonViTinh = DonViTinh;
        this.SoLuongTon = SoLuongTon;
        this.DonGiaNhap = DonGiaNhap;
        this.DonGiaBan = DonGiaBan;
        this.MaNSX = MaNSX;
        this.TrangThai = TrangThai;
    }

    public String getMaVT() {
        return MaVT;
    }

    public void setMaVT(String MaVT) {
        this.MaVT = MaVT;
    }

    public String getTenVT() {
        return TenVT;
    }

    public void setTenVT(String TenVT) {
        this.TenVT = TenVT;
    }

    public String getPhanLoai() {
        return PhanLoai;
    }

    public void setPhanLoai(String PhanLoai) {
        this.PhanLoai = PhanLoai;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public int getDonGiaNhap() {
        return DonGiaNhap;
    }

    public void setDonGiaNhap(int DonGiaNhap) {
        this.DonGiaNhap = DonGiaNhap;
    }

    public int getDonGiaBan() {
        return DonGiaBan;
    }

    public void setDonGiaBan(int DonGiaBan) {
        this.DonGiaBan = DonGiaBan;
    }

    public String getMaNSX() {
        return MaNSX;
    }

    public void setMaNSX(String MaNSX) {
        this.MaNSX = MaNSX;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    
    
}