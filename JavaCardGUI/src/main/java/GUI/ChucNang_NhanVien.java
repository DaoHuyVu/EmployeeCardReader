/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Model.SmartCard;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class ChucNang_NhanVien extends javax.swing.JFrame {
    private final SmartCard SmartCard;
    private JPanel onSelect = null;
    /**
     * Creates new form ChucNang_NhanVien
     */
    public ChucNang_NhanVien(SmartCard SmartCard) {
        this.SmartCard = SmartCard;
        initComponents();
        
        MainContent.setLayout(new BorderLayout());  // Set layout for MainContent
//        ThongTin a  = new ThongTin(SmartCard);  // Create ThongTin panel
//        MainContent.add(a, BorderLayout.CENTER);  // Add ThongTin to the center of MainContent

        MainContent.revalidate();  // Revalidate to refresh the layout
        MainContent.repaint();  // Repaint to ensure the changes are visible
    }

    public void setOnSelect(JPanel onSelect) {
        this.onSelect = onSelect;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nav = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        NameUser = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ChamCong = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        DoiPin = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Thoat = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        MuaDo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        MainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        nav.setBackground(new java.awt.Color(153, 255, 153));
        nav.setAlignmentX(0.0F);
        nav.setAlignmentY(0.0F);
        nav.setPreferredSize(new java.awt.Dimension(276, 544));

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Java Card\\TNV GUI\\JavaCardGUI\\src\\main\\java\\icon\\avt.png"));

        NameUser.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        NameUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameUser.setText("USER");
        NameUser.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel10.setText("HI !");

        ThongTin.setBackground(new java.awt.Color(153, 255, 153));
        ThongTin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ThongTinMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("THÔNG TIN CÁ NHÂN");

        javax.swing.GroupLayout ThongTinLayout = new javax.swing.GroupLayout(ThongTin);
        ThongTin.setLayout(ThongTinLayout);
        ThongTinLayout.setHorizontalGroup(
            ThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ThongTinLayout.setVerticalGroup(
            ThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongTinLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        ChamCong.setBackground(new java.awt.Color(153, 255, 153));
        ChamCong.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ChamCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ChamCongMousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("CHẤM CÔNG");

        javax.swing.GroupLayout ChamCongLayout = new javax.swing.GroupLayout(ChamCong);
        ChamCong.setLayout(ChamCongLayout);
        ChamCongLayout.setHorizontalGroup(
            ChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChamCongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        ChamCongLayout.setVerticalGroup(
            ChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChamCongLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        DoiPin.setBackground(new java.awt.Color(153, 255, 153));
        DoiPin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DoiPin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DoiPinMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("ĐỔI MÃ PIN");

        javax.swing.GroupLayout DoiPinLayout = new javax.swing.GroupLayout(DoiPin);
        DoiPin.setLayout(DoiPinLayout);
        DoiPinLayout.setHorizontalGroup(
            DoiPinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoiPinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DoiPinLayout.setVerticalGroup(
            DoiPinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoiPinLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        Thoat.setBackground(new java.awt.Color(153, 255, 153));
        Thoat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Thoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ThoatMousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Thoát");

        javax.swing.GroupLayout ThoatLayout = new javax.swing.GroupLayout(Thoat);
        Thoat.setLayout(ThoatLayout);
        ThoatLayout.setHorizontalGroup(
            ThoatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThoatLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        ThoatLayout.setVerticalGroup(
            ThoatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThoatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MuaDo.setBackground(new java.awt.Color(153, 255, 153));
        MuaDo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MuaDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MuaDoMousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("MUA ĐỒ");

        javax.swing.GroupLayout MuaDoLayout = new javax.swing.GroupLayout(MuaDo);
        MuaDo.setLayout(MuaDoLayout);
        MuaDoLayout.setHorizontalGroup(
            MuaDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MuaDoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MuaDoLayout.setVerticalGroup(
            MuaDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MuaDoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout navLayout = new javax.swing.GroupLayout(nav);
        nav.setLayout(navLayout);
        navLayout.setHorizontalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChamCong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(DoiPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MuaDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(navLayout.createSequentialGroup()
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(navLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(navLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(navLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(Thoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navLayout.setVerticalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(ThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MuaDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DoiPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Thoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        MainContent.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout MainContentLayout = new javax.swing.GroupLayout(MainContent);
        MainContent.setLayout(MainContentLayout);
        MainContentLayout.setHorizontalGroup(
            MainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        MainContentLayout.setVerticalGroup(
            MainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nav, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ThongTinMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThongTinMousePressed
            // TODO add your handling code here:
        if(onSelect == ThongTin) return;
        NhapPin NhapPin = new NhapPin(this, SmartCard, () -> {
            MainContent.removeAll();
            ThongTin tt = new ThongTin(SmartCard, this);
            MainContent.add(tt);
            MainContent.revalidate();
            MainContent.repaint();
            
            onSelect = ThongTin;
        }, () -> {
            onSelect = null;
        });
        NhapPin.setVisible(true);
    }//GEN-LAST:event_ThongTinMousePressed

    private void DoiPinMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoiPinMousePressed
        // TODO add your handling code here:
        if(onSelect == DoiPin) return;
        onSelect = DoiPin;
        MainContent.removeAll();
        DoiPin dp = new DoiPin(SmartCard, this);
        MainContent.add(dp);
        MainContent.revalidate();
        MainContent.repaint();
    }//GEN-LAST:event_DoiPinMousePressed

    private void ChamCongMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChamCongMousePressed
        // TODO add your handling code here:
        if(onSelect == ChamCong) return;
        onSelect = ChamCong;
        MainContent.removeAll();
        ChamCong cc = new ChamCong(SmartCard,this);
        MainContent.add(cc);
        MainContent.revalidate();
        MainContent.repaint();
    }//GEN-LAST:event_ChamCongMousePressed

    private void MuaDoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MuaDoMousePressed
        // TODO add your handling code here:
        if(onSelect == MuaDo) return;
        NhapPin NhapPin = new NhapPin(this, SmartCard, () -> {
            MainContent.removeAll();
            Mua_DoGUI md = new Mua_DoGUI();
            MainContent.add(md);
            MainContent.revalidate();
            MainContent.repaint();
            
            onSelect = MuaDo;
        }, () -> {
            onSelect = null;
        });
        NhapPin.setVisible(true);
    }//GEN-LAST:event_MuaDoMousePressed

    private void ThoatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoatMousePressed
        // TODO add your handling code here:
        KetNoi_GUI Ket_NoiGUI = new KetNoi_GUI();
        if(SmartCard.disconnectCard()) {
            onSelect = null;
            Ket_NoiGUI.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_ThoatMousePressed

  /**
     * @param args the command line arguments
     */
    // public static void main(String args[]) {
    //     /* Set the Nimbus look and feel */
    //     //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    //     /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
    //      * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
    //      */
    //     SmartCard SmartCard = new SmartCard();

    //     try {
    //         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
    //             if ("Nimbus".equals(info.getName())) {
    //                 javax.swing.UIManager.setLookAndFeel(info.getClassName());
    //                 break;
    //             }
    //         }
    //     } catch (ClassNotFoundException ex) {
    //         java.util.logging.Logger.getLogger(Chuc_NangGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //     } catch (InstantiationException ex) {
    //         java.util.logging.Logger.getLogger(Chuc_NangGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //     } catch (IllegalAccessException ex) {
    //         java.util.logging.Logger.getLogger(Chuc_NangGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //     } catch (javax.swing.UnsupportedLookAndFeelException ex) {
    //         java.util.logging.Logger.getLogger(Chuc_NangGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //     }
    //     //</editor-fold>

    //     /* Create and display the form */
    //     java.awt.EventQueue.invokeLater(new Runnable() {
    //         public void run() {
    //             new ChucNang_NhanVien(SmartCard).setVisible(true);
    //         }
    //     });
    // }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChamCong;
    private javax.swing.JPanel DoiPin;
    private javax.swing.JPanel MainContent;
    private javax.swing.JPanel MuaDo;
    private javax.swing.JLabel NameUser;
    private javax.swing.JPanel Thoat;
    private javax.swing.JPanel ThongTin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel nav;
    // End of variables declaration//GEN-END:variables

    private void enableEvents(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
