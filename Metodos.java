import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private String[] tecnicos = { "Carlos", "Diana", "Jorge" };
    // orden de atención de las prioridades
    private String[] prioridades = { "Urgente", "Alta", "Normal" };

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

    // no deja guardar un texto vacío o solo con espacios
    public String validarTexto(Scanner sc) {
        String texto = sc.nextLine();
        while (texto.isBlank()) {
            System.out.println("Este dato no puede quedar vacío, ingréselo de nuevo");
            texto = sc.nextLine();
        }
        return texto;
    }

    // MENÚS

    public String menuProblema(Scanner sc) {
        System.out.println("Tipo de problema");
        System.out.println("1) Eléctrico");
        System.out.println("2) Plomería");
        System.out.println("3) Aire acondicionado");
        System.out.println("4) Electrodomésticos");
        int opt = validarRango(sc, 1, 4);
        String problema = "";
        switch (opt) {
            case 1:
                problema = "Eléctrico";
                break;
            case 2:
                problema = "Plomería";
                break;
            case 3:
                problema = "Aire acondicionado";
                break;
            default:
                problema = "Electrodomésticos";
                break;
        }
        return problema;
    }

    public String menuPrioridad(Scanner sc) {
        System.out.println("Prioridad");
        for (int i = 0; i < prioridades.length; i++) {
            System.out.println((i + 1) + ") " + prioridades[i]);
        }
        return prioridades[validarRango(sc, 1, prioridades.length) - 1];
    }

    // si incluirCualquiera es true, agrega la opción "Cualquier técnico disponible" al final
    public String menuTecnico(Queue<Solicitud> cola, Scanner sc, boolean incluirCualquiera) {
        for (int i = 0; i < tecnicos.length; i++) {
            String disponibilidad = "Disponible";
            if (tecnicoOcupado(cola, tecnicos[i])) {
                disponibilidad = "Ocupado";
            }
            System.out.println((i + 1) + ") " + tecnicos[i] + " - " + disponibilidad);
        }
        int max = tecnicos.length;
        if (incluirCualquiera) {
            System.out.println((tecnicos.length + 1) + ") Cualquier técnico disponible");
            max = tecnicos.length + 1;
        }
        int opt = validarRango(sc, 1, max);
        if (opt == tecnicos.length + 1) {
            return "Cualquiera";
        }
        return tecnicos[opt - 1];
    }

    // MÉTODOS DE APOYO

    public boolean tecnicoOcupado(Queue<Solicitud> cola, String tecnico) {
        for (Solicitud s : cola) {
            if (s.getTecnicoAsignado().equals(tecnico) && s.getEstado().equals("En proceso")) {
                return true;
            }
        }
        return false;
    }

    // busca la solicitud en espera que le toca a un técnico: la suya o la de "Cualquiera", por prioridad y llegada
    public Solicitud siguientePara(Queue<Solicitud> cola, String tecnico) {
        for (int p = 0; p < prioridades.length; p++) {
            for (Solicitud s : cola) {
                if (s.getEstado().equals("En espera") && s.getPrioridad().equals(prioridades[p])
                        && (s.getTecnicoSolicitado().equals(tecnico) || s.getTecnicoSolicitado().equals("Cualquiera"))) {
                    return s;
                }
            }
        }
        return null;
    }

    // apenas un técnico queda libre, empieza automáticamente el siguiente trabajo que le corresponde
    public String asignarSiguiente(Queue<Solicitud> cola, String tecnico) {
        Solicitud s = siguientePara(cola, tecnico);
        if (s == null) {
            return "\n" + tecnico + " queda disponible: no tiene trabajos pendientes";
        }
        s.setTecnicoAsignado(tecnico);
        s.setEstado("En proceso");
        return "\n" + tecnico + " inicia la solicitud " + s.getNumero() + " (" + s.getProblema() + " en "
                + s.getUbicacion() + ", prioridad " + s.getPrioridad() + ")";
    }

    public Solicitud buscarSolicitud(Queue<Solicitud> cola, int numero) {
        for (Solicitud s : cola) {
            if (s.getNumero() == numero) {
                return s;
            }
        }
        return null;
    }

    private String datosSolicitud(Solicitud s) {
        String asignado = s.getTecnicoAsignado();
        if (asignado.isEmpty()) {
            asignado = "Sin asignar";
        }
        return "Solicitud " + s.getNumero() + " | " + s.getCliente() + " | " + s.getUbicacion() + " | "
                + s.getProblema() + " | Pidió: " + s.getTecnicoSolicitado() + " | Asignado: " + asignado
                + " | Prioridad " + s.getPrioridad() + " | " + s.getEstado() + "\n";
    }

    // OPERACIONES

    // si el técnico pedido está libre, el trabajo empieza de inmediato; si no, la solicitud espera
    public Queue<Solicitud> registrarSolicitudes(Queue<Solicitud> cola, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Solicitud s = new Solicitud();
            sc.nextLine();
            System.out.println("\nNombre del cliente");
            s.setCliente(m.validarTexto(sc));
            System.out.println("Ubicación (dirección)");
            s.setUbicacion(m.validarTexto(sc));
            s.setProblema(m.menuProblema(sc));
            System.out.println("¿Qué técnico solicita?");
            s.setTecnicoSolicitado(m.menuTecnico(cola, sc, true));
            s.setPrioridad(m.menuPrioridad(sc));
            s.setNumero(cola.size() + 1);
            s.setTecnicoAsignado("");
            s.setEstado("En espera");
            cola.offer(s);
            System.out.println("Solicitud " + s.getNumero() + " registrada");
            String tecnicoLibre = m.tecnicoLibreParaSolicitud(cola, s);
            if (!tecnicoLibre.isEmpty()) {
                System.out.println(m.asignarSiguiente(cola, tecnicoLibre).trim());
            } else {
                System.out.println("No hay técnico disponible: queda en espera");
            }
            System.out.println("¿Registrar otra solicitud? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    // devuelve el técnico que podría tomar la solicitud ya mismo, o "" si ninguno está libre
    public String tecnicoLibreParaSolicitud(Queue<Solicitud> cola, Solicitud s) {
        if (!s.getTecnicoSolicitado().equals("Cualquiera")) {
            if (tecnicoOcupado(cola, s.getTecnicoSolicitado())) {
                return "";
            }
            return s.getTecnicoSolicitado();
        }
        for (int i = 0; i < tecnicos.length; i++) {
            if (!tecnicoOcupado(cola, tecnicos[i])) {
                return tecnicos[i];
            }
        }
        return "";
    }

    public String consultarEnEspera(Queue<Solicitud> cola) {
        String texto = "";
        for (int p = 0; p < prioridades.length; p++) {
            for (Solicitud s : cola) {
                if (s.getEstado().equals("En espera") && s.getPrioridad().equals(prioridades[p])) {
                    texto += datosSolicitud(s);
                }
            }
        }
        if (texto.isEmpty()) {
            return "No hay solicitudes en espera";
        }
        return "Solicitudes en espera (por prioridad y llegada):\n" + texto;
    }

    // muestra qué está haciendo cada técnico en este momento
    public String estadoTecnicos(Queue<Solicitud> cola) {
        String texto = "Técnicos:\n";
        for (int i = 0; i < tecnicos.length; i++) {
            String trabajo = "Disponible";
            for (Solicitud s : cola) {
                if (s.getTecnicoAsignado().equals(tecnicos[i]) && s.getEstado().equals("En proceso")) {
                    trabajo = "Trabajando en la solicitud " + s.getNumero() + " (" + s.getProblema() + ", "
                            + s.getUbicacion() + ")";
                }
            }
            texto += "  " + tecnicos[i] + ": " + trabajo + "\n";
        }
        return texto;
    }

    // al completar, el técnico queda libre y toma su siguiente trabajo
    public String completarTrabajo(Queue<Solicitud> cola, int numero) {
        Solicitud s = buscarSolicitud(cola, numero);
        if (s == null) {
            return "No existe esa solicitud";
        }
        if (!s.getEstado().equals("En proceso")) {
            return "Solo se puede completar un trabajo en proceso. Estado actual: " + s.getEstado();
        }
        s.setEstado("Completada");
        return "Solicitud " + s.getNumero() + " completada por " + s.getTecnicoAsignado()
                + asignarSiguiente(cola, s.getTecnicoAsignado());
    }

    // si se cancela un trabajo en proceso, el técnico también toma su siguiente trabajo
    public String cancelarSolicitud(Queue<Solicitud> cola, int numero) {
        Solicitud s = buscarSolicitud(cola, numero);
        if (s == null) {
            return "No existe esa solicitud";
        }
        if (s.getEstado().equals("En espera")) {
            s.setEstado("Cancelada");
            return "Solicitud " + s.getNumero() + " cancelada";
        }
        if (s.getEstado().equals("En proceso")) {
            s.setEstado("Cancelada");
            return "Solicitud " + s.getNumero() + " cancelada mientras " + s.getTecnicoAsignado() + " trabajaba"
                    + asignarSiguiente(cola, s.getTecnicoAsignado());
        }
        return "No se puede cancelar una solicitud " + s.getEstado().toLowerCase();
    }

    // solo tiene sentido cambiar la prioridad mientras la solicitud espera
    public String cambiarPrioridad(Queue<Solicitud> cola, int numero, Scanner sc) {
        Solicitud s = buscarSolicitud(cola, numero);
        if (s == null) {
            return "No existe esa solicitud";
        }
        if (!s.getEstado().equals("En espera")) {
            return "Solo se puede cambiar la prioridad de una solicitud en espera. Estado actual: " + s.getEstado();
        }
        String anterior = s.getPrioridad();
        s.setPrioridad(menuPrioridad(sc));
        if (anterior.equals(s.getPrioridad())) {
            return "La solicitud ya tenía prioridad " + anterior;
        }
        return "Prioridad de la solicitud " + s.getNumero() + " cambiada de " + anterior + " a " + s.getPrioridad();
    }

    public String consultarSolicitud(Queue<Solicitud> cola, int numero) {
        Solicitud s = buscarSolicitud(cola, numero);
        if (s == null) {
            return "No existe esa solicitud";
        }
        return datosSolicitud(s);
    }

    // RESUMEN

    public int contarPorEstado(Queue<Solicitud> cola, String estado) {
        int contador = 0;
        for (Solicitud s : cola) {
            if (s.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public int completadasPorTecnico(Queue<Solicitud> cola, String tecnico) {
        int contador = 0;
        for (Solicitud s : cola) {
            if (s.getTecnicoAsignado().equals(tecnico) && s.getEstado().equals("Completada")) {
                contador++;
            }
        }
        return contador;
    }

    public String resumen(Queue<Solicitud> cola) {
        String texto = "Resumen de mantenimiento"
                + "\nTotal de solicitudes: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nEn proceso: " + contarPorEstado(cola, "En proceso")
                + "\nCompletadas: " + contarPorEstado(cola, "Completada")
                + "\nCanceladas: " + contarPorEstado(cola, "Cancelada")
                + "\nCompletadas por técnico:";
        for (int i = 0; i < tecnicos.length; i++) {
            texto += "\n  " + tecnicos[i] + ": " + completadasPorTecnico(cola, tecnicos[i]);
        }
        return texto;
    }
}