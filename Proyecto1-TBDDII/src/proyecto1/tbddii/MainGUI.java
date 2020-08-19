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
            mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.SEVERE);

            mongoClient = MongoClients.create(
                    "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");
            //mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority
            database = mongoClient.getDatabase("test");

            //createCollectionProductos(mongoClient);
            //deleteCollectionProductos(mongoClient);
            //updateCollectionProductos(mongoClient);
            listCollectionFarmacias(mongoClient, jl_farmacias);
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

    public void deleteCollectionProductos(MongoClient mongoClient) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", 6);
        farmacia.deleteOne(doc);
    }

    public void updateCollectionProductos(MongoClient mongoClient) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", 6);
        farmacia.updateOne(doc, Updates.set("fabricante", "Minox"));
    }

    public void listCollectionProductos(MongoClient mongoClient,JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionAlmacenes(MongoClient mongoClient,JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmaceuticos(MongoClient mongoClient,JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmaceuticos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmacias(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionLaboratorios(MongoClient mongoClient,JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("laboratorios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionPropietarios(MongoClient mongoClient,JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = (DefaultListModel) lista.getModel();

            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
            }

            lista.setModel(modelo);
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
        FarmaciasPanel = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jl_farmacias = new javax.swing.JList<>();
        AlmacenesPanel = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jl_almacenes = new javax.swing.JList<>();
        PropietariosPanel = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jl_propietarios = new javax.swing.JList<>();
        FarmaceuticosPanel = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jl_farmaceuticos = new javax.swing.JList<>();
        ProductosPanel = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jl_productos = new javax.swing.JList<>();
        LaboratoriosPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jl_laboratorios = new javax.swing.JList<>();
        jFrame1 = new javax.swing.JFrame();
        jScrollPane12 = new javax.swing.JScrollPane();
        jl_farmacias1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        CRUD.setPreferredSize(new java.awt.Dimension(1280, 820));

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

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

        javax.swing.GroupLayout FarmaciasPanelLayout = new javax.swing.GroupLayout(FarmaciasPanel);
        FarmaciasPanel.setLayout(FarmaciasPanelLayout);
        FarmaciasPanelLayout.setHorizontalGroup(
            FarmaciasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FarmaciasPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane9)
        );

        FarmaciasPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton8, jButton9});

        FarmaciasPanelLayout.setVerticalGroup(
            FarmaciasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FarmaciasPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(FarmaciasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        FarmaciasPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton10, jButton8, jButton9});

        jTabbedPane1.addTab("Farmacias", FarmaciasPanel);

        jButton17.setText("Modificar");

        jButton18.setText("Eliminar");

        jButton19.setText("Crear");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jl_almacenes.setModel(new DefaultListModel());
        jScrollPane7.setViewportView(jl_almacenes);

        javax.swing.GroupLayout AlmacenesPanelLayout = new javax.swing.GroupLayout(AlmacenesPanel);
        AlmacenesPanel.setLayout(AlmacenesPanelLayout);
        AlmacenesPanelLayout.setHorizontalGroup(
            AlmacenesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenesPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane7)
        );

        AlmacenesPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton17, jButton18, jButton19});

        AlmacenesPanelLayout.setVerticalGroup(
            AlmacenesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenesPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(AlmacenesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        AlmacenesPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton17, jButton18, jButton19});

        jTabbedPane1.addTab("Almacenes", AlmacenesPanel);

        PropietariosPanel.setFocusTraversalPolicyProvider(true);

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

        javax.swing.GroupLayout PropietariosPanelLayout = new javax.swing.GroupLayout(PropietariosPanel);
        PropietariosPanel.setLayout(PropietariosPanelLayout);
        PropietariosPanelLayout.setHorizontalGroup(
            PropietariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropietariosPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane11)
        );

        PropietariosPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton14, jButton15, jButton16});

        PropietariosPanelLayout.setVerticalGroup(
            PropietariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropietariosPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(PropietariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        PropietariosPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton14, jButton15, jButton16});

        jTabbedPane1.addTab("Propietarios", PropietariosPanel);

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

        javax.swing.GroupLayout FarmaceuticosPanelLayout = new javax.swing.GroupLayout(FarmaceuticosPanel);
        FarmaceuticosPanel.setLayout(FarmaceuticosPanelLayout);
        FarmaceuticosPanelLayout.setHorizontalGroup(
            FarmaceuticosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FarmaceuticosPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane8)
        );

        FarmaceuticosPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton11, jButton12, jButton13});

        FarmaceuticosPanelLayout.setVerticalGroup(
            FarmaceuticosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FarmaceuticosPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(FarmaceuticosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        FarmaceuticosPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton11, jButton12, jButton13});

        jTabbedPane1.addTab("Farmaceuticos", FarmaceuticosPanel);

        jButton5.setText("Modificar");

        jButton6.setText("Eliminar");

        jButton7.setText("Crear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jl_productos.setModel(new DefaultListModel());
        jScrollPane6.setViewportView(jl_productos);

        javax.swing.GroupLayout ProductosPanelLayout = new javax.swing.GroupLayout(ProductosPanel);
        ProductosPanel.setLayout(ProductosPanelLayout);
        ProductosPanelLayout.setHorizontalGroup(
            ProductosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductosPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );

        ProductosPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton5, jButton6, jButton7});

        ProductosPanelLayout.setVerticalGroup(
            ProductosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductosPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(ProductosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        ProductosPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jButton6, jButton7});

        jTabbedPane1.addTab("Productos", ProductosPanel);

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

        javax.swing.GroupLayout LaboratoriosPanelLayout = new javax.swing.GroupLayout(LaboratoriosPanel);
        LaboratoriosPanel.setLayout(LaboratoriosPanelLayout);
        LaboratoriosPanelLayout.setHorizontalGroup(
            LaboratoriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaboratoriosPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
            .addComponent(jScrollPane10)
        );

        LaboratoriosPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        LaboratoriosPanelLayout.setVerticalGroup(
            LaboratoriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaboratoriosPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(LaboratoriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        LaboratoriosPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        jTabbedPane1.addTab("Laboratorios", LaboratoriosPanel);

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

        jl_farmacias1.setModel(new DefaultListModel());
        jScrollPane12.setViewportView(jl_farmacias1);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrame1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane12)
                    .addContainerGap()))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(38, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton1.setText("CRUD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(1003, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(591, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        //primero
        
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                listCollectionFarmacias(mongoClient, jl_farmacias);
                break;
            case 1:
                listCollectionAlmacenes(mongoClient, jl_almacenes);
                break;
            case 2:
                listCollectionPropietarios(mongoClient, jl_propietarios);
                break;
            case 3:
                listCollectionFarmaceuticos(mongoClient, jl_farmaceuticos);
                break;
            case 4:
                listCollectionProductos(mongoClient, jl_productos);
                break;
            case 5:
                listCollectionLaboratorios(mongoClient, jl_laboratorios);
                break;
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

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
    private javax.swing.JPanel AlmacenesPanel;
    private javax.swing.JDialog CRUD;
    private javax.swing.JPanel FarmaceuticosPanel;
    private javax.swing.JPanel FarmaciasPanel;
    private javax.swing.JPanel LaboratoriosPanel;
    private javax.swing.JPanel ProductosPanel;
    private javax.swing.JPanel PropietariosPanel;
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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> jl_almacenes;
    private javax.swing.JList<String> jl_farmaceuticos;
    private javax.swing.JList<String> jl_farmacias;
    private javax.swing.JList<String> jl_farmacias1;
    private javax.swing.JList<String> jl_laboratorios;
    private javax.swing.JList<String> jl_productos;
    private javax.swing.JList<String> jl_propietarios;
    // End of variables declaration//GEN-END:variables
Logger mongoLogger;
MongoClient mongoClient;
MongoDatabase database;
}
