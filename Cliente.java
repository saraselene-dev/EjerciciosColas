public class Cliente {
    private String Identificacion;
    private String Nombre;
    private int TipoTramite;
    private int Edad;
    private int CondicionPreferencial;
    private int Turno;
    private int Estado;
    
    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public Cliente() {
    }

    public Cliente(String identificacion, String nombre, int tipoTramite, int edad, int condicionPreferencial,
            int turno) {
        Identificacion = identificacion;
        Nombre = nombre;
        TipoTramite = tipoTramite;
        Edad = edad;
        CondicionPreferencial = condicionPreferencial;
        Turno = turno;
        Estado=1;
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

    public int getCondicionPreferencial() {
        return CondicionPreferencial;
    }

    public void setCondicionPreferencial(int condicionPreferencial) {
        CondicionPreferencial = condicionPreferencial;
    }

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int turno) {
        Turno = turno;
    }
    
    


}
