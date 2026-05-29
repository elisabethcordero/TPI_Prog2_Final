package interfazUsuario;

import java.util.ArrayList;
import java.util.List;
import servicios.PedidoService;
import servicios.ProductoService;
import servicios.UsuarioService;
import entidades.DetallePedido;
import entidades.Pedido;
import entidades.Producto;
import enumeraciones.Estado;
import enumeraciones.FormaPago;
import excepciones.StockInvalidoException;

public class MenuPedido extends MenuBase {

    @Override
    protected String getTitulo() {
        return "GESTIÓN DE PEDIDOS";
    }

    @Override
    protected void mostrarOpciones() {
        System.out.println("1. Crear Nuevo Pedido");
        System.out.println("2. Listar Pedidos");
        System.out.println("3. Editar Estado/Forma de Pago");
        System.out.println("4. Eliminar Pedido");
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
        if (UsuarioService.obtenerActivos().isEmpty() || ProductoService.obtenerActivos().isEmpty()) {
            System.out.println("Error: Se necesitan usuarios y productos activos.");
            return;
        }

        MenuUsuario menuUser = new MenuUsuario();
        menuUser.listar();
        System.out.print("ID del Usuario: ");
        Long idUser = Long.parseLong(leer.nextLine());
        entidades.Usuario usuario = UsuarioService.buscarPorId(idUser);
        if (usuario == null) {
            System.out.println("Error: Usuario no encontrado.");
            return;
        }

        System.out.println("Forma de Pago: 1.TARJETA, 2.TRANSFERENCIA, 3.EFECTIVO");
        int opPago = Integer.parseInt(leer.nextLine());
        FormaPago pago = (opPago == 1) ? FormaPago.TARJETA : (opPago == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO;

        // Creamos el objeto Pedido primero (sin guardarlo aún)
        Pedido nuevoPedido = new Pedido(0L, usuario, pago);
        List<Producto> productosAfectados = new ArrayList<>();
        List<Integer> cantidadesAfectadas = new ArrayList<>();

        String continuar = "s";
        MenuProducto menuProd = new MenuProducto();
        
        try {
            while (continuar.equalsIgnoreCase("s")) {
                menuProd.listar();
                System.out.print("ID del Producto: ");
                Long idProd = Long.parseLong(leer.nextLine());
                Producto prod = ProductoService.buscarPorId(idProd);

                if (prod != null) {
                    System.out.print("Cantidad: ");
                    int cant = Integer.parseInt(leer.nextLine());
                    if (cant > 0 && cant <= prod.getStock()) {
                        // REQUISITO: Usar obligatoriamente addDetallePedido de la clase Pedido
                        nuevoPedido.addDetallePedido(cant, prod.getPrecio(), prod);
                        
                        // Guardamos para revertir si falla luego
                        prod.setStock(prod.getStock() - cant);
                        productosAfectados.add(prod);
                        cantidadesAfectadas.add(cant);
                        
                        System.out.println("Producto agregado al pedido.");
                    } else {
                        throw new StockInvalidoException("Stock insuficiente para el producto: " + prod.getNombre());
                    }
                } else {
                    System.out.println("Producto no encontrado.");
                }
                System.out.print("¿Desea agregar otro producto? (s/n): ");
                continuar = leer.nextLine();
            }

            if (!nuevoPedido.getDetalles().isEmpty()) {
                // Si todo salió bien, persistimos el pedido
                PedidoService.guardarPedido(nuevoPedido);
                System.out.println("¡Pedido #" + nuevoPedido.getId() + " creado con éxito!");
            } else {
                System.out.println("Pedido cancelado: No se agregaron productos.");
            }

        } catch (Exception e) {
            // REQUISITO: Si hay excepción, cancelar la creación y evitar datos inconsistentes
            System.out.println("Error al crear el pedido: " + e.getMessage());
            System.out.println("Cancelando operación y restaurando stock...");
            
            // Restauramos el stock de lo que hayamos restado hasta el momento del error
            for (int i = 0; i < productosAfectados.size(); i++) {
                Producto p = productosAfectados.get(i);
                int cant = cantidadesAfectadas.get(i);
                p.setStock(p.getStock() + cant);
            }
        }
    }

    private void listar() {
        System.out.println("\n--- LISTADO DE PEDIDOS ---");
        var pedidos = PedidoService.obtenerActivos();
        
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados en el sistema.");
            return;
        }

        // Cumplimos con el requisito "Opcional" de filtrar por usuario (HU-PED-01)
        System.out.print("¿Desea filtrar por un usuario específico? (S/N): ");
        String filtro = leer.nextLine().trim().toUpperCase();
        
        Long idFiltro = -1L;
        if (filtro.equals("S")) {
            new MenuUsuario().listar();
            System.out.print("Ingrese el ID del usuario: ");
            try {
                idFiltro = Long.parseLong(leer.nextLine());
                if (servicios.UsuarioService.buscarPorId(idFiltro) == null) {
                    System.out.println("Error: No existe un usuario registrado con el ID " + idFiltro + ".");
                    return; // Salimos porque el cliente no existe
                }
            } catch (Exception e) {
                System.out.println("Error: El ID ingresado no es válido.");
                return;
            }
        }

        System.out.println("\n--- RESULTADO DE LA BÚSQUEDA ---");
        
        boolean encontro = false;
        for (Pedido p : pedidos) {
            // Si no hay filtro (-1) o el ID del usuario coincide, mostramos el pedido
            if (idFiltro == -1L || p.getUsuario().getId().equals(idFiltro)) {
                System.out.println(p); 
                // Mostramos los detalles de cada producto alineados a la izquierda
                for (DetallePedido d : p.getDetalles()) {
                    System.out.println("* " + d);
                }
                // REQUISITO: El total se muestra al final de los ítems con sangría
                System.out.println("\n   >>> TOTAL DEL PEDIDO: $" + String.format("%.2f", p.getTotal()));
                System.out.println("======================================================================\n");
                encontro = true;
            }
        }
        
        if (!encontro) {
            System.out.println("No se encontraron pedidos para el usuario seleccionado.");
        }
    }

    private void editar() {
        listar();
        if (PedidoService.obtenerActivos().isEmpty()) return;
        
        System.out.print("ID del Pedido a editar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Pedido p = PedidoService.buscarPorId(id);
            
            if (p != null) {
                System.out.println("1. Editar Estado, 2. Editar Forma de Pago");
                int op = Integer.parseInt(leer.nextLine());
                if (op == 1) {
                    System.out.println("1.PENDIENTE, 2.CONFIRMADO, 3.TERMINADO, 4.CANCELADO");
                    int st = Integer.parseInt(leer.nextLine());
                    Estado nuevo = (st == 1) ? Estado.PENDIENTE : (st == 2) ? Estado.CONFIRMADO : (st == 3) ? Estado.TERMINADO : Estado.CANCELADO;
                    PedidoService.cambiarEstado(id, nuevo);
                    System.out.println("Estado actualizado.");
                } else if (op == 2) {
                    System.out.println("1.TARJETA, 2.TRANSFERENCIA, 3.EFECTIVO");
                    int fp = Integer.parseInt(leer.nextLine());
                    FormaPago nueva = (fp == 1) ? FormaPago.TARJETA : (fp == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO;
                    PedidoService.cambiarFormaPago(id, nueva);
                    System.out.println("Forma de pago actualizada.");
                } else {
                    System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Error: ID inexistente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID ingresado no es válido (debe ser un número).");
        } catch (Exception e) {
            System.out.println("Error al editar.");
        }
    }

    private void eliminar() {
        System.out.print("ID Pedido a eliminar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            if (PedidoService.eliminar(id)) {
                System.out.println("Pedido eliminado con éxito.");
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
