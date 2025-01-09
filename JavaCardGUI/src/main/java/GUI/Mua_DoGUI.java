/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Model.LeftAlignHeaderRenderer;
import Model.LeftAlignRenderer;
import Model.Item;
import Model.SmartCard;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import service.ApiService;

/**
 *
 * @author ADMIN
 */
public class Mua_DoGUI extends javax.swing.JPanel {
    private final SmartCard SmartCard;
    private final ChucNang_NhanVien ChucNang_NhanVien;
    private ArrayList<Item> itemList;       // Available items
    private ArrayList<Item> addedList;     // Items added to cart
    private javax.swing.JPopupMenu listTablePopup;     // Popup menu for listTable
    private javax.swing.JPopupMenu addedTablePopup;    // Popup menu for addedTable
    private final ApiService service = new ApiService();
    private long balance;
    private long toPay = 0;
    /**
     * Creates new form Mua_DoGUI
     */
    public Mua_DoGUI(SmartCard SmartCard, ChucNang_NhanVien ChucNang_NhanVien) {
        this.SmartCard = SmartCard;
        this.ChucNang_NhanVien = ChucNang_NhanVien;
        itemList = new ArrayList<>();
        addedList = new ArrayList<>();
        initComponents();
        setBalanceToLabel();
        configureTableAlignment();
        setupPopupMenus();
        loadSampleItems(); // Load sample data into itemList
        updateListTable();
    }
    
    private void setBalanceToLabel() {
        try {
            balance = SmartCard.getBalance();
            balanceLabel.setText(String.valueOf(balance) + " VND");
        } catch (Exception e) {
            
        }
    }
    
    private void setupPopupMenus() {
        // Right-click menu for listTable
        listTablePopup = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem addItem = new javax.swing.JMenuItem("Thêm");
        addItem.addActionListener(e -> addProduct());
        listTablePopup.add(addItem);

        listTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    int row = listTable.rowAtPoint(evt.getPoint());
                    listTable.setRowSelectionInterval(row, row);
                    listTablePopup.show(listTable, evt.getX(), evt.getY());
                }
            }
        });

        // Right-click menu for addedTable
        addedTablePopup = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem removeItem = new javax.swing.JMenuItem("Bớt");
        removeItem.addActionListener(e -> removeProduct());
        addedTablePopup.add(removeItem);

        addedTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    int row = addedTable.rowAtPoint(evt.getPoint());
                    addedTable.setRowSelectionInterval(row, row);
                    addedTablePopup.show(addedTable, evt.getX(), evt.getY());
                }
            }
        });
    }
    
    private void addProduct() {
        int selectedRow = listTable.getSelectedRow();
        if (selectedRow < 0) return;

        String productName = (String) listTable.getValueAt(selectedRow, 1);
        int availableQuantity = (int) listTable.getValueAt(selectedRow, 2);
        long price = (long) listTable.getValueAt(selectedRow, 3); // Retrieve as long

        String input = JOptionPane.showInputDialog(this, "Nhập số lượng cần thêm:");
        if (input == null) return;

        try {
            int quantityToAdd = Integer.parseInt(input);
            if (quantityToAdd <= 0 || quantityToAdd > availableQuantity) {
                throw new IllegalArgumentException("Số lượng không hợp lệ.");
            }

            // Update the quantity in itemList
            for (Item item : itemList) {
                if (item.getName().equals(productName)) {
                    item.setQuantity(item.getQuantity() - quantityToAdd);
                    break;
                }
            }

            // Add to addedList
            boolean exists = false;
            for (Item item : addedList) {
                if (item.getName().equals(productName)) {
                    item.setQuantity(item.getQuantity() + quantityToAdd);
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                addedList.add(new Item(0, productName, quantityToAdd, price));
            }

            // Refresh tables
            updateListTable();
            updateAddedTable();
            updatePayMoneyLabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeProduct() {
        int selectedRow = addedTable.getSelectedRow();
        if (selectedRow < 0) return;

        String productName = (String) addedTable.getValueAt(selectedRow, 0);
        int currentQuantity = 0;

        // Find the current quantity in addedList
        for (Item item : addedList) {
            if (item.getName().equals(productName)) {
                currentQuantity = item.getQuantity();
                break;
            }
        }

        String input = JOptionPane.showInputDialog(this, "Nhập số lượng cần bớt:");
        if (input == null) return;

        try {
            int quantityToRemove = Integer.parseInt(input);
            if (quantityToRemove <= 0 || quantityToRemove > currentQuantity) {
                throw new IllegalArgumentException("Số lượng không hợp lệ.");
            }

            Item itemToRemove = null;

            // Update addedList and itemList
            for (Item item : addedList) {
                if (item.getName().equals(productName)) {
                    if (quantityToRemove == currentQuantity) {
                        itemToRemove = item; // Remove the item entirely
                    } else {
                        item.setQuantity(item.getQuantity() - quantityToRemove);
                    }
                    break;
                }
            }

            if (itemToRemove != null) {
                addedList.remove(itemToRemove);
            }

            // Restore quantity in itemList
            for (Item item : itemList) {
                if (item.getName().equals(productName)) {
                    item.setQuantity(item.getQuantity() + quantityToRemove);
                    break;
                }
            }

            // Refresh tables and labels
            updateListTable();
            updateAddedTable();
            updatePayMoneyLabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePayMoneyLabel() {
        toPay = 0;
        for (Item item : addedList) {
            toPay += item.getPrice() * item.getQuantity();
        }
        payMoneyLabel.setText(toPay + " VND");
    }

    private void updateListTable() {
        DefaultTableModel model = (DefaultTableModel) listTable.getModel();
        model.setRowCount(0);
        for (Item item : itemList) {
            model.addRow(new Object[]{item.getId(), item.getName(), item.getQuantity(), item.getPrice()});
        }
    }

    private void updateAddedTable() {
        DefaultTableModel model = (DefaultTableModel) addedTable.getModel();
        model.setRowCount(0);
        for (Item item : addedList) {
            model.addRow(new Object[]{item.getName(), item.getQuantity()});
        }
    }
    private void removeAddedTable(){
        DefaultTableModel model = (DefaultTableModel) addedTable.getModel();
        for(int i = 0; i<addedList.size() ;++i){
            model.removeRow(i);
            addedList.remove(i);
        }
    }
    private void loadSampleItems() {
        itemList.add(new Item(1, "A", 10, 10000));
        itemList.add(new Item(2, "B", 5, 20000));
        itemList.add(new Item(3, "C", 8, 15000));
        updateListTable();
    }
    
    private void configureTableAlignment() {
        // Right align cells
        DefaultTableCellRenderer leftAlignRenderer = new LeftAlignRenderer();
        for (int i = 0; i < listTable.getColumnModel().getColumnCount(); i++) {
            listTable.getColumnModel().getColumn(i).setCellRenderer(leftAlignRenderer);
        }
        for (int i = 0; i < addedTable.getColumnModel().getColumnCount(); i++) {
            addedTable.getColumnModel().getColumn(i).setCellRenderer(leftAlignRenderer);
        }

        // Right align headers
        LeftAlignHeaderRenderer leftAlignHeaderRenderer = new LeftAlignHeaderRenderer();
        listTable.getTableHeader().setDefaultRenderer(leftAlignHeaderRenderer);
        addedTable.getTableHeader().setDefaultRenderer(leftAlignHeaderRenderer);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        label8 = new java.awt.Label();
        balanceLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addedTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTable = new javax.swing.JTable();
        payButton = new javax.swing.JButton();
        payMoneyLabel = new javax.swing.JLabel();
        label9 = new java.awt.Label();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("MUA ĐỒ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        label8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label8.setText("Số dư:");

        balanceLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        balanceLabel.setText("0 VND");

        addedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        addedTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(addedTable);
        if (addedTable.getColumnModel().getColumnCount() > 0) {
            addedTable.getColumnModel().getColumn(0).setResizable(false);
            addedTable.getColumnModel().getColumn(0).setPreferredWidth(80);
            addedTable.getColumnModel().getColumn(1).setResizable(false);
            addedTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        listTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Giá tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(listTable);
        if (listTable.getColumnModel().getColumnCount() > 0) {
            listTable.getColumnModel().getColumn(0).setResizable(false);
            listTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            listTable.getColumnModel().getColumn(1).setResizable(false);
            listTable.getColumnModel().getColumn(1).setPreferredWidth(80);
            listTable.getColumnModel().getColumn(2).setResizable(false);
            listTable.getColumnModel().getColumn(2).setPreferredWidth(20);
            listTable.getColumnModel().getColumn(3).setResizable(false);
            listTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        payButton.setBackground(new java.awt.Color(153, 255, 153));
        payButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        payButton.setText("Thanh Toán");
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        payMoneyLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        payMoneyLabel.setText("0 VND");

        label9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label9.setText("Số tiền thanh toán:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(payButton)
                .addGap(227, 227, 227))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(payMoneyLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(balanceLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balanceLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(payMoneyLabel)
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(payButton)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        // TODO add your handling code here:
        if(balance < toPay){
            JOptionPane.showMessageDialog(this, "Không đủ tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else{
            byte[] cash = new byte[4];
            cash[0] = (byte)((toPay >> 24) & 0xff);
            cash[1] = (byte)((toPay >> 16) & 0xff);
            cash[2] = (byte)((toPay >> 8) & 0xff);
            cash[3] = (byte)((toPay) & 0xff);
            if(SmartCard.withdrawal(cash)) {
                service.withdrawal(toPay);
                JOptionPane.showMessageDialog(this,"Mua hàng thành công");
            } else {
                JOptionPane.showMessageDialog(this,"Xảy ra lỗi trong quá trình nạp");
            }   
        }
        setBalanceToLabel();
        removeAddedTable();
        updatePayMoneyLabel();
    }//GEN-LAST:event_payButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable addedTable;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTable listTable;
    private javax.swing.JButton payButton;
    private javax.swing.JLabel payMoneyLabel;
    // End of variables declaration//GEN-END:variables
}
