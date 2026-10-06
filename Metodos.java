import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private String[] prioridades = { "Alta", "Normal" };

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

    public double validarPeso(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Por favor ingrese un peso numérico");
            sc.next();
        }
        double peso = sc.nextDouble();
        while (peso <= 0) {
            System.out.println("El peso debe ser mayor que 0");
            while (!sc.hasNextDouble()) {
                System.out.println("Por favor ingrese un peso numérico");
                sc.next();
            }
            peso = sc.nextDouble();
        }
        return peso;
    }

    public String validarTexto(Scanner sc) {
        String texto = sc.nextLine();
        while (texto.isBlank()) {
            System.out.println("Este dato no puede quedar vacío, ingréselo de nuevo");
            texto = sc.nextLine();
        }
        return texto;
    }

    // FLOTA

    // crea los vehículos de la empresa, del más pequeño al más grande
    public Vehiculo[] crearFlota() {
        Vehiculo[] flota = new Vehiculo[4];
        flota[0] = new Vehiculo("ABC12D", "Moto", 50);
        flota[1] = new Vehiculo("FGH345", "Camioneta", 1000);
        flota[2] = new Vehiculo("JKL678", "Camión", 5000);
        flota[3] = new Vehiculo("MNO901", "Tractomula", 30000);
        return flota;
    }

    // la capacidad más grande de la flota marca el peso máximo que se puede enviar en un solo viaje
    public double capacidadMaxima(Vehiculo[] flota) {
        double mayor = 0;
        for (Vehiculo v : flota) {
            if (v.getCapacidad() > mayor) {
                mayor = v.getCapacidad();
            }
        }
        return mayor;
    }

    public Vehiculo menuVehiculo(Vehiculo[] flota, Scanner sc) {
        for (int i = 0; i < flota.length; i++) {
            Vehiculo v = flota[i];
            System.out.println((i + 1) + ") " + v.getTipo() + " " + v.getPlaca() + " - capacidad " + kilos(v.getCapacidad())
                    + " - " + v.getEstado());
        }
        return flota[validarRango(sc, 1, flota.length) - 1];
    }

    public String estadoFlota(Vehiculo[] flota) {
        String texto = "Flota:\n";
        for (Vehiculo v : flota) {
            texto += "  " + v.getTipo() + " " + v.getPlaca() + " (" + kilos(v.getCapacidad()) + "): " + v.getEstado();
            if (v.getEstado().equals("En ruta")) {
                texto += " con la solicitud " + v.getSolicitudActual();
            }
            texto += "\n";
        }
        return texto;
    }

    // MENÚS

    public String menuMercancia(Scanner sc) {
        System.out.println("Tipo de mercancía");
        System.out.println("1) General");
        System.out.println("2) Frágil");
        System.out.println("3) Refrigerada");
        System.out.println("4) Materiales de construcción");
        int opt = validarRango(sc, 1, 4);
        String tipo = "";
        switch (opt) {
            case 1:
                tipo = "General";
                break;
            case 2:
                tipo = "Frágil";
                break;
            case 3:
                tipo = "Refrigerada";
                break;
            default:
                tipo = "Materiales de construcción";
                break;
        }
        return tipo;
    }

    public String menuPrioridad(Scanner sc) {
        System.out.println("Prioridad: 1) Alta  2) Normal");
        return prioridades[validarRango(sc, 1, 2) - 1];
    }

    // MÉTODOS DE APOYO

    // muestra los kilos sin decimales innecesarios
    public String kilos(double peso) {
        if (peso == (long) peso) {
            return (long) peso + " kg";
        }
        return peso + " kg";
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
        String texto = "Solicitud " + s.getNumero() + " | " + s.getCliente() + " | " + s.getOrigen() + " -> "
                + s.getDestino() + " | " + s.getTipoMercancia() + " | " + kilos(s.getPeso()) + " | Prioridad "
                + s.getPrioridad() + " | " + s.getEstado();
        if (!s.getVehiculo().isEmpty()) {
            texto += " | Vehículo " + s.getVehiculo();
        }
        if (!s.getParte().isEmpty()) {
            texto += " | " + s.getParte();
        }
        return texto + "\n";
    }

    // OPERACIONES

    // si el peso supera al vehículo más grande, la carga se divide en varias solicitudes que sí caben
    public Queue<Solicitud> registrarSolicitudes(Queue<Solicitud> cola, Vehiculo[] flota, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            sc.nextLine();
            System.out.println("\nNombre del cliente");
            String cliente = m.validarTexto(sc);
            System.out.println("Ciudad de origen");
            String origen = m.validarTexto(sc);
            System.out.println("Ciudad de destino");
            String destino = m.validarTexto(sc);
            String tipo = m.menuMercancia(sc);
            System.out.println("Peso en kilogramos");
            double peso = m.validarPeso(sc);
            String prioridad = m.menuPrioridad(sc);

            double maximo = m.capacidadMaxima(flota);
            // cuántos viajes hacen falta, redondeando hacia arriba (2.1 viajes son 3 viajes)
            int partes = (int) (peso / maximo);
            if (peso % maximo != 0) {
                partes++;
            }
            double restante = peso;
            for (int i = 1; i <= partes; i++) {
                // cada parte lleva el máximo posible, y la última lleva lo que sobra
                double pesoParte = maximo;
                if (restante < maximo) {
                    pesoParte = restante;
                }
                restante -= pesoParte;
                Solicitud s = new Solicitud(cola.size() + 1, cliente, origen, destino, tipo, pesoParte, prioridad);
                if (partes > 1) {
                    s.setParte("Parte " + i + " de " + partes);
                }
                cola.offer(s);
                System.out.println("Solicitud " + s.getNumero() + " registrada: " + m.kilos(pesoParte) + " pendiente");
            }
            if (partes > 1) {
                System.out.println("La carga supera la capacidad del vehículo más grande (" + m.kilos(maximo)
                        + "), por eso se dividió en " + partes + " envíos");
            }
            System.out.println("¿Registrar otra solicitud? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    public String consultarPendientes(Queue<Solicitud> cola) {
        String texto = "";
        for (int p = 0; p < prioridades.length; p++) {
            for (Solicitud s : cola) {
                if (s.getEstado().equals("Pendiente") && s.getPrioridad().equals(prioridades[p])) {
                    texto += datosSolicitud(s);
                }
            }
        }
        if (texto.isEmpty()) {
            return "No hay solicitudes pendientes";
        }
        return "Pendientes (por prioridad y llegada):\n" + texto;
    }

    // el vehículo toma la primera solicitud que le cabe; las que no le caben conservan su lugar para uno más grande
    public String despacharVehiculo(Queue<Solicitud> cola, Vehiculo[] flota, Scanner sc) {
        System.out.println("¿Qué vehículo va a salir?");
        Vehiculo v = menuVehiculo(flota, sc);
        if (v.getEstado().equals("En ruta")) {
            return v.getTipo() + " " + v.getPlaca() + " ya está en ruta con la solicitud " + v.getSolicitudActual();
        }
        String saltadas = "";
        for (int p = 0; p < prioridades.length; p++) {
            for (Solicitud s : cola) {
                if (s.getEstado().equals("Pendiente") && s.getPrioridad().equals(prioridades[p])) {
                    if (s.getPeso() <= v.getCapacidad()) {
                        s.setEstado("En ruta");
                        s.setVehiculo(v.getPlaca());
                        v.setEstado("En ruta");
                        v.setSolicitudActual(s.getNumero());
                        return saltadas + v.getTipo() + " " + v.getPlaca() + " sale con: " + datosSolicitud(s);
                    }
                    // anota la solicitud que no cupo y sigue buscando
                    saltadas += "La solicitud " + s.getNumero() + " (" + kilos(s.getPeso()) + ") no cabe en "
                            + v.getTipo() + " (" + kilos(v.getCapacidad()) + "): conserva su lugar para un vehículo más grande\n";
                }
            }
        }
        if (saltadas.isEmpty()) {
            return "No hay solicitudes pendientes";
        }
        return saltadas + v.getTipo() + " " + v.getPlaca() + " no tiene carga que le quepa: sigue disponible";
    }

    // al entregar, la solicitud termina y el vehículo vuelve a quedar disponible
    public String registrarEntrega(Queue<Solicitud> cola, Vehiculo[] flota, Scanner sc) {
        System.out.println("¿Qué vehículo entregó?");
        Vehiculo v = menuVehiculo(flota, sc);
        if (!v.getEstado().equals("En ruta")) {
            return v.getTipo() + " " + v.getPlaca() + " no está en ruta";
        }
        Solicitud s = buscarSolicitud(cola, v.getSolicitudActual());
        s.setEstado("Entregada");
        v.setEstado("Disponible");
        v.setSolicitudActual(0);
        return "Solicitud " + s.getNumero() + " entregada en " + s.getDestino() + ". " + v.getTipo() + " "
                + v.getPlaca() + " queda disponible";
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

    public double kilosPorEstado(Queue<Solicitud> cola, String estado) {
        double total = 0;
        for (Solicitud s : cola) {
            if (s.getEstado().equals(estado)) {
                total += s.getPeso();
            }
        }
        return total;
    }

    public String resumen(Queue<Solicitud> cola) {
        return "Resumen de transporte"
                + "\nTotal de envíos: " + cola.size()
                + "\nPendientes: " + contarPorEstado(cola, "Pendiente") + " (" + kilos(kilosPorEstado(cola, "Pendiente")) + ")"
                + "\nEn ruta: " + contarPorEstado(cola, "En ruta") + " (" + kilos(kilosPorEstado(cola, "En ruta")) + ")"
                + "\nEntregados: " + contarPorEstado(cola, "Entregada") + " (" + kilos(kilosPorEstado(cola, "Entregada")) + ")";
    }
}