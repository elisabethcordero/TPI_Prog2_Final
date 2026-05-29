
package interfazUsuario;

import java.util.Scanner;

/**
 * Clase base abstracta para centralizar la lógica de los menús.
 * Implementa el principio DRY (Don't Repeat Yourself) y el uso de Herencia.
 */
public abstract class MenuBase {
    public static final Scanner leer = new Scanner(System.in);

    /**
     * Título del menú (ej: "GESTIÓN DE CATEGORÍAS")
     */
    protected abstract String getTitulo();

    /**
     * Opciones a mostrar (ej: "1. Crear\n2. Listar")
     */
    protected abstract void mostrarOpciones();

    /**
     * Lógica para ejecutar la opción seleccionada.
     */
    protected abstract void ejecutarOpcion(int opcion);

    /**
     * Método principal que maneja el bucle y la captura de errores.
     */
    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n--- " + getTitulo() + " ---");
            mostrarOpciones();
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            
            try {
                String entrada = leer.nextLine();
                if (entrada.isEmpty()) continue;
                opcion = Integer.parseInt(entrada);
                
                if (opcion == 0) break;
                
                ejecutarOpcion(opcion);
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                opcion = -1;
            }
        } while (opcion != 0);
    }
}
