import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {

    private String[] tipos = { "Información", "Reclamos", "Pagos", "Actualización de datos", "Solicitudes especiales" };
    private String[] prioridades = { "Alta", "Normal" };
    private String[] modulos = { "Módulo 1", "Módulo 2", "Módulo 3" };
    // todos los estados posibles, para recorrerlos en las estadísticas
    private String[] estados = { "En espera", "Llamado", "Atendido", "Cancelado", "No respondió", "Abandonó",
            "Anulado" };

    // VALIDACIONES

    public int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Por favor ingrese un dato numérico entero");
            sc.next();
        }
        return sc.nextInt();
    }

    public int validarRango(Scanner sc, int min, int max) {
        int num = validarEntero(sc);
        while (num < min || num > max) {
            System.out.println("Ingrese un valor entre " + min + " y " + max);
            num = validarEntero(sc);
        }
        return num;
    }

    public String validarTexto(Scanner sc) {
        String texto = sc.nextLine();
        while (texto.isBlank()) {
            System.out.println("Este dato no puede quedar vacío, ingréselo de nuevo");
            texto = sc.nextLine();
        }
        return texto;
    }

    public String validarDocumento(Scanner sc) {
        String documento = sc.next();
        while (!documento.matches("[0-9]+")) {
            System.out.println("El documento solo puede tener números, ingréselo de nuevo");
            documento = sc.next();
        }
        return documento;
    }

    // MENÚS

    public String menuTipo(Scanner sc) {
        System.out.println("Tipo de solicitud");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ") " + tipos[i]);
        }
        return tipos[validarRango(sc, 1, tipos.length) - 1];
    }

    public String menuPrioridad(Scanner sc) {
        System.out.println("Prioridad: 1) Alta (adulto mayor, embarazo, discapacidad)  2) Normal");
        return prioridades[validarRango(sc, 1, 2) - 1];
    }

    public String menuModulo(Queue<Cliente> cola, Scanner sc) {
        for (int i = 0; i < modulos.length; i++) {
            String disponibilidad = "Libre";
            if (moduloOcupado(cola, modulos[i])) {
                disponibilidad = "Ocupado";
            }
            System.out.println((i + 1) + ") " + modulos[i] + " - " + disponibilidad);
        }
        return modulos[validarRango(sc, 1, modulos.length) - 1];
    }

    // MÉTODOS DE APOYO

    public boolean moduloOcupado(Queue<Cliente> cola, String modulo) {
        for (Cliente c : cola) {
            if (c.getModulo().equals(modulo) && c.getEstado().equals("Llamado")) {
                return true;
            }
        }
        return false;
    }

    public boolean tieneTurnoActivo(Queue<Cliente> cola, String documento) {
        for (Cliente c : cola) {
            if (c.getDocumento().equals(documento)
                    && (c.getEstado().equals("En espera") || c.getEstado().equals("Llamado"))) {
                return true;
            }
        }
        return false;
    }

    public Cliente buscarTurno(Queue<Cliente> cola, int turno) {
        for (Cliente c : cola) {
            if (c.getTurno() == turno) {
                return c;
            }
        }
        return null;
    }

    // como nadie sale de la cola (solo cambia de estado o se reacomoda), el turno nunca se repite
    public int siguienteTurno(Queue<Cliente> cola) {
        return cola.size() + 1;
    }

    public Queue<Cliente> quitarDeCola(Queue<Cliente> cola, Cliente sacar) {
        Queue<Cliente> aux = new LinkedList<>();
        while (!cola.isEmpty()) {
            Cliente c = cola.poll();
            if (c != sacar) {
                aux.offer(c);
            }
        }
        while (!aux.isEmpty()) {
            cola.offer(aux.poll());
        }
        return cola;
    }

    public Stack<String> registrarHistorial(Stack<String> historial, String texto) {
        historial.push(texto);
        return historial;
    }

    private String datosCliente(Cliente c) {
        String texto = "Turno " + c.getTurno() + " | " + c.getNombre() + " (" + c.getDocumento() + ") | " + c.getTipo()
                + " | Prioridad " + c.getPrioridad() + " | " + c.getEstado();
        if (!c.getModulo().isEmpty()) {
            texto += " | " + c.getModulo();
        }
        if (!c.getReemplaza().isEmpty()) {
            texto += " | Reemplaza a " + c.getReemplaza();
        }
        if (c.getRegresoDe() != 0) {
            texto += " | Regresó (antes turno " + c.getRegresoDe() + ")";
        }
        if (c.isCorregido()) {
            texto += " | Registro corregido";
        }
        return texto + "\n";
    }

    // NUEVOS REGISTROS

    public Queue<Cliente> registrarClientes(Queue<Cliente> cola, Stack<String> historial, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nDocumento del cliente");
            String documento = m.validarDocumento(sc);
            while (m.tieneTurnoActivo(cola, documento)) {
                System.out.println("Este documento ya tiene un turno activo, ingrese otro");
                documento = m.validarDocumento(sc);
            }
            sc.nextLine();
            System.out.println("Nombre");
            String nombre = m.validarTexto(sc);
            String tipo = m.menuTipo(sc);
            String prioridad = m.menuPrioridad(sc);
            Cliente c = new Cliente(m.siguienteTurno(cola), documento, nombre, tipo, prioridad);
            cola.offer(c);
            m.registrarHistorial(historial, "Turno " + c.getTurno() + ": registro de " + nombre + " (" + tipo + ")");
            System.out.println(nombre + ", su turno es el " + c.getTurno());
            System.out.println("¿Registrar otro cliente? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    // CONSULTAS

    // orden real de atención: primero prioridad Alta, luego Normal; dentro de cada una, orden de la cola
    public String consultarEnEspera(Queue<Cliente> cola) {
        String texto = "";
        for (int p = 0; p < prioridades.length; p++) {
            for (Cliente c : cola) {
                if (c.getEstado().equals("En espera") && c.getPrioridad().equals(prioridades[p])) {
                    texto += datosCliente(c);
                }
            }
        }
        if (texto.isEmpty()) {
            return "No hay clientes en espera";
        }
        return "Clientes en espera (en orden de atención):\n" + texto;
    }

    public String consultarTurno(Queue<Cliente> cola, int turno) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null) {
            return "No existe ese turno";
        }
        return datosCliente(c);
    }

    // muestra el historial del más reciente al más antiguo, sin perderlo
    public String mostrarHistorial(Stack<String> historial) {
        if (historial.isEmpty()) {
            return "Aún no hay movimientos";
        }
        Stack<String> aux = new Stack<>();
        String texto = "Historial (más reciente primero):\n";
        while (!historial.isEmpty()) {
            String mov = historial.pop();
            texto += "- " + mov + "\n";
            aux.push(mov);
        }
        while (!aux.isEmpty()) {
            historial.push(aux.pop());
        }
        return texto;
    }

    // LLAMADO Y ATENCIÓN

    public String llamarSiguiente(Queue<Cliente> cola, Stack<String> historial, Scanner sc) {
        System.out.println("¿Qué módulo llama?");
        String modulo = menuModulo(cola, sc);
        if (moduloOcupado(cola, modulo)) {
            return modulo + " ya tiene un cliente llamado; debe atenderlo o registrar que no respondió";
        }
        for (int p = 0; p < prioridades.length; p++) {
            for (Cliente c : cola) {
                if (c.getEstado().equals("En espera") && c.getPrioridad().equals(prioridades[p])) {
                    c.setEstado("Llamado");
                    c.setModulo(modulo);
                    registrarHistorial(historial, "Turno " + c.getTurno() + ": llamado a " + modulo);
                    return "Turno " + c.getTurno() + ", " + c.getNombre() + ", diríjase a " + modulo;
                }
            }
        }
        return "No hay clientes en espera";
    }

    // primera vez sin responder: vuelve al final de la espera; segunda vez: pierde el turno
    public String noResponde(Queue<Cliente> cola, Stack<String> historial, int turno) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("Llamado")) {
            return "Ese turno no ha sido llamado";
        }
        c.setLlamadosSinRespuesta(c.getLlamadosSinRespuesta() + 1);
        String modulo = c.getModulo();
        c.setModulo("");
        if (c.getLlamadosSinRespuesta() >= 2) {
            c.setEstado("No respondió");
            registrarHistorial(historial, "Turno " + c.getTurno() + ": no respondió por segunda vez y perdió el turno");
            return c.getNombre() + " no respondió dos veces y perdió su turno. " + modulo + " queda libre";
        }
        quitarDeCola(cola, c);
        c.setEstado("En espera");
        cola.offer(c);
        registrarHistorial(historial, "Turno " + c.getTurno() + ": no respondió, pasa al final de la espera");
        return c.getNombre() + " no respondió: pasa al final de la espera. " + modulo + " queda libre";
    }

    public String atenderCliente(Queue<Cliente> cola, Stack<String> historial, int turno) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("Llamado")) {
            return "Solo se puede atender un turno llamado";
        }
        c.setEstado("Atendido");
        registrarHistorial(historial, "Turno " + c.getTurno() + ": atendido en " + c.getModulo());
        return c.getNombre() + " fue atendido en " + c.getModulo();
    }

    // CANCELACIONES Y ABANDONOS

    public String cancelarTurno(Queue<Cliente> cola, Stack<String> historial, int turno) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null) {
            return "No existe ese turno";
        }
        if (!c.getEstado().equals("En espera") && !c.getEstado().equals("Llamado")) {
            return "No se puede cancelar un turno en estado " + c.getEstado();
        }
        c.setEstado("Cancelado");
        registrarHistorial(historial, "Turno " + c.getTurno() + ": cancelado");
        return "Turno " + c.getTurno() + " cancelado";
    }

    // abandonar es irse sin avisar; se distingue de cancelar para las estadísticas
    public String abandonar(Queue<Cliente> cola, Stack<String> historial, int turno) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("En espera")) {
            return "Solo puede abandonar un cliente que está en espera";
        }
        c.setEstado("Abandonó");
        registrarHistorial(historial, "Turno " + c.getTurno() + ": abandonó la fila");
        return c.getNombre() + " abandonó la fila";
    }

    // quien abandonó o no respondió puede volver: recibe un turno nuevo al final, enlazado con el anterior
    public String regresar(Queue<Cliente> cola, Stack<String> historial, int turno) {
        Cliente anterior = buscarTurno(cola, turno);
        if (anterior == null) {
            return "No existe ese turno";
        }
        if (!anterior.getEstado().equals("Abandonó") && !anterior.getEstado().equals("No respondió")) {
            return "Solo puede regresar un cliente que abandonó o no respondió. Estado actual: " + anterior.getEstado();
        }
        if (tieneTurnoActivo(cola, anterior.getDocumento())) {
            return anterior.getNombre() + " ya tiene otro turno activo";
        }
        Cliente nuevo = new Cliente(siguienteTurno(cola), anterior.getDocumento(), anterior.getNombre(),
                anterior.getTipo(), anterior.getPrioridad());
        nuevo.setRegresoDe(anterior.getTurno());
        cola.offer(nuevo);
        registrarHistorial(historial, "Turno " + nuevo.getTurno() + ": " + nuevo.getNombre() + " regresó (antes turno "
                + anterior.getTurno() + ")");
        return nuevo.getNombre() + " regresó con el turno " + nuevo.getTurno() + ", al final de la espera";
    }

    // MODIFICACIONES, PRIORIDAD Y REEMPLAZOS

    public String modificarTipo(Queue<Cliente> cola, Stack<String> historial, int turno, Scanner sc) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("En espera")) {
            return "Solo se puede modificar la solicitud de un cliente en espera";
        }
        String anterior = c.getTipo();
        c.setTipo(menuTipo(sc));
        if (anterior.equals(c.getTipo())) {
            return "La solicitud ya era de tipo " + anterior;
        }
        registrarHistorial(historial, "Turno " + c.getTurno() + ": cambió de " + anterior + " a " + c.getTipo());
        return "Solicitud cambiada de " + anterior + " a " + c.getTipo() + ". Conserva su turno";
    }

    public String cambiarPrioridad(Queue<Cliente> cola, Stack<String> historial, int turno, Scanner sc) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("En espera")) {
            return "Solo se puede cambiar la prioridad de un cliente en espera";
        }
        String anterior = c.getPrioridad();
        c.setPrioridad(menuPrioridad(sc));
        if (anterior.equals(c.getPrioridad())) {
            return "Ya tenía prioridad " + anterior;
        }
        registrarHistorial(historial, "Turno " + c.getTurno() + ": prioridad de " + anterior + " a " + c.getPrioridad());
        return "Prioridad cambiada de " + anterior + " a " + c.getPrioridad();
    }

    // otra persona toma el turno con su lugar en la espera; queda constancia de a quién reemplazó
    public String reemplazar(Queue<Cliente> cola, Stack<String> historial, int turno, Scanner sc) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("En espera")) {
            return "Solo se puede reemplazar a un cliente en espera";
        }
        System.out.println("Documento de la persona que toma el turno");
        String documento = validarDocumento(sc);
        while (tieneTurnoActivo(cola, documento)) {
            System.out.println("Ese documento ya tiene un turno activo, ingrese otro");
            documento = validarDocumento(sc);
        }
        sc.nextLine();
        System.out.println("Nombre de la persona que toma el turno");
        String nombre = validarTexto(sc);
        String anterior = c.getNombre();
        c.setReemplaza(anterior);
        c.setDocumento(documento);
        c.setNombre(nombre);
        registrarHistorial(historial, "Turno " + c.getTurno() + ": " + nombre + " reemplaza a " + anterior);
        return nombre + " toma el turno " + c.getTurno() + " de " + anterior;
    }

    // ERRORES DE REGISTRO

    // un error se puede corregir (sin perder el lugar) o anular el turno si nunca debió existir
    public String errorRegistro(Queue<Cliente> cola, Stack<String> historial, int turno, Scanner sc) {
        Cliente c = buscarTurno(cola, turno);
        if (c == null || !c.getEstado().equals("En espera")) {
            return "Solo se pueden corregir o anular turnos en espera";
        }
        System.out.println("1) Corregir documento y nombre  2) Anular el turno (registro duplicado o por error)");
        if (validarRango(sc, 1, 2) == 2) {
            c.setEstado("Anulado");
            registrarHistorial(historial, "Turno " + c.getTurno() + ": anulado por error de registro");
            return "Turno " + c.getTurno() + " anulado";
        }
        System.out.println("Documento correcto");
        String documento = validarDocumento(sc);
        sc.nextLine();
        System.out.println("Nombre correcto");
        String nombre = validarTexto(sc);
        String datosAnteriores = c.getNombre() + " (" + c.getDocumento() + ")";
        c.setDocumento(documento);
        c.setNombre(nombre);
        c.setCorregido(true);
        registrarHistorial(historial, "Turno " + c.getTurno() + ": corregido de " + datosAnteriores + " a " + nombre
                + " (" + documento + ")");
        return "Registro corregido. Conserva su turno " + c.getTurno();
    }

    // ESTADÍSTICAS

    public int contarPorEstado(Queue<Cliente> cola, String estado) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public int contarPorTipo(Queue<Cliente> cola, String tipo) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getTipo().equals(tipo) && !c.getEstado().equals("Anulado")) {
                contador++;
            }
        }
        return contador;
    }

    public int atendidosPorModulo(Queue<Cliente> cola, String modulo) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getModulo().equals(modulo) && c.getEstado().equals("Atendido")) {
                contador++;
            }
        }
        return contador;
    }

    public String estadisticas(Queue<Cliente> cola) {
        int total = cola.size();
        int anulados = contarPorEstado(cola, "Anulado");
        int validos = total - anulados;
        int atendidos = contarPorEstado(cola, "Atendido");
        int reemplazos = 0;
        int regresos = 0;
        int corregidos = 0;
        int altas = 0;
        for (Cliente c : cola) {
            if (!c.getReemplaza().isEmpty()) {
                reemplazos++;
            }
            if (c.getRegresoDe() != 0) {
                regresos++;
            }
            if (c.isCorregido()) {
                corregidos++;
            }
            if (c.getPrioridad().equals("Alta") && !c.getEstado().equals("Anulado")) {
                altas++;
            }
        }

        String texto = "===== ESTADÍSTICAS DE LA JORNADA =====";
        texto += "\nTurnos entregados: " + total + " (válidos: " + validos + ")";
        texto += "\n\nPor estado:";
        for (int i = 0; i < estados.length; i++) {
            texto += "\n  " + estados[i] + ": " + contarPorEstado(cola, estados[i]);
        }
        // porcentaje sobre los turnos válidos; se multiplica por 100.0 para no perder los decimales
        if (validos > 0) {
            texto += "\n  Porcentaje atendido: " + Math.round(atendidos * 100.0 / validos) + "%";
        }

        texto += "\n\nPor tipo de solicitud:";
        String masSolicitado = "";
        int mayor = 0;
        for (int i = 0; i < tipos.length; i++) {
            int cantidad = contarPorTipo(cola, tipos[i]);
            texto += "\n  " + tipos[i] + ": " + cantidad;
            if (cantidad > mayor) {
                mayor = cantidad;
                masSolicitado = tipos[i];
            }
        }
        if (mayor > 0) {
            texto += "\n  Más solicitado: " + masSolicitado;
        }

        texto += "\n\nAtendidos por módulo:";
        for (int i = 0; i < modulos.length; i++) {
            texto += "\n  " + modulos[i] + ": " + atendidosPorModulo(cola, modulos[i]);
        }

        texto += "\n\nOtros datos:";
        texto += "\n  Prioridad alta: " + altas;
        texto += "\n  Reemplazos: " + reemplazos;
        texto += "\n  Regresos después de abandonar o no responder: " + regresos;
        texto += "\n  Errores de registro: " + (corregidos + anulados) + " (corregidos: " + corregidos
                + ", anulados: " + anulados + ")";
        return texto;
    }
}