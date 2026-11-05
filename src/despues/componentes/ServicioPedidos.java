package despues.componentes;

import despues.modelo.Pedido;
import despues.modelo.TipoEvento;

/**
 * Punto de entrada para el registro de pedidos.
 * Solo notifica el inicio de la orden al Mediador.
 */
public class ServicioPedidos extends ComponenteServicio {

    public void registrarPedido(Pedido pedido) {
        System.out.println("================================================================================");
        System.out.println("🛒 [Pedidos] Nueva solicitud recibida: Pedido #" + pedido.getId() + 
                           " | Cliente: " + pedido.getCliente() + 
                           " | Producto: " + pedido.getProducto() + 
                           " | Monto: $" + pedido.getMonto());
        System.out.println("================================================================================");

        // Notifica al mediador el inicio del flujo
        mediador.notificar(this, TipoEvento.PEDIDO_INICIADO, pedido);
    }
}

