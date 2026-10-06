import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    // asesores que atienden durante el día
    private String[] asesores = { "Laura Gómez", "Andrés Ríos", "Camila Mejía" };

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

    // igual que validarEntero, pero para números con decimales (hasNextDouble)
    public double validarMonto(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Por favor ingrese un monto numérico");
            sc.next();
        }
        double monto = sc.nextDouble();
        // un crédito no puede ser de 0 ni negativo
        while (monto <= 0) {
            System.out.println("El monto debe ser mayor que 0");
            while (!sc.hasNextDouble()) {
                System.out.println("Por favor ingrese un monto numérico");
                sc.next();
            }
            monto = sc.nextDouble();
        }
        return monto;
    }

    public String validarTelefono(Scanner sc) {
        String telefono = sc.next();
        while (!telefono.matches("[0-9]+")) {
            System.out.println("El teléfono solo puede tener números, ingréselo de nuevo");
            telefono = sc.next();
        }
        return telefono;
    }

    // MENÚS

    public String menuTipoCredito(Scanner sc) {
        System.out.println("Tipo de crédito");
        System.out.println("1) Libre inversión");
        System.out.println("2) Vivienda");
        System.out.println("3) Vehículo");
        System.out.println("4) Educativo");
        int opt = validarRango(sc, 1, 4);
        String tipo = "";
        switch (opt) {
            case 1:
                tipo = "Libre inversión";
                break;
            case 2:
                tipo = "Vivienda";
                break;
            case 3:
                tipo = "Vehículo";
                break;
            default:
                tipo = "Educativo";
                break;
        }
        return tipo;
    }

    // muestra los asesores desde el arreglo, indicando si están libres u ocupados
    public String menuAsesor(Queue<Persona> cola, Scanner sc) {
        System.out.println("Seleccione el asesor");
        for (int i = 0; i < asesores.length; i++) {
            String disponibilidad = "Libre";
            if (asesorOcupado(cola, asesores[i])) {
                disponibilidad = "Ocupado";
            }
            System.out.println((i + 1) + ") " + asesores[i] + " - " + disponibilidad);
        }
        int opt = validarRango(sc, 1, asesores.length);
        return asesores[opt - 1];
    }

    // MÉTODOS DE APOYO

    // un asesor está ocupado si tiene una solicitud en asesoría
    public boolean asesorOcupado(Queue<Persona> cola, String asesor) {
        for (Persona s : cola) {
            if (s.getAsesor().equals(asesor) && s.getEstado().equals("En asesoría")) {
                return true;
            }
        }
        return false;
    }

    // devuelve la solicitud activa (en espera o en asesoría) de un documento, o
    // null
    public Persona buscarActiva(Queue<Persona> cola, String documento) {
        for (Persona s : cola) {
            if (s.getDocumento().equals(documento)
                    && (s.getEstado().equals("En espera") || s.getEstado().equals("En asesoría"))) {
                return s;
            }
        }
        return null;
    }

    // devuelve la última solicitud de un documento, sin importar su estado, o null
    public Persona buscarUltima(Queue<Persona> cola, String documento) {
        Persona encontrada = null;
        for (Persona s : cola) {
            if (s.getDocumento().equals(documento)) {
                encontrada = s;
            }
        }
        return encontrada;
    }

    public int asignarTurno(Queue<Persona> cola) {
        return cola.size() + 1;
    }

    // muestra el monto sin decimales ni notación científica (5.0E7 -> 50000000)
    private String formatoMonto(double monto) {
        return "$" + (long) monto;
    }

    private String datosSolicitud(Persona s) {
        String asesor = s.getAsesor();
        if (asesor.isEmpty()) {
            asesor = "Sin asignar";
        }
        return "Turno: " + s.getTurno()
                + "\nDocumento: " + s.getDocumento()
                + "\nNombre: " + s.getNombre()
                + "\nTeléfono: " + s.getTelefono()
                + "\nCrédito: " + s.getTipoCredito()
                + "\nMonto: " + formatoMonto(s.getMonto())
                + "\nPlazo: " + s.getPlazoMeses() + " meses"
                + "\nAsesor: " + asesor
                + "\nEstado: " + s.getEstado()
                + "\n-----------------------------\n";
    }

    // =====================================================================
    // OPERACIONES
    // =====================================================================

    public Queue<Persona> registrarSolicitudes(Queue<Persona> cola, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Persona s = new Persona();
            System.out.println("\nIngrese el número de documento");
            String documento = sc.next();
            while (m.buscarActiva(cola, documento) != null) {
                System.out.println("Este documento ya tiene una solicitud activa, ingrese otro");
                documento = sc.next();
            }
            s.setDocumento(documento);
            sc.nextLine();
            System.out.println("Ingrese el nombre");
            s.setNombre(sc.nextLine());
            System.out.println("Ingrese el teléfono");
            s.setTelefono(m.validarTelefono(sc));
            s.setTipoCredito(m.menuTipoCredito(sc));
            System.out.println("Ingrese el monto solicitado");
            s.setMonto(m.validarMonto(sc));
            System.out.println("Ingrese el plazo en meses (6 a 240)");
            s.setPlazoMeses(m.validarRango(sc, 6, 240));
            s.setAsesor("");
            s.setTurno(m.asignarTurno(cola));
            s.setEstado("En espera");
            cola.offer(s);
            System.out.println("Solicitud registrada, su turno es el " + s.getTurno());
            System.out.println("¿Registrar otra solicitud? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    public String consultarEnEspera(Queue<Persona> cola) {
        String texto = "";
        for (Persona s : cola) {
            if (s.getEstado().equals("En espera")) {
                texto += datosSolicitud(s);
            }
        }
        if (texto.isEmpty()) {
            return "No hay solicitudes en espera";
        }
        return texto;
    }

    // el asesor elegido toma la primera solicitud en espera, solo si está libre
    public String iniciarAsesoria(Queue<Persona> cola, Scanner sc) {
        String asesor = menuAsesor(cola, sc);
        if (asesorOcupado(cola, asesor)) {
            return asesor + " ya está atendiendo una solicitud; debe finalizarla primero";
        }
        for (Persona s : cola) {
            if (s.getEstado().equals("En espera")) {
                s.setAsesor(asesor);
                s.setEstado("En asesoría");
                return asesor + " inicia la asesoría del turno " + s.getTurno() + ": " + s.getNombre()
                        + " (" + s.getTipoCredito() + " por " + formatoMonto(s.getMonto()) + ")";
            }
        }
        return "No hay solicitudes en espera";
    }

    // las restricciones dependen del estado: antes de la asesoría todo; durante,
    // solo el teléfono
    public String modificarSolicitud(Queue<Persona> cola, String documento, Scanner sc) {
        Persona s = buscarActiva(cola, documento);
        if (s == null) {
            return "No hay una solicitud activa con ese documento (ya terminó, fue cancelada o no existe)";
        }
        if (s.getEstado().equals("En asesoría")) {
            System.out.println("La asesoría ya inició: el tipo, el monto y el plazo están bloqueados.");
            System.out.println("Solo puede actualizar el teléfono. ¿Desea hacerlo? Sí: 1 / No: 0");
            if (validarRango(sc, 0, 1) == 0) {
                return "No se realizaron cambios";
            }
            System.out.println("Ingrese el teléfono correcto");
            s.setTelefono(validarTelefono(sc));
            return "Teléfono actualizado a " + s.getTelefono();
        }
        System.out.println("¿Qué desea modificar?");
        System.out.println("1) Tipo de crédito");
        System.out.println("2) Monto");
        System.out.println("3) Plazo");
        System.out.println("4) Teléfono");
        int opt = validarRango(sc, 1, 4);
        String cambio = "";
        switch (opt) {
            case 1:
                String tipoAnterior = s.getTipoCredito();
                s.setTipoCredito(menuTipoCredito(sc));
                cambio = "tipo de crédito de " + tipoAnterior + " a " + s.getTipoCredito();
                break;
            case 2:
                System.out.println("Ingrese el nuevo monto");
                double montoAnterior = s.getMonto();
                s.setMonto(validarMonto(sc));
                cambio = "monto de " + formatoMonto(montoAnterior) + " a " + formatoMonto(s.getMonto());
                break;
            case 3:
                System.out.println("Ingrese el nuevo plazo en meses (6 a 240)");
                int plazoAnterior = s.getPlazoMeses();
                s.setPlazoMeses(validarRango(sc, 6, 240));
                cambio = "plazo de " + plazoAnterior + " a " + s.getPlazoMeses() + " meses";
                break;
            default:
                System.out.println("Ingrese el teléfono correcto");
                s.setTelefono(validarTelefono(sc));
                cambio = "teléfono a " + s.getTelefono();
                break;
        }
        return "Se modificó " + cambio + ". Conserva su turno " + s.getTurno();
    }

    // el asesor registra el resultado; al terminar, el asesor queda libre otra vez
    public String finalizarAsesoria(Queue<Persona> cola, String documento, Scanner sc) {
        Persona s = buscarActiva(cola, documento);
        if (s == null || !s.getEstado().equals("En asesoría")) {
            return "Ese documento no tiene una asesoría en curso";
        }
        System.out.println("Resultado de la asesoría: 1) Aprobada  2) Rechazada");
        if (validarRango(sc, 1, 2) == 1) {
            s.setEstado("Aprobada");
        } else {
            s.setEstado("Rechazada");
        }
        return "Solicitud de " + s.getNombre() + " " + s.getEstado().toLowerCase() + " por " + s.getAsesor();
    }

    // se puede cancelar en espera o en asesoría; si estaba en asesoría, el asesor
    // queda libre
    public String cancelarSolicitud(Queue<Persona> cola, String documento) {
        Persona s = buscarActiva(cola, documento);
        if (s == null) {
            return "No hay una solicitud activa con ese documento";
        }
        String mensaje = "Solicitud de " + s.getNombre() + " cancelada";
        if (s.getEstado().equals("En asesoría")) {
            mensaje += ". " + s.getAsesor() + " queda libre";
        }
        s.setEstado("Cancelada");
        return mensaje;
    }

    public String consultarSolicitud(Queue<Persona> cola, String documento) {
        Persona s = buscarUltima(cola, documento);
        if (s == null) {
            return "No existe una solicitud con ese documento";
        }
        return datosSolicitud(s);
    }

    // RESUMEN

    public int contarPorEstado(Queue<Persona> cola, String estado) {
        int contador = 0;
        for (Persona s : cola) {
            if (s.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    // suma los montos de las solicitudes aprobadas
    public double montoAprobado(Queue<Persona> cola) {
        double total = 0;
        for (Persona s : cola) {
            if (s.getEstado().equals("Aprobada")) {
                total += s.getMonto();
            }
        }
        return total;
    }

    public String resumen(Queue<Persona> cola) {
        return "Resumen del día"
                + "\nTotal de solicitudes: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nEn asesoría: " + contarPorEstado(cola, "En asesoría")
                + "\nAprobadas: " + contarPorEstado(cola, "Aprobada")
                + "\nRechazadas: " + contarPorEstado(cola, "Rechazada")
                + "\nCanceladas: " + contarPorEstado(cola, "Cancelada")
                + "\nMonto total aprobado: " + formatoMonto(montoAprobado(cola));
    }
}