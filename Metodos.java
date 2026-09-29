import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {

    public Queue<Cliente> registrarTurnos(Queue<Cliente> cola, Metodos m, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            Cliente c = new Cliente();
            System.out.println();
            System.out.println("Bienvenidos al Banco");
            System.out.println();

            System.out.println("Ingrese el número de documento");
            String documento = sc.next();
            while (m.tieneTurnoActivo(cola, documento)) {
                System.out.println("Este documento ya tiene un turno activo, ingrese otro");
                documento = sc.next();
            }
            c.setIdentificacion(documento);
            sc.nextLine();

            System.out.println("Ingrese su nombre");
            c.setNombre(sc.nextLine());

            System.out.println("Ingrese la edad");
            c.setEdad(m.validarRango(sc, 1, 120));

            System.out.println("Condición preferencial Si: 1 / No: 0");
            c.setCondicionPreferencial(m.validarRango(sc, 0, 1));

            c.setTipoTramite(m.menuTramite(sc));
            c.setTurno(m.validarTurno(cola));
            c.setEstado(1); // 1 = en espera

            System.out.println("¿Desea registrar otro usuario? Si: 1 / No: 0");
            int opt = m.validarRango(sc, 0, 1);
            if (opt == 0) {
                continuar = false;
            }
            cola.offer(c);
        }
        return cola;
    }

    public int menuTramite(Scanner sc) {
        System.out.println();
        System.out.println("Seleccione el tipo de trámite");
        System.out.println("1) Consignación");
        System.out.println("2) Retiro");
        System.out.println("3) Pago de servicios");
        System.out.println("4) Asesoría");
        return validarRango(sc, 1, 4);
    }

    public boolean tieneTurnoActivo(Queue<Cliente> cola, String id) {
        for (Cliente c : cola) {
            if (c.getIdentificacion().equals(id) && (c.getEstado() == 1 || c.getEstado() == 2)) {
                return true;
            }
        }
        return false;
    }

    public int validarTurno(Queue<Cliente> cola) {
        int turno = 0;
        if (cola.isEmpty()) {
            turno = 1;
        } else {
            turno = cola.size() + 1;
        }
        return turno;
    }

    public String consultarEnEspera(Queue<Cliente> cola) {
        String texto = "";

        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.getCondicionPreferencial() == 1) {
                texto += "[PREFERENCIAL]\n" + datosCliente(c);
            }
        }

        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.getCondicionPreferencial() == 0) {
                texto += "[NORMAL]\n" + datosCliente(c);
            }
        }

        if (texto.isEmpty()) {
            return "No hay usuarios en espera";
        }
        return texto;
    }

    public String llamarSiguiente(Queue<Cliente> cola) {

        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.getCondicionPreferencial() == 1) {
                c.setEstado(2);
                return "Se llama al siguiente usuario (PREFERENCIAL):\n" + datosCliente(c);
            }
        }

        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.getCondicionPreferencial() == 0) {
                c.setEstado(2);
                return "Se llama al siguiente usuario (NORMAL):\n" + datosCliente(c);
            }
        }

        return "No hay usuarios en espera";
    }

    public String marcarAtendido(Queue<Cliente> cola, Stack<Cliente> historial, String id) {
        for (Cliente c : cola) {
            if (c.getIdentificacion().equals(id)) {
                switch (c.getEstado()) {
                    case 1:
                        return "El usuario aún no ha sido llamado a la ventanilla";
                    case 2:
                        c.setEstado(3);
                        historial.push(c);
                        return "Usuario " + c.getNombre() + " marcado como atendido";
                    case 3:
                        return "El usuario ya había sido atendido";
                    default:
                        return "El turno de este usuario fue cancelado";
                }
            }
        }
        return "No existe un usuario con esa identificación";
    }

    public String cambiarAPreferencial(Queue<Cliente> cola, String id) {
        for (Cliente c : cola) {
            if (c.getIdentificacion().equals(id)) {
                if (c.getEstado() != 1) {
                    return "Solo se puede cambiar la condición de un usuario que está en espera";
                }
                if (c.getCondicionPreferencial() == 1) {
                    return "El usuario ya tiene atención preferencial";
                }
                c.setCondicionPreferencial(1);
                return "Usuario " + c.getNombre() + " ahora tiene atención preferencial, conserva su turno "
                        + c.getTurno();
            }
        }
        return "No existe un usuario con esa identificación";
    }

    public String cancelarTurno(Queue<Cliente> cola, String id) {
        for (Cliente c : cola) {
            if (c.getIdentificacion().equals(id)) {
                switch (c.getEstado()) {
                    case 1:
                    case 2:
                        c.setEstado(4);
                        return "Turno " + c.getTurno() + " de " + c.getNombre() + " cancelado";
                    case 3:
                        return "No se puede cancelar: el usuario ya fue atendido";
                    default:
                        return "Este turno ya estaba cancelado";
                }
            }
        }
        return "No existe un usuario con esa identificación";
    }

    public String buscarPorId(Queue<Cliente> cola, String id) {
        for (Cliente c : cola) {
            if (c.getIdentificacion().equals(id)) {
                String condicion = "";
                if (c.getCondicionPreferencial() == 1) {
                    condicion = "Preferencial";
                } else {
                    condicion = "Normal";
                }
                return datosCliente(c)
                        + "Condición: " + condicion
                        + "\nEstado: " + nombreEstado(c.getEstado());
            }
        }
        return "No existe un usuario con esa identificación";
    }

    public int contarEnEspera(Queue<Cliente> cola, int condicion) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getEstado() == 1 && (condicion == -1 || c.getCondicionPreferencial() == condicion)) {
                contador++;
            }
        }
        return contador;
    }

    public String mostrarHistorial(Stack<Cliente> historial) {
        if (historial.isEmpty()) {
            return "Aún no se ha atendido a ningún usuario";
        }
        Stack<Cliente> copia = new Stack<>();
        copia.addAll(historial);

        String texto = "Historial de atenciones (más reciente primero):\n";
        while (!copia.isEmpty()) {
            texto += datosCliente(copia.pop());
        }
        return texto;
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

    private String nombreEstado(int estado) {
        String mensaje = "";
        switch (estado) {
            case 1:
                mensaje = "En espera";
                break;
            case 2:
                mensaje = "Llamado a ventanilla";
                break;
            case 3:
                mensaje = "Atendido";
                break;
            default:
                mensaje = "Cancelado";
                break;
        }
        return mensaje;
    }

    public int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Por favor ingresar un dato entero numérico");
            sc.next();
        }
        return sc.nextInt();
    }

    public int validarRango(Scanner sc, int min, int max) {
        int num = validarEntero(sc);
        while (num < min || num > max) {
            System.out.println("Ingrese un valor entre: " + min + " y " + max);
            num = validarEntero(sc);
        }
        return num;
    }
}