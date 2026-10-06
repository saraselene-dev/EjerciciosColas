public class Cliente {
    // número de turno; identifica cada paso del cliente por el centro
    private int Turno;
    private String Documento;
    private String Nombre;
    // Información, Reclamos, Pagos, Actualización de datos o Solicitudes especiales
    private String Tipo;
    // Alta o Normal
    private String Prioridad;
    // En espera, Llamado, Atendido, Cancelado, No respondió, Abandonó o Anulado
    private String Estado;
    // módulo que lo llamó o atendió; vacío mientras espera
    private String Modulo;
    private int LlamadosSinRespuesta;
    // si otra persona tomó este turno, aquí queda a quién reemplazó
    private String Reemplaza;
    // si el cliente regresó después de abandonar, aquí queda su turno anterior; 0 si no
    private int RegresoDe;
    // true si a este turno se le corrigió un error de registro
    private boolean Corregido;

    public Cliente() {
    }

    public Cliente(int turno, String documento, String nombre, String tipo, String prioridad) {
        Turno = turno;
        Documento = documento;
        Nombre = nombre;
        Tipo = tipo;
        Prioridad = prioridad;
        Estado = "En espera";
        Modulo = "";
        LlamadosSinRespuesta = 0;
        Reemplaza = "";
        RegresoDe = 0;
        Corregido = false;
    }

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int turno) {
        Turno = turno;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
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

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String modulo) {
        Modulo = modulo;
    }

    public int getLlamadosSinRespuesta() {
        return LlamadosSinRespuesta;
    }

    public void setLlamadosSinRespuesta(int llamadosSinRespuesta) {
        LlamadosSinRespuesta = llamadosSinRespuesta;
    }

    public String getReemplaza() {
        return Reemplaza;
    }

    public void setReemplaza(String reemplaza) {
        Reemplaza = reemplaza;
    }

    public int getRegresoDe() {
        return RegresoDe;
    }

    public void setRegresoDe(int regresoDe) {
        RegresoDe = regresoDe;
    }

    public boolean isCorregido() {
        return Corregido;
    }

    public void setCorregido(boolean corregido) {
        Corregido = corregido;
    }
}