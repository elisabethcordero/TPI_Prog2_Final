
package entidades;

/**
 * Clase que representa las categorías de los productos.
 */
public class Categoria extends Base {
    private String nombre;
    private String descripcion;

    public Categoria() {
        super();
        this.nombre = "Sin nombre";
        this.descripcion = "Sin descripción";
    }

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " - " + nombre + " (" + descripcion + ")";
    }
}
