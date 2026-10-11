public class ModuloEnergia extends Modulo {

    private double energiaPorCiclo;
    private int ciclosAcumulados;
    private double totalGenerado;

    public ModuloEnergia(
        int id,
        String nombre,
        int salud,
        boolean activo,
        double costoConstruccion,
        double energiaPorCiclo
    ) {
        super(id, nombre, salud, activo, costoConstruccion);

        if (energiaPorCiclo <= 0) {
            throw new IllegalArgumentException(
                "La energia generada por ciclo debe ser positiva."
            );
        }

        this.energiaPorCiclo = energiaPorCiclo;
        this.ciclosAcumulados = 0;
        this.totalGenerado = 0.0;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return "Operacion fallida: el modulo esta inactivo. "
                + "No se genero energia.";
        }
        mision.generarEnergia(energiaPorCiclo);
        ciclosAcumulados++;
        totalGenerado += energiaPorCiclo;

        return "Operacion exitosa."
            + "\nEnergia generada: " + energiaPorCiclo + " unidades"
            + "\nCiclos exitosos: " + ciclosAcumulados
            + "\nTotal historico generado: "
            + totalGenerado + " unidades";
    }

    @Override
    public String toString() {
        return super.toString()
            + "\nEnergia generada por ciclo: "
            + energiaPorCiclo + " unidades"
            + "\nCiclos exitosos acumulados: " + ciclosAcumulados
            + "\nTotal historico generado: "
            + totalGenerado + " unidades";
    }

    public double getEnergiaPorCiclo() {
        return energiaPorCiclo;
    }

    public int getCiclosAcumulados() {
        return ciclosAcumulados;
    }

    public double getTotalGenerado() {
        return totalGenerado;
    }
}