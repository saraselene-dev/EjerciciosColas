import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Persona> cola = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nEntidad financiera - Solicitudes de crédito");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar solicitudes");
            System.out.println("2) Consultar solicitudes en espera");
            System.out.println("3) Iniciar asesoría");
            System.out.println("4) Modificar solicitud");
            System.out.println("5) Finalizar asesoría");
            System.out.println("6) Cancelar solicitud");
            System.out.println("7) Consultar solicitud por documento");
            System.out.println("8) Resumen del día");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 8);
            String documento = "";

            switch (opt) {
                case 1:
                    cola = m.registrarSolicitudes(cola, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.iniciarAsesoria(cola, sc));
                    break;
                case 4:
                    System.out.print("Ingrese el documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.modificarSolicitud(cola, documento, sc));
                    break;
                case 5:
                    System.out.print("Ingrese el documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.finalizarAsesoria(cola, documento, sc));
                    break;
                case 6:
                    System.out.print("Ingrese el documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.cancelarSolicitud(cola, documento));
                    break;
                case 7:
                    System.out.print("Ingrese el documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.consultarSolicitud(cola, documento));
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