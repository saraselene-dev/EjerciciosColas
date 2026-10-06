import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private String[] operadores = { "Sofía", "Mateo", "Valentina" };
    // orden en que se atienden las prioridades: primero Alta, luego Media, luego Baja
    private String[] prioridades = { "Alta", "Media", "Baja" };

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

    // acepta solo horas reales en formato HH:MM, de 00:00 a 23:59
    public String validarHora(Scanner sc) {
        String hora = sc.next();
        while (!hora.matches("([01][0-9]|2[0-3]):[0-5][0-9]")) {
            System.out.println("Hora inválida. Use el formato HH:MM, por ejemplo 08:30 o 14:05");
            hora = sc.next();
        }
        return hora;
    }

    // MENÚS

    public String menuMotivo(Scanner sc) {
        System.out.println("Motivo de la llamada");
        System.out.println("1) Soporte técnico");
        System.out.println("2) Facturación");
        System.out.println("3) Ventas");
        System.out.println("4) Reclamo");
        int opt = validarRango(sc, 1, 4);
        String motivo = "";
        switch (opt) {
            case 1:
                motivo = "Soporte técnico";
                break;
            case 2:
                motivo = "Facturación";
                break;
            case 3:
                motivo = "Ventas";
                break;
            default:
                motivo = "Reclamo";
                break;
        }
        return motivo;
    }

    public String menuPrioridad(Scanner sc) {
        System.out.println("Prioridad");
        for (int i = 0; i < prioridades.length; i++) {
            System.out.println((i + 1) + ") " + prioridades[i]);
        }
        return prioridades[validarRango(sc, 1, prioridades.length) - 1];
    }

    public String menuOperador(Queue<Llamada> cola, Scanner sc) {
        for (int i = 0; i < operadores.length; i++) {
            String disponibilidad = "Libre";
            if (operadorOcupado(cola, operadores[i])) {
                disponibilidad = "Ocupado";
            }
            System.out.println((i + 1) + ") " + operadores[i] + " - " + disponibilidad);
        }
        return operadores[validarRango(sc, 1, operadores.length) - 1];
    }

    // MÉTODOS DE APOYO

    // un operador está ocupado si tiene una llamada en atención
    public boolean operadorOcupado(Queue<Llamada> cola, String operador) {
        for (Llamada l : cola) {
            if (l.getOperador().equals(operador) && l.getEstado().equals("En atención")) {
                return true;
            }
        }
        return false;
    }

    public Llamada buscarLlamada(Queue<Llamada> cola, int numero) {
        for (Llamada l : cola) {
            if (l.getNumero() == numero) {
                return l;
            }
        }
        return null;
    }

    private String datosLlamada(Llamada l) {
        String operador = l.getOperador();
        if (operador.isEmpty()) {
            operador = "Sin asignar";
        }
        return "Llamada " + l.getNumero() + " | " + l.getHora() + " | " + l.getCliente() + " | " + l.getMotivo()
                + " | Prioridad " + l.getPrioridad() + " | " + l.getEstado() + " | Operador: " + operador
                + " | Transferencias: " + l.getTransferencias() + "\n";
    }

    // OPERACIONES

    public Queue<Llamada> registrarLlamadas(Queue<Llamada> cola, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Llamada l = new Llamada();
            sc.nextLine();
            System.out.println("\nNombre del cliente");
            l.setCliente(sc.nextLine());
            l.setMotivo(m.menuMotivo(sc));
            System.out.println("Hora de la llamada (HH:MM)");
            l.setHora(m.validarHora(sc));
            l.setPrioridad(m.menuPrioridad(sc));
            l.setNumero(cola.size() + 1);
            l.setEstado("En espera");
            l.setOperador("");
            l.setTransferencias(0);
            cola.offer(l);
            System.out.println("Llamada " + l.getNumero() + " en espera con prioridad " + l.getPrioridad());
            System.out.println("¿Registrar otra llamada? Sí: 1 / No: 0");
            if (m.validarRango(sc, 0, 1) == 0) {
                continuar = false;
            }
        }
        return cola;
    }

    // muestra la espera en el orden real de atención: por prioridad y, dentro de cada una, por llegada
    public String consultarEnEspera(Queue<Llamada> cola) {
        String texto = "";
        for (int p = 0; p < prioridades.length; p++) {
            for (Llamada l : cola) {
                if (l.getEstado().equals("En espera") && l.getPrioridad().equals(prioridades[p])) {
                    texto += datosLlamada(l);
                }
            }
        }
        if (texto.isEmpty()) {
            return "No hay llamadas en espera";
        }
        return "Llamadas en espera (en orden de atención):\n" + texto;
    }

    // el operador libre toma la llamada en espera de mayor prioridad; en empate, la que entró primero
    public String atenderSiguiente(Queue<Llamada> cola, Scanner sc) {
        System.out.println("¿Qué operador va a atender?");
        String operador = menuOperador(cola, sc);
        if (operadorOcupado(cola, operador)) {
            return operador + " ya tiene una llamada en atención; debe finalizarla o transferirla primero";
        }
        for (int p = 0; p < prioridades.length; p++) {
            for (Llamada l : cola) {
                if (l.getEstado().equals("En espera") && l.getPrioridad().equals(prioridades[p])) {
                    l.setOperador(operador);
                    l.setEstado("En atención");
                    return operador + " atiende: " + datosLlamada(l);
                }
            }
        }
        return "No hay llamadas en espera";
    }

    // la llamada pasa a otro operador solo si está libre; si no, se queda con el actual
    public String transferirLlamada(Queue<Llamada> cola, int numero, Scanner sc) {
        Llamada l = buscarLlamada(cola, numero);
        if (l == null) {
            return "No existe esa llamada";
        }
        if (!l.getEstado().equals("En atención")) {
            return "Solo se puede transferir una llamada en atención. Estado actual: " + l.getEstado();
        }
        System.out.println("La atiende " + l.getOperador() + ". ¿A qué operador la transfiere?");
        String destino = menuOperador(cola, sc);
        if (destino.equals(l.getOperador())) {
            return "La llamada ya está con " + destino;
        }
        if (operadorOcupado(cola, destino)) {
            return destino + " está ocupado. La llamada sigue con " + l.getOperador();
        }
        String origen = l.getOperador();
        l.setOperador(destino);
        l.setTransferencias(l.getTransferencias() + 1);
        return "Llamada " + l.getNumero() + " transferida de " + origen + " a " + destino + ". " + origen
                + " queda libre";
    }

    // en espera se puede cambiar motivo y prioridad; en atención solo el motivo
    public String actualizarLlamada(Queue<Llamada> cola, int numero, Scanner sc) {
        Llamada l = buscarLlamada(cola, numero);
        if (l == null) {
            return "No existe esa llamada";
        }
        if (l.getEstado().equals("En atención")) {
            System.out.println("La llamada está en atención: solo se puede actualizar el motivo");
            String anterior = l.getMotivo();
            l.setMotivo(menuMotivo(sc));
            return "Motivo actualizado de " + anterior + " a " + l.getMotivo();
        }
        if (!l.getEstado().equals("En espera")) {
            return "No se puede actualizar una llamada " + l.getEstado().toLowerCase();
        }
        System.out.println("¿Qué desea actualizar? 1) Motivo  2) Prioridad");
        if (validarRango(sc, 1, 2) == 1) {
            String anterior = l.getMotivo();
            l.setMotivo(menuMotivo(sc));
            return "Motivo actualizado de " + anterior + " a " + l.getMotivo();
        }
        String anterior = l.getPrioridad();
        l.setPrioridad(menuPrioridad(sc));
        return "Prioridad actualizada de " + anterior + " a " + l.getPrioridad()
                + ". Su lugar en la espera cambia según la nueva prioridad";
    }

    public String cancelarLlamada(Queue<Llamada> cola, int numero) {
        Llamada l = buscarLlamada(cola, numero);
        if (l == null) {
            return "No existe esa llamada";
        }
        if (!l.getEstado().equals("En espera") && !l.getEstado().equals("En atención")) {
            return "No se puede cancelar una llamada " + l.getEstado().toLowerCase();
        }
        String mensaje = "Llamada " + l.getNumero() + " cancelada";
        if (l.getEstado().equals("En atención")) {
            mensaje += ". " + l.getOperador() + " queda libre";
        }
        l.setEstado("Cancelada");
        return mensaje;
    }

    public String finalizarLlamada(Queue<Llamada> cola, int numero) {
        Llamada l = buscarLlamada(cola, numero);
        if (l == null) {
            return "No existe esa llamada";
        }
        if (!l.getEstado().equals("En atención")) {
            return "Solo se puede finalizar una llamada en atención. Estado actual: " + l.getEstado();
        }
        l.setEstado("Finalizada");
        return "Llamada " + l.getNumero() + " finalizada por " + l.getOperador();
    }

    public String consultarLlamada(Queue<Llamada> cola, int numero) {
        Llamada l = buscarLlamada(cola, numero);
        if (l == null) {
            return "No existe esa llamada";
        }
        return datosLlamada(l);
    }

    // RESUMEN

    public int contarPorEstado(Queue<Llamada> cola, String estado) {
        int contador = 0;
        for (Llamada l : cola) {
            if (l.getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public int finalizadasPorOperador(Queue<Llamada> cola, String operador) {
        int contador = 0;
        for (Llamada l : cola) {
            if (l.getOperador().equals(operador) && l.getEstado().equals("Finalizada")) {
                contador++;
            }
        }
        return contador;
    }

    public int totalTransferencias(Queue<Llamada> cola) {
        int total = 0;
        for (Llamada l : cola) {
            total += l.getTransferencias();
        }
        return total;
    }

    public String resumen(Queue<Llamada> cola) {
        String texto = "Resumen de la jornada"
                + "\nTotal de llamadas: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nEn atención: " + contarPorEstado(cola, "En atención")
                + "\nFinalizadas: " + contarPorEstado(cola, "Finalizada")
                + "\nCanceladas: " + contarPorEstado(cola, "Cancelada")
                + "\nTransferencias realizadas: " + totalTransferencias(cola)
                + "\nFinalizadas por operador:";
        for (int i = 0; i < operadores.length; i++) {
            texto += "\n  " + operadores[i] + ": " + finalizadasPorOperador(cola, operadores[i]);
        }
        return texto;
    }
}