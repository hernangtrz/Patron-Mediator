package antes;

/**
 * Servicio que recibe la solicitud de compra del cliente.
 */
public class ServicioPedidos {

    // ⚠️ ACOPLAMIENTO: Conoce a Inventario, Pagos y Notificaciones
    private ServicioInventario inventario;
    private ServicioPagos pagos;
    private ServicioNotificaciones notificaciones;

    public ServicioPedidos(ServicioInventario inventario, ServicioPagos pagos, ServicioNotificaciones notificaciones) {
        this.inventario = inventario;
        this.pagos = pagos;
        this.notificaciones = notificaciones;
    }

    public void realizarPedido(Pedido pedido, boolean simularPagoExitoso) {
        System.out.println("================================================================================");
        System.out.println("🛒 [Pedidos] Procesando pedido #" + pedido.getId() + 
                           " | Cliente: " + pedido.getCliente() + 
                           " | Producto: " + pedido.getProducto() + 
                           " | Total: $" + pedido.getMonto());
        System.out.println("================================================================================");

        // 1. Verificar inventario directamente
        boolean stockReservado = inventario.reservarStock(pedido.getProducto(), pedido.getCantidad());
        if (!stockReservado) {
            System.out.println("❌ [Pedidos] La orden no puede continuar por falta de inventario.");
            notificaciones.enviarErrorStock(pedido);
            return;
        }

        // 2. Cobrar directamente a través de Pagos
        boolean pagoAprobado = pagos.procesarPago(pedido, simularPagoExitoso);
        if (pagoAprobado) {
            System.out.println("✅ [Pedidos] Pedido #" + pedido.getId() + " finalizado exitosamente.");
        } else {
            System.out.println("⚠️ [Pedidos] Pedido #" + pedido.getId() + " cancelado por error en el pago.");
        }
    }
}

