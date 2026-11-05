package despues.componentes;

import despues.modelo.Pedido;
import despues.modelo.TipoEvento;

/**
 * Servicio encargado de la logística y despacho de pedidos.
 * No tiene relación directa con el servicio de notificaciones.
 */
public class ServicioEnvios extends ComponenteServicio {

    public void programarEnvio(Pedido pedido) {
        String numeroGuia = "GUIA-" + (int)(Math.random() * 90000 + 10000);
        System.out.println("🚚 [Envíos] Generando despacho para " + pedido.getCliente() + 
                           " | Producto: " + pedido.getProducto() + " x" + pedido.getCantidad() + 
                           " | Guía: " + numeroGuia);

        // Notifica al mediador para que él decida cómo informar al cliente
        mediador.notificar(this, TipoEvento.ENVIO_PROGRAMADO, new EnvioInfo(pedido, numeroGuia));
    }

    /**
     * DTO auxiliar para transportar la información del envío.
     */
    public static class EnvioInfo {
        private final Pedido pedido;
        private final String guia;

        public EnvioInfo(Pedido pedido, String guia) {
            this.pedido = pedido;
            this.guia = guia;
        }

        public Pedido getPedido() { return pedido; }
        public String getGuia() { return guia; }
    }
}

