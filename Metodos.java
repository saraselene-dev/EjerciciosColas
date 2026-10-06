
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// Clase con toda la lógica de la clínica
public class Metodos {

    // VALIDACIONES

    // Método que lee un entero; recibe el lector
    public int validarEntero(Scanner sc) {
        // mientras lo escrito no sea un número entero
        while (!sc.hasNextInt()) {
            // avisa al usuario
            System.out.println("Por favor ingrese un dato numérico entero");
            // saca el dato malo del lector y bótalo
            sc.next();
        }
        // ya hay un entero: léelo y devuélvelo
        return sc.nextInt();
    }

    // método que lee un entero dentro de un rango; recibe el lector y los límites
    public int validarRango(Scanner sc, int min, int max) {
        // lee un número que ya sea entero
        int num = validarEntero(sc);
        // mientras esté por fuera del rango
        while (num < min || num > max) {
            // dile cuál es el rango permitido
            System.out.println("Ingrese un valor entre " + min + " y " + max);
            // y vuelve a leer
            num = validarEntero(sc);
        // vuelve a revisar
        }
        // devuelve el número válido
        return num;
    }

  // MATRIZ DE LOS OBJETOS

    // método que convierte cada fila de la matriz en un objeto Paciente; devuelve el arreglo de pacientes
    public Paciente[] crearPacientes(String[][] matriz) {
        // crea un arreglo del mismo tamaño que la cantidad de filas de la matriz
        Paciente[] registrados = new Paciente[matriz.length];
        // recorre la matriz fila por fila
        for (int i = 0; i < matriz.length; i++) {
            // crea un paciente con los datos de la fila i; la edad se convierte de texto a número
            registrados[i] = new Paciente(matriz[i][0], matriz[i][1], Integer.parseInt(matriz[i][2]),
                    matriz[i][3], matriz[i][4], i + 1);
        // pasa a la siguiente fila
        }
        // devuelve el arreglo con todos los pacientes
        return registrados;
    }


    // MÉTODOS DE APOYO

    // método que busca un paciente por su ID en el arreglo; devuelve el paciente o null si no existe
    public Paciente buscarPaciente(Paciente[] registrados, String id) {
        // recorre el arreglo, un paciente a la vez
        for (Paciente p : registrados) {
            // si su ID es el buscado...
            if (p.getId().equals(id)) {
                // ...devuelve ese paciente
                return p;
            }
        // pasa al siguiente
        }
        // revisé a todos y no estaba: devuelve "nada"
        return null;

    }

    // método de apoyo que arma una línea de texto con los datos de un paciente
    private String datosPaciente(Paciente p) {
        // empieza suponiendo que no es prioritario
        String prioridad = "No";
        // si sí es prioritario...
        if (p.isPrioritario()) {
            // ...cambia el texto
            prioridad = "Sí";
        // aquí termina el if
        }
        // devuelve todos los datos en una sola línea
        return "ID: " + p.getId() + " | " + p.getNombre() + " | " + p.getEdad() + " años | "
                + p.getServicio() + " | " + p.getEstado() + " | Prioritario: " + prioridad + "\n";

    }

    // método que copia en la matriz el servicio y el estado actuales de un paciente; devuelve la matriz
    public String[][] actualizarMatriz(String[][] matriz, Paciente p) {
        // recorre la matriz fila por fila
        for (int i = 0; i < matriz.length; i++) {
            // si el ID de esta fila es el del paciente...
            if (matriz[i][0].equals(p.getId())) {
                // ...actualiza la columna del servicio
                matriz[i][3] = p.getServicio();
                // y la columna del estado
                matriz[i][4] = p.getEstado();
            }
        // pasa a la siguiente fila
        }
        // devuelve la matriz ya actualizada
        return matriz;
    }

    // método que anota una operación en la primera casilla libre del historial; devuelve el historial
    public String[] registrarOperacion(String[] historial, String texto) {
        // recorre el arreglo casilla por casilla
        for (int i = 0; i < historial.length; i++) {
            // si esta casilla está vacía...
            if (historial[i] == null) {
                // ...escribe ahí la operación
                historial[i] = texto;
                // y termina, para no escribirla en más casillas
                return historial;
            }
        // pasa a la siguiente casilla
        }
        // si llegó aquí, el historial estaba lleno: avísalo
        System.out.println("El historial de operaciones está lleno");
        // devuelve el historial sin cambios
        return historial;
    }

    // método que saca a un paciente específico de una cola, usando una cola auxiliar; devuelve la cola
    public Queue<Paciente> quitarDeCola(Queue<Paciente> cola, Paciente p) {
        // crea una cola auxiliar vacía
        Queue<Paciente> aux = new LinkedList<>();
        // mientras la cola original tenga pacientes
        while (!cola.isEmpty()) {
            // ...saca el primero
            Paciente x = cola.poll();
            // si no es el paciente que queremos quitar...
            if (!x.getId().equals(p.getId())) {
                // ...guárdalo en la auxiliar
                aux.offer(x);
            // aquí termina el if (al que sí era, simplemente no se guarda)
            }
        // aquí termina el primer ciclo
        }
        // mientras la auxiliar tenga pacientes...
        while (!aux.isEmpty()) {
            // ...devuélvelos a la cola original, en el mismo orden
            cola.offer(aux.poll());
        }
        // devuelve la cola sin ese paciente
        return cola;
    }

    // método de apoyo que calcula el siguiente número de llegada
    private int siguienteOrden(Paciente[] registrados) {
        // empieza suponiendo que el mayor es 0
        int mayor = 0;
        // recorre a todos los pacientes
        for (Paciente p : registrados) {
            // si el orden de este paciente es mayor que el que llevo...
            if (p.getOrdenLlegada() > mayor) {
                // ...ese es el nuevo mayor
                mayor = p.getOrdenLlegada();
            }
        // pasa al siguiente
        }
        // el siguiente orden es el mayor más uno
        return mayor + 1;
    }

    // método que muestra los servicios y devuelve el elegido como texto
    public String menuServicio(Scanner sc) {
        // muestra la pregunta
        System.out.println("Seleccione el servicio");
        // opción 1
        System.out.println("1) Medicina");
        // opción 2
        System.out.println("2) Odontología");
        // lee la opción validada
        int opt = validarRango(sc, 1, 2);
        // si eligió 1...
        if (opt == 1) {
            // ...devuelve Medicina
            return "Medicina";
        }
        // si no, devuelve Odontología
        return "Odontología";
    }

    // OPERACIONES DE LA JORNADA

    // método que envía a un paciente pendiente a la fila que le corresponde
    public String enviarAFila(String id, Paciente[] registrados, String[][] matriz,
            Queue<Paciente> filaNormal, Queue<Paciente> filaPrioritaria, String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        }
        // si no está pendiente...
        if (!p.getEstado().equals("Pendiente")) {
            // ...no se puede enviar a la fila
            return "Solo se puede enviar a la fila a un paciente pendiente. Estado actual: " + p.getEstado();
        }
        // cambia su estado
        p.setEstado("En fila");
        // si es prioritario...
        if (p.isPrioritario()) {
            // ...va a la fila prioritaria
            filaPrioritaria.offer(p);
        // si no lo es...
        } else {
            // ...va a la fila normal
            filaNormal.offer(p);

        }
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " enviado a la fila");
        // confirma
        return p.getNombre() + " fue enviado a la fila de atención";
    // aquí termina el método
    }

    // método que marca a un paciente como prioritario y, si ya estaba en fila, lo pasa a la fila prioritaria
    public String marcarPrioritario(String id, Paciente[] registrados, Queue<Paciente> filaNormal,
            Queue<Paciente> filaPrioritaria, Queue<Paciente> prioritarios, String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        // aquí termina el if
        }
        // si ya es prioritario...
        if (p.isPrioritario()) {
            // ...no hay nada que cambiar
            return p.getNombre() + " ya es prioritario";
        // aquí termina el if
        }
        // si ya fue atendido, canceló o fue retirado...
        if (!p.getEstado().equals("Pendiente") && !p.getEstado().equals("En fila")) {
            // ...la prioridad ya no tiene efecto
            return "No se puede marcar como prioritario: estado actual " + p.getEstado();
        // aquí termina el if
        }
        // márcalo como prioritario
        p.setPrioritario(true);
        // agrégalo a la cola de prioritarios, en orden de marcación
        prioritarios.offer(p);
        // si ya estaba esperando en la fila normal...
        if (p.getEstado().equals("En fila")) {
            // ...sácalo de la fila normal
            quitarDeCola(filaNormal, p);
            // y ponlo en la fila prioritaria
            filaPrioritaria.offer(p);
        // aquí termina el if
        }
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " marcado como prioritario");
        // confirma
        return p.getNombre() + " ahora es prioritario";
    // aquí termina el método
    }

    // método que atiende al siguiente paciente: primero los prioritarios, luego la fila normal
    public String atenderSiguiente(String[][] matriz, Queue<Paciente> filaNormal, Queue<Paciente> filaPrioritaria,
            Stack<Paciente> pilaAtencion, Queue<Paciente> atendidos, String[] historial) {
        // crea la variable para el paciente, todavía sin nadie
        Paciente p = null;
        // si hay prioritarios esperando...
        if (!filaPrioritaria.isEmpty()) {
            // ...saca al primero de la fila prioritaria
            p = filaPrioritaria.poll();
        // si no, pero hay alguien en la fila normal...
        } else if (!filaNormal.isEmpty()) {
            // ...saca al primero de la fila normal
            p = filaNormal.poll();
        // si las dos filas están vacías...
        } else {
            // ...no hay a quién atender
            return "No hay pacientes en fila";
        // aquí termina el if
        }
        // cambia su estado
        p.setEstado("Atendido");
        // guárdalo encima de la pila de atención: el último atendido queda arriba
        pilaAtencion.push(p);
        // guárdalo también en la cola de atendidos, en orden de atención
        atendidos.offer(p);
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " atendido en " + p.getServicio());
        // confirma
        return "Se atiende a: " + datosPaciente(p);
    // aquí termina el método
    }

    // método que cancela la cita de un paciente pendiente o en fila
    public String cancelarCita(String id, Paciente[] registrados, String[][] matriz, Queue<Paciente> filaNormal,
            Queue<Paciente> filaPrioritaria, Queue<Paciente> cancelados, String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        // aquí termina el if
        }
        // si no está pendiente ni en fila...
        if (!p.getEstado().equals("Pendiente") && !p.getEstado().equals("En fila")) {
            // ...no se puede cancelar
            return "No se puede cancelar: estado actual " + p.getEstado();
        // aquí termina el if
        }
        // si estaba en alguna fila...
        if (p.getEstado().equals("En fila")) {
            // ...sácalo de la fila normal (si no está ahí, no pasa nada)
            quitarDeCola(filaNormal, p);
            // ...y de la fila prioritaria (si no está ahí, no pasa nada)
            quitarDeCola(filaPrioritaria, p);
        // aquí termina el if
        }
        // cambia su estado
        p.setEstado("Cancelado");
        // agrégalo a la cola de cancelados
        cancelados.offer(p);
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " canceló su cita");
        // confirma
        return "Cita de " + p.getNombre() + " cancelada";
    // aquí termina el método
    }

    // método que cambia el servicio de un paciente que todavía no ha sido atendido
    public String cambiarServicio(String id, String nuevoServicio, Paciente[] registrados, String[][] matriz,
            String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        // aquí termina el if
        }
        // si no está pendiente ni en fila...
        if (!p.getEstado().equals("Pendiente") && !p.getEstado().equals("En fila")) {
            // ...ya no se puede cambiar
            return "No se puede cambiar el servicio: estado actual " + p.getEstado();
        // aquí termina el if
        }
        // si eligió el mismo servicio que ya tenía...
        if (p.getServicio().equals(nuevoServicio)) {
            // ...no hay nada que cambiar
            return p.getNombre() + " ya tiene el servicio " + nuevoServicio;
        // aquí termina el if
        }
        // guarda el servicio anterior antes de cambiarlo
        String anterior = p.getServicio();
        // cambia el servicio
        p.setServicio(nuevoServicio);
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " cambió de " + anterior + " a " + nuevoServicio);
        // confirma; conserva su lugar en la fila
        return "Servicio cambiado de " + anterior + " a " + nuevoServicio + ". Conserva su lugar en la fila";
    // aquí termina el método
    }

    // método que retira de la fila a un paciente (decisión de la clínica)
    public String retirarPaciente(String id, Paciente[] registrados, String[][] matriz,
            Queue<Paciente> filaNormal, Queue<Paciente> filaPrioritaria, String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        // aquí termina el if
        }
        // si no está en fila...
        if (!p.getEstado().equals("En fila")) {
            // ...no hay de dónde retirarlo
            return "Solo se puede retirar a un paciente que está en fila. Estado actual: " + p.getEstado();
        // aquí termina el if
        }
        // sácalo de la fila normal (si no está ahí, no pasa nada)
        quitarDeCola(filaNormal, p);
        // sácalo de la fila prioritaria (si no está ahí, no pasa nada)
        quitarDeCola(filaPrioritaria, p);
        // cambia su estado
        p.setEstado("Retirado");
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " retirado de la atención");
        // confirma
        return p.getNombre() + " fue retirado de la atención";
    // aquí termina el método
    }

    // método que devuelve a la fila a un paciente cancelado o retirado, al final, como si volviera a sacar ficha
    public String volverASolicitar(String id, Paciente[] registrados, String[][] matriz, Queue<Paciente> filaNormal,
            Queue<Paciente> filaPrioritaria, Queue<Paciente> cancelados, String[] historial) {
        // busca al paciente por su ID
        Paciente p = buscarPaciente(registrados, id);
        // si no existe...
        if (p == null) {
            // ...avísalo
            return "No existe un paciente con ese ID";
        // aquí termina el if
        }
        // si no está cancelado ni retirado...
        if (!p.getEstado().equals("Cancelado") && !p.getEstado().equals("Retirado")) {
            // ...no tiene sentido volver a solicitar
            return "Solo puede volver a solicitar un paciente cancelado o retirado. Estado actual: " + p.getEstado();
        // aquí termina el if
        }
        // si estaba cancelado...
        if (p.getEstado().equals("Cancelado")) {
            // ...sácalo de la cola de cancelados, para que no aparezca en dos lugares
            quitarDeCola(cancelados, p);
        // aquí termina el if
        }
        // cambia su estado
        p.setEstado("En fila");
        // dale un nuevo número de llegada: queda al final
        p.setOrdenLlegada(siguienteOrden(registrados));
        // si es prioritario...
        if (p.isPrioritario()) {
            // ...vuelve a la fila prioritaria
            filaPrioritaria.offer(p);
        // si no lo es...
        } else {
            // ...vuelve a la fila normal
            filaNormal.offer(p);
        // aquí termina el if/else
        }
        // refleja el cambio en la matriz
        actualizarMatriz(matriz, p);
        // anota la operación en el historial
        registrarOperacion(historial, p.getNombre() + " volvió a solicitar atención");
        // confirma
        return p.getNombre() + " volvió a la fila, al final";
    // aquí termina el método
    }

   
    // RESULTADOS FINALES (MOSTRAR)
   

    // método que muestra el arreglo de pacientes registrados
    public String mostrarRegistrados(Paciente[] registrados) {
        // empieza el texto con un título
        String texto = "Pacientes registrados:\n";
        // recorre el arreglo por posición
        for (int i = 0; i < registrados.length; i++) {
            // pega los datos del paciente de la posición i
            texto += datosPaciente(registrados[i]);
        // pasa a la siguiente posición
        }
        // devuelve el texto
        return texto;
    // aquí termina el método
    }

    // método que muestra la matriz, para comprobar que está sincronizada con los objetos
    public String mostrarMatriz(String[][] matriz) {
        // empieza el texto con un título
        String texto = "Matriz original (actualizada):\n";
        // recorre las filas
        for (int i = 0; i < matriz.length; i++) {
            // recorre las columnas de esa fila
            for (int j = 0; j < matriz[i].length; j++) {
                // pega el dato de la casilla, con un separador
                texto += matriz[i][j] + " | ";
            // pasa a la siguiente columna
            }
            // al terminar la fila, salta de línea
            texto += "\n";
        // pasa a la siguiente fila
        }
        // devuelve el texto
        return texto;
    // aquí termina el método
    }

    // método que arma una pila con los pendientes, dejando arriba al primero que llegó
    public Stack<Paciente> construirPilaPendientes(Paciente[] registrados) {
        // pila auxiliar: aquí se apilan en orden de llegada (el último quedará arriba)
        Stack<Paciente> aux = new Stack<>();
        // recorre el arreglo en orden de llegada
        for (Paciente p : registrados) {
            // si todavía no ha sido atendido (pendiente o esperando en fila)...
            if (p.getEstado().equals("Pendiente") || p.getEstado().equals("En fila")) {
                // ...apílalo en la auxiliar
                aux.push(p);
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // pila final, vacía por ahora
        Stack<Paciente> pendientes = new Stack<>();
        // mientras la auxiliar tenga pacientes...
        while (!aux.isEmpty()) {
            // ...pásalos a la final: se invierten y el primero en llegar queda arriba
            pendientes.push(aux.pop());
        // aquí termina el ciclo
        }
        // devuelve la pila de pendientes
        return pendientes;
    // aquí termina el método
    }

    // método que muestra una pila de arriba hacia abajo sin perderla, usando una pila auxiliar
    public String mostrarPila(Stack<Paciente> pila, String titulo) {
        // si la pila está vacía...
        if (pila.isEmpty()) {
            // ...avísalo
            return titulo + ": no hay pacientes";
        // aquí termina el if
        }
        // pila auxiliar para no perder los datos
        Stack<Paciente> aux = new Stack<>();
        // empieza el texto con el título
        String texto = titulo + ":\n";
        // mientras la pila tenga pacientes...
        while (!pila.isEmpty()) {
            // ...saca el de arriba
            Paciente p = pila.pop();
            // pega sus datos al texto
            texto += datosPaciente(p);
            // y guárdalo en la auxiliar
            aux.push(p);
        // aquí termina el primer ciclo
        }
        // mientras la auxiliar tenga pacientes...
        while (!aux.isEmpty()) {
            // ...devuélvelos a la pila original: queda como estaba
            pila.push(aux.pop());
        // aquí termina el segundo ciclo
        }
        // devuelve el texto
        return texto;

    }

    // método que muestra una cola en orden de llegada, sin sacar a nadie
    public String mostrarCola(Queue<Paciente> cola, String titulo) {
        // si la cola está vacía...
        if (cola.isEmpty()) {
            // ...avísalo
            return titulo + ": no hay pacientes";

        }
        // empieza el texto con el título
        String texto = titulo + ":\n";
        // recorre la cola del primero al último
        for (Paciente p : cola) {
            // pega los datos de cada paciente
            texto += datosPaciente(p);
        // pasa al siguiente
        }
        // devuelve el texto
        return texto;

    }

    // método que muestra el historial de operaciones en el orden en que ocurrieron
    public String mostrarHistorialOperaciones(String[] historial) {
        // empieza el texto con un título
        String texto = "Historial de operaciones:\n";
        // recorre el arreglo casilla por casilla
        for (int i = 0; i < historial.length; i++) {
            // si encuentra una casilla vacía, ya no hay más operaciones...
            if (historial[i] == null) {
                // ...deja de recorrer
                break;

            }
            // pega la operación con su número
            texto += (i + 1) + ". " + historial[i] + "\n";
        // pasa a la siguiente casilla
        }
        // si la primera casilla estaba vacía, no hubo operaciones
        if (historial[0] == null) {
            // avísalo
            return "Aún no se han realizado operaciones";
        // aquí termina el if
        }
        // devuelve el texto
        return texto;

    }

}