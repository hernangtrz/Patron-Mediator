package despues.componentes;

import despues.modelo.Pedido;

/**
 * Servicio encargado de la comunicación con el cliente.
 * Solo se preocupa por formatear y enviar mensajes.
 */
public class ServicioNotificaciones extends ComponenteServicio {

    public void enviarErrorStock(Pedido pedido) {
        System.out.println("📧 [Notificaciones] Para " + pedido.getCliente() + 
                           ": Lo sentimos, no hay stock de '" + pedido.getProducto() + "'.");
    }

    public void enviarConfirmacionPago(Pedido pedido) {
        System.out.println("📧 [Notificaciones] Para " + pedido.getCliente() + 
                           ": Pago aprobado por $" + pedido.getMonto() + " para el pedido #" + pedido.getId() + ".");
    }

    public void enviarAlertaPagoFallido(Pedido pedido) {
        System.out.println("📧 [Notificaciones] Para " + pedido.getCliente() + 
                           ": Alerta, el pago del pedido #" + pedido.getId() + " fue rechazado.");
    }

    public void enviarGuiaSeguimiento(Pedido pedido, String guia) {
        System.out.println("📧 [Notificaciones] Para " + pedido.getCliente() + 
                           ": Tu paquete está en camino. Guía de seguimiento: " + guia + ".");
    }
}

