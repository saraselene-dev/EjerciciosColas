// trae las herramientas: cola (LinkedList y Queue) y lector del teclado (Scanner)
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// clase con toda la lógica de la recepción
public class Metodos {

    // VALIDACIONES
    // método que lee un entero; recibe el lector
    public int validarEntero(Scanner sc) {
        // mientras lo escrito no sea un número entero...
        while (!sc.hasNextInt()) {
            // ...avisa al usuario
            System.out.println("Por favor ingrese un dato numérico entero");
            // saca el dato malo del lector y bótalo
            sc.next();
        // vuelve a revisar lo siguiente que escriba
        }
        // ya hay un entero: léelo y devuélvelo
        return sc.nextInt();
    // aquí termina el método
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
        // vuelve a revisar
        }
        // devuelve el número válido
        return num;
    // aquí termina el método
    }

    // MENÚS DE OPCIONES

    // método que muestra los funcionarios y devuelve el elegido como texto
    public String menuFuncionario(Scanner sc) {
        // muestra la pregunta
        System.out.println("¿A qué funcionario desea visitar?");
        // opción 1
        System.out.println("1) Gerencia");
        // opción 2
        System.out.println("2) Recursos Humanos");
        // opción 3
        System.out.println("3) Contabilidad");
        // opción 4
        System.out.println("4) Compras");
        // lee la opción validada
        int opt = validarRango(sc, 1, 4);
        // texto que se va a devolver
        String funcionario = "";
        // según la opción...
        switch (opt) {
            // 1: Gerencia
            case 1:
                funcionario = "Gerencia";
                break;
            // 2: Recursos Humanos
            case 2:
                funcionario = "Recursos Humanos";
                break;
            // 3: Contabilidad
            case 3:
                funcionario = "Contabilidad";
                break;
            // 4: Compras
            default:
                funcionario = "Compras";
                break;
        }
        // devuelve el funcionario elegido
        return funcionario;
    // aquí termina el método
    }

    // método que muestra los motivos de visita y devuelve el elegido como texto
    public String menuMotivo(Scanner sc) {
        // muestra la pregunta
        System.out.println("Motivo de la visita");
        // opción 1
        System.out.println("1) Reunión");
        // opción 2
        System.out.println("2) Entrevista");
        // opción 3
        System.out.println("3) Entrega de documentos");
        // opción 4
        System.out.println("4) Otro");
        // lee la opción validada
        int opt = validarRango(sc, 1, 4);
        // texto que se va a devolver
        String motivo = "";
        // según la opción...
        switch (opt) {
            // 1: reunión
            case 1:
                motivo = "Reunión";
                break;
            // 2: entrevista
            case 2:
                motivo = "Entrevista";
                break;
            // 3: entrega
            case 3:
                motivo = "Entrega de documentos";
                break;
            // 4: otro
            default:
                motivo = "Otro";
                break;
        // aquí termina el switch
        }
        // devuelve el motivo elegido
        return motivo;
    // aquí termina el método
    }

    // MÉTODOS DE APOYO

    // método que busca un visitante por documento; devuelve el visitante o null si no existe
    public Visitante buscarVisitante(Queue<Visitante> cola, String documento) {
        // crea la variable del encontrado, todavía sin nadie
        Visitante encontrado = null;
        // recorre la cola, un visitante a la vez
        for (Visitante v : cola) {
            // si su documento es el buscado...
            if (v.getDocumento().equals(documento)) {
                // ...guárdalo (si vino varias veces, queda la visita más reciente)
                encontrado = v;
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // devuelve el encontrado, o null si no hubo nadie
        return encontrado;
    // aquí termina el método
    }

    // método que responde si un documento ya tiene una visita activa (en espera o llamado)
    public boolean tieneVisitaActiva(Queue<Visitante> cola, String documento) {
        // recorre la cola, un visitante a la vez
        for (Visitante v : cola) {
            // si es el mismo documento y su visita sigue viva...
            if (v.getDocumento().equals(documento)
                    && (v.getEstado().equals("En espera") || v.getEstado().equals("Llamado"))) {
                // ...responde que sí
                return true;
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // revisé a todos y ninguno tenía visita activa
        return false;
    // aquí termina el método
    }

    // método que calcula el siguiente turno: el mayor turno existente más uno
    public int siguienteTurno(Queue<Visitante> cola) {
        // empieza suponiendo que el mayor es 0
        int mayor = 0;
        // recorre la cola
        for (Visitante v : cola) {
            // si este turno es mayor que el que llevo...
            if (v.getTurno() > mayor) {
                // ...ese es el nuevo mayor
                mayor = v.getTurno();
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // el siguiente turno es el mayor más uno
        return mayor + 1;
    // aquí termina el método
    }

    // método que saca a un visitante específico de la cola usando una cola auxiliar; devuelve la cola
    public Queue<Visitante> quitarDeCola(Queue<Visitante> cola, Visitante sacar) {
        // crea la cola auxiliar vacía
        Queue<Visitante> aux = new LinkedList<>();
        // mientras la cola original tenga visitantes...
        while (!cola.isEmpty()) {
            // ...saca el primero
            Visitante v = cola.poll();
            // si no es el que queremos quitar (se compara si es el mismo objeto)...
            if (v != sacar) {
                // ...guárdalo en la auxiliar
                aux.offer(v);
            // aquí termina el if (al que sí era, no se guarda)
            }
        // aquí termina el primer ciclo
        }
        // mientras la auxiliar tenga visitantes...
        while (!aux.isEmpty()) {
            // ...devuélvelos a la original, en el mismo orden
            cola.offer(aux.poll());
        // aquí termina el segundo ciclo
        }
        // devuelve la cola sin ese visitante
        return cola;
    // aquí termina el método
    }

    // método de apoyo que arma el texto con los datos de un visitante
    private String datosVisitante(Visitante v) {
        // devuelve los datos en varias líneas, con un separador al final
        return "Turno: " + v.getTurno()
                + "\nDocumento: " + v.getDocumento()
                + "\nNombre: " + v.getNombre()
                + "\nFuncionario: " + v.getFuncionario()
                + "\nMotivo: " + v.getMotivo()
                + "\nEstado: " + v.getEstado()
                + "\n-----------------------------\n";
    // aquí termina el método
    }

    // OPERACIONES DEL PROCESO

    // LLEGAR: registra visitantes y los pone en la fila; devuelve la cola
    public Queue<Visitante> registrarLlegada(Queue<Visitante> cola, Metodos m, Scanner sc) {
        // interruptor del registro, encendido
        boolean continuar = true;
        // mientras quiera seguir registrando...
        while (continuar) {
            // crea una ficha nueva en blanco
            Visitante v = new Visitante();
            // pide el documento
            System.out.println("\nIngrese el número de documento");
            // lee el documento (una sola palabra)
            String documento = sc.next();
            // mientras ese documento ya tenga una visita activa...
            while (m.tieneVisitaActiva(cola, documento)) {
                // ...avisa
                System.out.println("Este documento ya tiene una visita activa, ingrese otro");
                // y pide otro
                documento = sc.next();
            // vuelve a revisar
            }
            // guarda el documento
            v.setDocumento(documento);
            // limpia el Enter que quedó pendiente
            sc.nextLine();
            // pide el nombre
            System.out.println("Ingrese el nombre");
            // lee el nombre completo, con espacios
            v.setNombre(sc.nextLine());
            // muestra los funcionarios y guarda el elegido
            v.setFuncionario(m.menuFuncionario(sc));
            // muestra los motivos y guarda el elegido
            v.setMotivo(m.menuMotivo(sc));
            // asigna el turno
            v.setTurno(m.siguienteTurno(cola));
            // empieza esperando
            v.setEstado("En espera");
            // todavía no lo han llamado
            v.setLlamadosSinRespuesta(0);
            // ponlo al final de la fila
            cola.offer(v);
            // confirma
            System.out.println("Bienvenido " + v.getNombre() + ", su turno es el " + v.getTurno());
            // pregunta si sigue registrando
            System.out.println("¿Registrar otro visitante? Sí: 1 / No: 0");
            // si responde 0...
            if (m.validarRango(sc, 0, 1) == 0) {
                // ...apaga el interruptor
                continuar = false;
            // aquí termina el if
            }
        // aquí termina el ciclo
        }
        // devuelve la cola actualizada
        return cola;
    // aquí termina el método
    }

    // ESPERAR: muestra a los visitantes que están esperando, en orden de llegada
    public String consultarEnEspera(Queue<Visitante> cola) {
        // texto vacío
        String texto = "";
        // recorre la fila
        for (Visitante v : cola) {
            // si está esperando...
            if (v.getEstado().equals("En espera")) {
                // ...pega sus datos
                texto += datosVisitante(v);
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // si nadie estaba esperando...
        if (texto.isEmpty()) {
            // ...avísalo
            return "No hay visitantes en espera";
        // aquí termina el if
        }
        // devuelve la lista
        return texto;
    // aquí termina el método
    }

    // SER LLAMADO: llama al primer visitante que está esperando
    public String llamarSiguiente(Queue<Visitante> cola) {
        // recorre la fila desde el primero
        for (Visitante v : cola) {
            // si está esperando...
            if (v.getEstado().equals("En espera")) {
                // ...pásalo a llamado
                v.setEstado("Llamado");
                // y anúncialo
                return "Se llama al turno " + v.getTurno() + ": " + v.getNombre() + ", por favor diríjase a "
                        + v.getFuncionario();
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // nadie esperaba
        return "No hay visitantes en espera";
    // aquí termina el método
    }

    // NO RESPONDER: al primer silencio va al final de la fila; al segundo pierde el turno
    public String noResponde(Queue<Visitante> cola, String documento) {
        // busca al visitante
        Visitante v = buscarVisitante(cola, documento);
        // si no existe...
        if (v == null) {
            // ...avísalo
            return "No existe un visitante con ese documento";
        // aquí termina el if
        }
        // si no fue llamado...
        if (!v.getEstado().equals("Llamado")) {
            // ...no puede "no responder"
            return "Solo puede no responder un visitante que fue llamado. Estado actual: " + v.getEstado();
        // aquí termina el if
        }
        // suma un llamado sin respuesta
        v.setLlamadosSinRespuesta(v.getLlamadosSinRespuesta() + 1);
        // si ya completó dos llamados sin responder...
        if (v.getLlamadosSinRespuesta() >= 2) {
            // ...pierde el turno
            v.setEstado("No respondió");
            // avísalo
            return v.getNombre() + " no respondió dos veces y perdió su turno";
        // aquí termina el if
        }
        // si es la primera vez: sácalo de su posición
        quitarDeCola(cola, v);
        // dale un turno nuevo, el último
        v.setTurno(siguienteTurno(cola));
        // vuelve a esperar
        v.setEstado("En espera");
        // ponlo al final de la fila
        cola.offer(v);
        // avísalo
        return v.getNombre() + " no respondió; pasa al final de la fila con el turno " + v.getTurno();
    // aquí termina el método
    }

    // CANCELAR LA VISITA: se permite mientras espera o fue llamado
    public String cancelarVisita(Queue<Visitante> cola, String documento) {
        // busca al visitante
        Visitante v = buscarVisitante(cola, documento);
        // si no existe...
        if (v == null) {
            // ...avísalo
            return "No existe un visitante con ese documento";
        // aquí termina el if
        }
        // si está esperando o llamado...
        if (v.getEstado().equals("En espera") || v.getEstado().equals("Llamado")) {
            // ...cancela la visita
            v.setEstado("Cancelado");
            // confirma
            return "Visita de " + v.getNombre() + " cancelada";
        // aquí termina el if
        }
        // en cualquier otro estado no se puede
        return "No se puede cancelar: estado actual " + v.getEstado();
    // aquí termina el método
    }

    // CAMBIAR EL FUNCIONARIO: solo mientras espera; conserva su turno
    public String cambiarFuncionario(Queue<Visitante> cola, String documento, String nuevoFuncionario) {
        // busca al visitante
        Visitante v = buscarVisitante(cola, documento);
        // si no existe...
        if (v == null) {
            // ...avísalo
            return "No existe un visitante con ese documento";
        // aquí termina el if
        }
        // si ya no está esperando...
        if (!v.getEstado().equals("En espera")) {
            // ...no se puede cambiar
            return "Solo se puede cambiar el funcionario mientras espera. Estado actual: " + v.getEstado();
        // aquí termina el if
        }
        // si eligió el mismo funcionario...
        if (v.getFuncionario().equals(nuevoFuncionario)) {
            // ...no hay nada que cambiar
            return v.getNombre() + " ya va a visitar a " + nuevoFuncionario;
        // aquí termina el if
        }
        // guarda el funcionario anterior
        String anterior = v.getFuncionario();
        // cambia el funcionario
        v.setFuncionario(nuevoFuncionario);
        // confirma
        return "Funcionario cambiado de " + anterior + " a " + nuevoFuncionario + ". Conserva su turno "
                + v.getTurno();
    // aquí termina el método
    }

    // SER ATENDIDO: solo quien fue llamado y se presentó
    public String marcarAtendido(Queue<Visitante> cola, String documento) {
        // busca al visitante
        Visitante v = buscarVisitante(cola, documento);
        // si no existe...
        if (v == null) {
            // ...avísalo
            return "No existe un visitante con ese documento";
        // aquí termina el if
        }
        // si no fue llamado...
        if (!v.getEstado().equals("Llamado")) {
            // ...no puede ser atendido todavía
            return "Solo se atiende a un visitante llamado. Estado actual: " + v.getEstado();
        // aquí termina el if
        }
        // márcalo como atendido
        v.setEstado("Atendido");
        // confirma
        return v.getNombre() + " fue atendido por " + v.getFuncionario();
    // aquí termina el método
    }

    // CONSULTAR UN VISITANTE: muestra sus datos y estado
    public String consultarVisitante(Queue<Visitante> cola, String documento) {
        // busca al visitante
        Visitante v = buscarVisitante(cola, documento);
        // si no existe...
        if (v == null) {
            // ...avísalo
            return "No existe un visitante con ese documento";
        // aquí termina el if
        }
        // devuelve sus datos
        return datosVisitante(v);
    // aquí termina el método
    }

    // RESUMEN

    // método que cuenta cuántos visitantes tienen un estado; recibe la cola y el estado a contar
    public int contarPorEstado(Queue<Visitante> cola, String estado) {
        // empieza en cero
        int contador = 0;
        // recorre la fila
        for (Visitante v : cola) {
            // si tiene el estado buscado...
            if (v.getEstado().equals(estado)) {
                // ...suma uno
                contador++;
            // aquí termina el if
            }
        // pasa al siguiente
        }
        // devuelve el conteo
        return contador;
    // aquí termina el método
    }

    // método que arma el resumen de la recepción
    public String resumen(Queue<Visitante> cola) {
        // devuelve el total y cuántos hay en cada estado
        return "Resumen de la recepción"
                + "\nTotal de visitas: " + cola.size()
                + "\nEn espera: " + contarPorEstado(cola, "En espera")
                + "\nLlamados: " + contarPorEstado(cola, "Llamado")
                + "\nAtendidos: " + contarPorEstado(cola, "Atendido")
                + "\nCancelados: " + contarPorEstado(cola, "Cancelado")
                + "\nNo respondieron: " + contarPorEstado(cola, "No respondió");
    }

}