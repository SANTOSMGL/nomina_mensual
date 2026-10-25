package aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {

    // Atributos privados de clase
    private static final String[] MESES = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static ArrayList<Empleado> empleados = new ArrayList<>();
    private static int numeroMes = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Asegurar que use punto decimal en vez de coma
        Locale.setDefault(Locale.US);

        seleccionarMes();

        boolean salir = false;
        while (!salir) {
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + MESES[numeroMes - 1].toUpperCase());
            System.out.println("1 - Ingreso de nómina");
            System.out.println("2 - Impresión de nómina");
            System.out.println("3 - Salir");
            int opcion = leerEnteroEnRango("Seleccione una opción: ", 1, 3);

            switch (opcion) {
                case 1:
                    menuIngresoNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.println("Gracias por utilizar el sistema de nómina. ¡Hasta pronto!");
                    salir = true;
                    break;
            }
        }
        scanner.close();
    }

    // -------------------------------------------------------------
    // Métodos de flujo de menús
    // -------------------------------------------------------------

    private static void seleccionarMes() {
        limpiarPantalla();
        System.out.println("CÁLCULO DE NÓMINA");
        numeroMes = leerEnteroEnRango("Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

    private static void menuIngresoNomina() {
        boolean continuarIngreso = true;
        while (continuarIngreso) {
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + MESES[numeroMes - 1].toUpperCase());
            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            int tipo = leerEnteroEnRango("Seleccione el tipo de empleado: ", 1, 2);

            if (tipo == 1) {
                registrarEmpleadoFijo();
            } else {
                registrarEmpleadoPorHora();
            }

            System.out.println("\nEmpleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");
            int decision = leerEnteroEnRango("Seleccione una opción: ", 1, 2);

            if (decision == 2) {
                continuarIngreso = false;
            }
        }
    }

    private static void registrarEmpleadoFijo() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + MESES[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO FIJO");

        String dpi = leerDPI();
        String nombre = leerTextoNoVacio("Nombre: ");
        double sueldo = leerDoubleMayorOIgualCero("Sueldo mensual (Q): ");
        double bonificacion = leerDoubleMayorOIgualCero("Bonificación de este mes (Q): ");

        EmpleadoFijo emp = new EmpleadoFijo(dpi, nombre, sueldo, bonificacion);
        empleados.add(emp);

        System.out.println("Pago del mes = sueldo mensual + bonificación");
        System.out.printf("Pago de %s = Q %.2f\n", emp.getNombre(), emp.calcularPago());
    }

    private static void registrarEmpleadoPorHora() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + MESES[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO POR HORA");

        String dpi = leerDPI();
        String nombre = leerTextoNoVacio("Nombre: ");
        double pagoPorHora = leerDoubleMayorOIgualCero("Pago por hora (Q): ");
        double horas = leerDoubleMayorOIgualCero("Horas trabajadas este mes: ");

        EmpleadoPorHora emp = new EmpleadoPorHora(dpi, nombre, pagoPorHora, horas);
        empleados.add(emp);

        System.out.println("Pago del mes = pago por hora x horas trabajadas");
        System.out.printf("Pago de %s = Q %.2f\n", emp.getNombre(), emp.calcularPago());
    }

    private static void imprimirNomina() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + MESES[numeroMes - 1].toUpperCase());

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes.");
            System.out.println("Total de trabajadores: 0");
            System.out.println("Total de nómina: Q 0.00");
        } else {
            System.out.printf("%-15s %-20s %-12s %-12s\n", "DPI", "Nombre", "Tipo", "Pago (Q)");
            double totalNomina = 0.0;

            // Polimorfismo en acción: recorremos con tipo Empleado
            for (Empleado emp : empleados) {
                double pago = emp.calcularPago(); // Cada subclase ejecuta su propia fórmula
                totalNomina += pago;
                System.out.printf("%-15s %-20s %-12s %10.2f\n",
                        emp.getDpi(),
                        emp.getNombre(),
                        emp.obtenerTipo(),
                        pago);
            }

            System.out.println("\nTotal de trabajadores: " + empleados.size());
            System.out.printf("Total de nómina: Q %.2f\n", totalNomina);
        }

        System.out.print("\nPresione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    // -------------------------------------------------------------
    // Métodos auxiliares y validaciones
    // -------------------------------------------------------------

    private static void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    private static int leerEnteroEnRango(String mensaje, int min, int max) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.printf("Error: Ingrese una opción entre %d y %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDoubleMayorOIgualCero(String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor >= 0.0) {
                    return valor;
                } else {
                    System.out.println("Error: El valor debe ser un número finito mayor o igual a 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido (ej. 25 o 25.50).");
            }
        }
    }

    private static String leerDPI() {
        while (true) {
            System.out.print("DPI: ");
            String dpi = scanner.nextLine().trim();
            if (dpi.matches("\\d{13}")) {
                return dpi;
            } else {
                System.out.println("Error: El DPI debe contener exactamente 13 dígitos numéricos.");
            }
        }
    }

    private static String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            } else {
                System.out.println("Error: El campo no puede estar vacío ni contener solo espacios.");
            }
        }
    }
}