import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// clase del menú principal: aquí arranca el programa
public class Menu {
    // método principal: lo primero que ejecuta Java
    public static void main(String[] args) {
        // crea el único lector del teclado del programa
        Scanner sc = new Scanner(System.in);
        // crea la fila de la recepción, vacía
        Queue<Visitante> cola = new LinkedList<>();
        // crea el objeto que tiene todos los métodos
        Metodos m = new Metodos();
        // crea el interruptor del menú y déjalo encendido
        boolean continuar = true;

        // da la bienvenida
        System.out.println("\nBienvenidos a la recepción de la empresa");

        // mientras el interruptor siga encendido, muestra el menú
        while (continuar) {
            // título del menú
            System.out.println("\n¿Qué desea realizar?");
            // opción 1: llegar
            System.out.println("1) Registrar llegada de visitantes");
            // opción 2: esperar
            System.out.println("2) Consultar visitantes en espera");
            // opción 3: ser llamado
            System.out.println("3) Llamar al siguiente visitante");
            // opción 4: no responder
            System.out.println("4) Registrar que un visitante no respondió");
            // opción 5: cancelar
            System.out.println("5) Cancelar visita");
            // opción 6: cambiar funcionario
            System.out.println("6) Cambiar funcionario a visitar");
            // opción 7: ser atendido
            System.out.println("7) Marcar visitante como atendido");
            // opción 8: consultar un visitante
            System.out.println("8) Consultar visitante por documento");
            // opción 9: resumen
            System.out.println("9) Resumen de la recepción");
            // opción 0: salir
            System.out.println("0) Salir");
            // pide la opción en la misma línea
            System.out.print("Opción: ");
            // lee la opción y asegúrate de que esté entre 0 y 9
            int opt = m.validarRango(sc, 0, 9);
            // variable para el documento, que usan varias opciones
            String documento = "";

            // según la opción elegida...
            switch (opt) {
                // opción 1
                case 1:
                    // registra visitantes y guarda la cola actualizada
                    cola = m.registrarLlegada(cola, m, sc);
                    break;
                // opción 2
                case 2:
                    // muestra a los que esperan
                    System.out.println(m.consultarEnEspera(cola));
                    break;
                // opción 3
                case 3:
                    // llama al siguiente y anuncia a quién
                    System.out.println(m.llamarSiguiente(cola));
                    break;
                // opción 4
                case 4:
                    // pide el documento
                    System.out.print("Ingrese el documento del visitante: ");
                    // lee el documento
                    documento = sc.next();
                    // registra que no respondió y muestra qué pasó con él
                    System.out.println(m.noResponde(cola, documento));
                    break;
                // opción 5
                case 5:
                    // pide el documento
                    System.out.print("Ingrese el documento del visitante: ");
                    // lee el documento
                    documento = sc.next();
                    // cancela la visita y muestra el resultado
                    System.out.println(m.cancelarVisita(cola, documento));
                    break;
                // opción 6
                case 6:
                    // pide el documento
                    System.out.print("Ingrese el documento del visitante: ");
                    // lee el documento
                    documento = sc.next();
                    // muestra los funcionarios y guarda el elegido
                    String nuevoFuncionario = m.menuFuncionario(sc);
                    // cambia el funcionario y muestra el resultado
                    System.out.println(m.cambiarFuncionario(cola, documento, nuevoFuncionario));
                    break;
                // opción 7
                case 7:
                    // pide el documento
                    System.out.print("Ingrese el documento del visitante: ");
                    // lee el documento
                    documento = sc.next();
                    // marca como atendido y muestra el resultado
                    System.out.println(m.marcarAtendido(cola, documento));
                    break;
                // opción 8
                case 8:
                    // pide el documento
                    System.out.print("Ingrese el documento del visitante: ");
                    // lee el documento
                    documento = sc.next();
                    // muestra los datos y el estado del visitante
                    System.out.println(m.consultarVisitante(cola, documento));
                    break;
                // opción 9
                case 9:
                    // muestra el resumen por estados
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