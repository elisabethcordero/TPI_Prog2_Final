package entidades;

/**
 * Clase que representa un ítem dentro de un pedido.
 */
public class DetallePedido extends Base {
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido() {
        super();
    }

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    public Double calcularSubtotal() {
        if (producto != null) {
            return producto.getPrecio() * cantidad;
        }
        return 0.0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    @Override
    public String toString() {
        String nombreProd = (producto != null) ? producto.getNombre() : "Producto no asignado";
        return "- " + nombreProd + " x" + cantidad + " | Subtotal: $" + String.format("%.2f", subtotal);
    }
}
