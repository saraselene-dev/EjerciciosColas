public class Cliente {
    private String Identificacion;
    private String Nombre;
    private int TipoTramite;
    private int Edad;
    private int Turno;
    private int Estado; 

    public Cliente() {
    }

    public Cliente(String identificacion, String nombre, int tipoTramite, int edad, int turno) {
        Identificacion = identificacion;
        Nombre = nombre;
        TipoTramite = tipoTramite;
        Edad = edad;
        Turno = turno;
        Estado = 1;
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(String identificacion) {
        Identificacion = identificacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTipoTramite() {
        return TipoTramite;
    }

    public void setTipoTramite(int tipoTramite) {
        TipoTramite = tipoTramite;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int turno) {
        Turno = turno;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }
}