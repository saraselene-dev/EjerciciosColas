
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// clase del menú principal: aquí arranca el programa
public class Menu {
    // método principal: lo primero que ejecuta Java
    public static void main(String[] args) {
        // crea el único lector del teclado del programa
        Scanner sc = new Scanner(System.in);
        // crea el objeto que tiene todos los métodos
        Metodos m = new Metodos();

        // crea la matriz original de la clínica: 5 pacientes (filas) con 5 datos cada uno (columnas)
        String[][] matriz = {
                // fila 0: ID, nombre, edad, servicio, estado
                { "101", "Ana", "32", "Medicina", "Pendiente" },
                // fila 1
                { "102", "Carlos", "67", "Medicina", "Pendiente" },
                // fila 2
                { "103", "Laura", "25", "Odontología", "Pendiente" },
                // fila 3
                { "104", "Pedro", "71", "Medicina", "Pendiente" },
                // fila 4
                { "105", "Marta", "45", "Odontología", "Pendiente" }
        // aquí termina la matriz
        };

        // convierte cada fila de la matriz en un objeto Paciente y guárdalos en un arreglo
        Paciente[] registrados = m.crearPacientes(matriz);
        // crea la fila normal de atención, vacía
        Queue<Paciente> filaNormal = new LinkedList<>();
        // crea la fila prioritaria, vacía
        Queue<Paciente> filaPrioritaria = new LinkedList<>();
        // crea la cola donde quedan todos los que se marcan como prioritarios
        Queue<Paciente> prioritarios = new LinkedList<>();
        // crea la cola de atendidos, en orden de atención
        Queue<Paciente> atendidos = new LinkedList<>();
        // crea la cola de cancelados, en orden de cancelación
        Queue<Paciente> cancelados = new LinkedList<>();
        // crea la pila de atención: el último atendido queda arriba
        Stack<Paciente> pilaAtencion = new Stack<>();
        // crea el arreglo del historial de operaciones, con espacio para 100
        String[] historial = new String[100];
        // crea el interruptor del menú y déjalo encendido
        boolean continuar = true;

        // da la bienvenida
        System.out.println("\nBienvenidos al sistema de atención de la clínica");

        // mientras el interruptor siga encendido, muestra el menú
        while (continuar) {
            // título del menú
            System.out.println("\n¿Qué desea realizar?");
            // opción 1
            System.out.println("1) Mostrar pacientes registrados");
            // opción 2
            System.out.println("2) Enviar paciente a la fila");
            // opción 3
            System.out.println("3) Marcar paciente como prioritario");
            // opción 4
            System.out.println("4) Atender siguiente paciente");
            // opción 5
            System.out.println("5) Cancelar cita");
            // opción 6
            System.out.println("6) Cambiar servicio");
            // opción 7
            System.out.println("7) Retirar paciente de la atención");
            // opción 8
            System.out.println("8) Volver a solicitar atención");
            // opción 9
            System.out.println("9) Mostrar pacientes pendientes (pila)");
            // opción 10
            System.out.println("10) Mostrar pacientes atendidos (cola)");
            // opción 11
            System.out.println("11) Mostrar pacientes cancelados (cola)");
            // opción 12
            System.out.println("12) Mostrar pacientes prioritarios (cola)");
            // opción 13
            System.out.println("13) Mostrar historial de operaciones");
            // opción 14
            System.out.println("14) Mostrar historial de atención (último primero)");
            // opción 15
            System.out.println("15) Mostrar matriz actualizada");
            // opción 0
            System.out.println("0) Mostrar resultado final y salir");
            // pide la opción en la misma línea
            System.out.print("Opción: ");
            // lee la opción y asegúrate de que esté entre 0 y 15
            int opt = m.validarRango(sc, 0, 15);
            // variable para el ID, que usan varias opciones
            String id = "";

            // según la opción elegida...
            switch (opt) {
                // opción 1
                case 1:
                    // muestra el arreglo de pacientes registrados
                    System.out.println(m.mostrarRegistrados(registrados));
                    // sal del switch
                    break;
                // opción 2
                case 2:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // envía al paciente a su fila y muestra el resultado
                    System.out.println(m.enviarAFila(id, registrados, matriz, filaNormal, filaPrioritaria, historial));
                    // sal del switch
                    break;
                // opción 3
                case 3:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // marca al paciente como prioritario y muestra el resultado
                    System.out.println(m.marcarPrioritario(id, registrados, filaNormal, filaPrioritaria, prioritarios, historial));
                    // sal del switch
                    break;
                // opción 4
                case 4:
                    // atiende al siguiente (primero los prioritarios) y muestra a quién
                    System.out.println(m.atenderSiguiente(matriz, filaNormal, filaPrioritaria, pilaAtencion, atendidos, historial));
                    // sal del switch
                    break;
                // opción 5
                case 5:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // cancela la cita y muestra el resultado
                    System.out.println(m.cancelarCita(id, registrados, matriz, filaNormal, filaPrioritaria, cancelados, historial));
                    // sal del switch
                    break;
                // opción 6
                case 6:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // muestra los servicios y guarda el elegido
                    String nuevoServicio = m.menuServicio(sc);
                    // cambia el servicio y muestra el resultado
                    System.out.println(m.cambiarServicio(id, nuevoServicio, registrados, matriz, historial));
                    // sal del switch
                    break;
                // opción 7
                case 7:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // retira al paciente de la fila y muestra el resultado
                    System.out.println(m.retirarPaciente(id, registrados, matriz, filaNormal, filaPrioritaria, historial));
                    // sal del switch
                    break;
                // opción 8
                case 8:
                    // pide el ID
                    System.out.print("Ingrese el ID del paciente: ");
                    // lee el ID
                    id = sc.next();
                    // devuelve al paciente a la fila y muestra el resultado
                    System.out.println(m.volverASolicitar(id, registrados, matriz, filaNormal, filaPrioritaria, cancelados, historial));
                    // sal del switch
                    break;
                // opción 9
                case 9:
                    // arma la pila de pendientes y muéstrala
                    System.out.println(m.mostrarPila(m.construirPilaPendientes(registrados), "Pacientes pendientes (orden de llegada)"));
                    // sal del switch
                    break;
                // opción 10
                case 10:
                    // muestra la cola de atendidos
                    System.out.println(m.mostrarCola(atendidos, "Pacientes atendidos"));
                    // sal del switch
                    break;
                // opción 11
                case 11:
                    // muestra la cola de cancelados
                    System.out.println(m.mostrarCola(cancelados, "Pacientes cancelados"));
                    // sal del switch
                    break;
                // opción 12
                case 12:
                    // muestra la cola de prioritarios
                    System.out.println(m.mostrarCola(prioritarios, "Pacientes prioritarios"));
                    // sal del switch
                    break;
                // opción 13
                case 13:
                    // muestra el arreglo del historial de operaciones
                    System.out.println(m.mostrarHistorialOperaciones(historial));
                    // sal del switch
                    break;
                // opción 14
                case 14:
                    // muestra la pila de atención, del último al primero
                    System.out.println(m.mostrarPila(pilaAtencion, "Historial de atención (último primero)"));
                    // sal del switch
                    break;
                // opción 15
                case 15:
                    // muestra la matriz para comprobar que está sincronizada
                    System.out.println(m.mostrarMatriz(matriz));
                    // sal del switch
                    break;
                // opción 0
                case 0:
                    // título del resultado final
                    System.out.println("\n========== RESULTADO FINAL ==========");
                    // 1. registrados (arreglo)
                    System.out.println(m.mostrarRegistrados(registrados));
                    // 2. pendientes (pila en orden de llegada)
                    System.out.println(m.mostrarPila(m.construirPilaPendientes(registrados), "Pacientes pendientes (orden de llegada)"));
                    // 3. atendidos (cola)
                    System.out.println(m.mostrarCola(atendidos, "Pacientes atendidos"));
                    // 4. cancelados (cola)
                    System.out.println(m.mostrarCola(cancelados, "Pacientes cancelados"));
                    // 5. prioritarios (cola)
                    System.out.println(m.mostrarCola(prioritarios, "Pacientes prioritarios"));
                    // 6. historial de operaciones (arreglo)
                    System.out.println(m.mostrarHistorialOperaciones(historial));
                    // despídete
                    System.out.println("Hasta luego");
                    // apaga el interruptor para que el menú no se repita
                    continuar = false;
                    // sal del switch
                    break;
                // cualquier otro número (no debería pasar, porque validarRango lo impide)
                default:
                    // avisa que la opción no existe
                    System.out.println("Opción no válida");
                    // sal del switch
                    break;
            // aquí termina el switch
            }
        // aquí termina el while: vuelve a mostrar el menú si el interruptor sigue encendido
        }

    }

}