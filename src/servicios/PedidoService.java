package servicios;

import config.Listas;
import entidades.Pedido;
import enumeraciones.Estado;
import enumeraciones.FormaPago;
import java.util.List;

public class PedidoService {

    public static void guardarPedido(Pedido p) {
        Listas.agregarPedido(p);
    }

    public static List<Pedido> obtenerActivos() {
        return Listas.getPedidosActivos();
    }

    public static Pedido buscarPorId(Long id) {
        return Listas.buscarPedidoPorId(id);
    }

    public static boolean cambiarEstado(Long idPedido, Estado nuevoEstado) {
        for (Pedido p : Listas.getPedidosActivos()) {
            if (p.getId().equals(idPedido)) {
                p.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }

    public static boolean cambiarFormaPago(Long idPedido, FormaPago nuevaForma) {
        for (Pedido p : Listas.getPedidosActivos()) {
            if (p.getId().equals(idPedido)) {
                p.setFormaPago(nuevaForma);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminar(Long idPedido) {
        for (Pedido p : Listas.getPedidosActivos()) {
            if (p.getId().equals(idPedido)) {
                p.setEliminado(true);
                return true;
            }
        }
        return false;
    }
}
