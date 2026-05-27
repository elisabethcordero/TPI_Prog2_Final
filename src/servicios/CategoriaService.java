package servicios;

import config.Listas;
import entidades.Categoria;
import java.util.List;

public class CategoriaService {

    public static boolean crear(String nombre, String descripcion) {
        Categoria nueva = new Categoria(0L, nombre, descripcion);
        return Listas.agregarCategoria(nueva);
    }

    public static boolean existeNombre(String nombre) {
        return Listas.existeCategoria(nombre);
    }

    public static List<Categoria> obtenerActivas() {
        return Listas.getCategoriasActivas();
    }

    public static Categoria buscarPorId(Long id) {
        return Listas.buscarCategoriaPorId(id);
    }

    public static boolean editar(Long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria c = Listas.buscarCategoriaPorId(id);
        if (c != null) {
            if (!nuevoNombre.isEmpty()) c.setNombre(nuevoNombre);
            if (!nuevaDescripcion.isEmpty()) c.setDescripcion(nuevaDescripcion);
            return true;
        }
        return false;
    }

    public static boolean eliminar(Long id) {
        // REGLA: No eliminar si tiene productos asociados (HU-CAT-04)
        for (entidades.Producto p : Listas.getProductosActivos()) {
            if (p.getCategoria().getId().equals(id)) {
                return false; // No se puede eliminar
            }
        }
        
        Categoria c = Listas.buscarCategoriaPorId(id);
        if (c != null) {
            c.setEliminado(true);
            return true;
        }
        return false;
    }
}
