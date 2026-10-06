// clase objeto: el molde de la ficha de cada paciente
public class Paciente {
    // identificación del paciente (texto, porque no se opera matemáticamente)
    private String Id;
    private String Nombre;
    private int Edad;
    private String Servicio;
    // en qué va: Pendiente, En fila, En atención, Atendido, Cancelado o Retirado
    private String Estado;
    // si tiene una condición especial que lo hace pasar antes
    private boolean Prioritario;
    private int OrdenLlegada;

    // constructor vacío: crea una ficha en blanco
    public Paciente() {
    }

    // constructor completo: crea la ficha con los datos que vienen de la matriz
    public Paciente(String id, String nombre, int edad, String servicio, String estado, int ordenLlegada) {
        // guarda la identificación
        Id = id;
        // guarda el nombre
        Nombre = nombre;
        // guarda la edad
        Edad = edad;
        // guarda el servicio
        Servicio = servicio;
        // guarda el estado
        Estado = estado;
        // guarda su posición de llegada
        OrdenLlegada = ordenLlegada;
        // todo paciente empieza sin prioridad, hasta que alguien lo marque
        Prioritario = false;
    }

    // devuelve la identificación
    public String getId() {
        // entrega el valor guardado
        return Id;
    }

    // cambia la identificación
    public void setId(String id) {
        // guarda el valor nuevo
        Id = id;
    }

    public String getNombre() {

        return Nombre;
    }

    public void setNombre(String nombre) {

        Nombre = nombre;

    }

    public int getEdad() {

        return Edad;

    }

    public void setEdad(int edad) {

        Edad = edad;

    }

    public String getServicio() {

        return Servicio;

    }

    public void setServicio(String servicio) {

        Servicio = servicio;

    }

    public String getEstado() {

        return Estado;

    }

    public void setEstado(String estado) {

        Estado = estado;

    }

    // responde si el paciente es prioritario (los boolean usan "is" en vez de
    // "get")
    public boolean isPrioritario() {
        // entrega el valor guardado
        return Prioritario;

    }

    // marca o desmarca al paciente como prioritario
    public void setPrioritario(boolean prioritario) {
        // guarda el valor nuevo
        Prioritario = prioritario;
        // aquí termina el setter
    }

    public int getOrdenLlegada() {

        return OrdenLlegada;

    }

    public void setOrdenLlegada(int ordenLlegada) {

        OrdenLlegada = ordenLlegada;

    }

}