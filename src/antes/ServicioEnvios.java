package antes;

/**
 * Servicio encargado de la logística y despacho de paquetes.
 */
public class ServicioEnvios {

    // ⚠️ ACOPLAMIENTO DIRECTO: Envíos depende directamente de Notificaciones
    private ServicioNotificaciones notificaciones;

    public ServicioEnvios(ServicioNotificaciones notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void programarEnvio(Pedido pedido) {
        String numeroGuia = "GUIA-" + (int)(Math.random() * 90000 + 10000);
        System.out.println("🚚 [Envíos] Generando despacho para " + pedido.getCliente() + 
                           " | Producto: " + pedido.getProducto() + " x" + pedido.getCantidad() + 
                           " | Guía: " + numeroGuia);

        // ⚠️ Envíos debe conocer a Notificaciones y llamarlo directamente
        notificaciones.enviarGuiaSeguimiento(pedido, numeroGuia);
    }
}

