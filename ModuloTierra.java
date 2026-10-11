public class ModuloTierra extends Modulo {
    private String estacion;
    private double capacidadDescarga;
    private double energiaPorDescarga;
    private double totalDescargado;

    public ModuloTierra(
        int id,
        String nombre,
        int salud,
        boolean activo,
        double costoConstruccion,
        String estacion,
        double capacidadDescarga,
        double energiaPorDescarga
    ) {
        super(id, nombre, salud, activo, costoConstruccion);

        if (estacion == null || estacion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La estacion no puede estar vacia."
            );
        }

        if (capacidadDescarga <= 0 || energiaPorDescarga < 0) {
            throw new IllegalArgumentException(
                "La capacidad debe ser positiva y la energia no negativa."
            );
        }

        this.estacion = estacion;
        this.capacidadDescarga = capacidadDescarga;
        this.energiaPorDescarga = energiaPorDescarga;
        this.totalDescargado = 0.0;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return "Operacion fallida: el modulo esta inactivo. "
                + "No se descargaron datos ni se consumio energia.";
        }

        if (mision.getDatosEnOrbita() <= 0) {
            return "Operacion fallida: no hay datos pendientes en orbita. "
                + "No se consumio energia.";
        }

        if (mision.getEnergiaDisponible() < energiaPorDescarga) {
            return "Operacion fallida: energia insuficiente. "
                + "Se necesitan " + energiaPorDescarga
                + " unidades. No se descargaron datos.";
        }

        if (!mision.consumirEnergia(energiaPorDescarga)) {
            return "Operacion fallida: no fue posible consumir energia.";
        }

        double datosDescargados =
            mision.retirarDatosOrbita(capacidadDescarga);

        totalDescargado += datosDescargados;

        return "Operacion exitosa."
            + "\nEstacion: " + estacion
            + "\nDatos descargados: " + datosDescargados + " MB"
            + "\nEnergia consumida: " + energiaPorDescarga
            + "\nTotal historico descargado por este modulo: "
            + totalDescargado + " MB";
    }

    @Override
    public String toString() {
        return super.toString()
            + "\nEstacion: " + estacion
            + "\nCapacidad de descarga por ciclo: "
            + capacidadDescarga + " MB"
            + "\nEnergia por descarga: " + energiaPorDescarga
            + "\nTotal historico descargado: "
            + totalDescargado + " MB";
    }

    public String getEstacion() {
        return estacion;
    }

    public double getCapacidadDescarga() {
        return capacidadDescarga;
    }

    public double getEnergiaPorDescarga() {
        return energiaPorDescarga;
    }

    public double getTotalDescargado() {
        return totalDescargado;
    }
}