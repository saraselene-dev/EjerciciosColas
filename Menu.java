import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Solicitud> cola = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nEmpresa de mantenimiento");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar solicitudes");
            System.out.println("2) Ver solicitudes en espera");
            System.out.println("3) Ver estado de los técnicos");
            System.out.println("4) Completar trabajo");
            System.out.println("5) Cambiar prioridad");
            System.out.println("6) Cancelar solicitud");
            System.out.println("7) Consultar solicitud por número");
            System.out.println("8) Resumen");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 8);
            int numero = 0;

            switch (opt) {
                case 1:
                    cola = m.registrarSolicitudes(cola, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.estadoTecnicos(cola));
                    break;
                case 4:
                    System.out.print("Número de la solicitud: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.completarTrabajo(cola, numero));
                    break;
                case 5:
                    System.out.print("Número de la solicitud: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.cambiarPrioridad(cola, numero, sc));
                    break;
                case 6:
                    System.out.print("Número de la solicitud: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.cancelarSolicitud(cola, numero));
                    break;
                case 7:
                    System.out.print("Número de la solicitud: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.consultarSolicitud(cola, numero));
                    break;
                case 8:
                    System.out.println(m.resumen(cola));
                    break;
                case 0:
                    System.out.println(m.resumen(cola));
                    System.out.println("Hasta luego");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}