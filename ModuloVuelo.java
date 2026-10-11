public class ModuloVuelo extends Modulo {

    private String tipoInstrumento;
    private double datosPorCiclo;
    private double consumoEnergia;
    private int ciclosAcumulados;
    private double totalRecolectado;

    public ModuloVuelo(
        int id,
        String nombre,
        int salud,
        boolean activo,
        double costoConstruccion,
        String tipoInstrumento,
        double datosPorCiclo,
        double consumoEnergia
    ) {
        super(id, nombre, salud, activo, costoConstruccion);

        if (tipoInstrumento == null ||
            (!tipoInstrumento.equalsIgnoreCase("Camara") &&
             !tipoInstrumento.equalsIgnoreCase("Sensor"))) {
            throw new IllegalArgumentException(
                "El instrumento debe ser Camara o Sensor."
            );
        }

        if (datosPorCiclo <= 0 || consumoEnergia < 0) {
            throw new IllegalArgumentException(
                "Los datos deben ser positivos y el consumo no negativo."
            );
        }

        this.tipoInstrumento = tipoInstrumento;
        this.datosPorCiclo = datosPorCiclo;
        this.consumoEnergia = consumoEnergia;
        this.ciclosAcumulados = 0;
        this.totalRecolectado = 0.0;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return "Operacion fallida: el modulo esta inactivo. "
                + "No se recolectaron datos ni se consumio energia.";
        }

        if (mision.getEnergiaDisponible() < consumoEnergia) {
            return "Operacion fallida: energia insuficiente. "
                + "Se necesitan " + consumoEnergia
                + " unidades de energia. No se recolectaron datos.";
        }

        if (!mision.consumirEnergia(consumoEnergia)) {
            return "Operacion fallida: no fue posible consumir energia.";
        }

        mision.agregarDatosOrbita(datosPorCiclo);
        ciclosAcumulados++;
        totalRecolectado += datosPorCiclo;

        return "Operacion exitosa."
            + "\nInstrumento: " + tipoInstrumento
            + "\nDatos recolectados: " + datosPorCiclo + " MB"
            + "\nEnergia consumida: " + consumoEnergia
            + "\nCiclos exitosos: " + ciclosAcumulados
            + "\nTotal historico recolectado: "
            + totalRecolectado + " MB";
    }

    @Override
    public String toString() {
        return super.toString()
            + "\nTipo de instrumento: " + tipoInstrumento
            + "\nDatos por ciclo: " + datosPorCiclo + " MB"
            + "\nConsumo de energia por ciclo: " + consumoEnergia
            + "\nCiclos exitosos acumulados: " + ciclosAcumulados
            + "\nTotal recolectado: " + totalRecolectado + " MB";
    }

    public String getTipoInstrumento() {
        return tipoInstrumento;
    }

    public double getDatosPorCiclo() {
        return datosPorCiclo;
    }

    public double getConsumoEnergia() {
        return consumoEnergia;
    }

    public int getCiclosAcumulados() {
        return ciclosAcumulados;
    }

    public double getTotalRecolectado() {
        return totalRecolectado;
    }
}