package Lab2Bai1;
import java.util.Scanner;
import java.util.Locale;
public class SinhVien {
  private int masv;
  private String tensv;
  private Float diemLT;
  private Float diemTH;
public SinhVien(int masv, String tensv, float diemLT, float diemTH) {
	this.masv = masv;
	this.tensv = tensv;
	this.diemLT = diemLT;
	this.diemTH = diemTH;
}

public SinhVien() {
}

public int getMasv() {
	return masv;
}
public void setMasv(int masv) {
	this.masv = masv;
}
public String getTensv() {
	return tensv;
}
public void setTensv(String tensv) {
	this.tensv = tensv;
}
public float getDiemLT() {
	return diemLT;
}
public void setDiemLT(float diemLT) {
	this.diemLT = diemLT;
}
public float getDiemTH() {
	return diemTH;
}
public void setDiemTH(float diemTH) {
	this.diemTH = diemTH;
}
public double tinhdiemTB() {
	return (this.diemLT+this.diemTH)/2;
}
public String ToString() {
	return masv+"-"+tensv+"diem trung binh"+ tinhdiemTB();
}
public void inSV() {
    Locale locale = new Locale("vi", "VN");
    System.out.printf(locale, "%-10d%-20s%-12.2f%-12.2f%-12.2f\n", masv, tensv, diemLT, diemTH, tinhdiemTB());
}

}
  
