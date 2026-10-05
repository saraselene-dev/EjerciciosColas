import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    /*método público llamado registrarTurnos que recibe una cola de
    clientes, un objeto Metodos y un lector de teclado
    y devuelve una cola de clientes */
    public Queue<Cliente> registrarTruos(Queue<Cliente> cola, Metodos m, Scanner sc){
        // Creo un interruptor llamado cotinuar y lo dejo encedido 
        boolean continuar=true;
        /* (Esto aplica a todo lo que este dentro de {}.
        Mientras el interruptor siga encendido, repite el registro.
        El ciclo solo termina cuando, más abajo, el usuario responde que no quiere registrar a nadie más
        y se ejecuta continuar = false;, que apaga el interruptor.*/
        while(continuar){
            /*creo un cliente nuevo, en blanco, y lo llamo c". 
            Es como sacar un formulario vacío para llenarlo; 
            las líneas siguientes (c.setIdentificacion(...), c.setNombre(...), etc.) 
            son las que escriben los datos en ese formulario. */
            Cliente c= new Cliente();
            System.out.println();
            System.out.println("Bienvenidos al Banco");
            System.out.println();
            System.out.println("Ingrese el número de documento");
            c.setIdentificacion(sc.nextLine());
            System.out.println("Ingrese su nombre");
            c.setNombre(sc.nextLine());
            System.out.println("Ingrese la edad");
            c.setEdad(sc.nextInt());
            System.out.println("Seleccione el tipo de tramite");
            c.setTipoTramite(sc.nextInt());
            c.setEstado(1);
            System.out.println("¿Desea registrar otro usuario? Si: 1 / No: 0");
            int opt=sc.nextInt();
            // si respondió que no, apaga el interruptor para que el ciclo no se repita
            if(opt == 0){
                continuar = false;
            } // pon al cliente que acabo de llenar al final de la fila
            cola.offer(c);
        }
        return cola;
    }
    
}
