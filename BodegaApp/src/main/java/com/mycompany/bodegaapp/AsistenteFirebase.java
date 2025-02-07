
package com.mycompany.bodegaapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class AsistenteFirebase {
    public static void agregarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Elemento valor){ //C
        CountDownLatch latch = new CountDownLatch(1);
        try {
            
            // Obtener referencia al nodo especificado
            
            System.out.println("Obteniendo referencia al nodo: " + nodo);
            
            DatabaseReference referencia = firebaseDatabase.getReference(nodo);

            // Agregar la información al nodo
            System.out.println("Subiendo datos: " + valor.toString());
            referencia.child(clave).setValue(valor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        System.out.println("Error al agregar la informacion: " + error.getMessage());
                    } else {
                        System.out.println("Informacion agregada correctamente al nodo: " + ref.getPath());
                    }
                    latch.countDown();
                }
                
            });
            
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al agregar informacion: " + ex.getMessage());
        }
    }
    
    public static void leerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave){ //R
        CountDownLatch latch = new CountDownLatch(1);
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        System.out.println("Informacion en el nodo: " + referencia.getPath());
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase
                Elemento elemento = dataSnapshot.getValue(Elemento.class);
                
                if (elemento != null) {
                    System.out.println("Datos actuales: " + elemento);
                    
                } else {
                    System.out.println("Empleado no encontrado.");
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al obtener informacion: " + ex.getMessage());
        }
    }
    
    public static void actualizarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Elemento nuevoValor){ //U
        CountDownLatch latch = new CountDownLatch(1);
        try {
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        referencia.setValue(nuevoValor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    System.out.println("Error al actualizar la informacion: " + error.getMessage());
                } else {
                    System.out.println("Informacion actualizada correctamente en el nodo: " + ref.getPath());
                }
                latch.countDown();
            }
        });
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al actualizar informacion: " + ex.getMessage());
        }
        } catch (Exception e) {
        System.out.println("Error al actualizar la informacion: " + e.getMessage());
        }
    }
    
    public static void borrarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave){ //D
        CountDownLatch latch = new CountDownLatch(1);
        try {
            
            // Obtener referencia al nodo especificado
            System.out.println("Obteniendo referencia al nodo: " + nodo);
            DatabaseReference referencia = firebaseDatabase.getReference(nodo);

            // Borrar la información del nodo
            
            referencia.child(clave).removeValue(new DatabaseReference.CompletionListener() {
                @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        System.out.println("Error al agregar la informacion: " + error.getMessage());
                    } else {
                        System.out.println("Informacion eliminada correctamente del nodo: " + ref.getPath());
                    }
                }
            });
            
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al borrar informacion: " + ex.getMessage());
        }
    }
        public static Elemento obtenerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave) throws InterruptedException{ //R
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        final Elemento[] elemento = new Elemento[1]; // Crear un arreglo de una posición para almacenar el elemento
        final CountDownLatch latch = new CountDownLatch(1); // Para esperar la respuesta asincrónica
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase y guardarla en el arreglo
                elemento[0] = dataSnapshot.getValue(Elemento.class);
                latch.countDown(); // Liberamos el latch
                
                if (elemento != null) {
                    
                    
                } else {
                    System.out.println("Elemento no encontrado.");
                    latch.countDown(); 
                }
                latch.countDown();
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        latch.await(); // Esperamos a que se complete el callback
        return elemento[0]; // Retornamos el elemento obtenido
    }
    public static ArrayList<Elemento> obtenerTodosLosElementos(FirebaseDatabase firebaseDatabase, String nodo) throws InterruptedException {
    long inicio = System.currentTimeMillis(); 
    ArrayList<Elemento> listaElementos = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tuNodo");
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot snapshot) {
        System.out.println("Firebase respondió correctamente");
        
    }

    @Override
    public void onCancelled(DatabaseError error) {
        System.out.println("Error en Firebase: " + error.getMessage());
    }
    });
    CountDownLatch latch = new CountDownLatch(1);
    DatabaseReference referencia = firebaseDatabase.getReference(nodo);

    referencia.addListenerForSingleValueEvent(new ValueEventListener() {
        
        
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println("Cantidad de elementos en Firebase: " + dataSnapshot.getChildrenCount());
            System.out.println("Cargando elementos en el ArrayList...");
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                System.out.println("Datos en snapshot (JSON): " + snapshot.getValue()); // Ver JSON exacto

                Elemento elemento = snapshot.getValue(Elemento.class);
                if (elemento == null) {
                System.out.println(" Error: snapshot.getValue(Elemento.class) devolvió null.");
                } else {
                listaElementos.add(elemento);
                System.out.println(" Elemento obtenido: " + elemento);
                }
                
                
            }
            latch.countDown();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("Error al obtener la información: " + databaseError.getMessage());
            latch.countDown();
        }
    });

     boolean completado = latch.await(5, TimeUnit.SECONDS); // Espera máximo 5 segundos
    if (!completado) {
        System.out.println("Tiempo de espera agotado. No se pudieron recuperar los datos.");
    } // Esperamos a que se complete la carga
    long fin = System.currentTimeMillis(); //  Marca el final
    System.out.println("Tiempo de carga: " + (fin - inicio) + " ms");
    
    return listaElementos;
    }
     
    public static void buscarPorIndice(FirebaseDatabase firebaseDatabase, String nod, JTextArea textArea) {
    try {
        // Cargar los datos en la lista
        String nodo = "Bodega";
        ArrayList<Elemento> lista = obtenerTodosLosElementos(firebaseDatabase, nodo);
        
        // Pedir el índice al usuario
        String input = JOptionPane.showInputDialog("Ingrese el índice del elemento:");
        if (input == null) return; // Si cancela, no hace nada

        int indice = Integer.parseInt(input);

        // Verificar que el índice es válido
        if (indice >= 0 && indice < lista.size()) {
            Elemento elemento = lista.get(indice);
            textArea.setText("Elemento encontrado:\n" + elemento.toString());
            
        } else {
            JOptionPane.showMessageDialog(null, "Índice fuera de rango.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    }    

    public static String consultarPorId(String input) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
