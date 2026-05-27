package servicios;

import config.Listas;
import entidades.Categoria;
import entidades.Producto;
import excepciones.EntidadNoEncontradaException;
import excepciones.PrecioInvalidoException;
import excepciones.StockInvalidoException;
import java.util.List;

public class ProductoService {

    public static void crear(String nombre, String descripcion, Double precio, int stock, String imagen, Long idCategoria, boolean disponible) 
            throws PrecioInvalidoException, StockInvalidoException, EntidadNoEncontradaException {
        
        if (precio < 0) throw new PrecioInvalidoException("El precio no puede ser negativo.");
        if (stock < 0) throw new StockInvalidoException("El stock no puede ser negativo.");
        
        Categoria cat = Listas.buscarCategoriaPorId(idCategoria);
        if (cat == null) throw new EntidadNoEncontradaException("La categoría seleccionada no existe.");

        Producto nuevo = new Producto(0L, nombre, precio, descripcion, stock, imagen, disponible, cat);
        Listas.agregarProducto(nuevo);
    }

    public static List<Producto> obtenerActivos() {
        return Listas.getProductosActivos();
    }

    public static Producto buscarPorId(Long id) {
        return Listas.buscarProductoPorId(id);
    }

    public static boolean editar(Long id, String nuevoNombre, String nuevoPrecioStr, String nuevoStockStr) {
        Producto p = Listas.buscarProductoPorId(id);
        if (p != null) {
            if (!nuevoNombre.isEmpty()) p.setNombre(nuevoNombre);
            
            if (!nuevoPrecioStr.isEmpty()) {
                double precio = Double.parseDouble(nuevoPrecioStr);
                if (precio >= 0) p.setPrecio(precio);
            }

            if (!nuevoStockStr.isEmpty()) {
                int stock = Integer.parseInt(nuevoStockStr);
                if (stock >= 0) p.setStock(stock);
            }
            return true;
        }
        return false;
    }

    public static boolean eliminar(Long id) {
        Producto p = Listas.buscarProductoPorId(id);
        if (p != null) {
            p.setEliminado(true);
            return true;
        }
        return false;
    }
}
