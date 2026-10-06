// clase objeto: el molde de la solicitud de visita de cada interesado
public class Interesado {
    // documento de identidad; texto, porque no se opera matemáticamente
    private String Documento;
    // nombre del interesado
    private String Nombre;
    // teléfono de contacto
    private String Telefono;
    // horario en que quiere visitar la propiedad
    private String Horario;
    // número de orden en que hizo la solicitud
    private int Turno;
    // en qué va: Confirmado, En lista de espera o Cancelado
    private String Estado;
    // si es un reemplazo, aquí queda a quién reemplazó; si no, queda vacío
    private String Reemplaza;

    // constructor vacío: crea una solicitud en blanco
    public Interesado() {
    }

    // constructor completo: crea la solicitud de un interesado
    public Interesado(String documento, String nombre, String telefono, String horario, int turno, String estado) {
        // guarda el documento
        Documento = documento;
        // guarda el nombre
        Nombre = nombre;
        // guarda el teléfono
        Telefono = telefono;
        // guarda el horario
        Horario = horario;
        // guarda el turno
        Turno = turno;
        // guarda el estado
        Estado = estado;
        // al principio no reemplaza a nadie
        Reemplaza = "";
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

    // devuelve el horario
    public String getHorario() {
        // entrega el valor guardado
        return Horario;
    }

    // cambia el horario
    public void setHorario(String horario) {
        // guarda el valor nuevo
        Horario = horario;
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

    // devuelve a quién reemplazó
    public String getReemplaza() {
        // entrega el valor guardado
        return Reemplaza;
    }

    // cambia a quién reemplazó
    public void setReemplaza(String reemplaza) {
        // guarda el valor nuevo
        Reemplaza = reemplaza;
    }
}