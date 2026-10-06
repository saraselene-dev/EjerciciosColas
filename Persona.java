public class Persona {
    private String Documento;
    private String Nombre;
    private String Telefono;
    private String TipoCredito;
    private double Monto;
    private int PlazoMeses;
    // asesor que atiende la solicitud; queda vacío mientras espera
    private String Asesor;
    private int Turno;
    // En espera, En asesoría, Aprobada, Rechazada o Cancelada
    private String Estado;

    public Persona() {
    }

    public Persona(String documento, String nombre, String telefono, String tipoCredito, double monto,
            int plazoMeses, int turno) {
        Documento = documento;
        Nombre = nombre;
        Telefono = telefono;
        TipoCredito = tipoCredito;
        Monto = monto;
        PlazoMeses = plazoMeses;
        Turno = turno;
        // toda solicitud nueva empieza esperando y sin asesor
        Asesor = "";
        Estado = "En espera";
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

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getTipoCredito() {
        return TipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        TipoCredito = tipoCredito;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    public int getPlazoMeses() {
        return PlazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        PlazoMeses = plazoMeses;
    }

    public String getAsesor() {
        return Asesor;
    }

    public void setAsesor(String asesor) {
        Asesor = asesor;
    }

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int turno) {
        Turno = turno;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}