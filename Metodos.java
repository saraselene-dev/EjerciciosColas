import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private String[] cajas = { "Caja 1 - Preferencial", "Caja 2 - Rápida (máx. 10 productos)", "Caja 3 - General",
            "Caja 4 - General" };

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

    // MENÚS

    public String menuMotivo(Scanner sc) {
        System.out.println("Motivo de atención");
        System.out.println("1) Preferencial (adulto mayor, embarazo, discapacidad)");
        System.out.println("2) Compra rápida");
        System.out.println("3) Compra normal");
        int opt = validarRango(sc, 1, 3);
        String motivo = "";
        switch (opt) {
            case 1:
                motivo = "Preferencial";
                break;
            case 2:
                motivo = "Compra rápida";
                break;
            default:
                motivo = "Compra normal";
                break;
        }
        return motivo;
    }

    // muestra las cajas con la cantidad de personas en cada fila y devuelve el número elegido
    public int menuCaja(Queue<Cliente> cola, Scanner sc) {
        for (int i = 0; i < cajas.length; i++) {
            System.out.println((i + 1) + ") " + cajas[i] + " - en fila: " + contarEnFila(cola, i + 1));
        }
        return validarRango(sc, 1, cajas.length);
    }

    // REGLAS DE LAS CAJAS

    // la caja 1 solo atiende preferenciales, la 2 hasta 10 productos y las generales a todos
    public boolean puedeUsarCaja(Cliente c, int caja) {
        if (caja == 1) {
            return c.getMotivo().equals("Preferencial");
        }
        if (caja == 2) {
            return c.getCantidadProductos() <= 10;
        }
        return true;
    }

    // texto que explica por qué un cliente no puede usar una caja
    public String razonNoPuede(int caja) {
        if (caja == 1) {
            return "La caja 1 es solo para atención preferencial";
        }
        return "La caja 2 es solo para compras de máximo 10 productos";
    }

    public int contarEnFila(Queue<Cliente> cola, int caja) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getCaja() == caja && c.getEstado().equals("En fila")) {
                contador++;
            }
        }
        return contador;
    }

    // de las cajas que el cliente puede usar, elige la de fila más corta (en empate, la de número menor)
    public int cajaMasCorta(Queue<Cliente> cola, Cliente c) {
        int mejorCaja = 0;
        int menorFila = 0;
        for (int caja = 1; caja <= cajas.length; caja++) {
            if (puedeUsarCaja(c, caja)) {
                int enFila = contarEnFila(cola, caja);
                if (mejorCaja == 0 || enFila < menorFila) {
                    mejorCaja = caja;
                    menorFila = enFila;
                }
            }
        }
        return mejorCaja;
    }

    // MÉTODOS DE APOYO

    // devuelve al cliente que sigue en fila con ese turno, o null
    public Cliente buscarEnFila(Queue<Cliente> cola, int turno) {
        for (Cliente c : cola) {
            if (c.getTurno() == turno && c.getEstado().equals("En fila")) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorTurno(Queue<Cliente> cola, int turno) {
        for (Cliente c : cola) {
            if (c.getTurno() == turno) {
                return c;
            }
        }
        return null;
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

    private String datosCliente(Cliente c) {
        return "Turno " + c.getTurno() + " | " + c.getNombre() + " | " + c.getCantidadProductos() + " productos | "
                + c.getMotivo() + " | Caja " + c.getCaja() + " | " + c.getEstado() + "\n";
    }

    // OPERACIONES

    // cada cliente nuevo queda en la caja permitida con la fila más corta
    public Queue<Cliente> registrarClientes(Queue<Cliente> cola, Queue<String> movimientos, Metodos m,
            Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Cliente c = new Cliente();
            sc.nextLine();
            System.out.println("\nIngrese el nombre del cliente");
            c.setNombre(sc.nextLine());
            c.setMotivo(m.menuMotivo(sc));
            System.out.println("Cantidad de productos (1 a 200)");
            c.setCantidadProductos(m.validarRango(sc, 1, 200));
            // el turno nunca se repite porque nadie sale de la cola, solo cambia de estado
            c.setTurno(cola.size() + 1);
            c.setCaja(m.cajaMasCorta(cola, c));
            c.setEstado("En fila");
            cola.offer(c);
            movimientos.offer("Turno " + c.getTurno() + " (" + c.getNombre() + ") entró a la fila de la caja "
                    + c.getCaja());
            System.out.println(c.getNombre() + ", su turno es el " + c.getTurno() + ". Diríjase a " + cajas[c.getCaja() - 1]);
            System.out.println("¿Registrar otro cliente? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    // recorre cada caja y muestra su fila en orden de llegada
    public String consultarFilas(Queue<Cliente> cola) {
        String texto = "";
        for (int caja = 1; caja <= cajas.length; caja++) {
            texto += "\n" + cajas[caja - 1] + " (" + contarEnFila(cola, caja) + " en fila)\n";
            for (Cliente c : cola) {
                if (c.getCaja() == caja && c.getEstado().equals("En fila")) {
                    texto += "  " + datosCliente(c);
                }
            }
        }
        return texto;
    }

    // si la caja no tiene fila, toma al primer cliente de otra fila que pueda atender
    public String atenderEnCaja(Queue<Cliente> cola, Queue<String> movimientos, Scanner sc) {
        System.out.println("¿Qué caja va a atender?");
        int caja = menuCaja(cola, sc);
        for (Cliente c : cola) {
            if (c.getCaja() == caja && c.getEstado().equals("En fila")) {
                c.setEstado("Atendido");
                movimientos.offer("Turno " + c.getTurno() + " (" + c.getNombre() + ") atendido en la caja " + caja);
                return "Caja " + caja + " atiende a: " + datosCliente(c);
            }
        }
        for (Cliente c : cola) {
            if (c.getEstado().equals("En fila") && puedeUsarCaja(c, caja)) {
                int cajaAnterior = c.getCaja();
                c.setCaja(caja);
                c.setEstado("Atendido");
                movimientos.offer("Turno " + c.getTurno() + " (" + c.getNombre() + ") pasó de la caja "
                        + cajaAnterior + " a la caja " + caja + " por estar disponible, y fue atendido");
                return "La caja " + caja + " estaba libre y atiende a " + c.getNombre() + ", que esperaba en la caja "
                        + cajaAnterior;
            }
        }
        return "No hay clientes en fila que la caja " + caja + " pueda atender";
    }

    public String abandonarFila(Queue<Cliente> cola, Queue<String> movimientos, int turno) {
        Cliente c = buscarEnFila(cola, turno);
        if (c == null) {
            return "No hay un cliente en fila con ese turno";
        }
        c.setEstado("Abandonó");
        movimientos.offer("Turno " + c.getTurno() + " (" + c.getNombre() + ") abandonó la fila de la caja "
                + c.getCaja());
        return c.getNombre() + " abandonó la fila";
    }

    // al cambiar de caja, el cliente entra al final de la nueva fila
    public String cambiarCaja(Queue<Cliente> cola, Queue<String> movimientos, int turno, Scanner sc) {
        Cliente c = buscarEnFila(cola, turno);
        if (c == null) {
            return "No hay un cliente en fila con ese turno";
        }
        System.out.println(c.getNombre() + " está en la caja " + c.getCaja() + ". ¿A qué caja desea pasar?");
        int nueva = menuCaja(cola, sc);
        if (nueva == c.getCaja()) {
            return "Ya está en esa caja";
        }
        if (!puedeUsarCaja(c, nueva)) {
            return razonNoPuede(nueva) + ". " + c.getNombre() + " sigue en la caja " + c.getCaja();
        }
        int anterior = c.getCaja();
        quitarDeCola(cola, c);
        c.setCaja(nueva);
        cola.offer(c);
        movimientos.offer("Turno " + c.getTurno() + " (" + c.getNombre() + ") cambió de la caja " + anterior
                + " a la caja " + nueva);
        return c.getNombre() + " pasó de la caja " + anterior + " al final de la fila de la caja " + nueva;
    }

    public String consultarCliente(Queue<Cliente> cola, int turno) {
        Cliente c = buscarPorTurno(cola, turno);
        if (c == null) {
            return "No existe un cliente con ese turno";
        }
        return datosCliente(c);
    }

    // CONTROL DE MOVIMIENTOS Y RESUMEN

    public String mostrarMovimientos(Queue<String> movimientos) {
        if (movimientos.isEmpty()) {
            return "Aún no hay movimientos";
        }
        String texto = "Movimientos (en orden):\n";
        int n = 1;
        for (String mov : movimientos) {
            texto += n + ". " + mov + "\n";
            n++;
        }
        return texto;
    }

    public int contarPorEstado(Queue<Cliente> cola, String estado) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public int atendidosEnCaja(Queue<Cliente> cola, int caja) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getCaja() == caja && c.getEstado().equals("Atendido")) {
                contador++;
            }
        }
        return contador;
    }

    public String resumen(Queue<Cliente> cola) {
        String texto = "Resumen del supermercado"
                + "\nTotal de clientes: " + cola.size()
                + "\nEn fila: " + contarPorEstado(cola, "En fila")
                + "\nAtendidos: " + contarPorEstado(cola, "Atendido")
                + "\nAbandonaron: " + contarPorEstado(cola, "Abandonó")
                + "\nAtendidos por caja:";
        for (int caja = 1; caja <= cajas.length; caja++) {
            texto += "\n  " + cajas[caja - 1] + ": " + atendidosEnCaja(cola, caja);
        }
        return texto;
    }
}