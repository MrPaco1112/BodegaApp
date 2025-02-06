
package com.mycompany.bodegaapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Juan Esteban Sarmiento Benitez
 */
public class BodegaApp {

    public FirebaseDatabase firebaseDatabase;
    public void initRTD(){ 
        
        //https://pacopruebas-165de-default-rtdb.firebaseio.com/ Link al repositorio de Juanes
        //C:\\Users\\USUARIO\\OneDrive\\Documentos\\NetBeansProjects\\IdManager\\pacopruebas-165de-firebase-adminsdk-fbsvc-6eb7c97568.json Ruta en el PC de juanes, al final ponen el nombre del archivo 
        //D:\Monica Sarmiento\Documents\NetBeansProjects\IdManagerV2\ID_Extractor_POO-main\IdManagerV2\IdManagerV2main\IdManager\pacopruebas-165de-firebase-adminsdk-fbsvc-6eb7c97568.json Ruta en Pacoportatil
        try {
            // Ruta al archivo JSON de credenciales
            String pathToServiceAccount = "D:\\Monica Sarmiento\\Documents\\NetBeansProjects\\IdManagerV2\\ID_Extractor_POO-main\\IdManagerV2\\IdManagerV2main\\IdManager\\pacopruebas-165de-firebase-adminsdk-fbsvc-6eb7c97568.json";

            // Construir opciones de Firebase
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://pacopruebas-165de-default-rtdb.firebaseio.com/")
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(pathToServiceAccount)))
                    .build();
             if (FirebaseApp.getApps().isEmpty()) { // Verificar que no se inicialice más de una vez
                FirebaseApp.initializeApp(firebaseOptions);
            }

            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexion exitosa a Firebase RTD.");
        }catch (RuntimeException ex) {
            System.out.println("Problema ejecucion ");
        }catch (FileNotFoundException ex) {
            System.out.println("Problema archivo");
        }catch(IOException e){
            System.out.println("Error al cargar las credenciales: " + e.getMessage());
        }
    }
   
}
