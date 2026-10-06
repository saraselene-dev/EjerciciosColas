import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;

// clase del menú principal: aquí arranca el programa
public class Menu {
    // método principal: lo primero que ejecuta Java
    public static void main(String[] args) {
        // crea el único lector del teclado de todo el programa
        Scanner sc = new Scanner(System.in);
        // crea la fila del banco, vacía
        Queue<Cliente> cola = new LinkedList<>();
        // crea la pila donde se guardará el historial de atenciones
        Stack<Cliente> historial = new Stack<>();
        // crea el objeto que tiene todos los métodos del programa
        Metodos m = new Metodos();
        // crea el interruptor del menú y déjalo encendido
        boolean continuar = true;

        // deja una línea en blanco
        System.out.println();
        // da la bienvenida
        System.out.println("Bienvenidos a BancaEstructura - Jornada de atención");

        // mientras el interruptor siga encendido, muestra el menú una y otra vez
        while (continuar) {
            // deja una línea en blanco para separar
            System.out.println();
            // pregunta qué quiere hacer
            System.out.println("¿Qué desea realizar?");
            System.out.println("1) Registrar clientes");
            System.out.println("2) Consultar clientes en espera");
            System.out.println("3) Llamar al siguiente cliente");
            System.out.println("4) Marcar cliente como atendido");
            System.out.println("5) Modificar trámite");
            System.out.println("6) Cancelar turno");
            System.out.println("7) Consultar estado de un cliente");
            System.out.println("8) Cantidad de clientes en espera");
            System.out.println("9) Resumen de la jornada");
            System.out.println("10) Historial de atenciones");
            System.out.println("0) Cerrar jornada y salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 10);
            // crea la variable para la cédula, que usan varias opciones
            String id = "";

            // según la opción elegida...
            switch (opt) {
                // opción 1
                case 1:
                    // registra clientes y guarda la cola actualizada
                    cola = m.registrarTurnos(cola, m, sc);
                    // sal del switch
                    break;
                // opción 2
                case 2:
                    // muestra la lista de los que esperan
                    System.out.println(m.consultarEnEspera(cola));
                    // sal del switch
                    break;
                // opción 3
                case 3:
                    // llama al siguiente y muestra a quién
                    System.out.println(m.llamarSiguiente(cola));
                    // sal del switch
                    break;
                // opción 4
                case 4:
                    // pide la cédula
                    System.out.print("Ingrese la identificación: ");
                    // lee la cédula
                    id = sc.next();
                    // marca como atendido, apílalo en el historial y muestra el resultado
                    System.out.println(m.marcarAtendido(cola, historial, id));
                    // sal del switch
                    break;
                // opción 5
                case 5:
                    // pide la cédula
                    System.out.print("Ingrese la identificación: ");
                    // lee la cédula
                    id = sc.next();
                    // muestra el menú de trámites y guarda el elegido
                    int nuevoTramite = m.menuTramite(sc);
                    // cambia el trámite y muestra el resultado
                    System.out.println(m.modificarTramite(cola, id, nuevoTramite));
                    // sal del switch
                    break;
                // opción 6
                case 6:
                    // pide la cédula
                    System.out.print("Ingrese la identificación: ");
                    // lee la cédula
                    id = sc.next();
                    // intenta cancelar el turno y muestra el resultado
                    System.out.println(m.cancelarTurno(cola, id));
                    // sal del switch
                    break;
                // opción 7
                case 7:
                    // pide la cédula
                    System.out.print("Ingrese la identificación: ");
                    // lee la cédula
                    id = sc.next();
                    // busca al cliente y muestra sus datos y estado
                    System.out.println(m.buscarPorId(cola, id));
                    // sal del switch
                    break;
                // opción 8
                case 8:
                    // cuenta los clientes con estado 1 y muéstralo
                    System.out.println("Clientes en espera: " + m.contarEstado(cola, 1));
                    // sal del switch
                    break;
                // opción 9
                case 9:
                    // muestra el balance de la jornada
                    System.out.println(m.resumenJornada(cola));
                    // sal del switch
                    break;
                // opción 10
                case 10:
                    // muestra el historial, del más reciente al más antiguo
                    System.out.println(m.mostrarHistorial(historial));
                    // sal del switch
                    break;
                // opción 0
                case 0:
                    // marca a los pendientes como no atendidos y muestra el resumen final
                    System.out.println(m.cerrarJornada(cola));
                    // apaga el interruptor para que el menú no se repita
                    continuar = false;
                    // despídete
                    JOptionPane.showMessageDialog(null, "¡Gracias por usar BancaEstructura, hasta luego!");
                    // sal del switch
                    break;
                // cualquier otro número (no debería pasar, porque validarRango lo impide)
                default:
                    // avisa que la opción no existe
                    JOptionPane.showMessageDialog(null, "¡Opción inválida!");
                    // sal del switch
                    break;
            // aquí termina el switch
            }
        // aquí termina el while: vuelve a mostrar el menú si el interruptor sigue encendido
        }
    // aquí termina el método main
    }
// aquí termina la clase Menu
}