package gui;
import business_logic.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** In this windows the user is asked to give a name for a table save.
 * Then the actual table is saved to that name either as a new record or an update to
 * an existing one.
 *
 * @author valyis
 */

public class FrameSave extends javax.swing.JFrame

{
    private JFrame frameBack;
    Model model;
    Connection con = null;
    public FrameSave(JFrame frame1, Model model)
    {
        initComponents();
        this.frameBack=frame1;
        this.model = model;
        frameBack.setVisible(false);
        setEnabled(true);
        setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSave = new javax.swing.JButton();
        jTextFieldName = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jLabelName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Open Position");
        setAlwaysOnTop(true);

        jButtonSave.setText("Save");
        jButtonSave.setEnabled(false);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jTextFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNameActionPerformed(evt);
            }
        });
        jTextFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNameKeyTyped(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelName.setText("Name of map");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButtonSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /** This event hsndler accepts events on the button "Save". It takes the connection with the data base table 
     * and then the task of the object is performed.
     * 
     * 
     * @param evt 
     */
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        String newName =  jTextFieldName.getText().trim();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrameSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (newName == null || newName.length() == 0) {JOptionPane.showMessageDialog(this,"Nulla név");} else
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mestint", "mestint", "tV3pCERG4X1QzI7t");
            PreparedStatement st = con.prepareStatement("SELECT name FROM FoxAndHounds WHERE name=?");
            st.setString(1,newName);
            st.execute();
            if(st.getResultSet().next())
            {
                System.out.println("UPDATE - Már létezett ilyen mentés.");
                PreparedStatement preparedStmt = con.prepareStatement("UPDATE FoxAndHounds set position = ? WHERE name = ?");
                preparedStmt.setString(1, newName );
                preparedStmt.setString(2, model.toXML());
                preparedStmt.executeUpdate();
            }
            else
            {
                System.out.println("INSERT - Még nem létezett ilyen mentés.");
                PreparedStatement preparedStmt = con.prepareStatement("INSERT INTO FoxAndHounds (Name, Position) VALUES (?,?)");
                preparedStmt.setString(1, newName);
                preparedStmt.setString(2, model.toXML());
                preparedStmt.executeUpdate();

            }
        } 
        catch (SQLException hiba)
        {
             System.out.println("SQL hiba no. " +hiba.getSQLState());
        }
        finally {
                    if (con != null) try {
                        con.close();
                    } catch (SQLException ex) {
                        System.out.println("Az adatbázis-kapcsolat nem zárult le.");
                    }
            frameBack.setEnabled(true);
        setVisible(false);
        frameBack.setVisible(true);
        dispose();
      }
        
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        frameBack.setEnabled(true);
        setVisible(false);
        frameBack.setVisible(true);
        dispose();
        
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNameActionPerformed
        String newName =  jTextFieldName.getText().trim();
        if (newName != null && newName.length() != 0) 
            jButtonSave.setEnabled(true);
        else
            jButtonSave.setEnabled(false);
    }//GEN-LAST:event_jTextFieldNameActionPerformed

    private void jTextFieldNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNameKeyTyped
        String newName =  jTextFieldName.getText().trim();
        if (newName != null && newName.length() != 0) 
            jButtonSave.setEnabled(true);
        else
            jButtonSave.setEnabled(false);
                                                  
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNameKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
