public abstract class Modulo {
    private int id;
    private String nombre;
    private int salud;
    private boolean activo;
    private double costoConstruccion;

    public Modulo(int id, String nombre, int salud, boolean activo, double costoConstruccion) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "El ID debe ser mayor que cero."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacio."
            );
        }

        if (salud < 0 || salud > 100) {
            throw new IllegalArgumentException(
                "La salud debe estar entre 0 y 100."
            );
        }

        if (costoConstruccion < 0) {
            throw new IllegalArgumentException(
                "El costo no puede ser negativo."
            );
        }

        this.id = id;
        this.nombre = nombre;
        this.salud = salud;
        this.activo = activo;
        this.costoConstruccion = costoConstruccion;
    }

    public abstract String procesarCiclo(Mision mision);

    public void activar() {
        if (salud > 0) {
            activo = true;
        }
    }

    public void desactivar() {
        activo = false;
    }

    @Override
    public String toString() {
        return "ID: " + id
            + "\nNombre: " + nombre
            + "\nSalud: " + salud + "/100"
            + "\nEstado: " + (activo ? "Activo" : "Inactivo")
            + "\nCosto de construccion: " + costoConstruccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "El ID debe ser mayor que cero."
            );
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacio."
            );
        }
        this.nombre = nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        if (salud < 0 || salud > 100) {
            throw new IllegalArgumentException(
                "La salud debe estar entre 0 y 100."
            );
        }
        this.salud = salud;
        if (salud == 0) {
            activo = false;
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        if (activo && salud == 0) {
            throw new IllegalStateException(
                "No se puede activar un modulo sin salud."
            );
        }
        this.activo = activo;
    }

    public double getCostoConstruccion() {
        return costoConstruccion;
    }

    public void setCostoConstruccion(double costoConstruccion) {
        if (costoConstruccion < 0) {
            throw new IllegalArgumentException(
                "El costo no puede ser negativo."
            );
        }
        this.costoConstruccion = costoConstruccion;
    }
}