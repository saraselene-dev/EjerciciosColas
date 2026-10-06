import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Caso> cola = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nServicio posventa");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar clientes");
            System.out.println("2) Ver clientes en espera");
            System.out.println("3) Iniciar atención");
            System.out.println("4) Cambiar motivo");
            System.out.println("5) Presentar nueva información");
            System.out.println("6) Cancelar / desistir");
            System.out.println("7) Resolver caso");
            System.out.println("8) Consultar caso por documento");
            System.out.println("9) Resumen");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 9);
            String documento = "";

            switch (opt) {
                case 1:
                    cola = m.registrarCasos(cola, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.iniciarAtencion(cola, sc));
                    break;
                case 4:
                    System.out.print("Documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.cambiarMotivo(cola, documento, sc));
                    break;
                case 5:
                    System.out.print("Documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.presentarInformacion(cola, documento, sc));
                    break;
                case 6:
                    System.out.print("Documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.cancelarCaso(cola, documento));
                    break;
                case 7:
                    System.out.print("Documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.resolverCaso(cola, documento));
                    break;
                case 8:
                    System.out.print("Documento del cliente: ");
                    documento = sc.next();
                    System.out.println(m.consultarCaso(cola, documento));
                    break;
                case 9:
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