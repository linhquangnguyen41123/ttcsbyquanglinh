/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
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
public class Panel_HoaDon extends javax.swing.JPanel {

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
        hd.setSoHD("HD"+createMa()) ; 
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");        
        Date date = jDateNgayLap.getDate();
        

        hd.setNgayLapHoaDon(sdf.format(date));
        if(rbtnNhap.isSelected()){
            hd.setLoaiHD("Nhập");   
        } else if(rbtnxuat.isSelected()){
            hd.setLoaiHD("Xuất");   
        }        
        
        QuanLyVatTu qlvt = new QuanLyVatTu();
        hd.setMaNV(qlvt.txt_MaNV.getText());
        hd.setTenNV(qlvt.txt_username.getText());
        hd.setMaKH("KH"+ createMa());
        hd.setTenKH(txtTenKH.getText());
        //insert khach hang
        
        
        KhachHang kh = new KhachHang();
        kh.setMaKH("KH"+ createMa());
        kh.setTenKH(txtTenKH.getText());
        new DAOKhachHang().insertKhachHang(kh);
        
        
        
        //maNV==null
        if( "".equals(hd.getSoHD()) || hd.getLoaiHD() == null){
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
                , hd.getTenNV(),hd.getMaKH(), hd.getThanhTien(), hd.getTrangThai()};
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
        jTextPane1.setText(jTextPane1.getText() + "Tên khách hàng: " +hd.getTenKH()+ "\n");
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
    
    
    
    public Panel_HoaDon() {
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
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbldsvt = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 3), "LẬP CHI TIẾT HÓA ĐƠN\n", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 153, 153))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã vật tư:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tên vật tư:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Số lượng:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Đơn giá:");

        txtMaVT.setBackground(new java.awt.Color(204, 255, 255));
        txtMaVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVTActionPerformed(evt);
            }
        });

        txtTenVT.setBackground(new java.awt.Color(204, 255, 255));

        txtSoLuong.setBackground(new java.awt.Color(204, 255, 255));

        txtDonGia.setBackground(new java.awt.Color(204, 255, 255));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
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
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnInHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInHoaDon)
                            .addComponent(btnTraHang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnXoa))
                        .addGap(73, 73, 73))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblThanhTien))
                                .addGap(39, 39, 39)))
                        .addContainerGap())))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 3), "LẬP HÓA ĐƠN\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(51, 0, 153))); // NOI18N

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
        rbtnNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNhapActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnxuat);
        rbtnxuat.setText("Xuất");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnLap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
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
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rbtnxuat)
                    .addComponent(rbtnNhap))
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset))
                .addGap(41, 41, 41))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane4.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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
                        java.util.logging.Logger.getLogger(Panel_HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

    private void btnLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapActionPerformed

        if(this.checkNgay()== false){
            JOptionPane.showMessageDialog(this, "Ngày lập hóa đơn không được lớn hơn ngày hiện tại!!!",
                "Thông báo ngày lập hóa đơn", JOptionPane.ERROR_MESSAGE);
        }
        if (this.addHoaDon() == null ) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!!");
        } else{
            new DAOHoaDon().insertHoaDon(addHoaDon());
            JOptionPane.showMessageDialog(this, "Thêm thành công!!!");
            this.ShowTableHoaDon();
        }
    }//GEN-LAST:event_btnLapActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        buttonGroup1.clearSelection();
        txtTenKH.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void rbtnNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnNhapActionPerformed

    private void tbldshdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldshdMouseClicked
        this.ShowTableCTHD();
        this.ShowTableHoaDon();
        this.showTableVT();
    }//GEN-LAST:event_tbldshdMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLap;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTraHang;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser jDateNgayLap;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JRadioButton rbtnNhap;
    private javax.swing.JRadioButton rbtnxuat;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tbldshd;
    private javax.swing.JTable tbldsvt;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaVT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenVT;
    // End of variables declaration//GEN-END:variables
}
