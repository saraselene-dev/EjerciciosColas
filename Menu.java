import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Solicitud> cola = new LinkedList<>();
        Metodos m = new Metodos();
        // arreglo con los vehículos de la empresa
        Vehiculo[] flota = m.crearFlota();
        boolean continuar = true;

        System.out.println("\nEmpresa de transporte");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar solicitudes de transporte");
            System.out.println("2) Ver solicitudes pendientes");
            System.out.println("3) Ver estado de la flota");
            System.out.println("4) Despachar un vehículo");
            System.out.println("5) Registrar entrega");
            System.out.println("6) Consultar solicitud por número");
            System.out.println("7) Resumen");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 7);

            switch (opt) {
                case 1:
                    cola = m.registrarSolicitudes(cola, flota, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarPendientes(cola));
                    break;
                case 3:
                    System.out.println(m.estadoFlota(flota));
                    break;
                case 4:
                    System.out.println(m.despacharVehiculo(cola, flota, sc));
                    break;
                case 5:
                    System.out.println(m.registrarEntrega(cola, flota, sc));
                    break;
                case 6:
                    System.out.print("Número de la solicitud: ");
                    System.out.println(m.consultarSolicitud(cola, m.validarEntero(sc)));
                    break;
                case 7:
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