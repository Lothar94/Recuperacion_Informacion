/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import mathsearcher.MathSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author Lothar Soto Palma
 * @author Daniel López García
 * @author Iván Calle Gil
 * @author José Carlos Entrena Jiménez
 */
public class searchPanel extends javax.swing.JPanel {

    MathSearcher mainSearcher;
    String findType;
    String field;
    ArrayList<String> filterFields;
    ArrayList<String> filterFinds;
    Object[] fields;
    String[] intpoints = {"Año", "Página inicio", "Página fin"};
    String[] fieldSet1 = {"Todos","Titulo","Abstract","Autor","Año","Fuente","Palabras clave autor","Página inicio", "Página fin"};
    String[] fieldSet2 = {"Titulo","Abstract","Fuente"};
    /**
     * Creates new form searchPanel
     */
    public searchPanel() {
        initComponents();
        filterFinds = new ArrayList<>();
        filterFields = new ArrayList<>();
        advancedPanel1.setVisible(false);
        mainSearcher = new MathSearcher("../Index", "../Index/taxo");
        try {
            fields = mainSearcher.getFields().toArray();
            updateFields(fieldSet1);
        } catch (IOException ex) {
            Logger.getLogger(searchPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        field = fieldTypeBox.getSelectedItem().toString();
        findType = findTypeBox.getSelectedItem().toString();
        distanceBox.setVisible(false);
        distanceLabel.setVisible(false);
        filterPanel2.setFieldsBox(fieldTypeBox);
        idioma_select.setEnabled(false);
        tipo_select.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        findButton = new javax.swing.JButton();
        advancedButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idioma_select = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tipo_select = new javax.swing.JComboBox<>();
        findTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        searchInfo1 = new mathInterface.searchInfo();
        hitsTable1 = new mathInterface.hitsTable();
        jLabel3 = new javax.swing.JLabel();
        findTypeBox = new javax.swing.JComboBox<>();
        fieldTypeBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        advancedPanel1 = new mathInterface.advancedPanel();
        distanceBox = new javax.swing.JTextField();
        distanceLabel = new javax.swing.JLabel();
        filterPanel2 = new mathInterface.filterPanel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMinimumSize(new java.awt.Dimension(877, 400));

        findButton.setText("Buscar");
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        advancedButton.setText("Opciones Avanzadas");
        advancedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advancedButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Opciones de búsqueda:");

        idioma_select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos","English", "Spanish","Russian","Chinese" }));
        idioma_select.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                idioma_selectItemStateChanged(evt);
            }
        });

        jLabel5.setText("Idioma:");

        jLabel6.setText("Tipo de documento:");

        tipo_select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos","Article", "Conference paper", "Book Chapter","Article in press","Review","Conference review","Book","Erratum"}));
        tipo_select.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipo_selectItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tipo_select, 0, 159, Short.MAX_VALUE)
                            .addComponent(idioma_select, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(idioma_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        findTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                findTextFieldKeyPressed(evt);
            }
        });

        jLabel1.setText("Introduzca la búsqueda:");

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hitsTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(searchInfo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(searchInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hitsTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setText("Tipo de búsqueda:");

        findTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estándar", "Proximidad", "Exacta" }));
        findTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTypeBoxActionPerformed(evt);
            }
        });

        fieldTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abstract", "Titulo" }));
        fieldTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldTypeBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Campo de Búsqueda:");

        distanceBox.setText("0");

        distanceLabel.setText("Distancia:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(advancedButton))
                    .addComponent(jLabel1)
                    .addComponent(findTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(findTypeBox, 0, 136, Short.MAX_VALUE)
                            .addComponent(fieldTypeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(advancedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(distanceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(distanceBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filterPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(distanceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(distanceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(findTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fieldTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(advancedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findButton)
                    .addComponent(advancedButton))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void advancedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_advancedButtonActionPerformed
        // TODO add your handling code here:
        if (!advancedButton.getText().equals("Ocultar búsqueda por rango")) {
            advancedPanel1.setVisible(true);
            advancedButton.setText("Ocultar búsqueda por rango");
        } else {
            advancedPanel1.Clear();
            advancedPanel1.setVisible(false);
            advancedButton.setText("Búsqueda por rango");
        }
    }//GEN-LAST:event_advancedButtonActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        tipo_select.setSelectedIndex(0);
        idioma_select.setSelectedIndex(0);
        if (search()){
            updateFacetas("idioma");
            updateFacetas("tipo");
        }
        else 
            clearFacetas(); 
    }//GEN-LAST:event_findButtonActionPerformed

    private void findTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTypeBoxActionPerformed
        fieldTypeBox.setModel(new DefaultComboBoxModel(fields));
        field = fieldTypeBox.getSelectedItem().toString();
        findType = findTypeBox.getSelectedItem().toString();

        if (findTypeBox.getSelectedItem().toString().equals("Proximidad")) {
            distanceBox.setVisible(true);
            distanceLabel.setVisible(true);
        } else {
            distanceBox.setVisible(false);
            distanceLabel.setVisible(false);
        }
        try{
            if (findTypeBox.getSelectedItem().toString().equals("Proximidad") || findTypeBox.getSelectedItem().toString().equals("Exacta")){
                updateFields(fieldSet2);
            }else{
                updateFields(fieldSet1);
            }
        }catch (IOException ex) {
            Logger.getLogger(searchPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_findTypeBoxActionPerformed

    private void fieldTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldTypeBoxActionPerformed
        field = fieldTypeBox.getSelectedItem().toString();
    }//GEN-LAST:event_fieldTypeBoxActionPerformed
    public void filter() {
        ArrayList<javax.swing.JComboBox> fields = null;
        ArrayList<javax.swing.JTextField> finds = null;

        fields = filterPanel2.getFilterFields();
        finds = filterPanel2.getFilterTextFields();
        
        filterFields.add((String) fieldTypeBox.getSelectedItem());
        for(int i = 0; i< fields.size(); i++)
            if(!finds.get(i).getText().equals(""))
                filterFields.add((String) fields.get(i).getSelectedItem());
        
        filterFinds.add(findTextField.getText()) ;
        for(int i = 0; i< finds.size(); i++)
            if(!finds.get(i).getText().equals(""))
                filterFinds.add(finds.get(i).getText());       
    }
    
    private void idioma_selectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_idioma_selectItemStateChanged
        if(evt.getStateChange()==1)
            search();
    }//GEN-LAST:event_idioma_selectItemStateChanged

    private void tipo_selectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipo_selectItemStateChanged
        if(evt.getStateChange()==1)
            search();
    }//GEN-LAST:event_tipo_selectItemStateChanged

    private void findTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findTextFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            tipo_select.setSelectedIndex(0);
            idioma_select.setSelectedIndex(0);
           if (search()){
                updateFacetas("idioma");
                updateFacetas("tipo");
           }
           else
                clearFacetas(); 
        }
    }//GEN-LAST:event_findTextFieldKeyPressed

    public boolean search() {
        idioma_select.setEnabled(true);
        tipo_select.setEnabled(true);
        ArrayList<Document> hits = null;
        try {
            filter();
            String[] fieldNames = {"Idioma", "Tipo de documento"};
            String idioma = idioma_select.getSelectedItem().toString().toLowerCase().split("[(]")[0];
            String tipo = tipo_select.getSelectedItem().toString().toLowerCase().split("[(]")[0];

            String[] fieldValues = {idioma, tipo};
            
            if (findTypeBox.getSelectedItem().toString().equals("Proximidad")) {
                hits = mainSearcher.search(Integer.parseInt(distanceBox.getText()), filterFields, filterFinds, fieldNames, fieldValues, advancedPanel1.GetField(), advancedPanel1.GetRange(), 50000);
            } else if (findTypeBox.getSelectedItem().toString().equals("Exacta")) {
                hits = mainSearcher.search(0, filterFields, filterFinds, fieldNames, fieldValues, advancedPanel1.GetField(), advancedPanel1.GetRange(), 50000);
            } else {
                hits = mainSearcher.search(-1, filterFields, filterFinds, fieldNames, fieldValues, advancedPanel1.GetField(), advancedPanel1.GetRange(), 50000);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(searchPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringBuilder sb =new StringBuilder();
        for(int i=1;filterFields.size()>i;i++){
            sb.append(filterFields.get(i));
            sb.append("->");
            sb.append(filterFinds.get(i));
            sb.append(";");
        }
        searchInfo1.updateInfo(findTextField.getText(), (String) findTypeBox.getSelectedItem(),sb.toString(),advancedPanel1.GetInfo(), hits);

        hitsTable1.updateTable(hits);
        
        filterFinds.clear();
        filterFields.clear();
        return !hits.isEmpty();
    }

    public void updateFields(Object[] f) throws IOException {
        fieldTypeBox.setModel(new DefaultComboBoxModel(f));
    }

    // actualiza la faceta indicada
    public void updateFacetas(String faceta) {
        if (! mainSearcher.getFacets().isEmpty()){
            ArrayList<FacetResult> facetas = mainSearcher.getFacets();

            if (faceta.equals("idioma")) {
                String[] idiomas = new String[facetas.get(0).labelValues.length + 1];
                idiomas[0] = "Todos";
                
                String idioma_actual = (String) idioma_select.getSelectedItem();
                idioma_actual= idioma_actual.split("[(]")[0];
                
                for (int i = 0; i < facetas.get(0).labelValues.length; i++) {
                    idiomas[i + 1] = facetas.get(0).labelValues[i].label + "(" + facetas.get(0).labelValues[i].value + ")";
                    if (facetas.get(0).labelValues[i].label.equals(idioma_actual))
                            idioma_actual = idiomas[i+1];
                }
                idioma_select.setModel(new DefaultComboBoxModel(idiomas));
                idioma_select.setSelectedItem(idioma_actual);
            }    
            else{  
                if (faceta.equals("tipo")) {
                    String[] tipo = new String[facetas.get(1).labelValues.length + 1];
                    tipo[0] = "Todos";
                    
                    String tipo_actual = (String) tipo_select.getSelectedItem();
                    tipo_actual= tipo_actual.split("[(]")[0];
                    

                    for (int i = 0; i < facetas.get(1).labelValues.length; i++) {
                        tipo[i + 1] = facetas.get(1).labelValues[i].label + "(" + facetas.get(1).labelValues[i].value + ")";
                        if (facetas.get(1).labelValues[i].label.equals(tipo_actual))
                            tipo_actual = tipo[i+1];
                    }
                    

                    tipo_select.setModel(new DefaultComboBoxModel(tipo));
                    tipo_select.setSelectedItem(tipo_actual);
                }
            }
        }
    }
    
    public void clearFacetas(){
        
        String[] documentos = new String[1]; 
        documentos[0] = "Todos"; 
        tipo_select.setModel(new DefaultComboBoxModel(documentos)); 
        
        String[] idiomas = new String[1]; 
        idiomas[0] = "Todos";
        idioma_select.setModel(new DefaultComboBoxModel(idiomas));
        
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton advancedButton;
    private mathInterface.advancedPanel advancedPanel1;
    private javax.swing.JTextField distanceBox;
    private javax.swing.JLabel distanceLabel;
    private javax.swing.JComboBox<String> fieldTypeBox;
    private mathInterface.filterPanel filterPanel2;
    private javax.swing.JButton findButton;
    private javax.swing.JTextField findTextField;
    private javax.swing.JComboBox<String> findTypeBox;
    private mathInterface.hitsTable hitsTable1;
    private javax.swing.JComboBox<String> idioma_select;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private mathInterface.searchInfo searchInfo1;
    private javax.swing.JComboBox<String> tipo_select;
    // End of variables declaration//GEN-END:variables
}
