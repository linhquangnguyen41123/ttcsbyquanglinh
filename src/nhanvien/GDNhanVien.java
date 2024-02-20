package nhanvien;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GDNhanVien extends javax.swing.JFrame {
 final String header[]={"MANV","HONV","TENNV","PHAI","NGAYSINH","SĐT","EMAIL"};
    final DefaultTableModel tb= new DefaultTableModel(header,0);
    connectDB1 cn= new connectDB1();
    Connection conn=null;
    ResultSet rs;
    private String sql;
    public void loadBang() {
    try {
        conn = cn.getConnection();
        int number;
        Vector row;
        String sql = "select * from nhanvien";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metadata = rs.getMetaData();
        number = metadata.getColumnCount();

        // Thêm cột thứ tự vào mô hình bảng
        Vector<String> headerWithIndex = new Vector<>();
        headerWithIndex.add("STT"); 
        for (int i = 1; i <= number; i++) {
            headerWithIndex.add(metadata.getColumnName(i));
        }
        tb.setColumnIdentifiers(headerWithIndex);

        tb.setRowCount(0);

        int stt = 1; 

        while (rs.next()) {
            row = new Vector();
            row.add(stt++); 
            for (int i = 1; i <= number; i++) {
                row.addElement(rs.getString(i));
            }
            tb.addRow(row); 
        }

        tblnhanvien.setModel(tb); 

        st.close();
        rs.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    
}
    public GDNhanVien() {
        initComponents();
        loadBang();
    }
private void xoatrang(){
    txtmanv.setText("");
    txtho.setText("");
    txtten.setText("");
    txtngaysinh.setText("");
    txtsđt.setText("");
    txtemail.setText("");
    txtmanv.setEnabled(true);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtphai = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        txtten = new javax.swing.JTextField();
        txtngaysinh = new javax.swing.JTextField();
        txtsđt = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        btnNam = new javax.swing.JRadioButton();
        btnNu = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnThêm = new javax.swing.JButton();
        btnSửa = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNhapmoi = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        btnLUU = new javax.swing.JButton();
        btnsxtheoten = new javax.swing.JButton();
        btnload = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtho = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnhanvien = new javax.swing.JTable();
        btnthoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 153));
        jTextField1.setText("QUẢN LÝ NHÂN VIÊN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(523, 523, 523)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setText("MÃ NHÂN VIÊN");

        jLabel2.setText("HỌ");

        txtphai.setText("PHÁI");

        jLabel4.setText("NGAYSINH");

        jLabel5.setText("SỐ ĐIỆN THOẠI");

        jLabel6.setText("EMAIL");

        buttonGroup1.add(btnNam);
        btnNam.setSelected(true);
        btnNam.setText("Nam");

        buttonGroup1.add(btnNu);
        btnNu.setText("Nữ");

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));

        btnThêm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Create.png"))); // NOI18N
        btnThêm.setText("Thêm");
        btnThêm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThêmActionPerformed(evt);
            }
        });

        btnSửa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Modify.png"))); // NOI18N
        btnSửa.setText("Sửa");
        btnSửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSửaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnNhapmoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Refresh.png"))); // NOI18N
        btnNhapmoi.setText("Nhập Mới");
        btnNhapmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapmoiActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/View.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLUU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Save.png"))); // NOI18N
        btnLUU.setText("Lưu");
        btnLUU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLUUActionPerformed(evt);
            }
        });

        btnsxtheoten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Filter.png"))); // NOI18N
        btnsxtheoten.setText("Sắp xếp theo tên nv");
        btnsxtheoten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsxtheotenActionPerformed(evt);
            }
        });

        btnload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Upload.png"))); // NOI18N
        btnload.setText("Load bảng");
        btnload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNhapmoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThêm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSửa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(btnLUU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnsxtheoten, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnload, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThêm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNhapmoi, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnLUU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsxtheoten, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnload, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel3.setText("TÊN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtsđt, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtphai))
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtho)
                                    .addComponent(txtmanv)
                                    .addComponent(txtten, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNu))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtngaysinh)
                                        .addGap(14, 14, 14)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtphai)
                    .addComponent(btnNam)
                    .addComponent(btnNu))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsđt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        tblnhanvien.setBackground(new java.awt.Color(255, 255, 204));
        tblnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MANV", "HỌ", "TÊN", "PHÁI", "NGAYSINH", "SỐ ĐT", "EMAIL"
            }
        ));
        tblnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblnhanvien);

        btnthoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/Cancel.png"))); // NOI18N
        btnthoat.setText("Thoát");
        btnthoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnthoat))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(nhanvien.GDNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nhanvien.GDNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nhanvien.GDNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nhanvien.GDNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nhanvien.GDNhanVien().setVisible(true);
            }
        });
    }
    private void btnNhapmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapmoiActionPerformed
               txtmanv.setText("");
               txtho.setText("");
               txtten.setText("");
               txtngaysinh.setText("");
               txtsđt.setText("");
               txtemail.setText("");
               txtmanv.setEnabled(true);             
    }//GEN-LAST:event_btnNhapmoiActionPerformed

    private void btnThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThêmActionPerformed

       conn = cn.getConnection();
    try {
        if (txtmanv.getText().equals("") || txtten.getText().equals("") ||txtho.getText().equals("") || txtngaysinh.getText().equals("") ||
                txtsđt.getText().equals("") || txtemail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập đủ dữ liệu");
        } else {
            // Kiểm tra xem nhân viên có tồn tại hay không
            String sql_check_pk = "SELECT MANV FROM nhanvien WHERE MANV=?";
            try (PreparedStatement pstCheckPK = conn.prepareStatement(sql_check_pk)) {
                pstCheckPK.setString(1, txtmanv.getText());
                try (ResultSet rsCheckPK = pstCheckPK.executeQuery()) {
                    if (rsCheckPK.next()) {
                        JOptionPane.showMessageDialog(this, "Nhân viên này đã tồn tại!");
                        return; 
                    }
                }
            }
            String gioiTinh = "";
            if (btnNam.isSelected()) {
                gioiTinh = "Nam";
            } else if (btnNu.isSelected()) {
                gioiTinh = "Nữ";
            }
           
            String sql_insert = "INSERT INTO nhanvien VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstInsert = conn.prepareStatement(sql_insert)) {
                pstInsert.setString(1, txtmanv.getText());
                pstInsert.setString(2, txtho.getText());
                pstInsert.setString(3, txtten.getText());
                pstInsert.setString(4, gioiTinh);
                pstInsert.setString(5, txtngaysinh.getText());
                pstInsert.setString(6, txtsđt.getText());
                pstInsert.setString(7, txtemail.getText());
                int kq = pstInsert.executeUpdate();
                if (kq > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm mới thành công");
                    loadBang();
                    xoatrang();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnThêmActionPerformed

    private void tblnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnhanvienMouseClicked
    int x=tblnhanvien.getSelectedRow();
        if(x>=0){
            txtmanv.setText(tblnhanvien.getValueAt(x, 1)+"");
            txtho.setText(tblnhanvien.getValueAt(x, 2)+"");
            txtten.setText(tblnhanvien.getValueAt(x, 3)+"");
        String gioiTinh = tblnhanvien.getValueAt(x, 4).toString();
        if (gioiTinh.equals("Nam")) {
            btnNam.setSelected(true);
            btnNu.setSelected(false);
        } else if (gioiTinh.equals("Nữ")) {
            btnNam.setSelected(false);
            btnNu.setSelected(true);
        } else {
            btnNam.setSelected(false);
            btnNu.setSelected(false);
        }
            txtngaysinh.setText(tblnhanvien.getValueAt(x, 5)+"");
            txtsđt.setText(tblnhanvien.getValueAt(x, 6)+"");
            txtemail.setText(tblnhanvien.getValueAt(x, 7)+"");
            txtmanv.setEnabled(false);
        }
               
    }//GEN-LAST:event_tblnhanvienMouseClicked

    private void btnSửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSửaActionPerformed

        conn = cn.getConnection();
    try {
        if (txtmanv.getText().equals("") || txtho.getText().equals("") ||txtten.getText().equals("") || txtngaysinh.getText().equals("") ||
                txtsđt.getText().equals("") || txtemail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập đủ dữ liệu");
        } else {
            String gioiTinh = "";
            if (btnNam.isSelected()) {
                gioiTinh = "Nam";
            } else if (btnNu.isSelected()) {
                gioiTinh = "Nữ";
            }
          
            String sql = "UPDATE nhanvien SET HONV=?, TENNV=?, PHAI=?, NGAYSINH=?, SĐT=?, EMAIL=? WHERE MANV=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, txtho.getText());
                preparedStatement.setString(2, txtten.getText());
                preparedStatement.setString(3, gioiTinh);
                preparedStatement.setString(4, txtngaysinh.getText());
                preparedStatement.setString(5, txtsđt.getText());
                preparedStatement.setString(6, txtemail.getText());
                preparedStatement.setString(7, txtmanv.getText());

                int kq = preparedStatement.executeUpdate();
                if (kq > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    loadBang();
                    xoatrang();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }     
        
    }//GEN-LAST:event_btnSửaActionPerformed

    private void btnLUUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLUUActionPerformed
   
    }//GEN-LAST:event_btnLUUActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        conn=cn.getConnection();
        try{
            String sql="Delete nhanvien where MANV='"+txtmanv.getText()+"'";
            Statement st=conn.createStatement();
            int chk=JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa chứ !","Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk==JOptionPane.YES_OPTION){
            st.executeUpdate(sql);
            xoatrang();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            }
        }catch(Exception e){
            
        }
     
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnsxtheotenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsxtheotenActionPerformed
 try {
    // Lấy danh sách nhân viên từ bảng
    List<Nhanvien> list = new ArrayList<>();
    for (int i = 0; i < tblnhanvien.getRowCount(); i++) {
        String maNV = tblnhanvien.getValueAt(i, 1).toString();
        String ho = tblnhanvien.getValueAt(i, 2).toString();
        String ten = tblnhanvien.getValueAt(i, 3).toString();
        boolean gioiTinh = Boolean.parseBoolean(tblnhanvien.getValueAt(i, 4).toString());
        Date ngaySinh = (Date) tblnhanvien.getValueAt(i, 5); 
        String soDienThoai = tblnhanvien.getValueAt(i, 6).toString();
        String email = tblnhanvien.getValueAt(i, 7).toString();

        Nhanvien nv = new Nhanvien(maNV, ho, ten, gioiTinh, ngaySinh, soDienThoai, email);
        list.add(nv);
    }

    // Sắp xếp danh sách theo tên
    Collator collator = Collator.getInstance(new Locale("vi", "VN"));
    collator.setStrength(Collator.PRIMARY);
    Collections.sort(list, Comparator.comparing(Nhanvien::getTen, collator));

    // Cập nhật lại bảng với danh sách đã sắp xếp
    tb.setRowCount(0);
    int stt = 1;
    for (Nhanvien nv : list) {
        Vector<Object> row = new Vector<>();
        row.add(stt++); // Thêm số thứ tự vào dòng
        row.add(nv.getMaNV());
        row.add(nv.getHo());
        row.add(nv.getTen());
        row.add(nv.isGioiTinh()); // Assuming the table model uses boolean for gender
        row.add(nv.getNgaySinh());
        row.add(nv.getSoDT());
        row.add(nv.getEmail());
        tb.addRow(row); // Thêm dòng vào mô hình bảng
    }

    tblnhanvien.setModel(tb);
} catch (Exception e) {
    e.printStackTrace();
}

    }//GEN-LAST:event_btnsxtheotenActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
      try {
        conn = cn.getConnection();
        String keyword = JOptionPane.showInputDialog(this, "Nhập từ khóa tìm kiếm:");

        if (keyword != null) {
            String sql = "SELECT * FROM nhanvien WHERE MANV LIKE ? OR HONV LIKE ? OR TENNV LIKE ? OR PHAI LIKE ? OR NGAYSINH LIKE ? OR SĐT LIKE ? OR EMAIL LIKE ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 7; i++) {
                    preparedStatement.setString(i, "%" + keyword + "%");
                }
                ResultSet rs = preparedStatement.executeQuery();
                tb.setRowCount(0);

                int stt = 1;
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(stt++);
                    for (int i = 1; i <= 7; i++) {
                        row.addElement(rs.getString(i));
                    }
                    tb.addRow(row);
                }
                tblnhanvien.setModel(tb);
                rs.close();
            }
        }

        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }       
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloadActionPerformed
    loadBang();       
    }//GEN-LAST:event_btnloadActionPerformed

    private void btnthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthoatActionPerformed
        int choice = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc muốn thoát không?",
        "Xác nhận thoát",
        JOptionPane.YES_NO_OPTION
    );

    if (choice == JOptionPane.YES_OPTION) {
        this.dispose();
    }    
    }//GEN-LAST:event_btnthoatActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLUU;
    private javax.swing.JRadioButton btnNam;
    private javax.swing.JButton btnNhapmoi;
    private javax.swing.JRadioButton btnNu;
    private javax.swing.JButton btnSửa;
    private javax.swing.JButton btnThêm;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnload;
    private javax.swing.JButton btnsxtheoten;
    private javax.swing.JButton btnthoat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblnhanvien;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtho;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JLabel txtphai;
    private javax.swing.JTextField txtsđt;
    private javax.swing.JTextField txtten;
    // End of variables declaration//GEN-END:variables
}
