public class Cliente {
    // número de ficha que recibe al entrar a una fila; sirve para identificarlo
    private int Turno;
    private String Nombre;
    private int CantidadProductos;
    // Preferencial, Compra rápida o Compra normal
    private String Motivo;
    // número de la caja en cuya fila está (o en la que fue atendido)
    private int Caja;
    // En fila, Atendido o Abandonó
    private String Estado;

    public Cliente() {
    }

    public Cliente(int turno, String nombre, int cantidadProductos, String motivo, int caja) {
        Turno = turno;
        Nombre = nombre;
        CantidadProductos = cantidadProductos;
        Motivo = motivo;
        Caja = caja;
        Estado = "En fila";
    }

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int turno) {
        Turno = turno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCantidadProductos() {
        return CantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        CantidadProductos = cantidadProductos;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public int getCaja() {
        return Caja;
    }

    public void setCaja(int caja) {
        Caja = caja;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}