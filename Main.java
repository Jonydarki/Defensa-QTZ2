
public class Main {

    public static void main(String[] args) {
        Mision mision = new Mision();
        Consola vista = new Consola();
        Controlador controlador = new Controlador(mision, vista);

        controlador.ejecutar();
    }
}
