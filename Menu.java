import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Cliente> cola = new LinkedList<>();
        // todo lo que ocurre queda en una pila: lo más reciente arriba
        Stack<String> historial = new Stack<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nCentro de atención integral");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar clientes");
            System.out.println("2) Ver clientes en espera");
            System.out.println("3) Llamar siguiente cliente");
            System.out.println("4) Registrar que no respondió");
            System.out.println("5) Atender cliente llamado");
            System.out.println("6) Cancelar turno");
            System.out.println("7) Cliente abandona la fila");
            System.out.println("8) Cliente regresa después de abandonar");
            System.out.println("9) Modificar tipo de solicitud");
            System.out.println("10) Cambiar prioridad");
            System.out.println("11) Reemplazar cliente");
            System.out.println("12) Corregir o anular error de registro");
            System.out.println("13) Consultar turno");
            System.out.println("14) Ver historial");
            System.out.println("15) Estadísticas");
            System.out.println("0) Salir (muestra las estadísticas finales)");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 15);
            int turno = 0;

            // las opciones 4 a 13 trabajan sobre un turno específico, así que se pide una sola vez aquí
            if (opt >= 4 && opt <= 13) {
                System.out.print("Número de turno: ");
                turno = m.validarEntero(sc);
            }

            switch (opt) {
                case 1:
                    cola = m.registrarClientes(cola, historial, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.llamarSiguiente(cola, historial, sc));
                    break;
                case 4:
                    System.out.println(m.noResponde(cola, historial, turno));
                    break;
                case 5:
                    System.out.println(m.atenderCliente(cola, historial, turno));
                    break;
                case 6:
                    System.out.println(m.cancelarTurno(cola, historial, turno));
                    break;
                case 7:
                    System.out.println(m.abandonar(cola, historial, turno));
                    break;
                case 8:
                    System.out.println(m.regresar(cola, historial, turno));
                    break;
                case 9:
                    System.out.println(m.modificarTipo(cola, historial, turno, sc));
                    break;
                case 10:
                    System.out.println(m.cambiarPrioridad(cola, historial, turno, sc));
                    break;
                case 11:
                    System.out.println(m.reemplazar(cola, historial, turno, sc));
                    break;
                case 12:
                    System.out.println(m.errorRegistro(cola, historial, turno, sc));
                    break;
                case 13:
                    System.out.println(m.consultarTurno(cola, turno));
                    break;
                case 14:
                    System.out.println(m.mostrarHistorial(historial));
                    break;
                case 15:
                    System.out.println(m.estadisticas(cola));
                    break;
                case 0:
                    System.out.println(m.estadisticas(cola));
                    System.out.println("\nHasta luego");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}