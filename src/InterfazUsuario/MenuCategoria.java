package interfazUsuario;

import servicios.CategoriaService;
import entidades.Categoria;
import static interfazUsuario.MenuBase.leer;

public class MenuCategoria extends MenuBase {

    @Override
    protected String getTitulo() {
        return "GESTIÓN DE CATEGORÍAS";
    }

    @Override
    protected void mostrarOpciones() {
        System.out.println("1. Crear Categoría");
        System.out.println("2. Listar Categorías");
        System.out.println("3. Editar Categoría");
        System.out.println("4. Eliminar Categoría");
    }

    @Override
    protected void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1: crear(); break;
            case 2: listar(); break;
            case 3: editar(); break;
            case 4: eliminar(); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private void crear() {
        String nombre;
        do {
            System.out.print("Nombre de la categoría: ");
            nombre = leer.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
            } else if (CategoriaService.existeNombre(nombre)) {
                System.out.println("Error: Ya existe una categoría con ese nombre. Por favor, ingrese un nombre diferente.");
                nombre = ""; // Vaciamos para que el bucle vuelva a pedirlo
            }
        } while (nombre.isEmpty());

        System.out.print("Descripción: ");
        String desc = leer.nextLine().trim();
        if (desc.isEmpty()) desc = "Sin descripción";
        
        if (CategoriaService.crear(nombre, desc)) {
            System.out.println("¡Categoría creada con éxito!");
        } else {
            System.out.println("Error: No se pudo crear la categoría.");
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE CATEGORÍAS ACTIVAS ---");
        var activas = CategoriaService.obtenerActivas();
        if (activas.isEmpty()) {
            System.out.println("No hay categorías registradas.");
        } else {
            for (Categoria c : activas) System.out.println(c);
        }
    }

    private void editar() {
        listar();
        if (CategoriaService.obtenerActivas().isEmpty()) return;
        
        System.out.print("ID de la categoría a editar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Categoria c = CategoriaService.buscarPorId(id);
            if (c != null) {
                System.out.print("Nuevo nombre (actual: " + c.getNombre() + "): ");
                String nombre = leer.nextLine().trim();
                System.out.print("Nueva descripción (actual: " + c.getDescripcion() + "): ");
                String desc = leer.nextLine().trim();
                
                if (CategoriaService.editar(id, nombre, desc)) {
                    System.out.println("Categoría actualizada.");
                }
            } else {
                System.out.println("Categoría no encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Error al editar.");
        }
    }

    private void eliminar() {
        listar();
        if (CategoriaService.obtenerActivas().isEmpty()) return;
        
        System.out.print("Seleccione el ID de la categoría a eliminar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Categoria c = CategoriaService.buscarPorId(id);
            if (c != null) {
                String confirmacion;
                do {
                    System.out.print("¿Seguro que desea eliminar '" + c.getNombre() + "'? (S/N): ");
                    confirmacion = leer.nextLine().trim().toUpperCase();
                    if (confirmacion.equals("S")) {
                        if (CategoriaService.eliminar(id)) {
                            System.out.println("Categoría eliminada.");
                        } else {
                            System.out.println("Error: No se puede eliminar la categoría porque tiene productos asociados.");
                        }
                    } else if (confirmacion.equals("N")) {
                        System.out.println("Eliminación cancelada.");
                    } else {
                        System.out.println("Error: Por favor, ingrese 'S' para sí o 'N' para no.");
                    }
                } while (!confirmacion.equals("S") && !confirmacion.equals("N"));
            } else {
                System.out.println("Error: ID inexistente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID ingresado no es válido (debe ser un número).");
        } catch (Exception e) {
            System.out.println("Error al eliminar.");
        }
    }
}
