public class Llamada {
    // número consecutivo de la llamada; sirve para identificarla
    private int Numero;
    private String Cliente;
    private String Motivo;
    // hora de entrada de la llamada, en formato HH:MM
    private String Hora;
    // Alta, Media o Baja
    private String Prioridad;
    // En espera, En atención, Finalizada o Cancelada
    private String Estado;
    // operador que la atiende; vacío mientras espera
    private String Operador;
    // cuántas veces fue transferida entre operadores
    private int Transferencias;

    public Llamada() {
    }

    public Llamada(int numero, String cliente, String motivo, String hora, String prioridad) {
        Numero = numero;
        Cliente = cliente;
        Motivo = motivo;
        Hora = hora;
        Prioridad = prioridad;
        Estado = "En espera";
        Operador = "";
        Transferencias = 0;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(String prioridad) {
        Prioridad = prioridad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getOperador() {
        return Operador;
    }

    public void setOperador(String operador) {
        Operador = operador;
    }

    public int getTransferencias() {
        return Transferencias;
    }

    public void setTransferencias(int transferencias) {
        Transferencias = transferencias;
    }
}