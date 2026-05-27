
import interfazUsuario.MenuBase;
import interfazUsuario.MenuCategoria;
import interfazUsuario.MenuProducto;
import interfazUsuario.MenuUsuario;
import interfazUsuario.MenuPedido;

/**
 * Alumnas: Nadia Garcia. Analia Franco. Elisabeth Cordero Campero.
 */
public class Main {

    public static void main(String[] args) {

        // --- DATOS PRECARGADOS PARA PRUEBAS (Seed Data) ---
        // 1. Crear Usuarios
        try {
            servicios.UsuarioService.crear("Juan", "Perez", "juan@mail.com", "11223344", "pass123", enumeraciones.Rol.USUARIO);
            servicios.UsuarioService.crear("Maria", "Gomez", "maria@mail.com", "55667788", "admin123", enumeraciones.Rol.ADMIN);
            servicios.UsuarioService.crear("Carlos", "Lopez", "carlos@mail.com", "99001122", "pass456", enumeraciones.Rol.USUARIO);
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios de prueba: " + e.getMessage());
        }

        // 2. Crear Categorías
        servicios.CategoriaService.crear("Pizzas", "Pizzas a la piedra en horno de barro");
        servicios.CategoriaService.crear("Bebidas", "Gaseosas, aguas y cervezas");
        servicios.CategoriaService.crear("Postres", "Postres caseros y helados");

        // 3. Crear Productos
        try {
            // Nota: Se pasa el ID de la categoría (1L, 2L, 3L)que acabamos de crear arriba.
            servicios.ProductoService.crear("Muzzarella", "Clásica con aceitunas", 12000.0, 50, "muzza.jpg", 1L, true);
            servicios.ProductoService.crear("Coca-Cola 1.5L", "Gaseosa línea Coca-Cola", 2500.0, 100, "coca.jpg", 2L, true);
            servicios.ProductoService.crear("Tiramisú", "Postre italiano con café", 4500.0, 20, "tiramisu.jpg", 3L, true);
        } catch (Exception e) {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
        }

        // 4. Crear Pedidos (Seed Data)
        try {
            // Pedido 1: Juan Perez compra Pizza y Coca
            entidades.Usuario juan = servicios.UsuarioService.buscarPorId(1L);
            entidades.Pedido p1 = new entidades.Pedido(0L, juan, enumeraciones.FormaPago.TARJETA);
            p1.addDetallePedido(2, 12000.0, servicios.ProductoService.buscarPorId(1L)); // 2 Pizzas
            p1.addDetallePedido(1, 2500.0, servicios.ProductoService.buscarPorId(2L)); // 1 Coca
            servicios.PedidoService.guardarPedido(p1);

            // Pedido 2: Carlos Lopez compra Postre
            entidades.Usuario carlos = servicios.UsuarioService.buscarPorId(3L);
            entidades.Pedido p2 = new entidades.Pedido(0L, carlos, enumeraciones.FormaPago.EFECTIVO);
            p2.addDetallePedido(3, 4500.0, servicios.ProductoService.buscarPorId(3L)); // 3 Tiramisú
            servicios.PedidoService.guardarPedido(p2);
        } catch (Exception e) {
            System.out.println("Error al cargar pedidos de prueba: " + e.getMessage());
        }

        // Instanciamos los menús una sola vez para reutilizarlos
        MenuCategoria menuCat = new MenuCategoria();
        MenuProducto menuProd = new MenuProducto();
        MenuUsuario menuUser = new MenuUsuario();
        MenuPedido menuPed = new MenuPedido();

        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA FOOD STORE ---");
            System.out.println("1. Gestión de Categorías");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("4. Gestión de Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(MenuBase.leer.nextLine());

                switch (opcion) {
                    case 1:
                        menuCat.mostrarMenu();
                        break;
                    case 2:
                        menuProd.mostrarMenu();
                        break;
                    case 3:
                        menuUser.mostrarMenu();
                        break;
                    case 4:
                        menuPed.mostrarMenu();
                        break;
                    case 0:
                        System.out.println("¡Saliendo del sistema! ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);
    }
}
