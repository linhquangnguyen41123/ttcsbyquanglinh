
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
public class GDVatTu extends javax.swing.JFrame {
    
    final String VT []={"MAVT","TENVT","PHANLOAI","DONVITINH","SOLUONGTON","DONGIANHAP","DONGIABAN","MANSX"};
    final DefaultTableModel tableModel = new DefaultTableModel(VT,0);
    connectDB cn= new connectDB();
    Connection conn=null;
    ResultSet rs;
    

    private String sql;
    public GDVatTu() {
        initComponents();
        ListVT();
    }
  public void ListVT() {
    try {
        conn = cn.getConnection();
        int n ;
        Vector row;
        String sql = "select * from vattu677";
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
        tblvattu.setModel(tableModel); 
        st.close();
        rs.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    tblvattu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (tblvattu.getSelectedRow() >= 0) {
                txtmavt.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 1) + ""); 
                txttenvt.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 2) + ""); 
                txtdonvitinh.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 4) + "");
                txtsoluongton.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 5) + "");
                txtdongianhap.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 6) + ""); 
                txtdongiaban.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 7) + "");
                txtmansx.setText(tblvattu.getValueAt(tblvattu.getSelectedRow(), 8) + "");
        String phanloaira = tblvattu.getValueAt(tblvattu.getSelectedRow(), 3).toString();
        if (phanloaira.equals("Đồ gia dụng")) {
            btndogiadung.setSelected(true);
            btnthietbimay.setSelected(false);
        } else if (phanloaira.equals("Thiết bị máy")) {
            btndogiadung.setSelected(false);
            btnthietbimay.setSelected(true);
        } else {
            btndogiadung.setSelected(false);
            btnthietbimay.setSelected(false);
        }               
            }
        }
    });
}
private void LamMoi(){
    txtmavt.setText("");
    txttenvt.setText("");
    txtdonvitinh.setText("");
    txtsoluongton.setText("");
    txtdongianhap.setText("");
    txtdongiaban.setText("");
    txtmansx.setText("");
    ListVT();
    txtmavt.setEnabled(true);
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
        txtmavt = new javax.swing.JTextField();
        txttenvt = new javax.swing.JTextField();
        txtdonvitinh = new javax.swing.JTextField();
        btndogiadung = new javax.swing.JRadioButton();
        btnthietbimay = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtdongianhap = new javax.swing.JTextField();
        txtgiaban1 = new javax.swing.JTextField();
        txtsoluongton = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtdongiaban = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtmansx = new javax.swing.JTextField();
        btnThêm = new javax.swing.JButton();
        btnTìmKiếm = new javax.swing.JButton();
        btnSắpXếp = new javax.swing.JButton();
        btnLưu = new javax.swing.JButton();
        btnSửa = new javax.swing.JButton();
        btnXóa = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvattu = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setBackground(new java.awt.Color(204, 255, 204));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 0, 0));
        jTextField1.setText("QUẢN LÝ VẬT TƯ");
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
                .addGap(443, 443, 443)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã Vật Tư :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên Vật Tư : ");

        txtphai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtphai.setText("Phân Loại :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Đơn Vị Tính : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Số Lượng Tồn : ");

        buttonGroup1.add(btndogiadung);
        btndogiadung.setSelected(true);
        btndogiadung.setText("Đồ gia dụng");
        btndogiadung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndogiadungActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnthietbimay);
        btnthietbimay.setText("Thiết bị máy");
        btnthietbimay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthietbimayActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Đơn Giá Nhập : ");

        txtdongianhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdongianhapActionPerformed(evt);
            }
        });

        txtgiaban1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgiaban1ActionPerformed(evt);
            }
        });

        txtsoluongton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsoluongtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Đơn Giá Bán : ");

        txtdongiaban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdongiabanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã Nhà Sản Xuất : ");

        btnThêm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThêm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-plus-20.png"))); // NOI18N
        btnThêm.setText("Thêm");
        btnThêm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThêmActionPerformed(evt);
            }
        });

        btnTìmKiếm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTìmKiếm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-zoom-to-actual-size-20.png"))); // NOI18N
        btnTìmKiếm.setText("Tìm Kiếm theo mã vật tư");
        btnTìmKiếm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTìmKiếmActionPerformed(evt);
            }
        });

        btnSắpXếp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSắpXếp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-filter-20.png"))); // NOI18N
        btnSắpXếp.setText("Sắp xếp theo tên vật tư");
        btnSắpXếp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSắpXếpActionPerformed(evt);
            }
        });

        btnLưu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLưu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-save-20.png"))); // NOI18N
        btnLưu.setText("Lưu");
        btnLưu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLưuActionPerformed(evt);
            }
        });

        btnSửa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSửa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-edit-20.png"))); // NOI18N
        btnSửa.setText("Sửa");
        btnSửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSửaActionPerformed(evt);
            }
        });

        btnXóa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXóa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-clear-symbol-20.png"))); // NOI18N
        btnXóa.setText("Xóa");
        btnXóa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXóaActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/h16x16/icons8-reset-20.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(54, 54, 54))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btndogiadung)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtsoluongton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                .addComponent(txtdongianhap, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtdonvitinh)))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnXóa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnSắpXếp)
                                                .addGap(26, 26, 26)
                                                .addComponent(btnTìmKiếm))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnLưu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(58, 58, 58)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 16, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtphai, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(54, 54, 54)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(161, 161, 161)
                                                .addComponent(btnthietbimay))
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtmavt, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txttenvt, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtdongiaban, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(21, 21, 21)
                                            .addComponent(txtmansx, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(173, 173, 173)
                    .addComponent(txtgiaban1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmavt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttenvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndogiadung)
                    .addComponent(btnthietbimay)
                    .addComponent(txtphai))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtdonvitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtsoluongton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdongianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdongiaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmansx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXóa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLưu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTìmKiếm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSắpXếp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(377, Short.MAX_VALUE)
                    .addComponent(txtgiaban1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(201, 201, 201)))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        tblvattu.setBackground(new java.awt.Color(255, 204, 204));
        tblvattu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MAVT", "TENVT", "PHANLOAI", "DONVITINH", "SOLUONGTON", "DONGIANHAP", "DONGIABAN", "MANSX"
            }
        ));
        tblvattu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblvattuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblvattuMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblvattu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(312, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 159, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblvattuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvattuMouseClicked
        int x=tblvattu.getSelectedRow();
        if(x>=0){
            txtmavt.setText(tblvattu.getValueAt(x, 1)+"");
            txttenvt.setText(tblvattu.getValueAt(x, 2)+"");
            // Lấy giá trị giới tính từ cột 2 (giả sử)
        String phanloaira = tblvattu.getValueAt(x, 3).toString();

        // Thiết lập trạng thái của các radio button dựa trên giới tính
        if (phanloaira.equals("Do gia dung")) {
            btndogiadung.setSelected(true);
            btnthietbimay.setSelected(false);
        } else if (phanloaira.equals("Thiet bi may")) {
            btndogiadung.setSelected(false);
            btnthietbimay.setSelected(true);
        } else {
            btndogiadung.setSelected(false);
            btnthietbimay.setSelected(false);
        }

            txtdonvitinh.setText(tblvattu.getValueAt(x, 4)+"");
            txtsoluongton.setText(tblvattu.getValueAt(x, 5)+"");
            txtdongianhap.setText(tblvattu.getValueAt(x, 6)+"");
            txtdongiaban.setText(tblvattu.getValueAt(x, 7)+"");
            txtmansx.setText(tblvattu.getValueAt(x, 8)+"");
            txtmavt.setEnabled(false);
        }
       
    }//GEN-LAST:event_tblvattuMouseClicked

    private void txtdongianhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdongianhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdongianhapActionPerformed

    private void btnthietbimayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthietbimayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthietbimayActionPerformed

    private void txtgiaban1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgiaban1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgiaban1ActionPerformed

    private void txtdongiabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdongiabanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdongiabanActionPerformed

    private void txtsoluongtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsoluongtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsoluongtonActionPerformed

    private void tblvattuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvattuMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblvattuMousePressed

    private void btnLưuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLưuActionPerformed
        conn = cn.getConnection();
        try {
            if (txtmavt.getText().equals("") || txttenvt.getText().equals("") || txtdonvitinh.getText().equals("") ||
                txtsoluongton.getText().equals("")|| txtdongianhap.getText().equals("") || txtdongiaban.getText().equals("") || txtmansx.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
            } else{
            String maVtu = txtmavt.getText();
            String tenVtu = txttenvt.getText();         
            String phanloaira = "";
            if (btndogiadung.isSelected()) {
                phanloaira = "Đồ gia dụng";
            } else if (btnthietbimay.isSelected()) {
                phanloaira = "Thiết bị máy";
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phân loại");
                return;  
            }
            String dvtinh = txtdonvitinh.getText();
            int slton = Integer.parseInt(txtsoluongton.getText());
            float dgnhap = Float.parseFloat(txtdongianhap.getText());
            float dgban = Float.parseFloat(txtdongiaban.getText());
            String maNsxuat = txtmansx.getText();
            
            String sqlCheckExist = "select MaVT from vattu677 where MaVT=?";
            try (PreparedStatement psCheckExist = conn.prepareStatement(sqlCheckExist)) {
                psCheckExist.setString(1, maVtu);
                ResultSet rsCheckExist = psCheckExist.executeQuery();

                if (rsCheckExist.next()) {
                    String sqlUpdate = "update vattu677 set TenVT=?, PhanLoai=?, DonViTinh=?, SoLuongTon=?, DonGiaNhap=?, DonGiaBan=?, MaNSX=? WHERE MaVT=?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                        psUpdate.setString(1, tenVtu);
                        psUpdate.setString(2, phanloaira);
                        psUpdate.setString(3, dvtinh);
                        psUpdate.setInt(4, Integer.parseInt(txtsoluongton.getText()));
                        psUpdate.setFloat(5, Float.parseFloat(txtdongianhap.getText()));
                        psUpdate.setFloat(6, Float.parseFloat(txtdongiaban.getText()));
                        psUpdate.setString(7, maNsxuat);
                        psUpdate.setString(8, maVtu);

                        int rowsAffected = psUpdate.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã lưu thành công");
                            ListVT();
                            LamMoi();
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi rồi vui lòng kiểm tra lại");
                        }
                    }
                } else {
                    String sqlInsert = "insert into vattu677 (MaVT, TenVT, PhanLoai, DonViTinh,SoLuongTon, DonGiaNhap, DonGiaBan, MaNSX) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                        psInsert.setString(1, maVtu);
                        psInsert.setString(2, tenVtu);
                        psInsert.setString(3, phanloaira);
                        psInsert.setString(4, dvtinh);
                        psInsert.setInt(5, Integer.parseInt(txtsoluongton.getText()));
                        psInsert.setFloat(6, Float.parseFloat(txtdongianhap.getText()));
                        psInsert.setFloat(7, Float.parseFloat(txtdongiaban.getText()));
                        psInsert.setString(8, maNsxuat);

                        int rowsAffected = psInsert.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã thêm mới vật tư thành công");
                            ListVT();
                            LamMoi();
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi rồi vui lòng kiểm tra lại");
                        }
                    }
                }

                rsCheckExist.close();
            }
            }} catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnLưuActionPerformed

    private void btnTìmKiếmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTìmKiếmActionPerformed
        try {
            conn = cn.getConnection();
            String key= JOptionPane.showInputDialog(this, "Nhập tên vật tư bạn muốn tìm kiếm:");

            if (key!= null) {
                String sql = "select *  from vattu677 where MaVT like ? or TenVT like ? or PhanLoai like ? or DonViTinh like ? or SoLuongTon like ? or DonGiaNhap like ? or DonGiaBan like ? OR MaNSX like ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    for (int i = 1; i <= 8; i++) {
                        preparedStatement.setString(i, "%" + key + "%");
                    }
                    ResultSet rs = preparedStatement.executeQuery();
                    tableModel.setRowCount(0);

                    int stt = 1;
                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        row.add(stt++);
                        for (int i = 1; i <= 8; i++) {
                            row.addElement(rs.getString(i));
                        }
                        tableModel.addRow(row);
                    }

                    tblvattu.setModel(tableModel);
                    rs.close();
                }
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTìmKiếmActionPerformed

    private void btnXóaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXóaActionPerformed
        conn=cn.getConnection();
        try{
            String sql="Delete vattu677 where MAVT='"+txtmavt.getText()+"'";
            Statement st=conn.createStatement();
            int check_l=JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa vật tư này không !","Thông báo!",JOptionPane.YES_NO_OPTION);
            if(check_l ==JOptionPane.YES_OPTION){
                st.executeUpdate(sql);
                LamMoi();
                JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã xóa nhà sản xuất thành công");
            }
        }catch(Exception e){
        }
    }//GEN-LAST:event_btnXóaActionPerformed

    private void btnSửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSửaActionPerformed
       conn = cn.getConnection();
        try {
            if (txtmavt.getText().equals("") || txttenvt.getText().equals("") || txtdonvitinh.getText().equals("") ||
                txtsoluongton.getText().equals("") || txtdongianhap.getText().equals("") || txtdongiaban.getText().equals("") || txtmansx.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
            } else {
                String phanloaira = "";
                if (btndogiadung.isSelected()) {
                    phanloaira = "Đồ gia dụng";
                } else if (btnthietbimay.isSelected()) {
                    phanloaira = "Thiết bị máy";
                }

                String sql = "update vattu677 set TenVT= ?,PhanLoai= ?,DonViTinh= ?,SoLuongTon= ?,DonGiaNhap= ?,DonGiaBan= ?,MaNSX=? where MaVT = ? ";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, txttenvt.getText());
                    preparedStatement.setString(2, phanloaira);
                    preparedStatement.setString(3, txtdonvitinh.getText());
                    preparedStatement.setInt(4, Integer.parseInt(txtsoluongton.getText()));
                    preparedStatement.setFloat(5, Float.parseFloat(txtdongianhap.getText()));
                    preparedStatement.setFloat(6, Float.parseFloat(txtdongiaban.getText()));
                    preparedStatement.setString(7, txtmansx.getText());
                    preparedStatement.setString(8, txtmavt.getText());
                   int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã chỉnh sửa thành công");
                        ListVT();
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

    private void btnThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThêmActionPerformed
        conn = cn.getConnection();
        try {
            if (txtmavt.getText().equals("") || txttenvt.getText().equals("") || txtdonvitinh.getText().equals("") ||
                txtsoluongton.getText().equals("")|| txtdongianhap.getText().equals("") || txtdongiaban.getText().equals("") || txtmansx.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập dữ liệu vui lòng nhập lại");
            } else {     
                String sql_check = "select MaVT from vattu677 where MaVT=?";
                try (PreparedStatement pstCheck = conn.prepareStatement(sql_check)) {
                    pstCheck.setString(1, txtmavt.getText());
                    try (ResultSet rsCheck = pstCheck.executeQuery()) {
                        if (rsCheck.next()) {
                            JOptionPane.showMessageDialog(this, "Vật tư này đã tồn tại trong bảng");
                            return; 
                        }
                    }
                }                
                String phanloaira = "";
                if (btndogiadung.isSelected()) {
                    phanloaira = "Đồ gia dụng";
                } else if (btnthietbimay.isSelected()) {
                    phanloaira = "Thiết bị máy";
                }
                String sql_insert = "insert into vattu677 values (?, ?, ?, ?, ?, ?, ?,?)";
                try (PreparedStatement pstInsert = conn.prepareStatement(sql_insert)) {
                    pstInsert.setString(1, txtmavt.getText());
                    pstInsert.setString(2, txttenvt.getText());
                    pstInsert.setString(3, phanloaira);
                    pstInsert.setString(4, txtdonvitinh.getText());
                    pstInsert.setInt(5, Integer.parseInt(txtsoluongton.getText()));
                    pstInsert.setFloat(6, Float.parseFloat(txtdongianhap.getText()));
                    pstInsert.setFloat(7, Float.parseFloat(txtdongiaban.getText()));
                    pstInsert.setString(8, txtmansx.getText());

                    int result = pstInsert.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Chúc mừng bạn đã thêm mới vật tư thành công");
                        ListVT();
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

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
    LamMoi();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSắpXếpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSắpXếpActionPerformed
     try {
        List<VatTu> list = new ArrayList<>();
        for (int i = 0; i < tblvattu.getRowCount(); i++) {
            String maVtu = tblvattu.getValueAt(i, 1).toString(); 
            String tenVtu = tblvattu.getValueAt(i, 2).toString();
            String phanloaira = tblvattu.getValueAt(i, 3).toString();
            String dvtinh = tblvattu.getValueAt(i, 4).toString();
            int slton = Integer.parseInt(tblvattu.getValueAt(i, 5).toString());
            float dgnhap = Float.parseFloat(tblvattu.getValueAt(i, 6).toString());
            float dgban = Float.parseFloat(tblvattu.getValueAt(i, 7).toString());
            String maNsxuat = tblvattu.getValueAt(i, 8).toString();
            VatTu vt = new VatTu(maVtu,tenVtu,phanloaira,dvtinh,slton,dgnhap,dgban,maNsxuat );
            list.add(vt);
        }
        Collections.sort(list, Comparator.comparing(VatTu::getTenVT));
        tableModel.setRowCount(0); 
        int stt = 1; 
        for (VatTu vt : list) {
            Vector<Object> row = new Vector<>();
            row.add(stt++); 
            row.add(vt.getMaVT());
            row.add(vt.getTenVT());
            row.add(vt.getPhanLoai());
            row.add(vt.getDonViTinh());
            row.add(vt.getSoLuongTon());
            row.add(vt.getDonGiaNhap());
            row.add(vt.getDonGiaBan());
            row.add(vt.getMaNSX());
            tableModel.addRow(row);
        }
        tblvattu.setModel(tableModel); 
    } catch (Exception e) {
        e.printStackTrace();
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnSắpXếpActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btndogiadungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndogiadungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btndogiadungActionPerformed

  public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            GDVatTu frame = new GDVatTu();
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
    private javax.swing.JButton btnSắpXếp;
    private javax.swing.JButton btnSửa;
    private javax.swing.JButton btnThêm;
    private javax.swing.JButton btnTìmKiếm;
    private javax.swing.JButton btnXóa;
    private javax.swing.JRadioButton btndogiadung;
    private javax.swing.JRadioButton btnthietbimay;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblvattu;
    private javax.swing.JTextField txtdongiaban;
    private javax.swing.JTextField txtdongianhap;
    private javax.swing.JTextField txtdonvitinh;
    private javax.swing.JTextField txtgiaban1;
    private javax.swing.JTextField txtmansx;
    private javax.swing.JTextField txtmavt;
    private javax.swing.JLabel txtphai;
    private javax.swing.JTextField txtsoluongton;
    private javax.swing.JTextField txttenvt;
    // End of variables declaration//GEN-END:variables

    private void clearFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void While(boolean next) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
