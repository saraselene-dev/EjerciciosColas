public class Solicitud {
    private int Numero;
    private String Cliente;
    private String Ubicacion;
    private String Problema;
    // técnico que pidió el cliente; puede ser "Cualquiera"
    private String TecnicoSolicitado;
    // técnico que realmente hace el trabajo; vacío mientras espera
    private String TecnicoAsignado;
    // Urgente, Alta o Normal
    private String Prioridad;
    // En espera, En proceso, Completada o Cancelada
    private String Estado;

    public Solicitud() {
    }

    public Solicitud(int numero, String cliente, String ubicacion, String problema, String tecnicoSolicitado,
            String prioridad) {
        Numero = numero;
        Cliente = cliente;
        Ubicacion = ubicacion;
        Problema = problema;
        TecnicoSolicitado = tecnicoSolicitado;
        Prioridad = prioridad;
        TecnicoAsignado = "";
        Estado = "En espera";
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

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getProblema() {
        return Problema;
    }

    public void setProblema(String problema) {
        Problema = problema;
    }

    public String getTecnicoSolicitado() {
        return TecnicoSolicitado;
    }

    public void setTecnicoSolicitado(String tecnicoSolicitado) {
        TecnicoSolicitado = tecnicoSolicitado;
    }

    public String getTecnicoAsignado() {
        return TecnicoAsignado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        TecnicoAsignado = tecnicoAsignado;
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
}