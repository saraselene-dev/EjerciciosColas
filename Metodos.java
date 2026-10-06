// trae las herramientas: cola (LinkedList y Queue) y lector del teclado (Scanner)
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// clase con toda la lógica de las visitas a la propiedad
public class Metodos {

    // cuántas personas pueden visitar la propiedad en cada horario
    private int cupoPorHorario = 2;
    // los horarios de visita disponibles
    private String[] horarios = { "Mañana 9:00", "Mediodía 12:00", "Tarde 4:00" };

    // =====================================================================
    // VALIDACIONES
    // =====================================================================

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

    // =====================================================================
    // MENÚ DE HORARIOS
    // =====================================================================

    // método que muestra los horarios con sus cupos y devuelve el elegido
    public String menuHorario(Queue<Interesado> cola, Scanner sc) {
        // muestra la pregunta
        System.out.println("Seleccione el horario de visita");
        // recorre el arreglo de horarios
        for (int i = 0; i < horarios.length; i++) {
            // calcula cuántos cupos quedan en ese horario
            int libres = cupoPorHorario - contarConfirmados(cola, horarios[i]);
            // muestra la opción con su número y los cupos libres
            System.out.println((i + 1) + ") " + horarios[i] + " - cupos libres: " + libres);
        }
        // lee la opción validada
        int opt = validarRango(sc, 1, horarios.length);
        // devuelve el horario de esa posición (el arreglo empieza en 0)
        return horarios[opt - 1];
    }

    // =====================================================================
    // MÉTODOS DE APOYO
    // =====================================================================

    // método que cuenta cuántos confirmados tiene un horario
    public int contarConfirmados(Queue<Interesado> cola, String horario) {
        // empieza en cero
        int contador = 0;
        // recorre la cola
        for (Interesado i : cola) {
            // si es de ese horario y está confirmado...
            if (i.getHorario().equals(horario) && i.getEstado().equals("Confirmado")) {
                // ...suma uno
                contador++;
            }
        }
        // devuelve el conteo
        return contador;
    }

    // método que responde si un horario todavía tiene cupo
    public boolean hayCupo(Queue<Interesado> cola, String horario) {
        // hay cupo si los confirmados son menos que el cupo permitido
        return contarConfirmados(cola, horario) < cupoPorHorario;
    }

    // método que busca la solicitud activa de un documento; devuelve el interesado o null
    public Interesado buscarActivo(Queue<Interesado> cola, String documento) {
        // recorre la cola
        for (Interesado i : cola) {
            // si es ese documento y su solicitud sigue viva...
            if (i.getDocumento().equals(documento)
                    && (i.getEstado().equals("Confirmado") || i.getEstado().equals("En lista de espera"))) {
                // ...devuélvelo
                return i;
            }
        }
        // no tiene solicitud activa
        return null;
    }

    // método que calcula el siguiente turno: el mayor turno existente más uno
    public int siguienteTurno(Queue<Interesado> cola) {
        // empieza suponiendo que el mayor es 0
        int mayor = 0;
        // recorre la cola
        for (Interesado i : cola) {
            // si este turno es mayor que el que llevo...
            if (i.getTurno() > mayor) {
                // ...ese es el nuevo mayor
                mayor = i.getTurno();
            }
        }
        // el siguiente turno es el mayor más uno
        return mayor + 1;
    }

    // método que saca a un interesado específico de la cola usando una cola auxiliar; devuelve la cola
    public Queue<Interesado> quitarDeCola(Queue<Interesado> cola, Interesado sacar) {
        // crea la cola auxiliar vacía
        Queue<Interesado> aux = new LinkedList<>();
        // mientras la cola original tenga interesados...
        while (!cola.isEmpty()) {
            // ...saca el primero
            Interesado i = cola.poll();
            // si no es el que queremos quitar...
            if (i != sacar) {
                // ...guárdalo en la auxiliar
                aux.offer(i);
            }
        }
        // mientras la auxiliar tenga interesados...
        while (!aux.isEmpty()) {
            // ...devuélvelos a la original, en el mismo orden
            cola.offer(aux.poll());
        }
        // devuelve la cola sin ese interesado
        return cola;
    }

    // método que confirma al primero de la lista de espera de un horario, si lo hay
    public String promoverDeLista(Queue<Interesado> cola, String horario) {
        // recorre la cola en orden de solicitud
        for (Interesado i : cola) {
            // si es de ese horario y está en lista de espera...
            if (i.getHorario().equals(horario) && i.getEstado().equals("En lista de espera")) {
                // ...confírmalo
                i.setEstado("Confirmado");
                // devuelve el aviso
                return "\nSe liberó un cupo: " + i.getNombre() + " pasa de la lista de espera a confirmado en " + horario;
            }
        }
        // nadie esperaba ese horario
        return "";
    }

    // método de apoyo que arma el texto con los datos de un interesado
    private String datosInteresado(Interesado i) {
        // arma los datos básicos
        String texto = "Turno: " + i.getTurno() + " | " + i.getNombre() + " | Doc: " + i.getDocumento()
                + " | Tel: " + i.getTelefono() + " | " + i.getHorario() + " | " + i.getEstado();
        // si es un reemplazo...
        if (!i.getReemplaza().isEmpty()) {
            // ...agrégalo
            texto += " | Reemplaza a " + i.getReemplaza();
        }
        // devuelve el texto con salto de línea
        return texto + "\n";
    }

    // =====================================================================
    // OPERACIONES
    // =====================================================================

    // SOLICITAR VISITA: si hay cupo queda confirmado; si no, entra a la lista de espera
    public Queue<Interesado> registrarSolicitudes(Queue<Interesado> cola, Metodos m, Scanner sc) {
        // interruptor del registro, encendido
        boolean continuar = true;
        // mientras quiera seguir registrando...
        while (continuar) {
            // crea una solicitud nueva en blanco
            Interesado i = new Interesado();
            // pide el documento
            System.out.println("\nIngrese el número de documento");
            // lee el documento (una sola palabra)
            String documento = sc.next();
            // mientras ese documento ya tenga una solicitud activa...
            while (m.buscarActivo(cola, documento) != null) {
                // ...avisa
                System.out.println("Este documento ya tiene una solicitud activa, ingrese otro");
                // y pide otro
                documento = sc.next();
            }
            // guarda el documento
            i.setDocumento(documento);
            // limpia el Enter que quedó pendiente
            sc.nextLine();
            // pide el nombre
            System.out.println("Ingrese el nombre");
            // lee el nombre completo, con espacios
            i.setNombre(sc.nextLine());
            // pide el teléfono
            System.out.println("Ingrese el teléfono");
            // lee el teléfono validado
            i.setTelefono(m.validarTelefono(sc));
            // muestra los horarios y guarda el elegido
            i.setHorario(m.menuHorario(cola, sc));
            // asigna el turno
            i.setTurno(m.siguienteTurno(cola));
            // no reemplaza a nadie
            i.setReemplaza("");
            // si el horario tiene cupo...
            if (m.hayCupo(cola, i.getHorario())) {
                // ...queda confirmado
                i.setEstado("Confirmado");
            } else {
                // si no, entra a la lista de espera
                i.setEstado("En lista de espera");
            }
            // ponlo al final de la cola de solicitudes
            cola.offer(i);
            // confirma
            System.out.println(i.getNombre() + ": " + i.getEstado() + " para " + i.getHorario());
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

    // CANCELAR: si estaba confirmado, su cupo pasa al primero de la lista de espera
    public String cancelarSolicitud(Queue<Interesado> cola, String documento) {
        // busca la solicitud activa
        Interesado i = buscarActivo(cola, documento);
        // si no hay...
        if (i == null) {
            // ...avísalo
            return "No hay una solicitud activa con ese documento";
        }
        // recuerda si tenía cupo antes de cancelar
        boolean teniaCupo = i.getEstado().equals("Confirmado");
        // cancela la solicitud
        i.setEstado("Cancelado");
        // mensaje de confirmación
        String mensaje = "Solicitud de " + i.getNombre() + " cancelada";
        // si tenía cupo...
        if (teniaCupo) {
            // ...ese cupo se le da al primero de la lista de espera de su horario
            mensaje += promoverDeLista(cola, i.getHorario());
        }
        // devuelve el mensaje
        return mensaje;
    }

    // CAMBIAR HORARIO: depende de si estaba confirmado o en lista y de si el nuevo horario tiene cupo
    public String cambiarHorario(Queue<Interesado> cola, String documento, Scanner sc) {
        // busca la solicitud activa
        Interesado i = buscarActivo(cola, documento);
        // si no hay...
        if (i == null) {
            // ...avísalo
            return "No hay una solicitud activa con ese documento";
        }
        // muestra su horario actual
        System.out.println("Horario actual de " + i.getNombre() + ": " + i.getHorario());
        // muestra los horarios y guarda el nuevo
        String nuevo = menuHorario(cola, sc);
        // si eligió el mismo...
        if (nuevo.equals(i.getHorario())) {
            // ...no hay nada que cambiar
            return "Ya tiene ese horario";
        }
        // guarda el horario anterior
        String anterior = i.getHorario();
        // CASO 1: estaba confirmado
        if (i.getEstado().equals("Confirmado")) {
            // si el nuevo horario no tiene cupo...
            if (!hayCupo(cola, nuevo)) {
                // ...no se cambia, para que no pierda el cupo que ya tiene
                return "No hay cupo en " + nuevo + ". " + i.getNombre() + " conserva su cupo en " + anterior;
            }
            // cambia el horario: sigue confirmado
            i.setHorario(nuevo);
            // el cupo que dejó se le da al primero de la lista de espera de su horario anterior
            return i.getNombre() + " cambió de " + anterior + " a " + nuevo + " (confirmado)"
                    + promoverDeLista(cola, anterior);
        }
        // CASO 2: estaba en lista de espera y el nuevo horario tiene cupo
        if (hayCupo(cola, nuevo)) {
            // cambia el horario
            i.setHorario(nuevo);
            // y queda confirmado
            i.setEstado("Confirmado");
            // confirma
            return i.getNombre() + " cambió a " + nuevo + " y quedó confirmado";
        }
        // CASO 3: estaba en lista y el nuevo tampoco tiene cupo: pasa al final de esa lista
        quitarDeCola(cola, i);
        // cambia el horario
        i.setHorario(nuevo);
        // recibe un turno nuevo, el último
        i.setTurno(siguienteTurno(cola));
        // vuelve a entrar al final
        cola.offer(i);
        // avisa
        return i.getNombre() + " cambió a " + nuevo + " y quedó al final de su lista de espera";
    }

    // REEMPLAZAR: otra persona autorizada toma el lugar, conservando cupo, horario y turno
    public String reemplazarInteresado(Queue<Interesado> cola, String documento, Scanner sc) {
        // busca la solicitud activa
        Interesado i = buscarActivo(cola, documento);
        // si no hay...
        if (i == null) {
            // ...avísalo
            return "No hay una solicitud activa con ese documento";
        }
        // pide el documento de la persona autorizada
        System.out.println("Ingrese el documento de la persona autorizada");
        // lo lee
        String nuevoDocumento = sc.next();
        // mientras ese documento ya tenga una solicitud activa...
        while (buscarActivo(cola, nuevoDocumento) != null) {
            // ...avisa
            System.out.println("Ese documento ya tiene una solicitud activa, ingrese otro");
            // y pide otro
            nuevoDocumento = sc.next();
        }
        // limpia el Enter pendiente
        sc.nextLine();
        // pide el nombre
        System.out.println("Ingrese el nombre de la persona autorizada");
        // lo lee completo
        String nuevoNombre = sc.nextLine();
        // pide el teléfono
        System.out.println("Ingrese el teléfono de la persona autorizada");
        // lo lee validado
        String nuevoTelefono = validarTelefono(sc);
        // guarda a quién se reemplaza
        String anterior = i.getNombre();
        // deja la constancia del reemplazo
        i.setReemplaza(anterior);
        // cambia el documento
        i.setDocumento(nuevoDocumento);
        // cambia el nombre
        i.setNombre(nuevoNombre);
        // cambia el teléfono
        i.setTelefono(nuevoTelefono);
        // confirma: horario, turno y estado no cambian
        return nuevoNombre + " reemplaza a " + anterior + " en " + i.getHorario() + " (" + i.getEstado() + ")";
    }

    // =====================================================================
    // CONSULTAS
    // =====================================================================

    // método que muestra, por cada horario, los confirmados y la lista de espera en orden
    public String consultarAgenda(Queue<Interesado> cola) {
        // texto vacío
        String texto = "";
        // recorre cada horario del arreglo
        for (int h = 0; h < horarios.length; h++) {
            // título del horario con sus cupos
            texto += "\n=== " + horarios[h] + " (" + contarConfirmados(cola, horarios[h]) + "/" + cupoPorHorario
                    + " cupos) ===\n";
            // subtítulo
            texto += "Confirmados:\n";
            // recorre la cola
            for (Interesado i : cola) {
                // si es de este horario y está confirmado...
                if (i.getHorario().equals(horarios[h]) && i.getEstado().equals("Confirmado")) {
                    // ...pega sus datos
                    texto += "  " + datosInteresado(i);
                }
            }
            // subtítulo
            texto += "Lista de espera:\n";
            // recorre la cola en orden de solicitud
            for (Interesado i : cola) {
                // si es de este horario y está en lista...
                if (i.getHorario().equals(horarios[h]) && i.getEstado().equals("En lista de espera")) {
                    // ...pega sus datos
                    texto += "  " + datosInteresado(i);
                }
            }
        }
        // devuelve la agenda completa
        return texto;
    }

    // método que muestra la solicitud activa de un documento
    public String consultarInteresado(Queue<Interesado> cola, String documento) {
        // busca la solicitud activa
        Interesado i = buscarActivo(cola, documento);
        // si no hay...
        if (i == null) {
            // ...avísalo
            return "No hay una solicitud activa con ese documento";
        }
        // devuelve sus datos
        return datosInteresado(i);
    }

    // método que cuenta cuántas solicitudes tienen un estado
    public int contarPorEstado(Queue<Interesado> cola, String estado) {
        // empieza en cero
        int contador = 0;
        // recorre la cola
        for (Interesado i : cola) {
            // si tiene el estado buscado...
            if (i.getEstado().equals(estado)) {
                // ...suma uno
                contador++;
            }
        }
        // devuelve el conteo
        return contador;
    }

    // método que arma el resumen de la inmobiliaria
    public String resumen(Queue<Interesado> cola) {
        // devuelve el total y cuántos hay en cada estado
        return "Resumen de visitas"
                + "\nTotal de solicitudes: " + cola.size()
                + "\nConfirmados: " + contarPorEstado(cola, "Confirmado")
                + "\nEn lista de espera: " + contarPorEstado(cola, "En lista de espera")
                + "\nCancelados: " + contarPorEstado(cola, "Cancelado");
    }
}