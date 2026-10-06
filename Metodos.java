import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {

    /*
     * método público llamado registrarTurnos que recibe una cola de
     * clientes, un objeto Metodos y un lector de teclado
     * y devuelve una cola de clientes
     */
    public Queue<Cliente> registrarTurnos(Queue<Cliente> cola, Metodos m, Scanner sc) {
        // Creo un interruptor llamado cotinuar y lo dejo encedido
        boolean continuar = true;
        /*
         * (Esto aplica a todo lo que este dentro de {}.
         * Mientras el interruptor siga encendido, repite el registro.
         * El ciclo solo termina cuando, más abajo, el usuario responde que no quiere
         * registrar a nadie más
         * y se ejecuta continuar = false;, que apaga el interruptor.
         */
        while (continuar) {
            /*
             * creo un cliente nuevo, en blanco, y lo llamo c".
             * Es como sacar un formulario vacío para llenarlo;
             * las líneas siguientes (c.setIdentificacion(...), c.setNombre(...), etc.)
             * son las que escriben los datos en ese formulario.
             */
            Cliente c = new Cliente();
            System.out.println();
            System.out.println("Ingrese el número de documento");
            c.setIdentificacion(sc.next());
            sc.nextLine();
            System.out.println("Ingrese su nombre");
            c.setNombre(sc.nextLine());
            System.out.println("Ingrese la edad");
            c.setEdad(m.validarRango(sc, 1, 120));
            c.setTipoTramite(m.menuTramite(sc));
            c.setTurno(m.asignarTurno(cola));
            c.setEstado(1);
            System.out.println("¿Desea registrar otro usuario? Si: 1 / No: 0");
            int opt = m.validarRango(sc, 0, 1);
            // si respondió que no, apaga el interruptor para que el ciclo no se repita
            if (opt == 0) {
                continuar = false;
            } // pon al cliente que acabo de llenar al final de la fila
            cola.offer(c);
        }
        return cola;
    }

    // Método público que devuelve un número entero y recibe el lector del teclado
    public int validarEntero(Scanner sc) {
        // mientras lo que escribió el usuario NO sea un número entero
        while (!sc.hasNextInt()) {
            // avísale que debe escribir un número
            System.out.println("Por favor ingrese un dato numérico entero");
            // saca el dato malo del lector y lo bota
            sc.next();
            // vuelve a revisar lo siguiente que escriba
        }
        // ya hay un entero, lo lee y devuelve a quien llamó al método
        return sc.nextInt();
    }

    // método público que devuelve un entero; recibe el lector y los límites
    // permitidos
    public int validarRango(Scanner sc, int min, int max) {
        // lee un número que ya sea entero y guárdalo en num
        int num = validarEntero(sc);
        // mientras num esté por debajo del mínimo o por encima del máximo
        while (num < min || num > max) {
            // dile al usuario cuál es el rango permitido
            System.out.println("Ingrese un valor entre: " + min + " y " + max);
            // y vuelve a leer otro entero, reemplazando el anterior
            num = validarEntero(sc);
            // vuelve a revisar si el nuevo número quedó dentro del rango
        }
        // el número ya es válido: devuélvelo
        return num;
    }

    // método público que devuelve el número del trámite elegido; recibe el lector
    public int menuTramite(Scanner sc) {
        System.out.println();
        System.out.println("Seleccione el tipo de trámite");
        System.out.println("1) Consignación");
        System.out.println("2) Retiro");
        System.out.println("3) Pago de servicios");
        System.out.println("4) Asesoría");
        // lee la respuesta, asegúrate de que esté entre 1 y 4, y devuélvela
        return validarRango(sc, 1, 4);
    }

    // método público que devuelve el número de turno; recibe la cola de clientes
    public int asignarTurno(Queue<Cliente> cola) {
        // crea la variable turno y empiézala en 0i
        int turno = 0;
        // si la cola no tiene a nadie...
        if (cola.isEmpty()) {
            // ...el turno es el 1
            turno = 1;
            // si ya hay clientes
        } else {
            // el turno es la cantidad de clientes más uno
            turno = cola.size() + 1;
        }
        // devuelve el turno calculado
        return turno;
    }

    // método público que responde sí o no; recibe la cola y la cédula a buscar
    public boolean verificarTurnoActivo(Queue<Cliente> cola, String id) {
        // recorre la cola, tomando un cliente a la vez
        for (Cliente c : cola) {
            // si su cédula es la buscada Y su turno sigue "vivo" (en espera o llamado)
            if (c.getIdentificacion().equals(id) && (c.getEstado() == 1 || c.getEstado() == 2)) {
                // .responde que sí tiene turno activo, y sal del método de una vez
                return true;
            }
        }
        // revisé a todos y ninguno tenía turno vivo: responde que no
        return false;
    }

    private String datosCliente(Cliente c) {
        return "Turno: " + c.getTurno()
                + "\nDocumento: " + c.getIdentificacion()
                + "\nNombre: " + c.getNombre()
                + "\nEdad: " + c.getEdad()
                + "\nTrámite: " + nombreTramite(c.getTipoTramite())
                + "\n-----------------------------\n";
    }

    private String nombreTramite(int opt) {
        String mensaje = "";
        switch (opt) {
            case 1:
                mensaje = "Consignación";
                break;
            case 2:
                mensaje = "Retiro";
                break;
            case 3:
                mensaje = "Pago de servicios";
                break;
            default:
                mensaje = "Asesoría";
                break;
        }
        return mensaje;
    }

    // método público que devuelve un texto; recibe la cola de clientes
    public String consultarEnEspera(Queue<Cliente> cola) {
        // empieza con el mensaje vacío
        String mensaje = "";
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si este cliente sigue esperando
            if (c.getEstado() == 1) {
                // pega sus datos al final del mensaje
                mensaje = mensaje + datosCliente(c);
            }
        }
        // si después de revisar a todos el mensaje sigue vacío, nadie estaba esperando
        if (mensaje.isEmpty()) {
            // avísalo
            return "No hay clientes en espera";
        }
        // si hubo clientes, devuelve la lista armada
        return mensaje;
    }

    // método público que devuelve un texto; recibe la cola de clientes

    public String llamarSiguiente(Queue<Cliente> cola) {
        // recorre la fila desde el primero que llegó
        for (Cliente c : cola) {
            // si este cliente sigue esperand
            if (c.getEstado() == 1) {
                // pásalo a "llamado a ventanilla"
                c.setEstado(2);
                // y devuelve el aviso con sus datos, saliendo del método de una vez
                return "Siguiente usaurio \n" + datosCliente(c);
            }
        }
        // si revisé a todos y nadie esperaba, avísalo
        return "No hay usuarios en espera";
    }

    // método público que devuelve un mensaje; recibe la cola, la pila del historial
    // y la cédula del cliente
    public String marcarAtendido(Queue<Cliente> cola, Stack<Cliente> historial, String id) {
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si la cédula de este cliente es la que buscamos
            if (c.getIdentificacion().equals(id)) {
                // ...revisa en qué estado está
                switch (c.getEstado()) {
                    // si todavía está esperando
                    case 1:
                        // no se puede atender a alguien que no ha pasado a la ventanilla
                        return "El cliente aún no ha sido llamado a la ventanilla";
                    // si está en la ventanilla
                    case 2:
                        // márcalo como atendido
                        c.setEstado(3);
                        // ponlo encima del historial: es el más reciente
                        historial.push(c);
                        // confirma la atención
                        return "Cliente " + c.getNombre() + " marcado como atendido";
                    // si ya estaba atendido
                    case 3:
                        // avísalo para no atenderlo dos veces
                        return "El cliente ya había sido atendido";
                    // si había cancelado su turno
                    case 4:
                        // avísalo: un turno cancelado no se atiende
                        return "El turno de este cliente fue cancelado";
                    // cualquier otro estado (el 5: la jornada cerró sin atenderlo)
                    default:
                        // avísalo
                        return "La jornada se cerró sin que el cliente fuera atendido";
                }
            }
        }
        // revisé a todos y nadie tenía esa cédula
        return "No existe un cliente con esa identificación";
    }

    // método público que devuelve un mensaje; recibe la cola, la cédula y el nuevo
    // trámite elegido
    public String modificarTramite(Queue<Cliente> cola, String id, int nuevoTramite) {
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si la cédula de este cliente es la que buscamos
            if (c.getIdentificacion().equals(id)) {
                // y ya no está esperando ( llamado, atendido, cancelado o no atendido)
                if (c.getEstado() != 1) {
                    // no se le puede cambiar el trámite
                    return "Solo se puede modificar el trámite de un cliente en espera";
                }
                // si eligió el mismo trámite que ya tenía
                if (c.getTipoTramite() == nuevoTramite) {
                    // avísale que no hay nada que cambiar
                    return "El cliente ya tiene el trámite " + nombreTramite(nuevoTramite);
                }
                // guarda el nombre del trámite anterior antes de cambiarlo
                String anterior = nombreTramite(c.getTipoTramite());
                // cambia el trámite por el nuevo
                c.setTipoTramite(nuevoTramite);
                // confirma el cambio y recuerda que conserva su lugar en la fila
                return "Trámite modificado de " + anterior + " a " + nombreTramite(nuevoTramite)
                        + ". Conserva su turno " + c.getTurno();
            }
        }
        // revisé a todos y nadie tenía esa cédula
        return "No existe un cliente con esa identificación";
    }

    // método público que devuelve un mensaje; recibe la cola y la cédula del
    // cliente
    public String cancelarTurno(Queue<Cliente> cola, String id) {
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si la cédula de este cliente es la que buscamos
            if (c.getIdentificacion().equals(id)) {
                // revisa en qué estado está
                switch (c.getEstado()) {
                    // si está esperando
                    case 1:
                        // o si fue llamado pero no se presentó en la ventanilla
                    case 2:
                        // cancela su turno
                        c.setEstado(4);
                        // confirma la cancelación
                        return "Turno " + c.getTurno() + " de " + c.getNombre() + " cancelado";
                    // si ya fue atendido
                    case 3:
                        // no se puede cancelar algo que ya ocurrió
                        return "No se puede cancelar: el cliente ya fue atendido";
                    // si ya había cancelado antes
                    case 4:
                        // avísalo para no cancelarlo dos veces
                        return "Este turno ya estaba cancelado";
                    // cualquier otro estado (el 5: la jornada cerró sin atenderlo)
                    default:
                        // avísalo: la jornada ya terminó para este cliente
                        return "No se puede cancelar: la jornada se cerró sin que el cliente fuera atendido";
                    // aquí termina el switch
                }
                // aquí termina el if
            }
            // aquí termina el recorrido
        }
        // revisé a todos y nadie tenía esa cédula
        return "No existe un cliente con esa identificación";
        // aquí termina el método
    }

    // método público que devuelve un número; recibe la cola
    public int contarEstado(Queue<Cliente> cola, int estado) {
        // empieza a contar desde cero
        int contador = 0;
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si el estado de este cliente es el que me pidieron contar
            if (c.getEstado() == estado) {
                // ...suma uno
                contador++;
            }
        }
        // devuelve cuántos encontró
        return contador;
    }

    // método de apoyo que convierte el número del estado en texto
    private String nombreEstado(int estado) {
        // empieza con el texto vacío
        String mensaje = "";
        // según el número del estado...
        switch (estado) {
            // 1: sigue en la fila
            case 1:
                mensaje = "En espera";
                break;
            // 2: está en la ventanilla
            case 2:
                mensaje = "Llamado a ventanilla";
                break;
            // 3: ya terminó su trámite
            case 3:
                mensaje = "Atendido";
                break;
            // 4: decidió irse o no se presentó
            case 4:
                mensaje = "Cancelado";
                break;
            // 5: la jornada cerró y no alcanzó a ser atendido
            default:
                mensaje = "No atendido";
                break;
        }
        // devuelve el texto del estado
        return mensaje;
    }

    // método público que devuelve los datos y el estado de un cliente; recibe la
    // cola y la cédula
    public String buscarPorId(Queue<Cliente> cola, String id) {
        // recorre la cola, un cliente a la vez
        for (Cliente c : cola) {
            // si la cédula de este cliente es la que buscamos...
            if (c.getIdentificacion().equals(id)) {
                // devuelve sus datos y en qué va su turno
                return datosCliente(c) + "Estado: " + nombreEstado(c.getEstado());
            }
        }
        // revisé a todos y nadie tenía esa cédula
        return "No existe un cliente con esa identificación";
        // aquí termina el método
    }

    // método público que arma el balance de la jornada; recibe la cola
public String resumenJornada(Queue<Cliente> cola) {
    // arma el texto con el total y cuántos hay en cada estado
    return "Resumen de la jornada"
            + "\nTotal de turnos: " + cola.size()
            + "\nEn espera: " + contarEstado(cola, 1)
            + "\nEn ventanilla: " + contarEstado(cola, 2)
            + "\nAtendidos: " + contarEstado(cola, 3)
            + "\nCancelados: " + contarEstado(cola, 4)
            + "\nNo atendidos: " + contarEstado(cola, 5);
// aquí termina el método
}

// método público que muestra a los atendidos, del más reciente al más antiguo; recibe la pila
public String mostrarHistorial(Stack<Cliente> historial) {
    // si nadie ha sido atendido todavía...
    if (historial.isEmpty()) {
        // avísalo
        return "Aún no se ha atendido a ningún cliente";
    }
    // crea la segunda pila: una pila auxiliar vacía
    Stack<Cliente> pilaAux = new Stack<>();
    // empieza el texto con un título
    String texto = "Historial de atenciones (más reciente primero):\n";
    // mientras el historial tenga clientes...
    while (!historial.isEmpty()) {
        // ...saca el de arriba (el último atendido)
        Cliente c = historial.pop();
        // pega sus datos al texto
        texto += datosCliente(c);
        // y déjalo en la pila auxiliar para no perderlo
        pilaAux.push(c);
    // aquí termina el primer ciclo: el historial quedó vacío y todo está en la auxiliar
    }
    // mientras la pila auxiliar tenga clientes...
    while (!pilaAux.isEmpty()) {
        // ...sácalo de la auxiliar y devuélvelo al historial
        historial.push(pilaAux.pop());
    // aquí termina el segundo ciclo: el historial quedó como estaba
    }
    // devuelve el historial armado
    return texto;
// aquí termina el método
}

// método público que cierra el día y devuelve el resumen final; recibe la cola
public String cerrarJornada(Queue<Cliente> cola) {
    // recorre la cola, un cliente a la vez
    for (Cliente c : cola) {
        // si quedó esperando o en la ventanilla sin terminar...
        if (c.getEstado() == 1 || c.getEstado() == 2) {
            // ...márcalo como no atendido
            c.setEstado(5);
        }
    }
    // devuelve el balance final del día
    return "Jornada cerrada\n" + resumenJornada(cola);
// aquí termina el método
}
}


