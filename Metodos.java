import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private String[] modulos = { "Módulo 1", "Módulo 2" };

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

    // MENÚS

    public String menuMotivo(Scanner sc) {
        System.out.println("Motivo de la solicitud");
        System.out.println("1) Cambio (hasta 30 días después de la compra)");
        System.out.println("2) Garantía (hasta 365 días después de la compra)");
        System.out.println("3) Devolución (hasta 30 días después de la compra)");
        int opt = validarRango(sc, 1, 3);
        String motivo = "";
        switch (opt) {
            case 1:
                motivo = "Cambio";
                break;
            case 2:
                motivo = "Garantía";
                break;
            default:
                motivo = "Devolución";
                break;
        }
        return motivo;
    }

    public String menuModulo(Queue<Caso> cola, Scanner sc) {
        for (int i = 0; i < modulos.length; i++) {
            String disponibilidad = "Libre";
            if (moduloOcupado(cola, modulos[i])) {
                disponibilidad = "Ocupado";
            }
            System.out.println((i + 1) + ") " + modulos[i] + " - " + disponibilidad);
        }
        return modulos[validarRango(sc, 1, modulos.length) - 1];
    }

    // REGLAS DE LA TIENDA

    // plazo máximo en días según el motivo: garantía 365, cambio y devolución 30
    public int plazoMaximo(String motivo) {
        if (motivo.equals("Garantía")) {
            return 365;
        }
        return 30;
    }

    // MÉTODOS DE APOYO

    public boolean moduloOcupado(Queue<Caso> cola, String modulo) {
        for (Caso c : cola) {
            if (c.getModulo().equals(modulo) && c.getEstado().equals("En atención")) {
                return true;
            }
        }
        return false;
    }

    public Caso buscarActivo(Queue<Caso> cola, String documento) {
        for (Caso c : cola) {
            if (c.getDocumento().equals(documento)
                    && (c.getEstado().equals("En espera") || c.getEstado().equals("En atención"))) {
                return c;
            }
        }
        return null;
    }

    public Caso buscarUltimo(Queue<Caso> cola, String documento) {
        Caso encontrado = null;
        for (Caso c : cola) {
            if (c.getDocumento().equals(documento)) {
                encontrado = c;
            }
        }
        return encontrado;
    }

    private String datosCaso(Caso c) {
        String texto = "Turno: " + c.getTurno()
                + "\nCliente: " + c.getNombre() + " (" + c.getDocumento() + ")"
                + "\nProducto: " + c.getProducto()
                + "\nMotivo: " + c.getMotivo()
                + "\nFactura: " + c.getFactura() + " (" + c.getDiasDesdeCompra() + " días desde la compra)"
                + "\nEstado: " + c.getEstado();
        if (!c.getModulo().isEmpty()) {
            texto += "\nMódulo: " + c.getModulo();
        }
        if (!c.getResultado().isEmpty()) {
            texto += "\nResultado: " + c.getResultado();
        }
        if (!c.getInformacion().isEmpty()) {
            texto += "\nInformación presentada:" + c.getInformacion();
        }
        return texto + "\n-----------------------------\n";
    }

    // OPERACIONES

    public Queue<Caso> registrarCasos(Queue<Caso> cola, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Caso c = new Caso();
            System.out.println("\nIngrese el número de documento");
            String documento = sc.next();
            while (m.buscarActivo(cola, documento) != null) {
                System.out.println("Este documento ya tiene un caso activo, ingrese otro");
                documento = sc.next();
            }
            c.setDocumento(documento);
            sc.nextLine();
            System.out.println("Ingrese el nombre");
            c.setNombre(m.validarTexto(sc));
            System.out.println("Producto");
            c.setProducto(m.validarTexto(sc));
            System.out.println("Número de factura");
            c.setFactura(m.validarTexto(sc));
            System.out.println("¿Hace cuántos días hizo la compra? (0 a 3650)");
            c.setDiasDesdeCompra(m.validarRango(sc, 0, 3650));
            c.setMotivo(m.menuMotivo(sc));
            c.setTurno(cola.size() + 1);
            c.setInformacion("");
            c.setModulo("");
            c.setEstado("En espera");
            c.setResultado("");
            cola.offer(c);
            System.out.println(c.getNombre() + ", su turno es el " + c.getTurno());
            System.out.println("¿Registrar otro cliente? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    public String consultarEnEspera(Queue<Caso> cola) {
        String texto = "";
        for (Caso c : cola) {
            if (c.getEstado().equals("En espera")) {
                texto += datosCaso(c);
            }
        }
        if (texto.isEmpty()) {
            return "No hay clientes en espera";
        }
        return texto;
    }

    public String iniciarAtencion(Queue<Caso> cola, Scanner sc) {
        System.out.println("¿Qué módulo va a atender?");
        String modulo = menuModulo(cola, sc);
        if (moduloOcupado(cola, modulo)) {
            return modulo + " ya está atendiendo a un cliente";
        }
        for (Caso c : cola) {
            if (c.getEstado().equals("En espera")) {
                c.setModulo(modulo);
                c.setEstado("En atención");
                return modulo + " atiende el turno " + c.getTurno() + ": " + c.getNombre() + " (" + c.getMotivo()
                        + " de " + c.getProducto() + ")";
            }
        }
        return "No hay clientes en espera";
    }

    // el motivo solo cambia en la espera: en atención el módulo ya está evaluando ese motivo
    public String cambiarMotivo(Queue<Caso> cola, String documento, Scanner sc) {
        Caso c = buscarActivo(cola, documento);
        if (c == null) {
            return "No hay un caso activo con ese documento";
        }
        if (c.getEstado().equals("En atención")) {
            return "La atención ya inició: el motivo ya no se puede cambiar. Si el cliente lo necesita, debe desistir y registrar un caso nuevo";
        }
        String anterior = c.getMotivo();
        c.setMotivo(menuMotivo(sc));
        if (anterior.equals(c.getMotivo())) {
            return "El caso ya tenía el motivo " + anterior;
        }
        return "Motivo cambiado de " + anterior + " a " + c.getMotivo() + ". Conserva su turno " + c.getTurno();
    }

    // la información nueva se agrega en espera y en atención, pero queda marcado en qué momento se presentó
    public String presentarInformacion(Queue<Caso> cola, String documento, Scanner sc) {
        Caso c = buscarActivo(cola, documento);
        if (c == null) {
            return "No hay un caso activo con ese documento";
        }
        String momento = "en espera";
        if (c.getEstado().equals("En atención")) {
            momento = "durante la atención";
        }
        System.out.println("¿Qué presenta? 1) Una observación  2) Una factura diferente");
        int opt = validarRango(sc, 1, 2);
        sc.nextLine();
        if (opt == 1) {
            System.out.println("Escriba la observación");
            String nota = validarTexto(sc);
            // se pega al final de lo que ya había, cada novedad en su propia línea
            c.setInformacion(c.getInformacion() + "\n  - (" + momento + ") " + nota);
            return "Observación agregada al caso de " + c.getNombre();
        }
        // una factura nueva cambia la fecha de compra, y con eso puede cambiar si se aprueba
        System.out.println("Número de la nueva factura");
        String factura = validarTexto(sc);
        System.out.println("¿Hace cuántos días fue esa compra? (0 a 3650)");
        int dias = validarRango(sc, 0, 3650);
        c.setInformacion(c.getInformacion() + "\n  - (" + momento + ") Reemplazó la factura " + c.getFactura()
                + " por la " + factura);
        c.setFactura(factura);
        c.setDiasDesdeCompra(dias);
        return "Factura actualizada: ahora cuenta con " + dias + " días desde la compra";
    }

    // en espera se cancela; en atención ya no se cancela, el cliente desiste y queda registrado así
    public String cancelarCaso(Queue<Caso> cola, String documento) {
        Caso c = buscarActivo(cola, documento);
        if (c == null) {
            return "No hay un caso activo con ese documento";
        }
        if (c.getEstado().equals("En espera")) {
            c.setEstado("Cancelado");
            return "Caso de " + c.getNombre() + " cancelado";
        }
        c.setEstado("Desistió");
        return "La atención ya había iniciado: el caso de " + c.getNombre() + " queda como desistido y "
                + c.getModulo() + " queda libre";
    }

    // el módulo resuelve según el plazo que permite el motivo
    public String resolverCaso(Queue<Caso> cola, String documento) {
        Caso c = buscarActivo(cola, documento);
        if (c == null || !c.getEstado().equals("En atención")) {
            return "Ese documento no tiene un caso en atención";
        }
        int plazo = plazoMaximo(c.getMotivo());
        if (c.getDiasDesdeCompra() <= plazo) {
            c.setResultado("Aprobado: " + c.getMotivo() + " dentro del plazo de " + plazo + " días");
        } else {
            c.setResultado("Rechazado: la compra tiene " + c.getDiasDesdeCompra() + " días y el plazo para "
                    + c.getMotivo().toLowerCase() + " es de " + plazo);
        }
        c.setEstado("Resuelto");
        return "Caso de " + c.getNombre() + " resuelto. " + c.getResultado();
    }

    public String consultarCaso(Queue<Caso> cola, String documento) {
        Caso c = buscarUltimo(cola, documento);
        if (c == null) {
            return "No existe un caso con ese documento";
        }
        return datosCaso(c);
    }

    // RESUMEN

    public int contarPorEstado(Queue<Caso> cola, String estado) {
        int contador = 0;
        for (Caso c : cola) {
            if (c.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    // cuenta los resueltos cuyo resultado empieza con "Aprobado" o "Rechazado"
    public int contarResultado(Queue<Caso> cola, String resultado) {
        int contador = 0;
        for (Caso c : cola) {
            if (c.getResultado().startsWith(resultado)) {
                contador++;
            }
        }
        return contador;
    }

    public String resumen(Queue<Caso> cola) {
        return "Resumen posventa"
                + "\nTotal de casos: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nEn atención: " + contarPorEstado(cola, "En atención")
                + "\nResueltos: " + contarPorEstado(cola, "Resuelto")
                + " (aprobados: " + contarResultado(cola, "Aprobado") + ", rechazados: "
                + contarResultado(cola, "Rechazado") + ")"
                + "\nCancelados en espera: " + contarPorEstado(cola, "Cancelado")
                + "\nDesistieron en atención: " + contarPorEstado(cola, "Desistió");
    }
}