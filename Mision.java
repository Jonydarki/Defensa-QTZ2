import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Mision {
    private List<Modulo> modulos;
    private double energiaDisponible;
    private double datosEnOrbita;
    private double totalDatosDescargados;
    private int ciclosTranscurridos;

    public Mision() {
        modulos = new ArrayList<>();
        energiaDisponible = 10.0;
        datosEnOrbita = 0.0;
        totalDatosDescargados = 0.0;
        ciclosTranscurridos = 0;
        cargarDatosIniciales();
    }

    public final void cargarDatosIniciales() {
        modulos.clear();
        modulos.add(new ModuloEnergia(1, "Panel-S-01", 100, true, 5000.0, 20.0));

        modulos.add(new ModuloEnergia(2, "Panel-S-02", 100, true, 4500.0, 15.0));

        modulos.add(new ModuloEnergia(3, "Panel-S-03", 100, false, 4000.0, 10.0));

        modulos.add(new ModuloVuelo(4, "Camara-Orbital-01", 100, true, 8000.0, "Camara", 15.0, 8.0));

        modulos.add(new ModuloVuelo(5, "Sensor-Orbital-01", 100, true, 6500.0, "Sensor", 10.0, 5.0));

        modulos.add(new ModuloVuelo(6, "Camara-Orbital-02", 100, true, 7500.0, "Camara", 20.0, 12.0));

        modulos.add(new ModuloVuelo(7, "Sensor-Orbital-02", 100, false, 6000.0, "Sensor", 12.0, 6.0));

        modulos.add(new ModuloTierra(8, "Antena-Guatemala", 100, true, 9000.0, "Estacion-Guatemala", 10.0, 5.0));

        modulos.add(new ModuloTierra(9, "Antena-Peten", 100, true, 8500.0, "Estacion-Peten", 20.0, 7.0));

        modulos.add(new ModuloTierra(10, "Antena-Quetzaltenango", 100, false, 7000.0, "Estacion-Quetzaltenango", 15.0, 6.0));
    }

    public void agregarModulo(Modulo modulo) {
        if (modulo == null) {
            return;
        }

        for (Modulo existente : modulos) {
            if (existente.getId() == modulo.getId()) {
                throw new IllegalArgumentException(
                    "Ya existe un modulo con ese ID."
                );
            }
        }

        modulos.add(modulo);
    }

    public String avanzarCiclo() {
        StringBuilder informe = new StringBuilder();

        ciclosTranscurridos++;
        informe.append("\n========== CICLO ");
        informe.append(ciclosTranscurridos);
        informe.append(" ==========\n");

        for (Modulo modulo : modulos) {
            informe.append("\nModulo: ");
            informe.append(modulo.getNombre());
            informe.append("\n");
            informe.append(modulo.procesarCiclo(this));
            informe.append("\n");
        }

        informe.append("\n========== ESTADO FINAL ==========\n");
        informe.append("Energia disponible: ");
        informe.append(energiaDisponible);
        informe.append(" unidades\n");
        informe.append("Datos pendientes en orbita: ");
        informe.append(datosEnOrbita);
        informe.append(" MB\n");
        informe.append("Total historico descargado: ");
        informe.append(totalDatosDescargados);
        informe.append(" MB\n");
        return informe.toString();
    }

    public Modulo buscarPorId(int id) {
        for (Modulo modulo : modulos) {
            if (modulo.getId() == id) {
                return modulo;
            }
        }

        return null;
    }

    public Modulo buscarPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        for (Modulo modulo : modulos) {
            if (modulo.getNombre().equalsIgnoreCase(nombre.trim())) {
                return modulo;
            }
        }

        return null;
    }

    public List<Modulo> listarModulos() {
        return new ArrayList<>(modulos);
    }

    public List<Modulo> ordenarPorCosto() {
        List<Modulo> catalogo = new ArrayList<>(modulos);
        catalogo.sort(
            Comparator.comparingDouble(Modulo::getCostoConstruccion)
        );
        return catalogo;
    }

    public boolean consumirEnergia(double cantidad) {
        if (cantidad < 0 || cantidad > energiaDisponible) {
            return false;
        }

        energiaDisponible -= cantidad;
        return true;
    }

    public void generarEnergia(double cantidad) {
        if (cantidad > 0) {
            energiaDisponible += cantidad;
        }
    }

    public void agregarDatosOrbita(double cantidad) {
        if (cantidad > 0) {
            datosEnOrbita += cantidad;
        }
    }

    public double retirarDatosOrbita(double cantidad) {
        if (cantidad <= 0) {
            return 0.0;
        }
        double retirada = Math.min(cantidad, datosEnOrbita);
        datosEnOrbita -= retirada;
        totalDatosDescargados += retirada;
        return retirada;
    }

    public String generarResumenComunicaciones() {
        StringBuilder resumen = new StringBuilder();
        int totalModulosTierra = 0;
        int modulosTierraActivos = 0;
        double capacidadTotal = 0.0;
        double maximoDescargado = -1.0;

        List<ModuloTierra> modulosConMayorDescarga =
            new ArrayList<>();

        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra) {
                ModuloTierra tierra = (ModuloTierra) modulo;

                totalModulosTierra++;

                if (tierra.isActivo()) {
                    modulosTierraActivos++;
                    capacidadTotal += tierra.getCapacidadDescarga();
                }

                double descargado = tierra.getTotalDescargado();

                if (descargado > maximoDescargado) {
                    maximoDescargado = descargado;
                    modulosConMayorDescarga.clear();
                    modulosConMayorDescarga.add(tierra);
                } else if (Double.compare(descargado, maximoDescargado) == 0) {
                    modulosConMayorDescarga.add(tierra);
                }
            }
        }

        resumen.append("Total de modulos de tierra: ");
        resumen.append(totalModulosTierra);
        resumen.append("\nModulos activos: ");
        resumen.append(modulosTierraActivos);
        resumen.append("\nCapacidad total de descarga por ciclo: ");
        resumen.append(capacidadTotal);
        resumen.append(" MB\nTotal historico descargado por la mision: ");
        resumen.append(totalDatosDescargados);
        resumen.append(" MB\n");
        resumen.append("\nModulo(s) con mayor descarga historica:\n");

        if (modulosConMayorDescarga.isEmpty()) {
            resumen.append("No hay modulos de tierra registrados.\n");
        } else {
            for (ModuloTierra tierra : modulosConMayorDescarga) {
                resumen.append("Nombre: ");
                resumen.append(tierra.getNombre());
                resumen.append(" | ID: ");
                resumen.append(tierra.getId());
                resumen.append(" | Estacion: ");
                resumen.append(tierra.getEstacion());
                resumen.append(" | Total descargado: ");
                resumen.append(tierra.getTotalDescargado());
                resumen.append(" MB\n");
            }
        }

        return resumen.toString();
    }

    public double getEnergiaDisponible() {
        return energiaDisponible;
    }

    public double getDatosEnOrbita() {
        return datosEnOrbita;
    }

    public double getTotalDatosDescargados() {
        return totalDatosDescargados;
    }

    public int getCiclosTranscurridos() {
        return ciclosTranscurridos;
    }
}

//Nota: Modifique el nombre de la clase y los .append