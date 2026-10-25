package modelo;

public class EmpleadoFijo extends Empleado {
    private double sueldoMensual;
    private double bonificacion;

    // Invoca al constructor padre mediante super(...)
    public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion) {
        super(dpi, nombre);
        this.sueldoMensual = sueldoMensual;
        this.bonificacion = bonificacion;
    }

    // Sobrescritura obligatoria de métodos abstractos
    @Override
    public double calcularPago() {
        return this.sueldoMensual + this.bonificacion;
    }

    @Override
    public String obtenerTipo() {
        return "Fijo";
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public double getBonificacion() {
        return bonificacion;
    }
}