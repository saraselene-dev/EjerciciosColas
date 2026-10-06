public class Solicitud {
    private int Numero;
    private String Cliente;
    private String Origen;
    private String Destino;
    private String TipoMercancia;
    // peso en kilogramos
    private double Peso;
    // Alta o Normal
    private String Prioridad;
    // Pendiente, En ruta o Entregada
    private String Estado;
    // placa del vehículo que la transporta; vacío mientras está pendiente
    private String Vehiculo;
    // si una carga muy pesada se dividió, aquí queda "Parte 1 de 3"; si no, vacío
    private String Parte;

    public Solicitud() {
    }

    public Solicitud(int numero, String cliente, String origen, String destino, String tipoMercancia, double peso,
            String prioridad) {
        Numero = numero;
        Cliente = cliente;
        Origen = origen;
        Destino = destino;
        TipoMercancia = tipoMercancia;
        Peso = peso;
        Prioridad = prioridad;
        Estado = "Pendiente";
        Vehiculo = "";
        Parte = "";
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

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getTipoMercancia() {
        return TipoMercancia;
    }

    public void setTipoMercancia(String tipoMercancia) {
        TipoMercancia = tipoMercancia;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double peso) {
        Peso = peso;
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

    public String getVehiculo() {
        return Vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        Vehiculo = vehiculo;
    }

    public String getParte() {
        return Parte;
    }

    public void setParte(String parte) {
        Parte = parte;
    }
}