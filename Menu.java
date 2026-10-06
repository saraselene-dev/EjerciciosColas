import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Cliente> cola = new LinkedList<>();
        // registro de cada movimiento de los clientes, en el orden en que ocurren
        Queue<String> movimientos = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nSupermercado - Control de cajas");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar clientes en fila");
            System.out.println("2) Ver filas de todas las cajas");
            System.out.println("3) Atender en una caja");
            System.out.println("4) Cliente abandona la fila");
            System.out.println("5) Cliente cambia de caja");
            System.out.println("6) Consultar cliente por turno");
            System.out.println("7) Ver movimientos");
            System.out.println("8) Resumen");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 8);
            int turno = 0;

            switch (opt) {
                case 1:
                    cola = m.registrarClientes(cola, movimientos, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarFilas(cola));
                    break;
                case 3:
                    System.out.println(m.atenderEnCaja(cola, movimientos, sc));
                    break;
                case 4:
                    System.out.print("Ingrese el turno del cliente: ");
                    turno = m.validarEntero(sc);
                    System.out.println(m.abandonarFila(cola, movimientos, turno));
                    break;
                case 5:
                    System.out.print("Ingrese el turno del cliente: ");
                    turno = m.validarEntero(sc);
                    System.out.println(m.cambiarCaja(cola, movimientos, turno, sc));
                    break;
                case 6:
                    System.out.print("Ingrese el turno del cliente: ");
                    turno = m.validarEntero(sc);
                    System.out.println(m.consultarCliente(cola, turno));
                    break;
                case 7:
                    System.out.println(m.mostrarMovimientos(movimientos));
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