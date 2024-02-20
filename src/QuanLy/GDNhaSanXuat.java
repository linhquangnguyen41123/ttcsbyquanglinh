
package QLVT_BY_LINH;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class GDNhaSanXuat extends javax.swing.JFrame {
    
    final String NSX []= {"MANSX","TENNSX"};
    final DefaultTableModel tableModel = new DefaultTableModel(NSX,0);
    connectDB cn= new connectDB();
    Connection conn=null;
    ResultSet rs;
    
    private String sql;
    public GDNhaSanXuat() {
        initComponents();
        ListNSX();
    }
    
  public void ListNSX() {
    try {
        conn = cn.getConnection();
        int n;
        Vector row;
        String sql = "select * from dbo.NhaSanXuat";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metadata = rs.getMetaData();
        n = metadata.getColumnCount();
        Vector <String> headerWithIndex = new Vector<>();
        headerWithIndex.add("STT"); 
        for (int i = 1; i <= n; i++) {
            headerWithIndex.add(metadata.getColumnName(i));
        }
        tableModel.setColumnIdentifiers(headerWithIndex);
        tableModel.setRowCount(0); 
        int stt = 1; 
        
        while (rs.next()) {
            row = new Vector();
            row.add(stt++); 
            for (int i = 1; i <= n; i++) {
                row.addElement(rs.getString(i));
            }
            tableModel.addRow(row); 
        }
        tblnsx.setModel(tableModel); 
        st.close();
        rs.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    tblnsx.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (tblnsx.getSelectedRow() >= 0) {
                txtmansx.setText(tblnsx.getValueAt(tblnsx.getSelectedRow(), 1) + ""); 
                txttennsx.setText(tblnsx.getValueAt(tblnsx.getSelectedRow(), 2) + "");          
            }
        }
    });
}
  
private void LamMoi(){
    txtmansx.setText("");
    txttennsx.setText("");
    ListNSX();
    txtmansx.setEnabled(true);
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
        txtmansx = new javax.swing.JTextField();
        txttennsx = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnThêm = new javax.swing.JButton();
        btnSửa = new javax.swing.JButton();
        btnXóa = new javax.swing.JButton();
        btnTìmKiếm = new javax.swing.JButton();
        btnLưu = new javax.swing.JButton();
        btnSắpXếpNSXTheoTên = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        txtgiaban1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnsx = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setBackground(new java.awt.Color(204, 204, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 102, 0));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("THÔNG TIN NHÀ SẢN XUẤT");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(453, 453, 453)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mã Nhà Sản Xuất  :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên Nhà Sản Xuất : ");

        txtmansx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmansxActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        btnThêm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThêm.setText("Thêm");
        btnThêm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThêmActionPerformed(evt);
            }
        });

        btnSửa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSửa.setText("Sửa");
        btnSửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSửaActionPerformed(evt);
            }
        });

        btnXóa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXóa.setText("Xóa");
        btnXóa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXóaActionPerformed(evt);
            }
        });

        btnTìmKiếm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTìmKiếm.setText("Tìm Kiếm Theo Tên ");
        btnTìmKiếm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTìmKiếmActionPerformed(evt);
            }
        });

        btnLưu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLưu.setText("Lưu");
        btnLưu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLưuActionPerformed(evt);
            }
        });

        btnSắpXếpNSXTheoTên.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSắpXếpNSXTheoTên.setText("Sắp xếp nhà sản xuất theo tên");
        btnSắpXếpNSXTheoTên.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSắpXếpNSXTheoTênActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTìmKiếm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLưu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(btnXóa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnSắpXếpNSXTheoTên, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXóa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTìmKiếm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLưu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnSắpXếpNSXTheoTên, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtgiaban1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgiaban1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmansx, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txttennsx))
                        .addGap(47, 47, 47))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(173, 173, 173)
                    .addComponent(txtgiaban1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmansx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txttennsx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(119, Short.MAX_VALUE)
                    .addComponent(txtgiaban1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(201, 201, 201)))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        tblnsx.setBackground(new java.awt.Color(255, 204, 204));
        tblnsx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "STT", "MANSX", "TENNSX"
            }
        ));
        tblnsx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnsxMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblnsxMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblnsx);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 374, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThêmActionPerformed
        conn = cn.getConnection();
        try {
        if (txtmansx.getText().equals("") || txttennsx.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
        } else {
            String sql_check = "select MaNSX from dbo.NhaSanXuat where MaNSX=?";
            try (PreparedStatement pstCheck = conn.prepareStatement(sql_check)) {
                pstCheck.setString(1, txtmansx.getText());
                try (ResultSet rsCheck = pstCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        JOptionPane.showMessageDialog(this, "Nhà sản xuất này đã tồn tại trong bảng");
                        return; 
                    }
                }
            }
            String sql_insert = "insert into dbo.NhaSanXuat values (?, ?)";
            try (PreparedStatement pstInsert = conn.prepareStatement(sql_insert)) {
                pstInsert.setString(1, txtmansx.getText());
                pstInsert.setString(2, txttennsx.getText());

                int result = pstInsert.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm mới nhà sản xuất thành công");
                    ListNSX();
                    LamMoi();
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

    private void tblnsxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnsxMouseClicked
        int click = tblnsx.getSelectedRow();
        if(click >=0){
            txtmansx.setText(tblnsx.getValueAt(click , 1)+"");
            txttennsx.setText(tblnsx.getValueAt(click , 2)+"");
            txtmansx.setEnabled(false);
        }
       
    }//GEN-LAST:event_tblnsxMouseClicked

    private void btnSửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSửaActionPerformed
    conn = cn.getConnection();
    try {
        if (txtmansx.getText().equals("") || txttennsx.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
        } else {
           
    String sql = "update dbo.NhaSanXuat set TenNSX= ? where MaNSX = ? ";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
    preparedStatement.setString(1, txttennsx.getText());
    preparedStatement.setString(2, txtmansx.getText());
                
    int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã chỉnh sửa thành công");
                    ListNSX();
                    LamMoi();
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

    private void btnLưuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLưuActionPerformed
    conn = cn.getConnection();

    try {
       String maNsxuat = txtmansx.getText();
       String tenNsxuat = txttennsx.getText();
        if ( maNsxuat.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
            return;  
        }

        String sqlCheckExist = "select MaNSX from dbo.NhaSanXuat where MaNSX=?";
        try (PreparedStatement psCheckExist = conn.prepareStatement(sqlCheckExist)) {
            psCheckExist.setString(1, maNsxuat);
            ResultSet rsCheckExist = psCheckExist.executeQuery();

            if (rsCheckExist.next()) {
                String sqlUpdate = "update dbo.NhaSanXuat set TenNSX=? where MaNSX=?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, tenNsxuat);
                    psUpdate.setString(2, maNsxuat);

                    int rowsAffected = psUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã lưu thành công");
                        ListNSX();
                        LamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi rồi vui lòng kiểm tra lại");
                    }
                }
            } else {
                String sqlInsert = "insert into dbo.NhaSanXuat (MaNSX, TenNSX) values (?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                    psInsert.setString(1, maNsxuat);
                    psInsert.setString(2, tenNsxuat);
                    int rowsAffected = psInsert.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã thêm mới nhà sản xuất thành công");
                        ListNSX();
                        LamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi rồi vui lòng kiểm tra lại");
                    }
                }
            }

            rsCheckExist.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
       
    }//GEN-LAST:event_btnLưuActionPerformed

    private void btnXóaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXóaActionPerformed
        //this.removenv();
        //this.fillToTable();
        conn=cn.getConnection();
        try{
            String sql="Delete dbo.NhaSanXuat where MaNSX='"+txtmansx.getText()+"'";
            Statement st=conn.createStatement();
            int check_l=JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa nhà sản xuất không ?","Thông báo!",JOptionPane.YES_NO_OPTION);
            if(check_l==JOptionPane.YES_OPTION){
            st.executeUpdate(sql);
            LamMoi();
            JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã xóa nhà sản xuất thành công");
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_btnXóaActionPerformed

    private void btnSắpXếpNSXTheoTênActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSắpXếpNSXTheoTênActionPerformed
     try {
        List<NhaSanXuat> list = new ArrayList<>();
        for (int i = 0; i < tblnsx.getRowCount(); i++) {
            String maNsxuat = tblnsx.getValueAt(i, 1).toString(); 
            String tenNsxuat = tblnsx.getValueAt(i, 2).toString();
            NhaSanXuat nsx = new NhaSanXuat(maNsxuat,tenNsxuat);
            list.add(nsx);
        }
        Collections.sort(list, Comparator.comparing(NhaSanXuat::getTenNSX));
        tableModel.setRowCount(0); 
        int stt = 1; 
        for (NhaSanXuat nsx : list) {
            Vector<Object> row = new Vector<>();
            row.add(stt++); 
            row.add(nsx.getMaNSX());
            row.add(nsx.getTenNSX()); 
            tableModel.addRow(row);
        }
        tblnsx.setModel(tableModel); 
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnSắpXếpNSXTheoTênActionPerformed

    private void btnTìmKiếmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTìmKiếmActionPerformed
       try {
        conn = cn.getConnection();
        String key = JOptionPane.showInputDialog(this, "Nhập tên nhà sản xuất bạn muốn tìm kiếm:");

        if (key != null) {
            String sql = "select *  from dbo.NhaSanXuat where MaNSX like ? or TenNSX like ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 2; i++) {
                    preparedStatement.setString(i, "%" + key + "%");
                }
                ResultSet rs = preparedStatement.executeQuery();
                tableModel.setRowCount(0);
                int stt = 1;
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(stt++);
                    for (int i = 1; i <= 2; i++) {
                        row.addElement(rs.getString(i));
                    }
                    tableModel.addRow(row);
                }

                tblnsx.setModel(tableModel);
                rs.close();
            }
        }

        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    }//GEN-LAST:event_btnTìmKiếmActionPerformed

    private void txtgiaban1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgiaban1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgiaban1ActionPerformed

    private void tblnsxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnsxMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblnsxMousePressed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
    LamMoi();        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtmansxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmansxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmansxActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

  public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            GDNhaSanXuat frame = new GDNhaSanXuat();
            frame.setVisible(true);

            // Đảm bảo đóng kết nối khi đóng ứng dụng
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    frame.closeConnection();
                }
            });
        });
    }
   // Phương thức đóng kết nối
    public void closeConnection() {
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLưu;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSắpXếpNSXTheoTên;
    private javax.swing.JButton btnSửa;
    private javax.swing.JButton btnThêm;
    private javax.swing.JButton btnTìmKiếm;
    private javax.swing.JButton btnXóa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblnsx;
    private javax.swing.JTextField txtgiaban1;
    private javax.swing.JTextField txtmansx;
    private javax.swing.JTextField txttennsx;
    // End of variables declaration//GEN-END:variables

    private void clearFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void While(boolean next) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
