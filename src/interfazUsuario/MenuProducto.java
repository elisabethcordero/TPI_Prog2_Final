package interfazUsuario;

import servicios.ProductoService;
import servicios.CategoriaService;
import entidades.Producto;
import excepciones.EntidadNoEncontradaException;

public class MenuProducto extends MenuBase {

    @Override
    protected String getTitulo() {
        return "GESTIÓN DE PRODUCTOS";
    }

    @Override
    protected void mostrarOpciones() {
        System.out.println("1. Crear Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Editar Producto");
        System.out.println("4. Eliminar Producto");
    }

    @Override
    protected void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1: crear(); break;
                case 2: listar(); break;
                case 3: editar(); break;
                case 4: eliminar(); break;
                default: System.out.println("Opción no válida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crear() throws Exception {
        if (CategoriaService.obtenerActivas().isEmpty()) {
            throw new EntidadNoEncontradaException("Debe crear al menos una categoría primero.");
        }

        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = leer.nextLine().trim();
            if (nombre.isEmpty()) System.out.println("Error: El nombre es obligatorio.");
        } while (nombre.isEmpty());

        System.out.print("Descripción: ");
        String desc = leer.nextLine().trim();
        if (desc.isEmpty()) desc = "Sin descripción";

        Double precio = 0.0;
        boolean precioValido = false;
        do {
            try {
                System.out.print("Precio: ");
                precio = Double.parseDouble(leer.nextLine());
                if (precio < 0) {
                    System.out.println("Error: El precio no puede ser negativo.");
                } else {
                    precioValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un precio válido (solo números y punto para decimales).");
            }
        } while (!precioValido);

        int stock = 0;
        boolean stockValido = false;
        do {
            try {
                System.out.print("Stock: ");
                stock = Integer.parseInt(leer.nextLine());
                if (stock < 0) {
                    System.out.println("Error: El stock no puede ser negativo.");
                } else {
                    stockValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero para el stock.");
            }
        } while (!stockValido);

        System.out.print("Imagen: "); String img = leer.nextLine().trim();
        if (img.isEmpty()) img = "Sin imagen";

        System.out.println("\n--- SELECCIÓN DE CATEGORÍA ---");
        System.out.println("A continuación, elija el ID de la categoría a la que pertenece el producto:");
        MenuCategoria menuCat = new MenuCategoria();
        menuCat.listar();
        
        Long idCat = 0L;
        boolean idValido = false;
        do {
            try {
                System.out.print("Ingrese ID de la Categoría: ");
                idCat = Long.parseLong(leer.nextLine());
                if (CategoriaService.buscarPorId(idCat) == null) {
                    System.out.println("Error: No existe una categoría con ese ID.");
                } else {
                    idValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un ID numérico válido.");
            }
        } while (!idValido);

        // Estado de disponibilidad
        System.out.print("¿Habilitar producto para la venta inmediata? (S/N): ");
        boolean disponible = leer.nextLine().trim().equalsIgnoreCase("S");

        ProductoService.crear(nombre, desc, precio, stock, img, idCat, disponible);
        System.out.println("¡Producto creado con éxito!");
    }

    public void listar() {
        System.out.println("\n--- LISTA DE PRODUCTOS ACTIVOS ---");
        var activos = ProductoService.obtenerActivos();
        if (activos.isEmpty()) {
            System.out.println("No hay productos.");
        } else {
            for (Producto p : activos) System.out.println(p);
        }
    }

    private void editar() {
        listar();
        if (ProductoService.obtenerActivos().isEmpty()) return;
        
        System.out.print("ID del producto a editar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Producto p = ProductoService.buscarPorId(id);
            if (p != null) {
                System.out.print("Nuevo nombre (actual: " + p.getNombre() + "): ");
                String nombre = leer.nextLine().trim();
                System.out.print("Nuevo precio: ");
                String precioStr = leer.nextLine().trim();
                System.out.print("Nuevo stock: ");
                String stockStr = leer.nextLine().trim();

                if (ProductoService.editar(id, nombre, precioStr, stockStr)) {
                    System.out.println("Producto actualizado.");
                }
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al editar.");
        }
    }

    private void eliminar() {
        listar();
        if (ProductoService.obtenerActivos().isEmpty()) return;
        
        System.out.print("ID del producto a eliminar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Producto p = ProductoService.buscarPorId(id);
            if (p != null) {
                System.out.print("¿Desea eliminar '" + p.getNombre() + "'? (S/N): ");
                if (leer.nextLine().equalsIgnoreCase("S")) {
                    ProductoService.eliminar(id);
                    System.out.println("Producto eliminado.");
                }
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
