
import java.util.Scanner;

public class Controlador {

    private Mision mision;
    private Consola vista;
    private Scanner teclado;

    public Controlador(Mision mision, Consola vista) {
        this.mision = mision;
        this.vista = vista;
        this.teclado = new Scanner(System.in);
    }

    public void ejecutar() {
        int opcion;

        do {
            vista.mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    listarModulos();
                    break;

                case 2:
                    buscarModulo();
                    break;

                case 3:
                    consultarCatalogo();
                    break;

                case 4:
                    avanzarCiclo();
                    break;

                case 5:
                    consultarResumen();
                    break;

                case 0:
                    vista.mostrarMensaje(
                        "Finalizando el simulador Defensa de QTZ2..."
                    );
                    break;

                default:
                    vista.mostrarMensaje(
                        "Opcion invalida. Intente nuevamente."
                    );
            }

        } while (opcion != 0);
    }

    private int leerOpcion() {
        System.out.print("Seleccione una opcion: ");

        try {
            return Integer.parseInt(
                teclado.nextLine().trim()
            );
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void listarModulos() {
        vista.mostrarModulos(mision.listarModulos());
    }

    public void buscarModulo() {
        vista.mostrarMensaje(
            "Ingrese el criterio de busqueda:\n"
            + "1. Buscar por ID\n"
            + "2. Buscar por nombre"
        );

        System.out.print("Seleccione una opcion: ");
        String criterio = teclado.nextLine().trim();

        Modulo modulo = null;

        switch (criterio) {
            case "1":
                System.out.print("Ingrese el ID del modulo: ");

                try {
                    int id = Integer.parseInt(
                        teclado.nextLine().trim()
                    );
                    modulo = mision.buscarPorId(id);
                } catch (NumberFormatException e) {
                    vista.mostrarMensaje(
                        "El ID debe ser un numero entero."
                    );
                    return;
                }
                break;

            case "2":
                System.out.print("Ingrese el nombre del modulo: ");
                String nombre = teclado.nextLine().trim();

                if (nombre.isEmpty()) {
                    vista.mostrarMensaje(
                        "El nombre no puede estar vacio."
                    );
                    return;
                }

                modulo = mision.buscarPorNombre(nombre);
                break;

            default:
                vista.mostrarMensaje(
                    "Criterio de busqueda invalido."
                );
                return;
        }

        if (modulo != null) {
            vista.mostrarMensaje(modulo.toString());
        } else {
            vista.mostrarMensaje(
                "No se encontro el modulo solicitado."
            );
        }
    }

    public void consultarCatalogo() {
        vista.mostrarMensaje(
            "CATALOGO DE MODULOS ORDENADO POR COSTO"
        );

        vista.mostrarModulos(mision.ordenarPorCosto());
    }

    public void avanzarCiclo() {
        vista.mostrarMensaje(
            "Iniciando un nuevo ciclo de la mision..."
        );

        vista.mostrarMensaje(mision.avanzarCiclo());
    }

    public void consultarResumen() {
        vista.mostrarMensaje("RESUMEN DE COMUNICACIONES");

        vista.mostrarResumen(
            mision.generarResumenComunicaciones()
        );
    }
}
