
package config;

import java.util.ArrayList;
import java.util.List;
import entidades.Categoria;
import entidades.Producto;
import entidades.Usuario;
import entidades.Pedido;

/**
 * Clase encargada de centralizar el almacenamiento en memoria.
 */

public class Listas {
    private static List<Categoria> categorias = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    
    private static Long proximoIdCategoria = 1L;
    private static Long proximoIdProducto = 1L;
    private static Long proximoIdUsuario = 1L;
    private static Long proximoIdPedido = 1L;

    // --- MÉTODOS PARA CATEGORÍAS ---
    public static boolean agregarCategoria(Categoria categoria) {
        // Validar que el nombre sea único (Regla de negocio HU-CAT-02)
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(categoria.getNombre()) && !c.isEliminado()) {
                return false; 
            }
        }
        categoria.setId(proximoIdCategoria++);
        categorias.add(categoria);
        return true;
    }

    public static List<Categoria> getCategoriasActivas() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return activas;
    }

    public static Categoria buscarCategoriaPorId(Long id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        return null;
    }

    public static boolean existeCategoria(String nombre) {
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                return true;
            }
        }
        return false;
    }

    // --- MÉTODOS PARA PRODUCTOS ---
    public static void agregarProducto(Producto producto) {
        producto.setId(proximoIdProducto++);
        productos.add(producto);
    }

    public static List<Producto> getProductosActivos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public static Producto buscarProductoPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }

    // --- MÉTODOS PARA USUARIOS ---
    public static boolean agregarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(usuario.getMail())) {
                return false; 
            }
        }
        usuario.setId(proximoIdUsuario++);
        usuarios.add(usuario);
        return true;
    }

    public static List<Usuario> getUsuariosActivos() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    public static Usuario buscarUsuarioPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        return null;
    }

    // --- MÉTODOS PARA PEDIDOS ---
    public static void agregarPedido(Pedido pedido) {
        pedido.setId(proximoIdPedido++);
        pedidos.add(pedido);
    }

    public static List<Pedido> getPedidosActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public static Pedido buscarPedidoPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }
}
