import java.util.List;

public class Consola {
    public void mostrarMenu() {
        System.out.println();
        System.out.println(" Bienvenido al Simulador Espacial: Defensa Del Quetzal 2");
        System.out.println("--------------------Menu Principal--------------------");
        System.out.println("1. Listar todos los modulos");
        System.out.println("2. Buscar un modulo por ID o nombre");
        System.out.println("3. Consultar catalogo por costo");
        System.out.println("4. Avanzar un ciclo de la mision");
        System.out.println("5. Consultar resumen de comunicaciones");
        System.out.println("0. Salir");
        System.out.println("---------------------------------------------------------");
    }
    public void mostrarMensaje(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
    }

    public void mostrarModulos(List<Modulo> modulos) {
        System.out.println();
        System.out.println("------------> Lista De Modulos Actuales <------------");

        if (modulos == null || modulos.isEmpty()) {
            System.out.println("No hay modulos registrados.");
            return;
        }

        for (Modulo modulo : modulos) {
            System.out.println("----------------------------------------");
            System.out.println(modulo.toString());
        }

        System.out.println("----------------------------------------");
        System.out.println("Total de modulos: " + modulos.size());
    }

    public void mostrarResumen(String resumen) {
        System.out.println();
        System.out.println("------------> Resumen de las Comunicaciones <------------");

        if (resumen == null || resumen.isBlank()) {
            System.out.println("No hay informacion disponible.");
            return;
        }

        System.out.println(resumen);
        System.out.println("=======================================");
    }

}
