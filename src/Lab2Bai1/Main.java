package Lab2Bai1;
import java.util.Scanner;
import java.util.Locale;
public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
                SinhVien sv1=new SinhVien();
		System.out.print("Nhap ma sinh vien 1: ");
		sv1.setMasv(sc.nextInt()); sc.nextLine();
		System.out.print("Nhap ten sinh vien 1: ");
		sv1.setTensv(sc.nextLine());
		System.out.print("Nhap diem li thuyet: ");
		sv1.setDiemLT(sc.nextFloat());
		System.out.print("Nhap diem thuc hanh: ");
		sv1.setDiemTH(sc.nextFloat());
        System.out.println(sv1);
        SinhVien sv2=new SinhVien();
		System.out.print("Nhap ma sinh vien 2: ");
		sv2.setMasv(sc.nextInt()); sc.nextLine();
		System.out.print("Nhap ten sinh vien 2: ");
		sv2.setTensv(sc.nextLine());
		System.out.print("Nhap diem li thuyet: ");
		sv2.setDiemLT(sc.nextFloat());
		System.out.print("Nhap diem thuc hanh: ");
		sv2.setDiemTH(sc.nextFloat());
        System.out.println(sv2);
        SinhVien sv3=new SinhVien();
		System.out.print("Nhap ma sinh vien 3: ");
		sv3.setMasv(sc.nextInt()); sc.nextLine();
		System.out.print("Nhap ten sinh vien 3: ");
		sv3.setTensv(sc.nextLine());
		System.out.print("Nhap diem li thuyet: ");
		sv3.setDiemLT(sc.nextFloat());
		System.out.print("Nhap diem thuc hanh: ");
		sv3.setDiemTH(sc.nextFloat());
        System.out.println(sv3);
         System.out.println("MSSV\t\tHOTEN\t\tDIEMLT\t\tDIEMTH\t\tDIEMTB");
         sv1.inSV();
         sv2.inSV();
         sv3.inSV();  

	}
}
