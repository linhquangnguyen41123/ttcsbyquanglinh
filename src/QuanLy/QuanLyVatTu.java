/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QuanLy;

import DAO.DAOChiTietHD;
import DAO.DAOHoaDon;
import DAO.DAOKhachHang;
import DAO.DAOVatTu;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import ttcs.CTHoaDon;
import ttcs.HoaDon;
import ttcs.KhachHang;
import ttcs.VatTu;

/**
 *
 * @author Anh Kiet
 */
public class QuanLyVatTu extends javax.swing.JFrame {
    private String maNV;
    private String hoTen;
    private String vaiTro;
    
    public void setThongTinNhanVien(String maNV, String hoTen,String vaiTro) {
    this.maNV = maNV;
    this.hoTen = hoTen;
    this.vaiTro = vaiTro;

    // Hiển thị thông tin
    txt_MaNV.setText(this.maNV);
    txt_username.setText(this.hoTen);
    role1.setText(this.vaiTro);
    }
    
    private List<VatTu> listvt;

    private List<HoaDon> listhd;
    
    private List<CTHoaDon> listCTHD;

    public List<VatTu> getListvt() {
        return listvt;
    }

    public List<HoaDon> getListhd() {
        return listhd;
    }

    public List<CTHoaDon> getListCTHD() {
        return listCTHD;
    }
    
   
    
    public HoaDon addHoaDon(){
        HoaDon hd = new HoaDon();
        hd.setSoHD("HD"+createMa());     
        
        Date date = jDateNgayLap.getDate();
        if(date == null){
            return null;
        }else{
            hd.setNgayLapHoaDon(date);
            System.out.println("ngay lap hoa don:" + hd.getNgayLapHoaDon());
        }

        if(rbtnNhap.isSelected()){
            hd.setLoaiHD("Nhập"); 
        } else if(rbtnxuat.isSelected()){
            hd.setLoaiHD("Xuất");   
        }        
        
        hd.setMaNV(txt_MaNV.getText());
        hd.setTenNV(chuanHoaTen(txt_username.getText()));
        hd.setMaKH("KH"+createMa());
        hd.setTenKH(chuanHoaTen(txtTenKH.getText()));
        if(this.checkNgay()== false){
            JOptionPane.showMessageDialog(this, "Ngày lập hóa đơn không được lớn hơn ngày hiện tại!!!",
                "Thông báo ngày lập hóa đơn", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        if(hd.getLoaiHD() == null){
            return null;
        }
        return hd;
    }
    
    public String createMa(){
        DefaultTableModel model = (DefaultTableModel)tbldshd.getModel();
        listhd = new DAOHoaDon().getListHD();
        
        String s = "" + (tbldshd.getRowCount() + 1);
        return s;
        
    }
 
     public String chuanHoa(String str) {
        str = str.trim();
        //xoa khoang trang 2 dau
        str = str.replaceAll("\\s+", " ");
        return str;
    }
 
    public String chuanHoaTen(String str) {
        str = chuanHoa(str);
        String temp[] = str.split(" ");
        str = ""; 
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1).toLowerCase();
            if (i < temp.length - 1) 
                str += " ";
        }
        return str;
    }
    
    public boolean checkNgay(){            
        Date date = jDateNgayLap.getDate();
        
        Date dateNow = new Date();
        if(date.compareTo(dateNow) >= 1){          
           return false;
        }
        return true;
       
    }

    public boolean TraHangChiTietHoaDon() throws ParseException{
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        
        
        //Calendar: Ngay mua hang
        Date dateMua = hd.getNgayLapHoaDon();
        Calendar cMua = Calendar.getInstance();
        cMua.setTime(dateMua);
        cMua.add(Calendar.DATE, 3);
        
        //Calendar: hien tai
        Date dateNow = new Date();
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(dateNow);
        if(hd.getLoaiHD().equals("Xuất")){
            if(cMua.compareTo(cNow) == 1 || cMua.compareTo(cNow) == 0){
                JOptionPane.showMessageDialog(this, "Đã trả hàng thành công!!!");
                return true;
                
            }else 
            {
                JOptionPane.showMessageDialog(this, "Đã quá 3 ngày không thể trả hàng!!!",
                    "Thông báo trả hàng", JOptionPane.ERROR_MESSAGE);
                return false;
            }
          
        }else
        {
            JOptionPane.showMessageDialog(this, "Hóa đơn nhập thuộc chính sách đổi trả của nơi sản xuất!!!",
                    "Thông báo trả hàng", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    }
      

  
    public void ShowTableHoaDon(){
        DefaultTableModel model = (DefaultTableModel)tbldshd.getModel();
        listhd = new DAOHoaDon().getListHD();
        model.setRowCount(0);
        //Duyet qua danh sach 
        for(HoaDon hd : listhd){
            Object[] row = new Object[]{tbldshd.getRowCount()+1, hd.getSoHD() , hd.getNgayLapHoaDon(), hd.getLoaiHD()
                , hd.getTenNV(),hd.getTenKH(), hd.getThanhTien(), hd.getTrangThai()};
            model.addRow(row);
        }
        JTableHeader header = tbldshd.getTableHeader();
        header.setForeground(Color.BLUE);
        header.setFont(new Font("Default", Font.BOLD, 12));
    }
    public void showCTHD(){
        int ind = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(ind);
        
        int index = tbldsvt.getSelectedRow();
        VatTu vt = listvt.get(index);

        txtMaVT.setText(vt.getMaVT());
        txtTenVT.setText(vt.getTenVT());
        
        if (hd.getLoaiHD().equals("Xuất")) {
            txtDonGia.setText(String.valueOf(vt.getDonGiaBan()));
        }
        else if(hd.getLoaiHD().equals("Nhập")){
        txtDonGia.setText(String.valueOf(vt.getDonGiaNhap()));
        }
    }

    
    public void showTableVT(){
        DefaultTableModel modelVT = (DefaultTableModel) tbldsvt.getModel();
        listvt = new DAOVatTu().getListVT();
        modelVT.setRowCount(0);
        for(VatTu vt : listvt){
            Object[] row = new Object[]{tbldsvt.getRowCount()+1, vt.getMaVT() , vt.getTenVT(), vt.getDonViTinh()
                    ,vt.getDonGiaNhap(), vt.getDonGiaBan(), vt.getSoLuongTon()};
                modelVT.addRow(row);
        }
    }
    
    public void ShowTableCTHD(){
        int index = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(index);
        
        DefaultTableModel modelCTHD = (DefaultTableModel) tblCTHD.getModel();
        listCTHD = new DAOChiTietHD().getListCTHD(hd);
        modelCTHD.setRowCount(0);
        
       
        for(CTHoaDon cthd : listCTHD){

            Object[] row = new Object[]{tblCTHD.getRowCount()+1, cthd.getMaVatTu() , cthd.getTenVatTu(), cthd.getSoLuong()
                , cthd.getDonGia()};
            modelCTHD.addRow(row);

        }
        int thanhTien = 0;
        for (int i = 0; i < tblCTHD.getRowCount() ; i++) {
            thanhTien = thanhTien + (Integer.parseInt(tblCTHD.getValueAt(i, 4).toString())
                    * Integer.parseInt(tblCTHD.getValueAt(i, 3).toString()));
        }
        hd.setThanhTien(thanhTien);
        lblThanhTien.setText(convertCunrrency(String.valueOf(hd.getThanhTien()))+" VND");
        new DAOHoaDon().updateGiaTriHD(hd, hd.getThanhTien());
        this.ShowTableHoaDon();
        
    }
    
    
    public CTHoaDon addChiTietHoaDon(){
        int index = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(index);
        
        CTHoaDon cthd = new CTHoaDon();
        cthd.setMaHoaDon(hd.getSoHD());
        cthd.setTenVatTu(txtTenVT.getText());
        cthd.setMaVatTu(txtMaVT.getText());
        cthd.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        cthd.setDonGia(Integer.parseInt(txtDonGia.getText()));    
        new DAOVatTu().updateTrangThaiVT(txtMaVT.getText());
        return cthd;
    
    }
    public boolean UpdateSoLuongTon(){
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        
        int indexVT = tbldsvt.getSelectedRow();
        VatTu vt = listvt.get(indexVT);
        
        boolean check = false;
        int slt = 0;
        
        if(hd.getLoaiHD().equals("Nhập")){
            slt = vt.getSoLuongTon() + Integer.parseInt(txtSoLuong.getText());
            check = true;
        }
        else if(hd.getLoaiHD().equals("Xuất")){
            if(vt.getSoLuongTon() < Integer.parseInt(txtSoLuong.getText())){
                //slt ko du
                slt = vt.getSoLuongTon();
                check = false;
            }else
            {
                slt = vt.getSoLuongTon() - Integer.parseInt(txtSoLuong.getText());
                check = true;
            }
            
        }
        
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=vtvp;"
                    + "username=sa;password=123;encrypt=false");

            // Create an SQL update statement
            String sql = "update dbo.VatTu set SoLuongTon = ? where MaVT = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set values to the statement
            ps.setString(1, String.valueOf(slt));
            ps.setString(2, vt.getMaVT());
            
            ps.executeUpdate();
            

        } catch (Exception e) {
            e.printStackTrace();

        }
        return check;
    }
    
    public void printInvoice(){
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        hd.setTrangThai(1);
        
        jTextPane1.setText("                                  Văn phòng phẩm HKL\n");
        jTextPane1.setText(jTextPane1.getText() + "                          22A Lê Văn Việt - Q9.TPHCM" + "\n");  
        jTextPane1.setText(jTextPane1.getText() + "                                      ------------------" + "\n");
        jTextPane1.setText(jTextPane1.getText() + "                                    Hóa đơn " +hd.getLoaiHD()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Số hóa đơn: " +hd.getSoHD()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Tên nhân viên: " +hd.getTenNV()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Ngày lập hóa đơn: " +hd.getNgayLapHoaDon()+ "\n");
        if(hd.getTenKH() != null){
            jTextPane1.setText(jTextPane1.getText() + "Tên khách hàng: " +hd.getTenKH()+ "\n");
        }
        jTextPane1.setText(jTextPane1.getText() + "-------------------------------------------------------------------------------------" + "\n");
        String sf1 = String.format("%-8s%-17s%-15s%-15s%-15s", "STT","Tên sản phẩm","SL","Đơn giá","Thành tiền");
        jTextPane1.setText(jTextPane1.getText() + sf1 + "\n");
        DefaultTableModel modelCTHD = (DefaultTableModel) tblCTHD.getModel();
        for(int i = 0; i < tblCTHD.getRowCount(); i++){
            String stt = modelCTHD.getValueAt(i, 0).toString();
            String tenvt = modelCTHD.getValueAt(i, 2).toString();
            String sl = modelCTHD.getValueAt(i, 3).toString();
            String dongia = modelCTHD.getValueAt(i, 4).toString();
            String sf2 = String.format("%-11s%-23s%4s%18s%20s", stt, tenvt, sl, convertCunrrency(dongia), convertCunrrency(String.valueOf((Integer.valueOf(sl)*Integer.valueOf(dongia)))));
            jTextPane1.setText(jTextPane1.getText() +sf2 + "\n");
        }
        jTextPane1.setText(jTextPane1.getText() + "-------------------------------------------------------------------------------------" + "\n");
        String sf3 = String.format("Tổng hóa đơn: %60s", lblThanhTien.getText());
        jTextPane1.setText(jTextPane1.getText() + sf3 + "\n\n");
        if(hd.getLoaiHD().equals("Xuất")){
            jTextPane1.setText(jTextPane1.getText() + "\t      Cảm ơn quý khách!!!" + "\n");
            jTextPane1.setText(jTextPane1.getText() + "Vui lòng đổi trả hàng sau 3 ngày kể từ ngày xuất hóa đơn!!!" + "\n");
        }
        
        new DAOHoaDon().updateTrangThaiHD(hd);
    }
    
    public void resettxtVTHD(){
        txtMaVT.setText("");
        txtTenVT.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }      
    
    public String convertCunrrency(String s){
        StringBuilder sb = new StringBuilder(s);
        System.out.println("length S="+  s.length()/3);
        System.out.println("length SB="+  sb.length()/3);
        for (int i = 1; i < (float)s.length()/3; i++) {
            System.out.println("i=" + i);
            System.out.println("insert=" + (s.length()-3*i));
            sb.insert(s.length()-3*i, ".");
        }
        return sb.toString();
    }

   
    public QuanLyVatTu() {
        
        initComponents();
        setLocationRelativeTo(null);
        

        Panel_NhanVien panel_NhanVien = new Panel_NhanVien();
        jTabbedPane2.add("tab3",panel_NhanVien);
        Panel_VatTu panel_VatTu = new Panel_VatTu();
        jTabbedPane2.add("tab4",panel_VatTu);
        Panel_NhaCungCap panel_NhaCungCap = new Panel_NhaCungCap();
        jTabbedPane2.add("tab5",panel_NhaCungCap);
        Panel_ThongKe panel_ThongKe = new Panel_ThongKe();
        jTabbedPane2.add("tab6",panel_ThongKe);
        Panel_DangKy panel_DangKy = new Panel_DangKy();
        jTabbedPane2.add("tab7",panel_DangKy);

        this.ShowTableHoaDon();
        this.showTableVT();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jbtn_NhanVien = new javax.swing.JButton();
        jbtn_VatTu = new javax.swing.JButton();
        jbtnHoaDon = new javax.swing.JButton();
        jbtn_NhaCungCap = new javax.swing.JButton();
        user = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_username = new javax.swing.JLabel();
        role1 = new javax.swing.JLabel();
        jbtn_DangKy = new javax.swing.JButton();
        jbtn_Trangchu = new javax.swing.JButton();
        jbtn_ThongKe = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLap = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jDateNgayLap = new com.toedter.calendar.JDateChooser();
        rbtnNhap = new javax.swing.JRadioButton();
        rbtnxuat = new javax.swing.JRadioButton();
        txtTenKH = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldshd = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbldsvt = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        btnTraHang = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTenVT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaVT = new javax.swing.JTextField();
        btnLamMoiVT = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 0, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 23, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jbtn_NhanVien.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_NhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_NhanVien.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_NhanVien.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\employees48.png")); // NOI18N
        jbtn_NhanVien.setText("NHÂN VIÊN\n");
        jbtn_NhanVien.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseMoved(evt);
            }
        });
        jbtn_NhanVien.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jbtn_NhanVienMouseWheelMoved(evt);
            }
        });
        jbtn_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseReleased(evt);
            }
        });
        jbtn_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_NhanVienActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_NhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 210, 70));

        jbtn_VatTu.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_VatTu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_VatTu.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_VatTu.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\stationery48.png")); // NOI18N
        jbtn_VatTu.setText("VẬT TƯ\n");
        jbtn_VatTu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_VatTuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_VatTuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_VatTuMouseExited(evt);
            }
        });
        jbtn_VatTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_VatTuActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_VatTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 210, 70));

        jbtnHoaDon.setBackground(new java.awt.Color(51, 0, 102));
        jbtnHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        jbtnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bill48.png"))); // NOI18N
        jbtnHoaDon.setText("HÓA ĐƠN\n");
        jbtnHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtnHoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtnHoaDonMouseExited(evt);
            }
        });
        jbtnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnHoaDonActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 210, 71));

        jbtn_NhaCungCap.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_NhaCungCap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_NhaCungCap.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_NhaCungCap.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\supplier48.png")); // NOI18N
        jbtn_NhaCungCap.setText("NHÀ CUNG CẤP\n\n");
        jbtn_NhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_NhaCungCapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_NhaCungCapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_NhaCungCapMouseExited(evt);
            }
        });
        jbtn_NhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_NhaCungCapActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_NhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 210, 70));

        user.setForeground(new java.awt.Color(255, 255, 255));
        user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user80.png"))); // NOI18N
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, 90));

        txt_MaNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_MaNV.setForeground(new java.awt.Color(255, 255, 255));
        txt_MaNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_MaNV.setText("NV001");
        jPanel1.add(txt_MaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 70, -1));

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 660, 100, 30));

        txt_username.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_username.setForeground(new java.awt.Color(255, 255, 255));
        txt_username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_username.setText("username");
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 120, -1));

        role1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        role1.setForeground(new java.awt.Color(255, 255, 255));
        role1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        role1.setText("role");
        jPanel1.add(role1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 120, -1));

        jbtn_DangKy.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_DangKy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_DangKy.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_DangKy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit48.png"))); // NOI18N
        jbtn_DangKy.setText("ĐĂNG KÝ");
        jbtn_DangKy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_DangKyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_DangKyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_DangKyMouseExited(evt);
            }
        });
        jbtn_DangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_DangKyActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_DangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 210, 71));

        jbtn_Trangchu.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_Trangchu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_Trangchu.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_Trangchu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home48.png"))); // NOI18N
        jbtn_Trangchu.setText("TRANG CHỦ");
        jbtn_Trangchu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_TrangchuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_TrangchuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_TrangchuMouseExited(evt);
            }
        });
        jPanel1.add(jbtn_Trangchu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 210, 71));

        jbtn_ThongKe.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_ThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_ThongKe.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_ThongKe.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\bar-graph48.png")); // NOI18N
        jbtn_ThongKe.setText("THỐNG KÊ");
        jbtn_ThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_ThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtn_ThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtn_ThongKeMouseExited(evt);
            }
        });
        jbtn_ThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_ThongKeActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_ThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 210, 71));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 720));
        getContentPane().add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, -35, -1, 590));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Stationery Stores Cover.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 685, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab1", jPanel3);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 3), "LẬP HÓA ĐƠN\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(51, 0, 153))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Ngày lập: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Loại:");

        btnLap.setBackground(new java.awt.Color(0, 51, 153));
        btnLap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLap.setForeground(new java.awt.Color(51, 255, 255));
        btnLap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Apply (1).png"))); // NOI18N
        btnLap.setText("Xác nhận");
        btnLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 51, 153));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(51, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Refresh.png")); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jDateNgayLap.setBackground(new java.awt.Color(204, 255, 255));
        jDateNgayLap.setForeground(new java.awt.Color(255, 255, 255));
        jDateNgayLap.setDateFormatString("dd-MM-yyyy");

        buttonGroup1.add(rbtnNhap);
        rbtnNhap.setText("Nhập");
        rbtnNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtnNhapMouseClicked(evt);
            }
        });
        rbtnNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNhapActionPerformed(evt);
            }
        });
        rbtnNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbtnNhapKeyPressed(evt);
            }
        });

        buttonGroup1.add(rbtnxuat);
        rbtnxuat.setText("Xuất");
        rbtnxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtnxuatMouseClicked(evt);
            }
        });
        rbtnxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnxuatActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 153));
        jLabel15.setText("Tên khách hàng:");

        tbldshd.setBackground(new java.awt.Color(153, 204, 255));
        tbldshd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2));
        tbldshd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Số hóa đơn", "Ngày lập", "Loại", "Nhân viên", "Khách hàng", "Trị giá", "Trạng Thái"
            }
        ));
        tbldshd.setGridColor(new java.awt.Color(255, 255, 255));
        tbldshd.setSelectionBackground(new java.awt.Color(0, 51, 153));
        tbldshd.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbldshd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldshdMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbldshd);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnLap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(rbtnxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(34, 34, 34)
                                                .addComponent(rbtnNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel15))
                                .addGap(0, 12, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rbtnxuat)
                    .addComponent(rbtnNhap))
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset))
                .addGap(41, 41, 41))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 3), "LẬP CHI TIẾT HÓA ĐƠN\n", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 153, 153))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2), "DANH SÁCH VẬT TƯ\n", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        tbldsvt.setBackground(new java.awt.Color(0, 204, 204));
        tbldsvt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã vật tư", "Tên vật tư", "Đơn vị tính", "Giá nhập", "Giá bán", "Số lượng tồn"
            }
        ));
        tbldsvt.setSelectionBackground(new java.awt.Color(0, 102, 102));
        tbldsvt.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbldsvt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldsvtMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbldsvt);

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Create (1).png")); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-clear-symbol-20.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInHoaDon.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Print.png")); // NOI18N
        btnInHoaDon.setText("In Hóa Đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnTraHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraHang.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Redo.png")); // NOI18N
        btnTraHang.setText("Trả Hàng");
        btnTraHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraHangActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Số lượng:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Đơn giá:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tên vật tư:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã vật tư:");

        txtMaVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVTActionPerformed(evt);
            }
        });

        btnLamMoiVT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Refresh.png"))); // NOI18N
        btnLamMoiVT.setText("Load bảng");
        btnLamMoiVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiVTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoiVT)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnInHoaDon)
                    .addComponent(btnTraHang)
                    .addComponent(btnLamMoiVT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        tblCTHD.setBackground(new java.awt.Color(153, 255, 153));
        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã VT", "Tên vật tư", "Số lượng", "Đơn giá bán"
            }
        ));
        tblCTHD.setSelectionBackground(new java.awt.Color(102, 153, 0));
        tblCTHD.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(tblCTHD);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 51));
        jLabel13.setText("Thành tiền:");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblThanhTien.setText("0");
        lblThanhTien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblThanhTienAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThanhTien))))
                .addContainerGap())
        );

        jScrollPane4.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab2", jPanel5);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, -20, 1240, 720));
        getContentPane().add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_NhanVienActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jbtn_NhanVienActionPerformed

    private void jbtn_VatTuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_VatTuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_VatTuActionPerformed

    private void jbtnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnHoaDonActionPerformed

    private void jbtn_NhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_NhaCungCapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_NhaCungCapActionPerformed

    private void jbtn_NhanVienMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseDragged
        
    }//GEN-LAST:event_jbtn_NhanVienMouseDragged

    private void jbtn_NhanVienMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseMoved
        
    }//GEN-LAST:event_jbtn_NhanVienMouseMoved

    private void jbtn_NhanVienMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseWheelMoved
       
    }//GEN-LAST:event_jbtn_NhanVienMouseWheelMoved

    private void jbtn_NhanVienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jbtn_NhanVienMouseReleased

    private void jbtnHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnHoaDonMouseEntered
        jbtnHoaDon.setBackground(new Color(0,255,255));
       
    }//GEN-LAST:event_jbtnHoaDonMouseEntered

    private void jbtnHoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnHoaDonMouseExited
        jbtnHoaDon.setBackground(new Color(51, 0, 102));
    }//GEN-LAST:event_jbtnHoaDonMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        QuanLy.DangNhap gd=new QuanLy.DangNhap();
        gd.setVisible(true);
        gd.pack();
        gd.setLocationRelativeTo(null); 
        this.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtnHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnHoaDonMouseClicked
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_jbtnHoaDonMouseClicked

    private void jbtn_ThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseEntered
        if ("Quản lý".equals(role1.getText())) {
            jbtn_ThongKe.setBackground(new Color(0,255,255));
        }    
    }//GEN-LAST:event_jbtn_ThongKeMouseEntered

    private void jbtn_ThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseClicked
        if ("Quản lý".equals(role1.getText())) {
        
        jTabbedPane2.setSelectedIndex(5);
    } else if ("Nhân viên".equals(role1.getText())) {
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào chức năng này.");
    } 
    }//GEN-LAST:event_jbtn_ThongKeMouseClicked

    private void jbtn_ThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseExited
        jbtn_ThongKe.setBackground(new Color(51, 0, 102));
    }//GEN-LAST:event_jbtn_ThongKeMouseExited

    private void jbtn_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseClicked
if ("Quản lý".equals(role1.getText())) {
        
        jTabbedPane2.setSelectedIndex(2);
    } else if ("Nhân viên".equals(role1.getText())) {
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào chức năng này.");
    }
    }//GEN-LAST:event_jbtn_NhanVienMouseClicked

    private void jbtn_NhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseEntered
        if ("Quản lý".equals(role1.getText())) {
            jbtn_NhanVien.setBackground(new Color(0,255,255));
        }
    }//GEN-LAST:event_jbtn_NhanVienMouseEntered

    private void jbtn_NhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhanVienMouseExited

        jbtn_NhanVien.setBackground(new Color(51, 0, 102));
        
    }//GEN-LAST:event_jbtn_NhanVienMouseExited

    private void jbtn_VatTuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_VatTuMouseClicked
         if ("Quản lý".equals(role1.getText())) {
        
        jTabbedPane2.setSelectedIndex(3);
    } else if ("Nhân viên".equals(role1.getText())) {
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào chức năng này.");
    }
    }//GEN-LAST:event_jbtn_VatTuMouseClicked

    private void jbtn_VatTuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_VatTuMouseEntered
        if ("Quản lý".equals(role1.getText())) {
             jbtn_VatTu.setBackground(new Color(0,255,255));
        }    
    }//GEN-LAST:event_jbtn_VatTuMouseEntered

    private void jbtn_VatTuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_VatTuMouseExited
        // TODO add your handling code here: jTabbedPane2.setSelectedIndex(3);
          jbtn_VatTu.setBackground(new Color(51, 0, 102));
        
    }//GEN-LAST:event_jbtn_VatTuMouseExited

    private void jbtn_NhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhaCungCapMouseClicked
        // TODO add your handling code here:
         if ("Quản lý".equals(role1.getText())) {
        
        jTabbedPane2.setSelectedIndex(4);
    } else if ("Nhân viên".equals(role1.getText())) {
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào chức năng này.");
    }
         
    }//GEN-LAST:event_jbtn_NhaCungCapMouseClicked

    private void jbtn_NhaCungCapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhaCungCapMouseEntered
        // TODO add your handling code here:
        if ("Quản lý".equals(role1.getText())) {
            jbtn_NhaCungCap.setBackground(new Color(0,255,255));
        }
    }//GEN-LAST:event_jbtn_NhaCungCapMouseEntered

    private void jbtn_NhaCungCapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_NhaCungCapMouseExited
        // TODO add your handling code here:
          jbtn_NhaCungCap.setBackground(new Color(51, 0, 102));
    }//GEN-LAST:event_jbtn_NhaCungCapMouseExited

    private void jbtn_DangKyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_DangKyMouseClicked
        if ("Quản lý".equals(role1.getText())) {
        
        jTabbedPane2.setSelectedIndex(6);
    } else if ("Nhân viên".equals(role1.getText())) {
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào chức năng này.");
    }
       
    }//GEN-LAST:event_jbtn_DangKyMouseClicked

    private void jbtn_DangKyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_DangKyMouseEntered
        if ("Quản lý".equals(role1.getText())) {
            jbtn_DangKy.setBackground(new Color(0,255,255));
        }
    }//GEN-LAST:event_jbtn_DangKyMouseEntered

    private void jbtn_DangKyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_DangKyMouseExited
        jbtn_DangKy.setBackground(new Color(51, 0, 102));
        
    }//GEN-LAST:event_jbtn_DangKyMouseExited

    private void jbtn_DangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_DangKyActionPerformed
//       
    }//GEN-LAST:event_jbtn_DangKyActionPerformed

    private void btnLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapActionPerformed

        if (this.addHoaDon() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!!");
        } else{
            KhachHang kh = new KhachHang();
            kh.setMaKH("KH"+ createMa());
            kh.setTenKH(txtTenKH.getText());
            new DAOKhachHang().insertKhachHang(kh);
            new DAOHoaDon().insertHoaDon(addHoaDon());
            JOptionPane.showMessageDialog(this, "Thêm thành công!!!");
            this.ShowTableHoaDon();
        }
    }//GEN-LAST:event_btnLapActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        buttonGroup1.clearSelection();
        txtTenKH.setText("");
        jDateNgayLap.setCalendar(null);
    }//GEN-LAST:event_btnResetActionPerformed

    private void rbtnNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtnNhapMouseClicked
        txtTenKH.setEnabled(false);
    }//GEN-LAST:event_rbtnNhapMouseClicked

    private void rbtnNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnNhapActionPerformed

    private void rbtnNhapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbtnNhapKeyPressed

    }//GEN-LAST:event_rbtnNhapKeyPressed

    private void rbtnxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtnxuatMouseClicked
        txtTenKH.setEnabled(true);
    }//GEN-LAST:event_rbtnxuatMouseClicked

    private void rbtnxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnxuatActionPerformed

    }//GEN-LAST:event_rbtnxuatActionPerformed

    private void txtMaVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaVTActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        if (hd.getTrangThai() == 0) {
            if(txtSoLuong.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }
            if(UpdateSoLuongTon()==true){
                new DAOChiTietHD().insertChiTietHD(addChiTietHoaDon());
                JOptionPane.showMessageDialog(this, "Thêm vật tư vào hóa đơn thành công!!!");
                resettxtVTHD();
                this.ShowTableCTHD();
                this.showTableVT();
                this.ShowTableHoaDon();
                
            }
            else{
                JOptionPane.showMessageDialog(this, "Hàng trong kho không đủ!!!",
                    "Thông báo số lượng tồn", JOptionPane.ERROR_MESSAGE);

            }
        }else
        {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã lập không thể thêm chi tiết hóa đơn!",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        if(hd.getTrangThai()== 0){
            this.printInvoice();
            this.ShowTableHoaDon();
        }else
        {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được in.",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);

        int indexCTHD = tblCTHD.getSelectedRow();
        CTHoaDon cthd = listCTHD.get(indexCTHD);
        if(hd.getTrangThai() == 0)
        {
            for(VatTu vt : listvt){
                if(vt.getMaVT().equals(cthd.getMaVatTu()) )
                {
                    new DAOChiTietHD().deleteChiTietHD(cthd, hd);
                    JOptionPane.showMessageDialog(this, "Xóa vật tư khỏi hóa đơn thành công!!!");
                    new DAOChiTietHD().updateTraSLT(cthd, vt);
                }
            }
        }else if(hd.getTrangThai() == 1)
        {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã lập không thể xóa chi tiết hóa đơn",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }

        this.ShowTableCTHD();
        this.showTableVT();
        this.ShowTableHoaDon();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTraHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraHangActionPerformed

        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);

        if(hd.getTrangThai() == 1){
            int indexCTHD = tblCTHD.getSelectedRow();
            CTHoaDon cthd = listCTHD.get(indexCTHD);

            for(VatTu vt : listvt){
                if(vt.getMaVT().equals(cthd.getMaVatTu()) )
                {
                    
                    try {
                        if(this.TraHangChiTietHoaDon() == true)
                        {
                            new DAOChiTietHD().deleteChiTietHD(cthd, hd);
                            new DAOChiTietHD().updateTraSLT(cthd, vt);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(QuanLyVatTu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                }
            }

            this.ShowTableCTHD();
            this.showTableVT();
            this.ShowTableHoaDon();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Hoá đơn chưa lập!!!",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTraHangActionPerformed

    private void tbldsvtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldsvtMouseClicked
        this.showCTHD();
    }//GEN-LAST:event_tbldsvtMouseClicked

    private void lblThanhTienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblThanhTienAncestorAdded

    }//GEN-LAST:event_lblThanhTienAncestorAdded

    private void jbtn_TrangchuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_TrangchuMouseClicked
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_jbtn_TrangchuMouseClicked

    private void jbtn_TrangchuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_TrangchuMouseEntered
        jbtn_Trangchu.setBackground(new Color(0,255,255));
    }//GEN-LAST:event_jbtn_TrangchuMouseEntered

    private void jbtn_TrangchuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_TrangchuMouseExited
        jbtn_Trangchu.setBackground(new Color(51, 0, 102));
        
    }//GEN-LAST:event_jbtn_TrangchuMouseExited

    private void jbtn_ThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_ThongKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_ThongKeActionPerformed

    private void btnLamMoiVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiVTActionPerformed
        this.showTableVT();
    }//GEN-LAST:event_btnLamMoiVTActionPerformed

    private void tbldshdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldshdMouseClicked
        this.ShowTableCTHD();
        this.ShowTableHoaDon();
        this.showTableVT();        
        jTextPane1.setText("");
    }//GEN-LAST:event_tbldshdMouseClicked





    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyVatTu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyVatTu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyVatTu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyVatTu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyVatTu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLamMoiVT;
    private javax.swing.JButton btnLap;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTraHang;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateNgayLap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton jbtnHoaDon;
    private javax.swing.JButton jbtn_DangKy;
    private javax.swing.JButton jbtn_NhaCungCap;
    private javax.swing.JButton jbtn_NhanVien;
    private javax.swing.JButton jbtn_ThongKe;
    private javax.swing.JButton jbtn_Trangchu;
    private javax.swing.JButton jbtn_VatTu;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JRadioButton rbtnNhap;
    private javax.swing.JRadioButton rbtnxuat;
    public javax.swing.JLabel role1;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tbldshd;
    private javax.swing.JTable tbldsvt;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaVT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenVT;
    public javax.swing.JLabel txt_MaNV;
    public javax.swing.JLabel txt_username;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables

    private Color RGBColor(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
