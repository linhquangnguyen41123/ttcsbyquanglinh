/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QuanLy;

import DAO.DAOChiTietHD;
import DAO.DAOHoaDon;
import DAO.DAOVatTu;
import QLVT_BY_LINH.VatTu;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ttcs.CTHoaDon;
import ttcs.HoaDon;

/**
 *
 * @author Anh Kiet
 */
public class QuanLyVatTu extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyVatTu
     */
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
        hd.setSoHD(txtSohd.getText()) ; 
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");        
        Date date = jDateNgayLap.getDate();
        

        hd.setNgayLapHoaDon(sdf.format(date));
        if(rbtnNhap.isSelected()){
            hd.setLoaiHD("Nhập");   
        } else if(rbtnxuat.isSelected()){
            hd.setLoaiHD("Xuất");   
        }        
        hd.setMaNV(txtNhanVien.getText()); 
        
        if( "".equals(hd.getSoHD()) || hd.getLoaiHD() == null || hd.getMaNV() == null){
            return null;
        }
        return hd;
  
    }
    public boolean checkNgay(){     
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");        
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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateMua = df.parse(hd.getNgayLapHoaDon());
        Calendar cMua = Calendar.getInstance();
        cMua.setTime(dateMua);
        cMua.add(Calendar.DATE, 3);
        
        //Calendar: hien tai
        Date dateNow = new Date();        
        String dateHT = df.format(dateNow);
        Date myDate = df.parse(dateHT);
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(myDate);
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
                , hd.getMaNV(),hd.getMaKH(), hd.getThanhTien(), hd.getTrangThai()};
            model.addRow(row);
        }
        
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
        lblThanhTien.setText(String.valueOf(hd.getThanhTien())+" VND");
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
            // Establish database connection
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
        jTextPane1.setText(jTextPane1.getText() + "                                    HÓA ĐƠN " +hd.getLoaiHD()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Số hóa đơn: " +hd.getSoHD()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Mã nhân viên: " +hd.getMaNV()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "Ngày lập hóa đơn: " +hd.getNgayLapHoaDon()+ "\n");
        jTextPane1.setText(jTextPane1.getText() + "-------------------------------------------------------------------------------------" + "\n");
        String sf1 = String.format("%-8s%-17s%-15s%-15s%-15s", "STT","Tên sản phẩm","SL","Đơn giá","Thành tiền");
        jTextPane1.setText(jTextPane1.getText() + sf1 + "\n");
        DefaultTableModel modelCTHD = (DefaultTableModel) tblCTHD.getModel();
        for(int i = 0; i < tblCTHD.getRowCount(); i++){
            String stt = modelCTHD.getValueAt(i, 0).toString();
            String tenvt = modelCTHD.getValueAt(i, 2).toString();
            String sl = modelCTHD.getValueAt(i, 3).toString();
            String dongia = modelCTHD.getValueAt(i, 4).toString();
            String sf2 = String.format("%-11s%-23s%4s%18s%20s", stt, tenvt, sl, dongia, (Integer.valueOf(sl)*Integer.valueOf(dongia)));
            jTextPane1.setText(jTextPane1.getText() +sf2 + "\n");
        }
        jTextPane1.setText(jTextPane1.getText() + "-------------------------------------------------------------------------------------" + "\n");
        String sf3 = String.format("Tổng hóa đơn: %60s", lblThanhTien.getText() );
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
    
    public JFreeChart createChart() {
        JFreeChart barChart;
        barChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU NĂM " + txtNam.getText(),
                "Tháng", "Doanh thu(Đơn vị: VND)",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(int i = 0; i < tblDoanhThuNam.getRowCount() ; i++) {
            
            dataset.addValue((Number) tblDoanhThuNam.getValueAt(i, 1), "Doanh thu(Đơn vị: VND)", i+1);
        }    
        
        return dataset;
    }

    
    
    public List<String> getDaysBetweenDates(Date startdate, Date enddate){
        List<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        
        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            //chuyen date ->string
            String stringDate = df.format(result);
            dates.add(stringDate);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    
    public void thongKeDoanhThuNam(){
        List<HoaDon> listhd = new DAOHoaDon().getListHD();
        int sumYear = 0;
        for(int i = 1; i < tblDoanhThuNam.getRowCount() + 1; i++) {
            int sumMonth = 0;
            for(HoaDon hd : listhd){
                if(hd.getNgayLapHoaDon().contains(txtNam.getText()) && hd.getLoaiHD().equals("Xuất")){
                    if(Integer.parseInt(hd.getNgayLapHoaDon().substring(3, 5)) == i){
                        sumMonth+=hd.getThanhTien();
                    }

                }
            }
            sumYear+=sumMonth;
            txtSumDoanhThu.setText(String.valueOf(sumYear) + " VND");
            tblDoanhThuNam.setValueAt(sumMonth, i-1, 1);
        }    
    }
    public QuanLyVatTu() {
        initComponents();
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
        jbtn_CTHD = new javax.swing.JButton();
        jbtn_ThongKe = new javax.swing.JButton();
        user = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSohd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnLap = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jDateNgayLap = new com.toedter.calendar.JDateChooser();
        rbtnNhap = new javax.swing.JRadioButton();
        rbtnxuat = new javax.swing.JRadioButton();
        txtNhanVien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldshd = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaVT = new javax.swing.JTextField();
        txtTenVT = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTraHang = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbldsvt = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThongkeTuNgay = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jDateFrom = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jDateTo = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        rbtnNhap1 = new javax.swing.JRadioButton();
        rbtnxuat1 = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDoanhThuNam = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtNam = new javax.swing.JTextField();
        btnXacnhan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnBieuDo = new javax.swing.JButton();
        txtSumDoanhThu = new javax.swing.JLabel();
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbtn_NhanVienMouseReleased(evt);
            }
        });
        jbtn_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_NhanVienActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_NhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 160, 210, 70));

        jbtn_VatTu.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_VatTu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_VatTu.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_VatTu.setText("VẬT TƯ\n");
        jbtn_VatTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_VatTuActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_VatTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 230, 210, 70));

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
        jPanel1.add(jbtnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 300, 210, 71));

        jbtn_CTHD.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_CTHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_CTHD.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_CTHD.setText("CHI TIẾT HÓA ĐƠN\n");
        jbtn_CTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_CTHDActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_CTHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 370, 210, 70));

        jbtn_ThongKe.setBackground(new java.awt.Color(51, 0, 102));
        jbtn_ThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtn_ThongKe.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_ThongKe.setText("THỐNG KÊ\n");
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
        jPanel1.add(jbtn_ThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 440, 210, 71));

        user.setForeground(new java.awt.Color(255, 255, 255));
        user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user80.png"))); // NOI18N
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 90, 90));

        username.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        username.setText("username");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, -1));

        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 600, -1, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 670));
        getContentPane().add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, -35, -1, 590));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1240, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab1", jPanel3);

        jPanel5.setBackground(new java.awt.Color(0, 153, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 3), "LẬP HÓA ĐƠN\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(51, 0, 153))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Số hóa đơn:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Ngày lập: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Loại:");

        txtSohd.setBackground(new java.awt.Color(204, 255, 255));
        txtSohd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSohdActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("Nhân viên:");

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

        buttonGroup1.add(rbtnxuat);
        rbtnxuat.setText("Xuất");

        txtNhanVien.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnLap)
                        .addGap(65, 65, 65)
                        .addComponent(btnReset))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(rbtnxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbtnNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(txtSohd)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSohd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtnxuat)
                        .addComponent(rbtnNhap))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset)
                    .addComponent(btnLap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255), 2), "DANH SÁCH HÓA ĐƠN\n", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 51, 153))); // NOI18N

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
        tbldshd.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tbldshdAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 3), "LẬP CHI TIẾT HÓA ĐƠN\n", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 255, 204))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã vật tư:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tên vật tư:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Số lượng:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Đơn giá:");

        txtMaVT.setBackground(new java.awt.Color(204, 255, 204));
        txtMaVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVTActionPerformed(evt);
            }
        });

        txtTenVT.setBackground(new java.awt.Color(204, 255, 204));

        txtSoLuong.setBackground(new java.awt.Color(204, 255, 204));

        txtDonGia.setBackground(new java.awt.Color(204, 255, 204));

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(204, 255, 204));
        btnThem.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Create (1).png")); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnInHoaDon.setBackground(new java.awt.Color(0, 102, 102));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(153, 255, 204));
        btnInHoaDon.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Print.png")); // NOI18N
        btnInHoaDon.setText("In Hóa Đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 102));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(102, 255, 204));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Delete.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTraHang.setBackground(new java.awt.Color(0, 102, 102));
        btnTraHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraHang.setForeground(new java.awt.Color(204, 255, 204));
        btnTraHang.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Redo.png")); // NOI18N
        btnTraHang.setText("Trả Hàng");
        btnTraHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenVT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaVT)
                    .addComponent(txtDonGia))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTraHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btnInHoaDon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTraHang)
                    .addComponent(jLabel10))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0), 2), "DANH SÁCH CHI TIẾT HÓA ĐƠN\n", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(51, 255, 153))); // NOI18N

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
        tblCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTHD);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 255, 255));
        jLabel13.setText("Thành tiền:");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThanhTien.setForeground(new java.awt.Color(204, 255, 255));
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThanhTien))
                .addGap(10, 10, 10))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 204));
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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab2", jPanel11);

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 2), "THỐNG KÊ HÓA ĐƠN\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 51, 255))); // NOI18N

        tblThongkeTuNgay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Số hóa đơn", "Ngày lập", "Loại hóa đơn", "Nhân viên", "Trị giá "
            }
        ));
        jScrollPane5.setViewportView(tblThongkeTuNgay);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Từ ngày:");

        jDateFrom.setDateFormatString("dd-MM-yyyy");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Đến ngày:");

        jDateTo.setDateFormatString("dd-MM-yyyy");

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\Find.png")); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbtnNhap1);
        rbtnNhap1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnNhap1.setText("Nhập");
        rbtnNhap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNhap1ActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbtnxuat1);
        rbtnxuat1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnxuat1.setText("Xuất");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(rbtnNhap1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnxuat1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtnNhap1)
                        .addComponent(rbtnxuat1))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(0, 204, 204));

        tblDoanhThuNam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null},
                {"2", null},
                {"3", null},
                {"4", null},
                {"5", null},
                {"6", null},
                {"7", null},
                {"8", null},
                {"9", null},
                {"10", null},
                {"11", null},
                {"12", null}
            },
            new String [] {
                "Tháng", "Doanh thu"
            }
        ));
        tblDoanhThuNam.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane6.setViewportView(tblDoanhThuNam);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Bảng thống kê doanh thu năm:");

        btnXacnhan.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\OK.png")); // NOI18N
        btnXacnhan.setText("OK");
        btnXacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacnhanActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Tổng doanh thu :");

        btnBieuDo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBieuDo.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\Downloads\\3d bar chart.png")); // NOI18N
        btnBieuDo.setText("Biểu đồ");
        btnBieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBieuDoActionPerformed(evt);
            }
        });

        txtSumDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSumDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtSumDoanhThu.setText("0 VND");
        txtSumDoanhThu.setToolTipText("");
        txtSumDoanhThu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXacnhan)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSumDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacnhan))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSumDoanhThu)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(btnBieuDo)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab3", jPanel10);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, -20, 1240, -1));
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

    private void jbtn_CTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_CTHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_CTHDActionPerformed

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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtnHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnHoaDonMouseClicked
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_jbtnHoaDonMouseClicked

    private void txtSohdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSohdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSohdActionPerformed

    private void btnLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapActionPerformed

        if(this.checkNgay()== false){
            JOptionPane.showMessageDialog(this, "Ngày lập hóa đơn không được lớn hơn ngày hiện tại!!!",
                "Thông báo ngày lập hóa đơn", JOptionPane.ERROR_MESSAGE);
        }
        if (this.addHoaDon() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!!");
        } else{
            new DAOHoaDon().insertHoaDon(addHoaDon());
            JOptionPane.showMessageDialog(this, "Thêm thành công!!!");
            this.ShowTableHoaDon();
        }

    }//GEN-LAST:event_btnLapActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSohd.setText("");
        buttonGroup1.clearSelection();
        txtNhanVien.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void tbldshdAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tbldshdAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldshdAncestorAdded

    private void tbldshdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldshdMouseClicked
        this.ShowTableCTHD();

    }//GEN-LAST:event_tbldshdMouseClicked

    private void txtMaVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaVTActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int indexHD = tbldshd.getSelectedRow();
        HoaDon hd = listhd.get(indexHD);
        if (hd.getTrangThai() == 0) {
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

    private void tblCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDMouseClicked

    }//GEN-LAST:event_tblCTHDMouseClicked

    private void lblThanhTienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblThanhTienAncestorAdded

    }//GEN-LAST:event_lblThanhTienAncestorAdded

    private void tbldsvtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldsvtMouseClicked
        this.showCTHD();
    }//GEN-LAST:event_tbldsvtMouseClicked

    private void jbtn_ThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseEntered
        jbtn_ThongKe.setBackground(new Color(0,255,255));
        
    }//GEN-LAST:event_jbtn_ThongKeMouseEntered

    private void jbtn_ThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseClicked
         jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_jbtn_ThongKeMouseClicked

    private void jbtn_ThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_ThongKeMouseExited
        jbtn_ThongKe.setBackground(new Color(51, 0, 102));
    }//GEN-LAST:event_jbtn_ThongKeMouseExited

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        Date startdate = jDateFrom.getDate();
        Date enddate = jDateTo.getDate();
        List<String> listdate = getDaysBetweenDates(startdate, enddate);

        DefaultTableModel model = (DefaultTableModel)tblThongkeTuNgay.getModel();
        
        List<HoaDon> listhd = new DAOHoaDon().getListHD();
        model.setRowCount(0);

        String loai = "";
        if(rbtnNhap.isSelected()){
            loai = rbtnNhap.getText();
        }else if(rbtnxuat.isSelected()){
            loai = rbtnxuat.getText();
        }

        //Duyet qua danh sach va them sv
        for(HoaDon hd : listhd){
            if(listdate.contains(hd.getNgayLapHoaDon()) == true && hd.getLoaiHD().equals(loai)){
                Object[] row = new Object[]{tblThongkeTuNgay.getRowCount()+1, hd.getSoHD() , hd.getNgayLapHoaDon(), hd.getLoaiHD()
                    , hd.getMaNV(), hd.getThanhTien()};
                model.addRow(row);
            }

        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void rbtnNhap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNhap1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnNhap1ActionPerformed

    private void btnXacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacnhanActionPerformed
        this.thongKeDoanhThuNam();

    }//GEN-LAST:event_btnXacnhanActionPerformed

    private void btnBieuDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBieuDoActionPerformed
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ JFreeChart trong Java Swing");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }//GEN-LAST:event_btnBieuDoActionPerformed

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
    private javax.swing.JButton btnBieuDo;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLap;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTraHang;
    private javax.swing.JButton btnXacnhan;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateFrom;
    private com.toedter.calendar.JDateChooser jDateNgayLap;
    private com.toedter.calendar.JDateChooser jDateTo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton jbtnHoaDon;
    private javax.swing.JButton jbtn_CTHD;
    private javax.swing.JButton jbtn_NhanVien;
    private javax.swing.JButton jbtn_ThongKe;
    private javax.swing.JButton jbtn_VatTu;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JRadioButton rbtnNhap;
    private javax.swing.JRadioButton rbtnNhap1;
    private javax.swing.JRadioButton rbtnxuat;
    private javax.swing.JRadioButton rbtnxuat1;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblDoanhThuNam;
    private javax.swing.JTable tblThongkeTuNgay;
    private javax.swing.JTable tbldshd;
    private javax.swing.JTable tbldsvt;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaVT;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtNhanVien;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSohd;
    private javax.swing.JLabel txtSumDoanhThu;
    private javax.swing.JTextField txtTenVT;
    private javax.swing.JLabel user;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables

    private Color RGBColor(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
