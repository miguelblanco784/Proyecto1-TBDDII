/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1.tbddii;

import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    public static void main(String[] args) {
        System.out.println("inicia");
        try {
            System.out.println("Inicia");
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.SEVERE);
            System.out.println("Inicia!");
            MongoClient mongoClient = MongoClients.create(
                    "mongodb+srv://Francisco:hola12345@cluster0.wu6pi.mongodb.net/Cluster0?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("test");
            System.out.println("Llega aca?");
        } catch (Exception e) {
            System.out.println("Se produjo un error" + e.getMessage());
        }

    }

}
