package modelo;

public class EmpleadoPorHora extends Empleado {
    private double pagoPorHora;
    private double horasTrabajadas;

    // Invoca al constructor padre mediante super(...)
    public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre);
        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // Sobrescritura obligatoria de métodos abstractos
    @Override
    public double calcularPago() {
        return this.pagoPorHora * this.horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }

    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }
}