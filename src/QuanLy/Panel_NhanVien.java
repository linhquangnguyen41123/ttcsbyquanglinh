/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package QuanLy;
import static DAO.DAOHoaDon.toMysqlDateStr;
import QLVT_BY_LINH.connectDB;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ttcs.Nhanvien;
/**
 *
 * @author Anh Kiet
 */
public class Panel_NhanVien extends javax.swing.JPanel {
    final String header[]={"MANV","HONV","TENNV","PHAI","NGAYSINH","SĐT","EMAIL","VAITRO"};
    final DefaultTableModel tb= new DefaultTableModel(header,0);
    connectDB cn= new connectDB();
    Connection conn=null;
    ResultSet rs;
    private String sql;
    public void loadBang() {
    try {
        conn = cn.getConnection();
        int number;
        Vector row;
         String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1";
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
        if (metadata.getColumnName(i).equals("NgaySinh")) {
            // Định dạng lại ngày tháng từ yyyy-MM-dd thành dd/MM/yyyy
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngaySinhFormatted = dateFormat.format(rs.getDate(i));
            row.addElement(ngaySinhFormatted);
        } else {
            row.addElement(rs.getString(i));
        }
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
    /**
     * Creates new form Panel_NhanVien
     */
    public Panel_NhanVien() {
        initComponents();
        loadBang();
        txtmanv.setEnabled(false);
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
public static boolean isValidDate(String dateStr) {
    // Kiểm tra nếu chuỗi rỗng hoặc null
    if (dateStr == null || dateStr.isEmpty()) {
        return false;
    }

    // Biểu thức chính quy để kiểm tra định dạng ngày tháng (dd/MM/yyyy)
    String regex = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$";

    // Kiểm tra nếu chuỗi không khớp với định dạng dd/MM/yyyy
    if (!dateStr.matches(regex)) {
        return false;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    sdf.setLenient(false); // Tắt tính linh hoạt của SimpleDateFormat

    try {
        // Thử phân tích chuỗi thành ngày tháng
        Date parsedDate = sdf.parse(dateStr);

        // Kiểm tra ngày sinh không vượt quá năm hiện tại
        Calendar calNow = Calendar.getInstance();
        Calendar calDOB = Calendar.getInstance();
        calDOB.setTime(parsedDate);
        
        // Kiểm tra ngày sinh có ít nhất 18 tuổi
        calDOB.add(Calendar.YEAR, 18); // Thêm 18 năm vào ngày sinh
        if (calDOB.compareTo(calNow) > 0) {
            return false; // Nếu ngày sinh đã vượt quá 18 năm so với ngày hiện tại
        }

        // Nếu mọi thứ đều hợp lệ, trả về true
        return true;
    } catch (ParseException ex) {
        // Nếu không thể phân tích chuỗi thành ngày tháng, trả về false
        return false;
    }
}
 private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
 // Hàm kiểm tra email trùng lặp
private boolean isEmailDuplicate(String email) {
    String checkEmailQuery = "SELECT COUNT(*) FROM NhanVien WHERE Email = ?";
    try (PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailQuery)) {
        checkEmailStmt.setString(1, email);
        ResultSet emailResult = checkEmailStmt.executeQuery();
        if (emailResult.next() && emailResult.getInt(1) > 0) {
            return true; // Email bị trùng
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Email không bị trùng
}

// Hàm kiểm tra số điện thoại trùng lặp
private boolean isPhoneDuplicate(String phone) {
    String checkPhoneQuery = "SELECT COUNT(*) FROM NhanVien WHERE SoDT = ?";
    try (PreparedStatement checkPhoneStmt = conn.prepareStatement(checkPhoneQuery)) {
        checkPhoneStmt.setString(1, phone);
        ResultSet phoneResult = checkPhoneStmt.executeQuery();
        if (phoneResult.next() && phoneResult.getInt(1) > 0) {
            return true; // Số điện thoại bị trùng
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Số điện thoại không bị trùng
}



// Hàm lấy mã tự động từ hàm tạo mã trong SQL Server
private String getAutoGeneratedID(Connection connection, String functionName) {
    try {
        String sql = "SELECT DBO." + functionName + "()";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public void loadBang1() {
    try {
        conn = cn.getConnection();
        int number;
        Vector row;
        String sql = "SELECT * FROM NhanVien WHERE TrangThai = 0";
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
        if (metadata.getColumnName(i).equals("NgaySinh")) {
            // Định dạng lại ngày tháng từ yyyy-MM-dd thành dd/MM/yyyy
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngaySinhFormatted = dateFormat.format(rs.getDate(i));
            row.addElement(ngaySinhFormatted);
        } else {
            row.addElement(rs.getString(i));
        }
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnhanvien = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtphai = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        txtten = new javax.swing.JTextField();
        txtsđt = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        btnNam = new javax.swing.JRadioButton();
        btnNu = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtho = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtngaysinh = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        txttk = new javax.swing.JTextField();
        btnload = new javax.swing.JButton();
        btndx = new javax.swing.JButton();
        btnkp = new javax.swing.JButton();
        btnThêm = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSửa = new javax.swing.JButton();
        btnNhapmoi = new javax.swing.JButton();

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 153));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("QUẢN LÝ NHÂN VIÊN");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        tblnhanvien.setBackground(new java.awt.Color(204, 255, 255));
        tblnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MANV", "HỌ", "TÊN", "PHÁI", "NGAYSINH", "SỐ ĐT", "EMAIL", "VAITRO"
            }
        ));
        tblnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblnhanvien);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("MÃ NHÂN VIÊN:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("HỌ:");

        txtphai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtphai.setText("PHÁI:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("NGÀY SINH: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("SỐ ĐIỆN THOẠI:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("EMAIL:");

        txtmanv.setEnabled(false);

        btnNam.setSelected(true);
        btnNam.setText("Nam");

        btnNu.setText("Nữ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("TÊN:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("VAI TRÒ:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "QuanLy", "NhanVien" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        txtngaysinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngaysinhActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Thông tin nhân viên");

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
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtsđt))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(36, 36, 36)
                                .addComponent(txtngaysinh))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(txtphai)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtho)
                                    .addComponent(txtten)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnNam)
                                        .addGap(33, 33, 33)
                                        .addComponent(btnNu)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtemail)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtten, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtphai, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNam)
                        .addComponent(btnNu)))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtsđt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(105, 105, 105))
        );

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-zoom-to-actual-size-20.png"))); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txttk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttkActionPerformed(evt);
            }
        });

        btnload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Apply (1).png"))); // NOI18N
        btnload.setText("Load bảng");
        btnload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloadActionPerformed(evt);
            }
        });

        btndx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btndx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-save-20.png"))); // NOI18N
        btndx.setText("NV đã xóa");
        btndx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndxActionPerformed(evt);
            }
        });

        btnkp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnkp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset-20.png"))); // NOI18N
        btnkp.setText("Khôi Phục");
        btnkp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkpActionPerformed(evt);
            }
        });

        btnThêm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThêm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-plus-20.png"))); // NOI18N
        btnThêm.setText("Thêm");
        btnThêm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThêmActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-clear-symbol-20.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSửa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSửa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-edit-20.png"))); // NOI18N
        btnSửa.setText("Sửa");
        btnSửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSửaActionPerformed(evt);
            }
        });

        btnNhapmoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhapmoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Refresh.png"))); // NOI18N
        btnNhapmoi.setText("Nhập Mới");
        btnNhapmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapmoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSửa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNhapmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnload)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnkp, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(txttk, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 1220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 866, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSửa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txttk)
                        .addComponent(btnload, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btndx, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnkp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThêm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNhapmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void tblnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnhanvienMouseClicked
        int x=tblnhanvien.getSelectedRow();
        if(x>=0){
            txtmanv.setText(tblnhanvien.getValueAt(x, 1)+"");
            txtho.setText(tblnhanvien.getValueAt(x, 2)+"");
            txtten.setText(tblnhanvien.getValueAt(x, 3)+"");
            String gioiTinh = tblnhanvien.getValueAt(x, 4).toString();
            if (gioiTinh.equals("1")) {
                btnNam.setSelected(true);
                btnNu.setSelected(false);
            } else if (gioiTinh.equals("0")) {
                btnNam.setSelected(false);
                btnNu.setSelected(true);
            } else {
                btnNam.setSelected(false);
                btnNu.setSelected(false);
            }
            txtngaysinh.setText(tblnhanvien.getValueAt(x, 5)+"");
            txtsđt.setText(tblnhanvien.getValueAt(x, 6)+"");
            txtemail.setText(tblnhanvien.getValueAt(x, 7)+"");
            // Lấy vai trò từ bảng và cài đặt vào JComboBox
            String vaiTro = tblnhanvien.getValueAt(x, 8).toString();
            jComboBox1.setSelectedItem(vaiTro);
            txtmanv.setEnabled(false);
        }

    }//GEN-LAST:event_tblnhanvienMouseClicked

    private void btnThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThêmActionPerformed
        conn = cn.getConnection();

        // Kiểm tra nhập đủ dữ liệu
        if (txtten.getText().isEmpty() || txtho.getText().isEmpty() || txtngaysinh.getText().isEmpty() ||
            txtsđt.getText().isEmpty() || txtemail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập đủ dữ liệu");
            return; // Thoát khỏi phương thức nếu không đủ dữ liệu
        }

        // Kiểm tra độ dài số điện thoại và chỉ chứa chữ số
        String phoneNumber = txtsđt.getText();
        if (!phoneNumber.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có độ dài từ 10 đến 11 chữ số và chỉ chứa chữ số.");
            return; // Thoát khỏi phương thức nếu số điện thoại không hợp lệ
        }

        // Kiểm tra số điện thoại trùng lặp
        if (isPhoneDuplicate(txtsđt.getText())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại. Vui lòng sử dụng số điện thoại khác.");
            return; // Thoát khỏi phương thức nếu số điện thoại bị trùng
        }
        // Kiểm tra định dạng ngày sinh
            if (!isValidDate(txtngaysinh.getText())) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng dd/MM/yyyy.");
                return; // Thoát khỏi phương thức nếu ngày sinh không hợp lệ
            }
        
        // Kiểm tra định dạng email
        if (!isValidEmail(txtemail.getText())) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập lại.");
            return; // Thoát khỏi phương thức nếu email không hợp lệ
        };

        // Kiểm tra email trùng lặp
        if (isEmailDuplicate(txtemail.getText())) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại. Vui lòng sử dụng email khác.");
            return; // Thoát khỏi phương thức nếu email bị trùng
        }

        String sql_insert = "INSERT INTO NhanVien (MaNV, Ho, Ten, GioiTinh, NgaySinh, SoDT, Email, VaiTro, TrangThai) VALUES (DBO.AUTO_IDNV(), ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstInsert = conn.prepareStatement(sql_insert)) {
            pstInsert.setString(1, txtho.getText());
            pstInsert.setString(2, txtten.getText());

            // Lấy giá trị của giới tính từ nút radio được chọn
            if (btnNam.isSelected()) {
                pstInsert.setBoolean(3, true); // Nam
            } else if (btnNu.isSelected()) {
                pstInsert.setBoolean(3, false); // Nữ
            } else {
                pstInsert.setNull(3, java.sql.Types.BOOLEAN); // Giả sử không có giới tính được chọn
            }

            // Chuyển đổi chuỗi đại diện cho ngày tháng thành kiểu java.sql.Date

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Tắt tính linh hoạt của SimpleDateFormat
            java.util.Date parsedDate = sdf.parse(txtngaysinh.getText());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            pstInsert.setDate(4, sqlDate);

            pstInsert.setString(5, txtsđt.getText());
            pstInsert.setString(6, txtemail.getText());
            String vaiTro = jComboBox1.getSelectedItem().toString();
            pstInsert.setString(7, vaiTro);
            pstInsert.setInt(8, 1);
            int kq = pstInsert.executeUpdate();

            if (kq > 0) {
                JOptionPane.showMessageDialog(this, "Thêm mới thành công");
                loadBang();
                xoatrang();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Ghi log lỗi vào hệ thống log
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Panel_NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Ghi log lỗi vào hệ thống log
            }
        }
    }//GEN-LAST:event_btnThêmActionPerformed

    private void btnSửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSửaActionPerformed
        //MaNV, Ho, Ten, GioiTinh, NgaySinh, SoDT, Email
        conn = cn.getConnection();
        try {
            if (txtmanv.getText().isEmpty() || txtho.getText().isEmpty() || txtten.getText().isEmpty() || txtngaysinh.getText().isEmpty() ||
                txtsđt.getText().isEmpty() || txtemail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn cần nhập đủ dữ liệu");
                return;
            }
            // Kiểm tra định dạng ngày sinh
            if (!isValidDate(txtngaysinh.getText())) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng dd/MM/yyyy.");
                return; // Thoát khỏi phương thức nếu ngày sinh không hợp lệ
            }

            // Kiểm tra độ dài số điện thoại và chỉ chứa chữ số
            String phoneNumber = txtsđt.getText();
            if (!phoneNumber.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải có độ dài từ 10 đến 11 chữ số và chỉ chứa chữ số.");
                return; // Thoát khỏi phương thức nếu số điện thoại không hợp lệ
            }

            // Kiểm tra số điện thoại trùng lặp
            if (isPhoneDuplicate(txtsđt.getText())) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại. Vui lòng sử dụng số điện thoại khác.");
                return; // Thoát khỏi phương thức nếu số điện thoại bị trùng
            }

            // Kiểm tra định dạng email
            if (!isValidEmail(txtemail.getText())) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập lại.");
                return; // Thoát khỏi phương thức nếu email không hợp lệ
            };

            // Kiểm tra email trùng lặp
            if (isEmailDuplicate(txtemail.getText())) {
                JOptionPane.showMessageDialog(this, "Email đã tồn tại. Vui lòng sử dụng email khác.");
                return; // Thoát khỏi phương thức nếu email bị trùng
            }

            else {
                String gioiTinh = "";
                if (btnNam.isSelected()) {
                    gioiTinh = "1"; // Nam
                } else if (btnNu.isSelected()) {
                    gioiTinh = "0"; // Nữ
                }
                // Chuyển đổi chuỗi đại diện cho ngày tháng thành kiểu java.sql.Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsedDate = sdf.parse(txtngaysinh.getText());
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                String sql = "UPDATE NhanVien SET Ho=?, Ten=?, GioiTinh=?, NgaySinh=?, SoDT=?, Email=?, VaiTro=? WHERE MaNV=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, txtho.getText());
                    preparedStatement.setString(2, txtten.getText());
                    preparedStatement.setString(3, gioiTinh);
                    preparedStatement.setDate(4, sqlDate);
                    preparedStatement.setString(5, txtsđt.getText());
                    preparedStatement.setString(6, txtemail.getText());
                    String vaiTro = jComboBox1.getSelectedItem().toString();
                    preparedStatement.setString(7, vaiTro);
                    preparedStatement.setString(8, txtmanv.getText());

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

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        conn = cn.getConnection();
        try {
            String sql = "UPDATE nhanvien SET TrangThai = 0 WHERE MANV=? AND TrangThai = 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Kiểm tra xem mã sinh viên có giá trị không trống
            if (txtmanv.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
                return;
            }

            ps.setString(1, txtmanv.getText());

            int chk = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa chứ !", "Thông báo!", JOptionPane.YES_NO_OPTION);
            if (chk == JOptionPane.YES_OPTION) {
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    xoatrang();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadBang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa không thành công! Mã sinh viên không tồn tại hoặc đã bị xóa trước đó.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối tại đây nếu cần
        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNhapmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapmoiActionPerformed
        txtmanv.setText("");
        txtho.setText("");
        txtten.setText("");
        txtngaysinh.setText("");
        txtsđt.setText("");
        txtemail.setText("");
        txtmanv.setEnabled(true);   
        txtmanv.setEnabled(false);
    }//GEN-LAST:event_btnNhapmoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // MaNV, Ho, Ten, GioiTinh, NgaySinh, SoDT, Email, VaiTro
        try {
            conn = cn.getConnection();
            String keyword = txttk.getText(); // Lấy từ khóa từ TextField

            if (keyword != null && !keyword.isEmpty()) {
                // Sửa lại câu truy vấn SQL để chỉ tìm kiếm trong các cột quan trọng
                String sql = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR Ho LIKE ? OR Ten LIKE ? OR GioiTinh LIKE ? OR NgaySinh LIKE ? OR SoDT LIKE ? OR Email LIKE ? OR VaiTro LIKE ?";

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    for (int i = 1; i <= 8; i++) {
                        preparedStatement.setString(i, "%" + keyword + "%");
                    }

                    ResultSet rs = preparedStatement.executeQuery();
                    tb.setRowCount(0);

                    int stt = 1;
                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        row.add(stt++);
                        for (int i = 1; i <= 8; i++) {
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

    private void txttkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttkActionPerformed

    private void btnkpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkpActionPerformed
        conn = cn.getConnection();
        try {
            String sql = "UPDATE nhanvien SET TrangThai = 1 WHERE MANV=? AND TrangThai = 0";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Kiểm tra xem mã nhân viên có giá trị không trống
            if (txtmanv.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần khôi phục!");
                return;
            }

            ps.setString(1, txtmanv.getText());

            int chk = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn khôi phục chứ !", "Thông báo!", JOptionPane.YES_NO_OPTION);
            if (chk == JOptionPane.YES_OPTION) {
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    xoatrang();
                    JOptionPane.showMessageDialog(this, "Khôi phục thành công!");
                    loadBang1();
                } else {
                    JOptionPane.showMessageDialog(this, "Khôi phục không thành công! Mã nhân viên không tồn tại hoặc đã được khôi phục trước đó.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối tại đây nếu cần
        }
    }//GEN-LAST:event_btnkpActionPerformed

    private void btndxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndxActionPerformed
        // TODO add your handling code here:
        loadBang1();
    }//GEN-LAST:event_btndxActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtngaysinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngaysinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngaysinhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnNam;
    private javax.swing.JButton btnNhapmoi;
    private javax.swing.JRadioButton btnNu;
    private javax.swing.JButton btnSửa;
    private javax.swing.JButton btnThêm;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btndx;
    private javax.swing.JButton btnkp;
    private javax.swing.JButton btnload;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JTextField txttk;
    // End of variables declaration//GEN-END:variables
}
