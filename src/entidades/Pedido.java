package entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import enumeraciones.Estado;
import enumeraciones.FormaPago;


public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private double total;
    private Estado estado;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
    }

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
    }

    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) {
        Long idDetalle = (long) (detalles.size() + 1);
        DetallePedido nuevoDetalle = new DetallePedido(idDetalle, cantidad, producto);
        detalles.add(nuevoDetalle);
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(d -> d.getProducto() != null && d.getProducto().getId().equals(producto.getId()));
        calcularTotal();
    }

    @Override
    public void calcularTotal() {
        double suma = 0;
        for (DetallePedido dp : detalles) {
            suma += dp.getSubtotal();
        }
        this.total = suma;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    @Override
    public String toString() {
        String clienteInfo = (usuario != null) 
            ? "\nCLIENTE: " + usuario.getNombre() + " " + usuario.getApellido() + "\nCONTACTO: " + usuario.getMail()
            : "\nCLIENTE: Sin cliente asociado";
            
        return "======================================================================\n" +
               "PEDIDO ID: " + id + " | FECHA: " + fecha + 
               clienteInfo + "\n" +
               "ESTADO: " + estado + " | PAGO: " + formaPago + "\n" +
               "DETALLES DEL PEDIDO:";
    }
}
