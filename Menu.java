import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// clase del menú principal: aquí arranca el programa
public class Menu {
    // método principal: lo primero que ejecuta Java
    public static void main(String[] args) {
        // crea el único lector del teclado del programa
        Scanner sc = new Scanner(System.in);
        // crea la cola de solicitudes de visita, vacía
        Queue<Interesado> cola = new LinkedList<>();
        // crea el objeto que tiene todos los métodos
        Metodos m = new Metodos();
        // crea el interruptor del menú y déjalo encendido
        boolean continuar = true;

        // da la bienvenida
        System.out.println("\nInmobiliaria - Visitas a la propiedad");

        // mientras el interruptor siga encendido, muestra el menú
        while (continuar) {
            // título del menú
            System.out.println("\n¿Qué desea realizar?");
            // opción 1
            System.out.println("1) Registrar solicitudes de visita");
            // opción 2
            System.out.println("2) Ver agenda por horario (confirmados y lista de espera)");
            // opción 3
            System.out.println("3) Cancelar solicitud");
            // opción 4
            System.out.println("4) Cambiar horario");
            // opción 5
            System.out.println("5) Reemplazar por persona autorizada");
            // opción 6
            System.out.println("6) Consultar solicitud por documento");
            // opción 7
            System.out.println("7) Resumen");
            // opción 0
            System.out.println("0) Salir");
            // pide la opción en la misma línea
            System.out.print("Opción: ");
            // lee la opción y asegúrate de que esté entre 0 y 7
            int opt = m.validarRango(sc, 0, 7);
            // variable para el documento, que usan varias opciones
            String documento = "";

            // según la opción elegida...
            switch (opt) {
                // opción 1
                case 1:
                    // registra solicitudes y guarda la cola actualizada
                    cola = m.registrarSolicitudes(cola, m, sc);
                    break;
                // opción 2
                case 2:
                    // muestra la agenda de cada horario
                    System.out.println(m.consultarAgenda(cola));
                    break;
                // opción 3
                case 3:
                    // pide el documento
                    System.out.print("Ingrese el documento del interesado: ");
                    // lee el documento
                    documento = sc.next();
                    // cancela y muestra el resultado (y a quién se le dio el cupo)
                    System.out.println(m.cancelarSolicitud(cola, documento));
                    break;
                // opción 4
                case 4:
                    // pide el documento
                    System.out.print("Ingrese el documento del interesado: ");
                    // lee el documento
                    documento = sc.next();
                    // cambia el horario y muestra el resultado
                    System.out.println(m.cambiarHorario(cola, documento, sc));
                    break;
                // opción 5
                case 5:
                    // pide el documento de quien será reemplazado
                    System.out.print("Ingrese el documento de quien será reemplazado: ");
                    // lee el documento
                    documento = sc.next();
                    // hace el reemplazo y muestra el resultado
                    System.out.println(m.reemplazarInteresado(cola, documento, sc));
                    break;
                // opción 6
                case 6:
                    // pide el documento
                    System.out.print("Ingrese el documento del interesado: ");
                    // lee el documento
                    documento = sc.next();
                    // muestra la solicitud
                    System.out.println(m.consultarInteresado(cola, documento));
                    break;
                // opción 7
                case 7:
                    // muestra el resumen
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