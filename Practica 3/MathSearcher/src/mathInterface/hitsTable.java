/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathInterface;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.lucene.document.Document;

/**
 *
 * @author Lothar Soto Palma
 * @author Daniel López García
 * @author Iván Calle Gil
 * @author José Carlos Entrena Jiménez
 */
public class hitsTable extends javax.swing.JPanel {

    /**
     * Creates new form hitsTable
     */
    private String[] columnas;
    private DefaultTableModel model;
    private final Class[] tiposColumnas;
    private ImageIcon icono;
    ArrayList<Document> hits;
    public hitsTable() {
        icono =new ImageIcon("./imgs/eyeSmall.png");
        tiposColumnas = new Class[]{java.lang.Integer.class,java.lang.String.class,JButton.class};
        columnas= new String[]{"Rank","Titulo","Ir"};
        initComponents();
        Table.setAutoCreateRowSorter(true);
        Table.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {},columnas) {
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                // Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                // observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                // Sobrescribimos este método para evitar que la columna que contiene los botones sea editada.
                return false;
            }
        });
        Table.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });
        Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = Table.rowAtPoint(e.getPoint());
                int columna = Table.columnAtPoint(e.getPoint());
                if (Table.getModel().getColumnClass(columna).equals(JButton.class)) {
                    URL url;
                    try {
                        url = new URL(hits.get(fila).get("Link").toString());
                        openWebpage(url);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(hitsTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        Table.getColumnModel().getColumn(0).setMaxWidth(50);
        Table.getColumnModel().getColumn(0).setMinWidth(50);
        Table.getColumnModel().getColumn(2).setMaxWidth(30);
        Table.getColumnModel().getColumn(2).setMinWidth(30);
        model=(DefaultTableModel)Table.getModel();
    }
    private static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void refreshTable(){
        for(int i = 0; i < model.getRowCount(); i++)
            model.removeRow(i);
        model.setRowCount(0);
        Table.setModel(model);
    }
    public void updateTable(ArrayList<Document> hits){
        this.hits=hits;
        refreshTable();
        Object data[] = new Object[3];
        for(int i = 0; i < hits.size(); i++){
            data[0] = i+1;
            data[1] = hits.get(i).get("Titulo");
            JButton boton=new JButton();
            boton.setIcon(icono);
            data[2] = boton;
            model.addRow(data);
        }
        
        Table.setModel(model);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rank", "Título", "Ir a enlace"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setRowHeight(25);
        Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setHeaderValue("Rank");
            Table.getColumnModel().getColumn(1).setHeaderValue("Título");
        }

        jLabel1.setText("Hits para la búsqueda:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
