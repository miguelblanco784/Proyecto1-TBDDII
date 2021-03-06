/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1.tbddii;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.bson.conversions.Bson;

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
            database = mongoClient.getDatabase("test");
            //createCollectionProductos(mongoClient);
            //deleteCollectionProductos(mongoClient);
            //updateCollectionProductos(mongoClient);
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());
            listCollectionFarmacias(mongoClient, jl_compra);
            listCollectionFarmacias(mongoClient, jl_farmacias);
            listCollectionProductos(mongoClient, jl_productos);
            listCollectionAlmacenes(mongoClient, jl_almacenes);
            listCollectionFarmaceuticos(mongoClient, jl_farmaceuticos);
            listCollectionPropietarios(mongoClient, jl_propietarios);
            listCollectionLaboratorios(mongoClient, jl_laboratorios);
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

    public void createCollectionFarmacia(int id, String direccion) {
        MongoCollection<Document> farmacias = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
        Document doc = new Document("_id", id).append("direccion", direccion).append("idalmacen", idalmacen);

        farmacias.insertOne(doc);
    }

    public void createCollectionAlmacen(int id) {
        MongoCollection<Document> farmacias = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
        Document doc = new Document("_id", id);
        farmacias.insertOne(doc);
    }

    public void createCollectionFarmaceutico(int id, String nombre, int edad, boolean isResponsable, int idfarmacia) {
        Date now = new Date();
        MongoCollection<Document> farmaceutico = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmaceuticos");
        Document doc = new Document("_id", id).append("nombre", nombre).append("edad", edad).append("fechaingreso", now.toString()).append("isresponsable", isResponsable);
        farmaceutico.insertOne(doc);
        farmaceutico = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
        farmaceutico.updateOne(eq("_id", idfarmacia), Updates.addToSet("Farmaceuticos", id));
    }

    public void createCollectionProducto(int id, String nombre, String fabricante, String tipo, String finprincipal, boolean isprotegido) {
        MongoCollection<Document> productos = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
        Document doc = new Document("_id", id).append("nombre", nombre).append("fabricante", fabricante).append("tipo", tipo).append("finprincipal", finprincipal).append("isProtegido", isprotegido);
        productos.insertOne(doc);
    }

    public void createCollectionPropietario(int id, String nombre, int edad, String direccion, int idfarmacia) {
        MongoCollection<Document> propietario = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
        Document doc = new Document("_id", id).append("nombre", nombre).append("edad", edad).append("direccion", direccion);
        propietario.insertOne(doc);
        propietario = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
        propietario.updateOne(eq("_id", idfarmacia), Updates.addToSet("Propietarios", id));
    }

    public void createCollectionProductos() {
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

    public void listCollectionProductos(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionAlmacenes(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmaceuticos(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmaceuticos");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionFarmacias(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionLaboratorios(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("laboratorios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
            lista.setModel(modelo);
        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }
    }

    public void listCollectionPropietarios(MongoClient mongoClient, JList lista) {
        try {
            MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
            List<Document> farmaciaslista = farmacia.find().into(new ArrayList<>());

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : farmaciaslista) {
                modelo.addElement(document.toJson());
                i++;
            }
            lista.setVisibleRowCount(i);
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
        jPanel4 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jl_farmacias = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jl_productos = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jl_almacenes = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jl_farmaceuticos = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jl_propietarios = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jl_laboratorios = new javax.swing.JList<>();
        COMPRA = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jl_compra = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        COMPRA_ALMACENES = new javax.swing.JDialog();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jl_almacencompra = new javax.swing.JList<>();
        jScrollPane15 = new javax.swing.JScrollPane();
        jl_medicamentosalmacen = new javax.swing.JList<>();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        AgregarPropietario = new javax.swing.JDialog();
        jScrollPane12 = new javax.swing.JScrollPane();
        jl_farmacias1 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nombre_propietario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        edad_propietario = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        direccion_propietario = new javax.swing.JTextArea();
        AgregarFarmaceutico = new javax.swing.JDialog();
        jScrollPane13 = new javax.swing.JScrollPane();
        jl_farmacias2 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jButton23 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nombre_farmaceutico = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        edad_farmaceutico = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        radioSi = new javax.swing.JRadioButton();
        radioNo = new javax.swing.JRadioButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        AgregarFarmacia = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        direccionfarmacia = new javax.swing.JTextArea();
        jButton17 = new javax.swing.JButton();
        AgregarFarmacia2 = new javax.swing.JDialog();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        nombrepropietario = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        edad_propietario1 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        direccion_propietario1 = new javax.swing.JTextArea();
        jButton18 = new javax.swing.JButton();
        AgregarFarmacia3 = new javax.swing.JDialog();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        radioSi1 = new javax.swing.JRadioButton();
        nombre_farmaceutico1 = new javax.swing.JTextField();
        radioNo1 = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        edad_farmaceutico1 = new javax.swing.JFormattedTextField();
        jButton19 = new javax.swing.JButton();
        AgregarProducto = new javax.swing.JDialog();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nombreproducto = new javax.swing.JTextField();
        fabricanteproducto = new javax.swing.JTextField();
        tipoproducto = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        finproducto = new javax.swing.JTextArea();
        radioSi2 = new javax.swing.JRadioButton();
        radioNo2 = new javax.swing.JRadioButton();
        jButton24 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        CRUD.setPreferredSize(new java.awt.Dimension(1280, 820));

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jButton8.setText("Modificar");

        jButton9.setText("Eliminar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

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
                .addContainerGap(681, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton10, jButton8, jButton9});

        jTabbedPane1.addTab("Farmacias", jPanel4);

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
                .addContainerGap(681, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jButton6, jButton7});

        jTabbedPane1.addTab("Productos", jPanel5);

        jl_almacenes.setModel(new DefaultListModel());
        jScrollPane7.setViewportView(jl_almacenes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Almacenes", jPanel1);

        jButton11.setText("Modificar");

        jButton12.setText("Eliminar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

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
                .addContainerGap(681, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton11, jButton12, jButton13});

        jTabbedPane1.addTab("Farmaceuticos", jPanel3);

        jPanel2.setFocusTraversalPolicyProvider(true);

        jButton14.setText("Modificar");

        jButton15.setText("Eliminar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

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
                .addContainerGap(681, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton14, jButton15, jButton16});

        jTabbedPane1.addTab("Propietarios", jPanel2);

        jButton2.setText("Modificar");

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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
                .addContainerGap(681, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        jTabbedPane1.addTab("Laboratorios", jPanel6);

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

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1/images/fabrica.png"))); // NOI18N

        jl_almacencompra.setModel(new DefaultListModel());
        jScrollPane14.setViewportView(jl_almacencompra);

        jl_medicamentosalmacen.setModel(new DefaultListModel());
        jScrollPane15.setViewportView(jl_medicamentosalmacen);

        jButton25.setText("Comprar");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("Agregar Producto");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout COMPRA_ALMACENESLayout = new javax.swing.GroupLayout(COMPRA_ALMACENES.getContentPane());
        COMPRA_ALMACENES.getContentPane().setLayout(COMPRA_ALMACENESLayout);
        COMPRA_ALMACENESLayout.setHorizontalGroup(
            COMPRA_ALMACENESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, COMPRA_ALMACENESLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel29))
            .addComponent(jScrollPane14)
            .addComponent(jScrollPane15)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, COMPRA_ALMACENESLayout.createSequentialGroup()
                .addContainerGap(332, Short.MAX_VALUE)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        COMPRA_ALMACENESLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton25, jButton26});

        COMPRA_ALMACENESLayout.setVerticalGroup(
            COMPRA_ALMACENESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(COMPRA_ALMACENESLayout.createSequentialGroup()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(COMPRA_ALMACENESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        COMPRA_ALMACENESLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton25, jButton26});

        jl_farmacias1.setModel(new DefaultListModel());
        jScrollPane12.setViewportView(jl_farmacias1);

        jLabel4.setText("Eliga una farmacia a la cual desea agregar un propietario:");

        jButton22.setText("Siguiente");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel3.setText("Agregar Propietario");

        jLabel5.setText("Nombre:");

        jLabel6.setText("Edad:");

        try {
            edad_propietario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel7.setText("Direccion:");

        direccion_propietario.setColumns(20);
        direccion_propietario.setLineWrap(true);
        direccion_propietario.setRows(5);
        jScrollPane2.setViewportView(direccion_propietario);

        javax.swing.GroupLayout AgregarPropietarioLayout = new javax.swing.GroupLayout(AgregarPropietario.getContentPane());
        AgregarPropietario.getContentPane().setLayout(AgregarPropietarioLayout);
        AgregarPropietarioLayout.setHorizontalGroup(
            AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgregarPropietarioLayout.createSequentialGroup()
                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane12))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AgregarPropietarioLayout.createSequentialGroup()
                        .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)))
                            .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(edad_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombre_propietario)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))))
                        .addGap(0, 153, Short.MAX_VALUE))
                    .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton22)))
                .addContainerGap())
        );
        AgregarPropietarioLayout.setVerticalGroup(
            AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nombre_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(edad_propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AgregarPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AgregarPropietarioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton22)
                .addGap(23, 23, 23))
        );

        jl_farmacias2.setModel(new DefaultListModel());
        jScrollPane13.setViewportView(jl_farmacias2);

        jLabel8.setText("Eliga una farmacia a la cual desea agregar un propietario:");

        jButton23.setText("Siguiente");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel9.setText("Agregar Farmaceutico");

        jLabel10.setText("Nombre:");

        jLabel11.setText("Edad:");

        try {
            edad_farmaceutico.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel13.setText("Encargado:");

        buttonGroup1.add(radioSi);
        radioSi.setText("Si");

        buttonGroup1.add(radioNo);
        radioNo.setText("No");

        javax.swing.GroupLayout AgregarFarmaceuticoLayout = new javax.swing.GroupLayout(AgregarFarmaceutico.getContentPane());
        AgregarFarmaceutico.getContentPane().setLayout(AgregarFarmaceuticoLayout);
        AgregarFarmaceuticoLayout.setHorizontalGroup(
            AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                        .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9))
                            .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                                        .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11))
                                        .addGap(12, 12, 12)
                                        .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(edad_farmaceutico, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nombre_farmaceutico, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AgregarFarmaceuticoLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioSi)
                                        .addGap(18, 18, 18)
                                        .addComponent(radioNo)))))
                        .addGap(0, 168, Short.MAX_VALUE))
                    .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgregarFarmaceuticoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton23)))))
                .addContainerGap())
        );
        AgregarFarmaceuticoLayout.setVerticalGroup(
            AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmaceuticoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nombre_farmaceutico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(edad_farmaceutico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(AgregarFarmaceuticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioSi)
                        .addComponent(radioNo)))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel12.setText("Agregar Farmacia");

        jLabel14.setText("Direccion:");

        direccionfarmacia.setColumns(20);
        direccionfarmacia.setRows(5);
        direccionfarmacia.setWrapStyleWord(true);
        jScrollPane3.setViewportView(direccionfarmacia);

        jButton17.setText("Siguiente");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AgregarFarmaciaLayout = new javax.swing.GroupLayout(AgregarFarmacia.getContentPane());
        AgregarFarmacia.getContentPane().setLayout(AgregarFarmaciaLayout);
        AgregarFarmaciaLayout.setHorizontalGroup(
            AgregarFarmaciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmaciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgregarFarmaciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton17)
                    .addGroup(AgregarFarmaciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addGroup(AgregarFarmaciaLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        AgregarFarmaciaLayout.setVerticalGroup(
            AgregarFarmaciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmaciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AgregarFarmaciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton17)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel15.setText("Agregar Propietario a Farmacia");

        jLabel16.setText("Nombre:");

        jLabel17.setText("Edad:");

        try {
            edad_propietario1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel18.setText("Direccion:");

        direccion_propietario1.setColumns(20);
        direccion_propietario1.setLineWrap(true);
        direccion_propietario1.setRows(5);
        jScrollPane4.setViewportView(direccion_propietario1);

        jButton18.setText("Siguiente");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AgregarFarmacia2Layout = new javax.swing.GroupLayout(AgregarFarmacia2.getContentPane());
        AgregarFarmacia2.getContentPane().setLayout(AgregarFarmacia2Layout);
        AgregarFarmacia2Layout.setHorizontalGroup(
            AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombrepropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton18)
                        .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                            .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(edad_propietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        AgregarFarmacia2Layout.setVerticalGroup(
            AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(nombrepropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(edad_propietario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AgregarFarmacia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AgregarFarmacia2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addComponent(jButton18)
                .addGap(32, 32, 32))
        );

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel19.setText("Agregar Farmaceutico");

        jLabel20.setText("Encargado:");

        jLabel21.setText("Nombre:");

        buttonGroup1.add(radioSi1);
        radioSi1.setText("Si");

        buttonGroup1.add(radioNo1);
        radioNo1.setText("No");

        jLabel22.setText("Edad:");

        try {
            edad_farmaceutico1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButton19.setText("Siguiente");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AgregarFarmacia3Layout = new javax.swing.GroupLayout(AgregarFarmacia3.getContentPane());
        AgregarFarmacia3.getContentPane().setLayout(AgregarFarmacia3Layout);
        AgregarFarmacia3Layout.setHorizontalGroup(
            AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmacia3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(AgregarFarmacia3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AgregarFarmacia3Layout.createSequentialGroup()
                                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addGap(12, 12, 12)
                                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edad_farmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombre_farmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AgregarFarmacia3Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioSi1)
                                .addGap(18, 18, 18)
                                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton19)
                                    .addComponent(radioNo1))))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        AgregarFarmacia3Layout.setVerticalGroup(
            AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarFarmacia3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(nombre_farmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(edad_farmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(AgregarFarmacia3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioSi1)
                        .addComponent(radioNo1)))
                .addGap(52, 52, 52)
                .addComponent(jButton19)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel23.setText("Agregar Producto");

        jLabel24.setText("Nombre:");

        jLabel25.setText("Fabricante:");

        jLabel26.setText("Tipo:");

        jLabel27.setText("Fin Principal:");

        jLabel28.setText("Protegido Seguro Social:");

        finproducto.setColumns(20);
        finproducto.setRows(5);
        jScrollPane5.setViewportView(finproducto);

        buttonGroup1.add(radioSi2);
        radioSi2.setText("Si");

        buttonGroup1.add(radioNo2);
        radioNo2.setText("No");

        jButton24.setText("Crear Producto");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AgregarProductoLayout = new javax.swing.GroupLayout(AgregarProducto.getContentPane());
        AgregarProducto.getContentPane().setLayout(AgregarProductoLayout);
        AgregarProductoLayout.setHorizontalGroup(
            AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AgregarProductoLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioSi2)
                        .addGap(18, 18, 18)
                        .addComponent(radioNo2))
                    .addComponent(jLabel23)
                    .addGroup(AgregarProductoLayout.createSequentialGroup()
                        .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26))
                        .addGap(28, 28, 28)
                        .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombreproducto, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(fabricanteproducto)
                            .addComponent(tipoproducto)))
                    .addGroup(AgregarProductoLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        AgregarProductoLayout.setVerticalGroup(
            AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(fabricanteproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(tipoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgregarProductoLayout.createSequentialGroup()
                        .addGroup(AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioSi2)
                            .addComponent(radioNo2)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgregarProductoLayout.createSequentialGroup()
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
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
        AgregarProducto.setModal(true);
        AgregarProducto.pack();
        AgregarProducto.setLocationRelativeTo(this);
        AgregarProducto.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        //trabaja aqui
        AgregarFarmacia.setModal(true);
        AgregarFarmacia.pack();
        AgregarFarmacia.setLocationRelativeTo(this);
        AgregarFarmacia.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        listCollectionFarmacias(mongoClient, jl_farmacias2);
        AgregarFarmaceutico.setModal(true);
        AgregarFarmaceutico.pack();
        AgregarFarmaceutico.setLocationRelativeTo(this);
        AgregarFarmaceutico.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        listCollectionFarmacias(mongoClient, jl_farmacias1);
        AgregarPropietario.setModal(true);
        AgregarPropietario.pack();
        AgregarPropietario.setLocationRelativeTo(this);
        AgregarPropietario.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed


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
        listCollectionProductos(mongoClient, jl_productos);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        System.out.println(idparse(jl_compra));
        int idfarmacia = idparse(jl_compra);

        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
        List<Document> almacenseleccionado = farmacia.find(Filters.lte("_id", idfarmacia)).into(new ArrayList<>());
        ArrayList<String> almacen = new ArrayList();
        try {

            DefaultListModel modelo = new DefaultListModel();
            int i = 0;
            for (Document document : almacenseleccionado) {
                almacen.add(document.toJson());
                modelo.addElement(document.toJson());
                break;
            }
            jl_almacencompra.setVisibleRowCount(i);
            jl_almacencompra.setModel(modelo);

        } catch (Exception e) {
            System.out.println("el error es aqui " + e.getMessage());
        }

        ArrayList<String> medicinas = new ArrayList();
        String farmacias = almacen.get(0);
        boolean nuevo = false;
        int nommedicamento = 0;
        for (int i = 1; i < farmacias.length(); i++) {
            if (farmacias.charAt(i) == '{') {
                nuevo = true;
                i++;
            } else if (farmacias.charAt(i) == '}') {
                nuevo = false;
                nommedicamento++;
            }

            if (nuevo) {
                try {
                    medicinas.get(nommedicamento);
                } catch (Exception e) {
                    medicinas.add("");
                } finally {
                    String temp = medicinas.get(nommedicamento) + farmacias.charAt(i);
                    medicinas.set(nommedicamento, temp);
                }
            }
        }

        try {
            MongoCollection<Document> productos = mongoClient.getDatabase("SistemaFarmacias").getCollection("productos");
            List<Document> productoslista = productos.find().into(new ArrayList<>());
            DefaultListModel modelo = new DefaultListModel();

            ArrayList<String> listadeproductos = new ArrayList();

            for (String medicina : medicinas) {
                listadeproductos.add(medicina);
                //modelo.addElement(medicina+"");
            }
            for (int i = 0; i < listadeproductos.size(); i++) {
                for (Document document : productoslista) {
                    listadeproductos.set(i, listadeproductos.get(i) + " || " + document.toJson());
                    i++;
                }
            }

            for (String listadeproducto : listadeproductos) {
                modelo.addElement(listadeproducto);
            }
            jl_medicamentosalmacen.setVisibleRowCount(0);
            jl_medicamentosalmacen.setModel(modelo);
        } catch (Exception e) {
        }

        COMPRA.dispose();

        COMPRA_ALMACENES.setSize(WIDTH, HEIGHT);
        COMPRA_ALMACENES.setModal(true);

        COMPRA_ALMACENES.pack();
        COMPRA_ALMACENES.setLocationRelativeTo(this);
        COMPRA_ALMACENES.setVisible(true);

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

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                listCollectionFarmacias(mongoClient, jl_farmacias);
                break;
            case 1:
                listCollectionProductos(mongoClient, jl_productos);
                break;
            case 2:
                listCollectionAlmacenes(mongoClient, jl_almacenes);
                break;
            case 3:
                listCollectionFarmaceuticos(mongoClient, jl_farmaceuticos);
                break;
            case 4:
                listCollectionPropietarios(mongoClient, jl_propietarios);
                break;
            case 5:
                listCollectionLaboratorios(mongoClient, jl_laboratorios);
                break;
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");

        int id = idparse(jl_laboratorios);
        System.out.println(id);
        deleteCollectionLaboratorios(mongoClient, id);
        listCollectionLaboratorios(mongoClient, jl_laboratorios);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");

        int id = idparse(jl_propietarios);
        System.out.println(id);
        deleteCollectionPropietarios(mongoClient, id);
        listCollectionPropietarios(mongoClient, jl_propietarios);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");

        int id = idparse(jl_farmaceuticos);
        System.out.println(id);
        deleteCollectionFarmaceuticos(mongoClient, id);
        listCollectionFarmaceuticos(mongoClient, jl_farmaceuticos);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Francisco:hola12345@cluster0.dxkw8.mongodb.net/SistemaFarmacias?retryWrites=true&w=majority");

        int id = idparse(jl_farmacias);
        System.out.println(id);
        deleteCollectionFarmacias(mongoClient, id);
        listCollectionFarmacias(mongoClient, jl_productos);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        if (!nombre_propietario.getText().equals("") && !edad_propietario.getText().equals("") && !direccion_propietario.getText().equals("") && jl_farmacias1.getSelectedIndex() != -1) {
            String nombre = nombre_propietario.getText();
            int edad = Integer.parseInt(edad_propietario.getText());
            String direccion = direccion_propietario.getText();
            int id = idNextValue(jl_propietarios);
            int idfarmacia = idparse(jl_farmacias1);
            createCollectionPropietario(id, nombre, edad, direccion, idfarmacia);
            JOptionPane.showMessageDialog(rootPane, "Se creo propietario exitosamente");
            AgregarPropietario.dispose();
            listCollectionPropietarios(mongoClient, jl_propietarios);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        if (!nombre_farmaceutico.getText().equals("") && !edad_farmaceutico.getText().equals("") && (radioNo.isSelected() == true || radioSi.isSelected() == true) && jl_farmacias2.getSelectedIndex() != -1) {
            String nombre = nombre_farmaceutico.getText();
            int edad = Integer.parseInt(edad_farmaceutico.getText());
            boolean isresponsable;
            if (radioNo.isSelected()) {
                isresponsable = false;
            } else {
                isresponsable = true;
            }
            int id = idNextValue(jl_farmaceuticos);
            int idfarmacia = idparse(jl_farmacias2);
            createCollectionFarmaceutico(id, nombre, edad, isresponsable, idfarmacia);
            JOptionPane.showMessageDialog(rootPane, "Se creo farmaceutico exitosamente");
            AgregarFarmaceutico.dispose();
            listCollectionFarmaceuticos(mongoClient, jl_farmaceuticos);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        if (!direccionfarmacia.getText().equals("")) {
            idfarmacia = idNextValue(jl_farmacias);
            idalmacen = idNextValue(jl_almacenes);
            createCollectionAlmacen(idalmacen);
            String direccion = direccionfarmacia.getText();
            createCollectionFarmacia(idfarmacia, direccion);
            JOptionPane.showMessageDialog(rootPane, "Farmacia creada exitosamente");
            AgregarFarmacia.dispose();
            AgregarFarmacia2.setModal(true);
            AgregarFarmacia2.pack();
            AgregarFarmacia2.setLocationRelativeTo(this);
            AgregarFarmacia2.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }

    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if (!nombrepropietario.getText().equals("") && !edad_propietario1.getText().equals("") && !direccion_propietario1.getText().equals("")) {
            int id = idNextValue(jl_propietarios);
            String nombre = nombrepropietario.getText();
            int edad = Integer.parseInt(edad_propietario1.getText());
            String direccion = direccion_propietario1.getText();
            createCollectionPropietario(id, nombre, edad, direccion, idfarmacia);
            JOptionPane.showMessageDialog(rootPane, "Propietario creado exitosamente");
            AgregarFarmacia2.dispose();
            AgregarFarmacia3.setModal(true);
            AgregarFarmacia3.pack();
            AgregarFarmacia3.setLocationRelativeTo(this);
            AgregarFarmacia3.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if (!nombre_farmaceutico1.getText().equals("") && !edad_farmaceutico1.getText().equals("") && (radioNo1.isSelected() == true || radioSi1.isSelected() == true)) {
            String nombre = nombre_farmaceutico1.getText();
            int id = idNextValue(jl_farmaceuticos);
            int edad = Integer.parseInt(edad_farmaceutico1.getText());
            boolean isresponsable;
            if (radioNo1.isSelected()) {
                isresponsable = false;
            } else {
                isresponsable = true;
            }
            createCollectionFarmaceutico(id, nombre, edad, isresponsable, idfarmacia);
            JOptionPane.showMessageDialog(rootPane, "Termino el proceso de creacion de una farmacia");
            AgregarFarmacia3.dispose();
            listCollectionFarmacias(mongoClient, jl_farmacias);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        if (!nombreproducto.getText().equals("") && !fabricanteproducto.getText().equals("") && !tipoproducto.getText().equals("") && !finproducto.getText().equals("") && (radioNo2.isSelected() == true || radioSi2.isSelected() == true)) {
            int id = idNextValue(jl_productos);
            String nombre = nombreproducto.getText();
            String fabricante = fabricanteproducto.getText();
            String tipo = tipoproducto.getText();
            String fin = finproducto.getText();
            boolean isprotegido;
            if (radioNo2.isSelected()) {
                isprotegido = false;
            } else {
                isprotegido = false;
            }
            createCollectionProducto(id, nombre, fabricante, tipo, fin, isprotegido);
            JOptionPane.showMessageDialog(rootPane, "Producto creado");
            listCollectionProductos(mongoClient, jl_productos);
            AgregarProducto.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Llene todos los espacios del formulario");
        }

    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        JOptionPane.showMessageDialog(this, "Compra realizada con exito.", "Alerta", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton26ActionPerformed

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
    private javax.swing.JDialog AgregarFarmaceutico;
    private javax.swing.JDialog AgregarFarmacia;
    private javax.swing.JDialog AgregarFarmacia2;
    private javax.swing.JDialog AgregarFarmacia3;
    private javax.swing.JDialog AgregarProducto;
    private javax.swing.JDialog AgregarPropietario;
    private javax.swing.JDialog COMPRA;
    private javax.swing.JDialog COMPRA_ALMACENES;
    private javax.swing.JDialog CRUD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea direccion_propietario;
    private javax.swing.JTextArea direccion_propietario1;
    private javax.swing.JTextArea direccionfarmacia;
    private javax.swing.JFormattedTextField edad_farmaceutico;
    private javax.swing.JFormattedTextField edad_farmaceutico1;
    private javax.swing.JFormattedTextField edad_propietario;
    private javax.swing.JFormattedTextField edad_propietario1;
    private javax.swing.JTextField fabricanteproducto;
    private javax.swing.JTextArea finproducto;
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
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> jl_almacencompra;
    private javax.swing.JList<String> jl_almacenes;
    private javax.swing.JList<String> jl_compra;
    private javax.swing.JList<String> jl_farmaceuticos;
    private javax.swing.JList<String> jl_farmacias;
    private javax.swing.JList<String> jl_farmacias1;
    private javax.swing.JList<String> jl_farmacias2;
    private javax.swing.JList<String> jl_laboratorios;
    private javax.swing.JList<String> jl_medicamentosalmacen;
    private javax.swing.JList<String> jl_productos;
    private javax.swing.JList<String> jl_propietarios;
    private javax.swing.JTextField nombre_farmaceutico;
    private javax.swing.JTextField nombre_farmaceutico1;
    private javax.swing.JTextField nombre_propietario;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombrepropietario;
    private javax.swing.JRadioButton radioNo;
    private javax.swing.JRadioButton radioNo1;
    private javax.swing.JRadioButton radioNo2;
    private javax.swing.JRadioButton radioSi;
    private javax.swing.JRadioButton radioSi1;
    private javax.swing.JRadioButton radioSi2;
    private javax.swing.JTextField tipoproducto;
    // End of variables declaration//GEN-END:variables
    Logger mongoLogger;
    MongoClient mongoClient;
    MongoDatabase database;
    int idalmacen;
    int idfarmacia;

    public int idparse(JList lista) {
        String x = (String) lista.getSelectedValue();
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
        Double p = Double.parseDouble(x.substring(inicio, fin));
        int id = (int) Math.round(p);
        return id;
    }

    public int idNextValue(JList lista) {
        int id = 0;
        for (int i = 0; i < lista.getModel().getSize(); i++) {
            String x = (String) lista.getModel().getElementAt(i);
            int inicio, fin;
            inicio = 0;
            fin = 0;
            for (int j = 0; j < x.length(); j++) {
                if (x.charAt(j) == ':') {
                    inicio = j + 2;
                }
                if (j > 0 & x.charAt(j) == ',') {
                    fin = j;
                    j = x.length();
                }
            }
            Double p = Double.parseDouble(x.substring(inicio, fin));
            int id2 = (int) Math.round(p);
            if (id < id2) {
                id = id2;
            }
        }
        id++;
        return id;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteCollectionFarmacias(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmacias");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteCollectionAlmacenes(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("almacenes");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteCollectionFarmaceuticos(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("farmaceuticos");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteCollectionPropietarios(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("propietarios");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteCollectionLaboratorios(MongoClient mongoClient, int id) {
        MongoCollection<Document> farmacia = mongoClient.getDatabase("SistemaFarmacias").getCollection("laboratorios");
        Document doc = new Document("_id", id);
        farmacia.deleteOne(doc);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
