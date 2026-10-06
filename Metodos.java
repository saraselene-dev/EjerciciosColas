import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// clase con toda la lógica de la oficina de trámites
public class Metodos {

    // método que lee un entero sin dejar que el programa se caiga; recibe el lector
    public int validarEntero(Scanner sc) {
        // mientras lo escrito no sea un número entero...
        while (!sc.hasNextInt()) {
            // ...avisa al usuario
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
        // mientras esté por fuera del rango...
        while (num < min || num > max) {
            // ...dile cuál es el rango permitido
            System.out.println("Ingrese un valor entre " + min + " y " + max);
            // y vuelve a leer
            num = validarEntero(sc);
        }
        // devuelve el número válido
        return num;
    }

    // método que lee un teléfono y solo lo acepta si tiene únicamente dígitos
    public String validarTelefono(Scanner sc) {
        // lee el teléfono como una palabra
        String telefono = sc.next();
        // mientras no esté formado solo por dígitos...
        while (!telefono.matches("[0-9]+")) {
            // ...avisa
            System.out.println("El teléfono solo puede tener números, ingréselo de nuevo");
            // y vuelve a leer
            telefono = sc.next();
        }
        // devuelve el teléfono válido
        return telefono;
    }

    // MENÚS DE OPCIONES

    // método que muestra los documentos que se pueden solicitar y devuelve el elegido
    public String menuSolicitud(Scanner sc) {
        // muestra la pregunta
        System.out.println("¿Qué documento solicita?");
        // opción 1
        System.out.println("1) Certificado");
        // opción 2
        System.out.println("2) Pasaporte");
        // opción 3
        System.out.println("3) Licencia");
        // opción 4
        System.out.println("4) Registro civil");
        // lee la opción validada
        int opt = validarRango(sc, 1, 4);
        // texto que se va a devolver
        String solicitud = "";
        // según la opción...
        switch (opt) {
            // 1: certificado
            case 1:
                solicitud = "Certificado";
                break;
            // 2: pasaporte
            case 2:
                solicitud = "Pasaporte";
                break;
            // 3: licencia
            case 3:
                solicitud = "Licencia";
                break;
            // 4: registro civil
            default:
                solicitud = "Registro civil";
                break;
        }
        // devuelve el documento elegido
        return solicitud;
    }

    // MÉTODOS DE APOYO


    // método que busca a un ciudadano por documento; devuelve el ciudadano o null si no existe
    public Ciudadano buscarCiudadano(Queue<Ciudadano> cola, String documento) {
        // crea la variable del encontrado, todavía sin nadie
        Ciudadano encontrado = null;
        // recorre la cola, un ciudadano a la vez
        for (Ciudadano c : cola) {
            // si su documento es el buscado...
            if (c.getDocumento().equals(documento)) {
                // ...guárdalo (si vino varias veces, queda la solicitud más reciente)
                encontrado = c;
            }
        }
        // devuelve el encontrado, o null si no hubo nadie
        return encontrado;
    }

    // método que responde si un documento ya tiene una solicitud activa (en espera o llamado)
    public boolean tieneSolicitudActiva(Queue<Ciudadano> cola, String documento) {
        // recorre la cola, un ciudadano a la vez
        for (Ciudadano c : cola) {
            // si es el mismo documento y su solicitud sigue viva...
            if (c.getDocumento().equals(documento)
                    && (c.getEstado().equals("En espera") || c.getEstado().equals("Llamado"))) {
                // ...responde que sí
                return true;
            }
        }
        // revisé a todos y ninguno tenía solicitud activa
        return false;
    }

    // método que calcula el turno del ciudadano nuevo
    public int asignarTurno(Queue<Ciudadano> cola) {
        // como nadie sale de la cola, el tamaño más uno nunca se repite
        return cola.size() + 1;
    }

    // método que anota lo ocurrido encima de la pila del historial; devuelve la pila
    public Stack<String> registrarHistorial(Stack<String> historial, String texto) {
        // pon la anotación arriba: es la más reciente
        historial.push(texto);
        // devuelve el historial actualizado
        return historial;
    }

    // método de apoyo que arma el texto con los datos de un ciudadano
    private String datosCiudadano(Ciudadano c) {
        // devuelve los datos en varias líneas, con un separador al final
        return "Turno: " + c.getTurno()
                + "\nDocumento: " + c.getDocumento()
                + "\nNombre: " + c.getNombre()
                + "\nTeléfono: " + c.getTelefono()
                + "\nSolicitud: " + c.getTipoSolicitud()
                + "\nEstado: " + c.getEstado()
                + "\n-----------------------------\n";
    }


    // OPERACIONES DE LA OFICINA


    // REGISTRAR: los ciudadanos llegan y entran a la fila; devuelve la cola
    public Queue<Ciudadano> registrarSolicitudes(Queue<Ciudadano> cola, Stack<String> historial, Metodos m,
            Scanner sc) {
        // interruptor del registro, encendido
        boolean continuar = true;
        // mientras quiera seguir registrando...
        while (continuar) {
            // crea una solicitud nueva en blanco
            Ciudadano c = new Ciudadano();
            // pide el documento
            System.out.println("\nIngrese el número de documento");
            // lee el documento (una sola palabra)
            String documento = sc.next();
            // mientras ese documento ya tenga una solicitud activa...
            while (m.tieneSolicitudActiva(cola, documento)) {
                // ...avisa
                System.out.println("Este documento ya tiene una solicitud activa, ingrese otro");
                // y pide otro
                documento = sc.next();
            }
            // guarda el documento
            c.setDocumento(documento);
            // limpia el Enter que quedó pendiente
            sc.nextLine();
            // pide el nombre
            System.out.println("Ingrese el nombre");
            // lee el nombre completo, con espacios
            c.setNombre(sc.nextLine());
            // pide el teléfono
            System.out.println("Ingrese el teléfono");
            // lee el teléfono validado
            c.setTelefono(m.validarTelefono(sc));
            // muestra los documentos y guarda el elegido
            c.setTipoSolicitud(m.menuSolicitud(sc));
            // asigna el turno
            c.setTurno(m.asignarTurno(cola));
            // empieza esperando
            c.setEstado("En espera");
            // ponlo al final de la fila
            cola.offer(c);
            // anota lo ocurrido en el historial
            m.registrarHistorial(historial, "Turno " + c.getTurno() + ": " + c.getNombre() + " solicitó "
                    + c.getTipoSolicitud());
            // confirma
            System.out.println("Solicitud registrada, su turno es el " + c.getTurno());
            // pregunta si sigue registrando
            System.out.println("¿Registrar otra solicitud? Sí: 1 / No: 0");
            // si responde 0...
            if (m.validarRango(sc, 0, 1) == 0) {
                // ...apaga el interruptor
                continuar = false;
            }
        }
        // devuelve la cola actualizada
        return cola;
    }

    // CONSULTAR: muestra a los ciudadanos que esperan, en orden de llegada
    public String consultarEnEspera(Queue<Ciudadano> cola) {
        // texto vacío
        String texto = "";
        // recorre la fila
        for (Ciudadano c : cola) {
            // si está esperando...
            if (c.getEstado().equals("En espera")) {
                // ...pega sus datos
                texto += datosCiudadano(c);
            }
        }
        // si nadie estaba esperando...
        if (texto.isEmpty()) {
            // ...avísalo
            return "No hay ciudadanos en espera";
        }
        // devuelve la lista
        return texto;
    }

    // SER LLAMADO: llama al primer ciudadano que está esperando
    public String llamarSiguiente(Queue<Ciudadano> cola, Stack<String> historial) {
        // recorre la fila desde el primero
        for (Ciudadano c : cola) {
            // si está esperando...
            if (c.getEstado().equals("En espera")) {
                // ...pásalo a llamado
                c.setEstado("Llamado");
                // anota lo ocurrido
                registrarHistorial(historial, "Turno " + c.getTurno() + ": " + c.getNombre() + " fue llamado");
                // anúncialo
                return "Se llama al turno " + c.getTurno() + ": " + c.getNombre();
            }
        }
        // nadie esperaba
        return "No hay ciudadanos en espera";
    }

    // MODIFICAR INFORMACIÓN: solo mientras espera; puede cambiar nombre, teléfono o documento solicitado
    public String modificarInformacion(Queue<Ciudadano> cola, Stack<String> historial, String documento,
            Scanner sc) {
        // busca al ciudadano
        Ciudadano c = buscarCiudadano(cola, documento);
        // si no existe...
        if (c == null) {
            // ...avísalo
            return "No existe un ciudadano con ese documento";
        }
        // si ya no está esperando...
        if (!c.getEstado().equals("En espera")) {
            // ...no se puede modificar
            return "Solo se puede modificar la información mientras espera. Estado actual: " + c.getEstado();
        }
        // pregunta qué quiere modificar
        System.out.println("¿Qué desea modificar?");
        // opción 1
        System.out.println("1) Nombre");
        // opción 2
        System.out.println("2) Teléfono");
        // opción 3
        System.out.println("3) Documento solicitado");
        // lee la opción validada
        int opt = validarRango(sc, 1, 3);
        // texto que describe el cambio, para el historial y el mensaje
        String cambio = "";
        // según la opción...
        switch (opt) {
            // 1: nombre
            case 1:
                // limpia el Enter pendiente antes de leer una línea completa
                sc.nextLine();
                // pide el nombre nuevo
                System.out.println("Ingrese el nombre correcto");
                // guarda el nombre anterior
                String nombreAnterior = c.getNombre();
                // lee y guarda el nombre nuevo
                c.setNombre(sc.nextLine());
                // describe el cambio
                cambio = "nombre de " + nombreAnterior + " a " + c.getNombre();
                break;
            // 2: teléfono
            case 2:
                // pide el teléfono nuevo
                System.out.println("Ingrese el teléfono correcto");
                // lee y guarda el teléfono validado
                c.setTelefono(validarTelefono(sc));
                // describe el cambio
                cambio = "teléfono a " + c.getTelefono();
                break;
            // 3: documento solicitado
            default:
                // guarda el documento anterior
                String solicitudAnterior = c.getTipoSolicitud();
                // muestra el menú y guarda el nuevo
                c.setTipoSolicitud(menuSolicitud(sc));
                // describe el cambio
                cambio = "solicitud de " + solicitudAnterior + " a " + c.getTipoSolicitud();
                break;
        }
        // anota lo ocurrido
        registrarHistorial(historial, "Turno " + c.getTurno() + ": se modificó " + cambio);
        // confirma; conserva su turno
        return "Se modificó " + cambio + ". Conserva su turno " + c.getTurno();
    }

    // CANCELAR LA SOLICITUD: se permite mientras espera o fue llamado
    public String cancelarSolicitud(Queue<Ciudadano> cola, Stack<String> historial, String documento) {
        // busca al ciudadano
        Ciudadano c = buscarCiudadano(cola, documento);
        // si no existe...
        if (c == null) {
            // ...avísalo
            return "No existe un ciudadano con ese documento";
        }
        // si está esperando o llamado...
        if (c.getEstado().equals("En espera") || c.getEstado().equals("Llamado")) {
            // ...cancela la solicitud
            c.setEstado("Cancelado");
            // anota lo ocurrido
            registrarHistorial(historial, "Turno " + c.getTurno() + ": " + c.getNombre() + " canceló su solicitud");
            // confirma
            return "Solicitud de " + c.getNombre() + " cancelada";
        }
        // en cualquier otro estado no se puede
        return "No se puede cancelar: estado actual " + c.getEstado();
    }

    // FINALIZAR EL TRÁMITE: solo quien fue llamado
    public String finalizarTramite(Queue<Ciudadano> cola, Stack<String> historial, String documento) {
        // busca al ciudadano
        Ciudadano c = buscarCiudadano(cola, documento);
        // si no existe...
        if (c == null) {
            // ...avísalo
            return "No existe un ciudadano con ese documento";
        }
        // si no fue llamado...
        if (!c.getEstado().equals("Llamado")) {
            // ...no se puede finalizar
            return "Solo se puede finalizar el trámite de un ciudadano llamado. Estado actual: " + c.getEstado();
        }
        // finaliza el trámite
        c.setEstado("Finalizado");
        // anota lo ocurrido
        registrarHistorial(historial, "Turno " + c.getTurno() + ": " + c.getNombre() + " finalizó su trámite de "
                + c.getTipoSolicitud());
        // confirma
        return "Trámite de " + c.getNombre() + " finalizado";
    }

    // CONSULTAR UN CIUDADANO: muestra sus datos y estado
    public String consultarCiudadano(Queue<Ciudadano> cola, String documento) {
        // busca al ciudadano
        Ciudadano c = buscarCiudadano(cola, documento);
        // si no existe...
        if (c == null) {
            // ...avísalo
            return "No existe un ciudadano con ese documento";
        }
        // devuelve sus datos
        return datosCiudadano(c);
    }

    // HISTORIAL Y RESUMEN
    
    // método que muestra el historial del más reciente al más antiguo, sin perderlo (pila auxiliar)
    public String mostrarHistorial(Stack<String> historial) {
        // si no ha pasado nada...
        if (historial.isEmpty()) {
            // ...avísalo
            return "Aún no hay movimientos en el historial";
        }
        // pila auxiliar para no perder las anotaciones
        Stack<String> aux = new Stack<>();
        // empieza el texto con un título
        String texto = "Historial (más reciente primero):\n";
        // mientras el historial tenga anotaciones...
        while (!historial.isEmpty()) {
            // ...saca la de arriba
            String anotacion = historial.pop();
            // pégala al texto
            texto += "- " + anotacion + "\n";
            // y guárdala en la auxiliar
            aux.push(anotacion);
        }
        // mientras la auxiliar tenga anotaciones...
        while (!aux.isEmpty()) {
            // ...devuélvelas al historial: queda como estaba
            historial.push(aux.pop());
        }
        // devuelve el texto
        return texto;
    }

    // método que cuenta cuántos ciudadanos tienen un estado
    public int contarPorEstado(Queue<Ciudadano> cola, String estado) {
        // empieza en cero
        int contador = 0;
        // recorre la fila
        for (Ciudadano c : cola) {
            // si tiene el estado buscado...
            if (c.getEstado().equals(estado)) {
                // ...suma uno
                contador++;
            }
        }
        // devuelve el conteo
        return contador;
    }

    // método que arma el resumen de la oficina
    public String resumen(Queue<Ciudadano> cola) {
        // devuelve el total y cuántos hay en cada estado
        return "Resumen de la oficina"
                + "\nTotal de solicitudes: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nLlamados: " + contarPorEstado(cola, "Llamado")
                + "\nFinalizados: " + contarPorEstado(cola, "Finalizado")
                + "\nCancelados: " + contarPorEstado(cola, "Cancelado");
    }
}