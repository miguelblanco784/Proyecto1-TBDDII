/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1.tbddii;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import org.bson.Document;

/**
 *
 * @author migue
 */
public class MainGUI extends javax.swing.JFrame {

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();

        //Creacion Conexion
        try {
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.SEVERE);

            MongoClient mongoClient = MongoClients.create(
                    "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");
            //mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority
            MongoDatabase database = mongoClient.getDatabase("test");

            //createCollectionProductos(mongoClient);
            //deleteCollectionProductos(mongoClient);
            //updateCollectionProductos(mongoClient);
            listCollectionProductos(mongoClient);
            listCollectionAlmacenes(mongoClient);
            listCollectionFarmaceuticos(mongoClient);
            listCollectionFarmacias(mongoClient);
            listCollectionLaboratorios(mongoClient);
            listCollectionPropietarios(mongoClient);
            listCollectionFarmaciasVenta(mongoClient);

            /*MongoIterable<String> string = mongoClient.listDatabaseNames();
            MongoCursor<String> cursor = string.cursor();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }*/
        } catch (Exception e) {
            System.out.println("Se produjo un error " + e.getMessage());
        }
        System.out.println("La conexion a la base de datos fue exitosa");
        //Fin de la Conexion

    }

    public void createCollectionProductos(MongoClient mongoClient) {
        //MongoColletion<Document> farmacia = mongoClient.getDatabase(databaseName:"SistemaFarmacias").getCollection(collectionName:"");
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", 6).append("nombre", "Amlodipino").append("fabricante", "Fabs").append("tipo", "Antihipertensores").append("finprincipal", "Relajar la hipertensión").append("isProtegido", false);
        farmacia.insertOne(doc);
    }

    public void deleteCollectionProductos(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

    public void updateCollectionProductos(MongoClient mongoClient) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", 6);
        farmacia.updateOne(doc, Updates.set("fabricante", "Minox"));
    }

    public void listCollectionProductos(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_productos.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_productos.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionAlmacenes(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_almacenes.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_almacenes.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmaceuticos(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmaceuticos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_farmaceuticos.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_farmaceuticos.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmacias(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_farmacias.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println("toJson");
                System.out.println(document.toJson());
            }

            jl_farmacias.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionLaboratorios(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("laboratorios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_laboratorios.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_laboratorios.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionPropietarios(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_propietarios.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_propietarios.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
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

        CRUD = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jl_almacenes = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jl_farmaceuticos = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jl_farmacias = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jl_laboratorios = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jl_propietarios = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jl_productos = new javax.swing.JList<>();
        COMPRA = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jl_compra = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        CRUD.setPreferredSize(new java.awt.Dimension(1280, 820));

        jButton17.setText("Modificar");

        jButton18.setText("Eliminar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("Crear");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jl_almacenes.setModel(new DefaultListModel());
        jScrollPane7.setViewportView(jl_almacenes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane7)
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton17, jButton18, jButton19});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton17, jButton18, jButton19});

        jTabbedPane1.addTab("Almacenes", jPanel1);

        jButton11.setText("Modificar");

        jButton12.setText("Eliminar");

        jButton13.setText("Crear");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jl_farmaceuticos.setModel(new DefaultListModel());
        jScrollPane8.setViewportView(jl_farmaceuticos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane8)
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton11, jButton12, jButton13});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton11, jButton12, jButton13});

        jTabbedPane1.addTab("Farmaceuticos", jPanel3);

        jButton8.setText("Modificar");

        jButton9.setText("Eliminar");

        jButton10.setText("Crear");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jl_farmacias.setModel(new DefaultListModel());
        jScrollPane9.setViewportView(jl_farmacias);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane9)
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton8, jButton9});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton10, jButton8, jButton9});

        jTabbedPane1.addTab("Farmacias", jPanel4);

        jButton2.setText("Modificar");

        jButton3.setText("Eliminar");

        jButton4.setText("Crear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jl_laboratorios.setModel(new DefaultListModel());
        jScrollPane10.setViewportView(jl_laboratorios);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane10)
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        jTabbedPane1.addTab("Laboratorios", jPanel6);

        jPanel2.setFocusTraversalPolicyProvider(true);

        jButton14.setText("Modificar");

        jButton15.setText("Eliminar");

        jButton16.setText("Crear");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jl_propietarios.setModel(new DefaultListModel());
        jScrollPane11.setViewportView(jl_propietarios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane11)
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton14, jButton15, jButton16});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton14, jButton15, jButton16});

        jTabbedPane1.addTab("Propietarios", jPanel2);

        jButton5.setText("Modificar");

        jButton6.setText("Eliminar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Crear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jl_productos.setModel(new DefaultListModel());
        jScrollPane6.setViewportView(jl_productos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton5, jButton6, jButton7});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jButton6, jButton7});

        jTabbedPane1.addTab("Productos", jPanel5);

        javax.swing.GroupLayout CRUDLayout = new javax.swing.GroupLayout(CRUD.getContentPane());
        CRUD.getContentPane().setLayout(CRUDLayout);
        CRUDLayout.setHorizontalGroup(
            CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        CRUDLayout.setVerticalGroup(
            CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jl_compra.setModel(new DefaultListModel());
        jScrollPane1.setViewportView(jl_compra);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1/images/comprar (1).png"))); // NOI18N

        jButton21.setText("Seleccionar Farmacia");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout COMPRALayout = new javax.swing.GroupLayout(COMPRA.getContentPane());
        COMPRA.getContentPane().setLayout(COMPRALayout);
        COMPRALayout.setHorizontalGroup(
            COMPRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
            .addGroup(COMPRALayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, COMPRALayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        COMPRALayout.setVerticalGroup(
            COMPRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(COMPRALayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FARMACIA");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1/images/sistema.png"))); // NOI18N
        jButton1.setText("CRUD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1/images/farmaco.png"))); // NOI18N
        jButton20.setText("COMPRA");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1/images/hospital.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(jLabel1)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton20});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton20))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton20});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.setVisible(false);
        CRUD.setSize(WIDTH, HEIGHT);
        CRUD.setModal(true);

        CRUD.pack();
        CRUD.setLocationRelativeTo(this);
        CRUD.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed


    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");
        String x = (String) jl_productos.getSelectedValue();
        System.out.println(x);
        int inicio, fin;
        inicio = 0;
        fin = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == ':') {
                inicio = i + 2;
            }
            if (i > 0 & x.charAt(i) == ',') {
                fin = i;
                i = x.length();
            }
        }
        System.out.println(x.substring(inicio, fin));
        int id = Integer.parseInt(x.substring(inicio, fin));
        System.out.println(id);
        deleteCollectionProductos(mongoClient, id);
        listCollectionProductos(mongoClient);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        String x = (String) jl_compra.getSelectedValue();
        System.out.println(x);
        int inicio, fin;
        inicio = 0;
        fin = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == ':') {
                inicio = i + 2;
            }
            if (i > 0 & x.charAt(i) == ',') {
                fin = i;
                i = x.length();
            }
        }
        System.out.println(x.substring(inicio, fin));
        Double p = Double.parseDouble(x.substring(inicio, fin));
        System.out.println(p);

        int id = (int) Math.round(p);
        System.out.println(id);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        this.setVisible(false);
        COMPRA.setSize(WIDTH, HEIGHT);
        COMPRA.setModal(true);

        COMPRA.pack();
        COMPRA.setLocationRelativeTo(this);
        COMPRA.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog COMPRA;
    private javax.swing.JDialog CRUD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> jl_almacenes;
    private javax.swing.JList<String> jl_compra;
    private javax.swing.JList<String> jl_farmaceuticos;
    private javax.swing.JList<String> jl_farmacias;
    private javax.swing.JList<String> jl_laboratorios;
    private javax.swing.JList<String> jl_productos;
    private javax.swing.JList<String> jl_propietarios;
    // End of variables declaration//GEN-END:variables

    public void listCollectionFarmaciasVenta(MongoClient mongoClient) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) jl_compra.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                System.out.println(document.toJson());
            }

            jl_compra.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

}
