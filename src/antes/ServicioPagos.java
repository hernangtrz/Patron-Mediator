package antes;

/**
 * Servicio encargado del cobro financiero.
 */
public class ServicioPagos {

    // ⚠️ ALTO ACOPLAMIENTO:
    // Para cumplir su función, Pagos debe conocer y coordinar a Inventario, Envíos y Notificaciones.
    private ServicioInventario inventario;
    private ServicioEnvios envios;
    private ServicioNotificaciones notificaciones;

    // Se necesita inyectar o enlazar múltiples dependencias que no son de su propio dominio
    public void setDependencias(ServicioInventario inventario, ServicioEnvios envios, ServicioNotificaciones notificaciones) {
        this.inventario = inventario;
        this.envios = envios;
        this.notificaciones = notificaciones;
    }

    public boolean procesarPago(Pedido pedido, boolean simularExito) {
        System.out.println("💳 [Pagos] Procesando cargo de $" + pedido.getMonto() + " a " + pedido.getCliente() + "...");

        if (simularExito) {
            System.out.println("💳 [Pagos] Pago aprobado.");
            
            // ⚠️ Pagos tiene que llamar a Notificaciones
            notificaciones.enviarConfirmacionPago(pedido);

            // ⚠️ Pagos tiene que orquestar Envíos
            envios.programarEnvio(pedido);
            return true;
        } else {
            System.out.println("❌ [Pagos] Fondos insuficientes o tarjeta rechazada.");

            // ⚠️ Pagos tiene que saber de la existencia de Inventario y hacer rollback manual
            inventario.liberarStock(pedido.getProducto(), pedido.getCantidad());

            // ⚠️ Pagos tiene que notificar el fallo
            notificaciones.enviarAlertaPagoFallido(pedido);
            return false;
        }
    }
}

