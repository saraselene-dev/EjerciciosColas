// clase objeto: el molde de la ficha de cada visitante de la empresa
public class Visitante {
    // documento de identidad; texto, porque no se opera matemáticamente
    private String Documento;
    private String Nombre;
    private String Funcionario;
    private String Motivo;
    private int Turno;
    private String Estado;
    private int LlamadosSinRespuesta;

    
    public Visitante() {

    }

    // constructor completo: crea la ficha de un visitante que acaba de llegar
    public Visitante(String documento, String nombre, String funcionario, String motivo, int turno) {
        Documento = documento;
        Nombre = nombre;
        Funcionario = funcionario;
        Motivo = motivo;
        Turno = turno;    
        Estado = "En espera";
        LlamadosSinRespuesta = 0;
    }

    // devuelve el documento
    public String getDocumento() {
        // entrega el valor guardado
        return Documento;
    // aquí termina el getter
    }

    // cambia el documento
    public void setDocumento(String documento) {
        // guarda el valor nuevo
        Documento = documento;
    // aquí termina el setter
    }

    // devuelve el nombre
    public String getNombre() {
        // entrega el valor guardado
        return Nombre;
    // aquí termina el getter
    }

    // cambia el nombre
    public void setNombre(String nombre) {
        // guarda el valor nuevo
        Nombre = nombre;
    // aquí termina el setter
    }

    // devuelve el funcionario que desea visitar
    public String getFuncionario() {
        // entrega el valor guardado
        return Funcionario;
    // aquí termina el getter
    }

    // cambia el funcionario que desea visitar
    public void setFuncionario(String funcionario) {
        // guarda el valor nuevo
        Funcionario = funcionario;
    // aquí termina el setter
    }

    // devuelve el motivo de la visita
    public String getMotivo() {
        // entrega el valor guardado
        return Motivo;
    // aquí termina el getter
    }

    // cambia el motivo de la visita
    public void setMotivo(String motivo) {
        // guarda el valor nuevo
        Motivo = motivo;
    // aquí termina el setter
    }

    // devuelve el turno
    public int getTurno() {
        // entrega el valor guardado
        return Turno;
    // aquí termina el getter
    }

    // cambia el turno
    public void setTurno(int turno) {
        // guarda el valor nuevo
        Turno = turno;
    // aquí termina el setter
    }

    // devuelve el estado
    public String getEstado() {
        // entrega el valor guardado
        return Estado;
    // aquí termina el getter
    }

    // cambia el estado
    public void setEstado(String estado) {
        // guarda el valor nuevo
        Estado = estado;
    // aquí termina el setter
    }

    // devuelve cuántas veces lo llamaron sin que respondiera
    public int getLlamadosSinRespuesta() {
        // entrega el valor guardado
        return LlamadosSinRespuesta;
    // aquí termina el getter
    }

    // cambia cuántas veces lo llamaron sin que respondiera
    public void setLlamadosSinRespuesta(int llamadosSinRespuesta) {
        // guarda el valor nuevo
        LlamadosSinRespuesta = llamadosSinRespuesta;
    // aquí termina el setter
    }
// aquí termina la clase Visitante
}