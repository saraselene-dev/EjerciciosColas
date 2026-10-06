// clase objeto: el molde de la solicitud de cada ciudadano
public class Ciudadano {
    // documento de identidad; texto, porque no se opera matemáticamente
    private String Documento;
    // nombre del ciudadano
    private String Nombre;
    // teléfono de contacto; texto, por la misma razón que el documento
    private String Telefono;
    // documento que solicita: Certificado, Pasaporte, Licencia o Registro civil
    private String TipoSolicitud;
    // número de turno asignado al llegar
    private int Turno;
    // en qué va: En espera, Llamado, Finalizado o Cancelado
    private String Estado;

    // constructor vacío: crea una solicitud en blanco
    public Ciudadano() {
    }

    // constructor completo: crea la solicitud de un ciudadano que acaba de llegar
    public Ciudadano(String documento, String nombre, String telefono, String tipoSolicitud, int turno) {
        // guarda el documento
        Documento = documento;
        // guarda el nombre
        Nombre = nombre;
        // guarda el teléfono
        Telefono = telefono;
        // guarda el documento que solicita
        TipoSolicitud = tipoSolicitud;
        // guarda el turno
        Turno = turno;
        // toda solicitud nueva empieza esperando
        Estado = "En espera";
    }

    // devuelve el documento
    public String getDocumento() {
        // entrega el valor guardado
        return Documento;
    }

    // cambia el documento
    public void setDocumento(String documento) {
        // guarda el valor nuevo
        Documento = documento;
    }

    // devuelve el nombre
    public String getNombre() {
        // entrega el valor guardado
        return Nombre;
    }

    // cambia el nombre
    public void setNombre(String nombre) {
        // guarda el valor nuevo
        Nombre = nombre;
    }

    // devuelve el teléfono
    public String getTelefono() {
        // entrega el valor guardado
        return Telefono;
    }

    // cambia el teléfono
    public void setTelefono(String telefono) {
        // guarda el valor nuevo
        Telefono = telefono;
    }

    // devuelve el documento que solicita
    public String getTipoSolicitud() {
        // entrega el valor guardado
        return TipoSolicitud;
    }

    // cambia el documento que solicita
    public void setTipoSolicitud(String tipoSolicitud) {
        // guarda el valor nuevo
        TipoSolicitud = tipoSolicitud;
    }

    // devuelve el turno
    public int getTurno() {
        // entrega el valor guardado
        return Turno;
    }

    // cambia el turno
    public void setTurno(int turno) {
        // guarda el valor nuevo
        Turno = turno;
    }

    // devuelve el estado
    public String getEstado() {
        // entrega el valor guardado
        return Estado;
    }

    // cambia el estado
    public void setEstado(String estado) {
        // guarda el valor nuevo
        Estado = estado;
    }
}