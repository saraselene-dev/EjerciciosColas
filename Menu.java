import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Llamada> cola = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println("\nCall Center");

        while (continuar) {
            System.out.println("\n¿Qué desea realizar?");
            System.out.println("1) Registrar llamadas");
            System.out.println("2) Ver llamadas en espera");
            System.out.println("3) Atender siguiente llamada");
            System.out.println("4) Transferir llamada");
            System.out.println("5) Actualizar llamada");
            System.out.println("6) Cancelar llamada");
            System.out.println("7) Finalizar llamada");
            System.out.println("8) Consultar llamada por número");
            System.out.println("9) Resumen de la jornada");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 9);
            int numero = 0;

            switch (opt) {
                case 1:
                    cola = m.registrarLlamadas(cola, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.atenderSiguiente(cola, sc));
                    break;
                case 4:
                    System.out.print("Número de la llamada: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.transferirLlamada(cola, numero, sc));
                    break;
                case 5:
                    System.out.print("Número de la llamada: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.actualizarLlamada(cola, numero, sc));
                    break;
                case 6:
                    System.out.print("Número de la llamada: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.cancelarLlamada(cola, numero));
                    break;
                case 7:
                    System.out.print("Número de la llamada: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.finalizarLlamada(cola, numero));
                    break;
                case 8:
                    System.out.print("Número de la llamada: ");
                    numero = m.validarEntero(sc);
                    System.out.println(m.consultarLlamada(cola, numero));
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