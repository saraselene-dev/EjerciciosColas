public class Caso {
    private int Turno;
    private String Documento;
    private String Nombre;
    private String Producto;
    // Cambio, Garantía o Devolución
    private String Motivo;
    private String Factura;
    // días que han pasado desde la compra; de esto depende si se aprueba
    private int DiasDesdeCompra;
    // toda la información adicional que el cliente va presentando
    private String Informacion;
    // módulo que lo atiende; vacío mientras espera
    private String Modulo;
    // En espera, En atención, Resuelto, Cancelado o Desistió
    private String Estado;
    // resultado de la atención (Aprobado o Rechazado, con la razón); vacío hasta resolver
    private String Resultado;

    public Caso() {
    }

    public Caso(int turno, String documento, String nombre, String producto, String motivo, String factura,
            int diasDesdeCompra) {
        Turno = turno;
        Documento = documento;
        Nombre = nombre;
        Producto = producto;
        Motivo = motivo;
        Factura = factura;
        DiasDesdeCompra = diasDesdeCompra;
        Informacion = "";
        Modulo = "";
        Estado = "En espera";
        Resultado = "";
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

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public String getFactura() {
        return Factura;
    }

    public void setFactura(String factura) {
        Factura = factura;
    }

    public int getDiasDesdeCompra() {
        return DiasDesdeCompra;
    }

    public void setDiasDesdeCompra(int diasDesdeCompra) {
        DiasDesdeCompra = diasDesdeCompra;
    }

    public String getInformacion() {
        return Informacion;
    }

    public void setInformacion(String informacion) {
        Informacion = informacion;
    }

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String modulo) {
        Modulo = modulo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}