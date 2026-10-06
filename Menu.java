import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// clase del menú principal: aquí arranca el programa
public class Menu {
    // método principal: lo primero que ejecuta Java
    public static void main(String[] args) {
        // crea el único lector del teclado del programa
        Scanner sc = new Scanner(System.in);
        // crea la fila de la oficina, vacía
        Queue<Ciudadano> cola = new LinkedList<>();
        // crea la pila del historial: lo más reciente queda arriba
        Stack<String> historial = new Stack<>();
        // crea el objeto que tiene todos los métodos
        Metodos m = new Metodos();
        // crea el interruptor del menú y déjalo encendido
        boolean continuar = true;

        // da la bienvenida
        System.out.println("\nBienvenidos a la oficina de trámites");

        // mientras el interruptor siga encendido, muestra el menú
        while (continuar) {
            // título del menú
            System.out.println("\n¿Qué desea realizar?");
            // opción 1
            System.out.println("1) Registrar solicitudes");
            // opción 2
            System.out.println("2) Consultar ciudadanos en espera");
            // opción 3
            System.out.println("3) Llamar al siguiente ciudadano");
            // opción 4
            System.out.println("4) Modificar información de una solicitud");
            // opción 5
            System.out.println("5) Cancelar solicitud");
            // opción 6
            System.out.println("6) Finalizar trámite");
            // opción 7
            System.out.println("7) Consultar ciudadano por documento");
            // opción 8
            System.out.println("8) Ver historial");
            // opción 9
            System.out.println("9) Resumen de la oficina");
            // opción 0
            System.out.println("0) Salir");
            // pide la opción en la misma línea
            System.out.print("Opción: ");
            // lee la opción y asegúrate de que esté entre 0 y 9
            int opt = m.validarRango(sc, 0, 9);
            // variable para el documento, que usan varias opciones
            String documento = "";

            // según la opción elegida...
            switch (opt) {
                // opción 1
                case 1:
                    // registra solicitudes y guarda la cola actualizada
                    cola = m.registrarSolicitudes(cola, historial, m, sc);
                    break;
                // opción 2
                case 2:
                    // muestra a los que esperan
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                // opción 3
                case 3:
                    // llama al siguiente y anuncia a quién
                    System.out.println(m.llamarSiguiente(cola, historial));
                    break;
                // opción 4
                case 4:
                    // pide el documento
                    System.out.print("Ingrese el documento del ciudadano: ");
                    // lee el documento
                    documento = sc.next();
                    // modifica la información y muestra el resultado
                    System.out.println(m.modificarInformacion(cola, historial, documento, sc));
                    break;
                // opción 5
                case 5:
                    // pide el documento
                    System.out.print("Ingrese el documento del ciudadano: ");
                    // lee el documento
                    documento = sc.next();
                    // cancela la solicitud y muestra el resultado
                    System.out.println(m.cancelarSolicitud(cola, historial, documento));
                    break;
                // opción 6
                case 6:
                    // pide el documento
                    System.out.print("Ingrese el documento del ciudadano: ");
                    // lee el documento
                    documento = sc.next();
                    // finaliza el trámite y muestra el resultado
                    System.out.println(m.finalizarTramite(cola, historial, documento));
                    break;
                // opción 7
                case 7:
                    // pide el documento
                    System.out.print("Ingrese el documento del ciudadano: ");
                    // lee el documento
                    documento = sc.next();
                    // muestra los datos y el estado del ciudadano
                    System.out.println(m.consultarCiudadano(cola, documento));
                    break;
                // opción 8
                case 8:
                    // muestra el historial, lo más reciente primero
                    System.out.println(m.mostrarHistorial(historial));
                    break;
                // opción 9
                case 9:
                    // muestra el resumen por estados
                    System.out.println(m.resumen(cola));
                    break;
                // opción 0
                case 0:
                    // muestra el resumen final antes de salir
                    System.out.println(m.resumen(cola));
                    // despídete
                    System.out.println("Hasta luego");
                    // apaga el interruptor para que el menú no se repita
                    continuar = false;
                    break;
                // cualquier otro número (no debería pasar, porque validarRango lo impide)
                default:
                    // avisa que la opción no existe
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}