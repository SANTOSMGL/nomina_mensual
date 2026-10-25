package modelo;

public abstract class Empleado {
    // Atributos privados (Encapsulamiento)
    private String dpi;
    private String nombre;

    // Constructor con parámetros (Uso de 'this' para evitar ambigüedad)
    public Empleado(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    // Métodos abstractos (Abstracción y Polimorfismo)
    public abstract double calcularPago();
    public abstract String obtenerTipo();

    // Getters de consulta
    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }
}