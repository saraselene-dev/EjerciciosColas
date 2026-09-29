import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Cliente> cola = new LinkedList<>();
        Stack<Cliente> historial = new Stack<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        System.out.println();
        System.out.println("Bienvenidos a BancaEstructura ");
        System.out.println();

        while (continuar) {
            System.out.println();
            System.out.println("¿Qué desea realizar?");
            System.out.println("1) Registrar usuarios");
            System.out.println("2) Consultar usuarios en espera");
            System.out.println("3) Atender al siguiente usuario");
            System.out.println("4) Marcar un cliente como atendido");
            System.out.println("5) Cambiar atención a preferencial");
            System.out.println("6) Cancelar turno ");
            System.out.println("7) Buscar usuario por identificación");
            System.out.println("8) Consultar usuarios en lista de espera");
            System.out.println("9) Consultar usuarios con o sin preferencia en lista de espera ");
            System.out.println("10) Historial de atenciones");
            System.out.println("0) Salir ");
            System.out.print("Opción: ");
            int opt = m.validarRango(sc, 0, 10);
            String id = "";

            switch (opt) {
                case 1:
                    cola = m.registrarTurnos(cola, m, sc);
                    break;
                case 2:
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                case 3:
                    System.out.println(m.llamarSiguiente(cola));
                    break;
                case 4:
                    System.out.print("Ingrese la identificación: ");
                    id = sc.next();
                    System.out.println(m.marcarAtendido(cola, historial, id));
                    break;
                case 5:
                    System.out.print("Ingrese la identificación: ");
                    id = sc.next();
                    System.out.println("¿Presentó la documentación? Sí: 1 / No: 0");
                    int doc = m.validarRango(sc, 0, 1);
                    if (doc == 1) {
                        System.out.println(m.cambiarAPreferencial(cola, id));
                    } else {
                        System.out.println("Sin documentación no se puede cambiar la condición");
                    }
                    break;
                case 6:
                    System.out.print("Ingrese la identificación: ");
                    id = sc.next();
                    System.out.println(m.cancelarTurno(cola, id));
                    break;
                case 7:
                    System.out.print("Ingrese la identificación: ");
                    id = sc.next();
                    System.out.println(m.buscarPorId(cola, id));
                    break;
                case 8:
                    System.out.println("Usuarios en espera: " + m.contarEnEspera(cola, -1));
                    break;
                case 9:
                    System.out.println("Preferenciales en espera: " + m.contarEnEspera(cola, 1));
                    System.out.println("Normales en espera: " + m.contarEnEspera(cola, 0));
                    break;
                case 10:
                    System.out.println(m.mostrarHistorial(historial));
                    break;
                case 0:
                    continuar = false;
                    JOptionPane.showMessageDialog(null, "¡Gracias por usar BancaEstructura, hasta Luego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "¡Opción inválida!");
                    break;
            }
        }
    }
}