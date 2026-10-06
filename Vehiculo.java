// segunda clase objeto: cada vehículo de la flota
public class Vehiculo {
    private String Placa;
    private String Tipo;
    // máximo de kilos que puede llevar
    private double Capacidad;
    // Disponible o En ruta
    private String Estado;
    // número de la solicitud que lleva; 0 cuando está disponible
    private int SolicitudActual;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String tipo, double capacidad) {
        Placa = placa;
        Tipo = tipo;
        Capacidad = capacidad;
        Estado = "Disponible";
        SolicitudActual = 0;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public double getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(double capacidad) {
        Capacidad = capacidad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getSolicitudActual() {
        return SolicitudActual;
    }

    public void setSolicitudActual(int solicitudActual) {
        SolicitudActual = solicitudActual;
    }
}