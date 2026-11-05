package despues.componentes;

import despues.modelo.Pedido;
import despues.modelo.TipoEvento;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado del stock de productos.
 * No sabe qué otros servicios existen; solo interactúa con el Mediador.
 */
public class ServicioInventario extends ComponenteServicio {

    private Map<String, Integer> stock = new HashMap<>();

    public ServicioInventario() {
        stock.put("Laptop Pro", 5);
        stock.put("Mouse Inalámbrico", 10);
        stock.put("Teclado Mecánico", 0); // Agotado
    }

    public void verificarYReservar(Pedido pedido) {
        int disponible = stock.getOrDefault(pedido.getProducto(), 0);

        if (disponible >= pedido.getCantidad()) {
            stock.put(pedido.getProducto(), disponible - pedido.getCantidad());
            System.out.println("📦 [Inventario] Reservadas " + pedido.getCantidad() + 
                               " unidades de '" + pedido.getProducto() + "'. Restante: " + stock.get(pedido.getProducto()));
            // Notifica al mediador que el stock está reservado con éxito
            mediador.notificar(this, TipoEvento.STOCK_DISPONIBLE, pedido);
        } else {
            System.out.println("❌ [Inventario] Stock insuficiente para '" + pedido.getProducto() + 
                               "'. Solicitado: " + pedido.getCantidad() + " | Disponible: " + disponible);
            // Notifica al mediador que no hay stock
            mediador.notificar(this, TipoEvento.STOCK_INSUFICIENTE, pedido);
        }
    }

    public void liberarStock(String producto, int cantidad) {
        int actual = stock.getOrDefault(producto, 0);
        stock.put(producto, actual + cantidad);
        System.out.println("🔄 [Inventario] Rollback: Se liberaron " + cantidad + " unidades de '" + producto + 
                           "'. Stock restaurado: " + stock.get(producto));
    }
}

