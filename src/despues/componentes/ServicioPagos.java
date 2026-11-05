package despues.componentes;

import despues.modelo.Pedido;
import despues.modelo.TipoEvento;

/**
 * Servicio encargado exclusivamente del procesamiento financiero.
 * 
 * MEJORA CLAVE:
 * ¡Ya no tiene referencias a Inventario, Envíos ni Notificaciones!
 * Cumple con el Principio de Responsabilidad Única (SRP).
 */
public class ServicioPagos extends ComponenteServicio {

    public void procesarPago(Pedido pedido) {
        System.out.println("💳 [Pagos] Procesando cargo de $" + pedido.getMonto() + " para " + pedido.getCliente() + "...");

        if (pedido.debeSimularPagoExitoso()) {
            System.out.println("💳 [Pagos] Pago aprobado exitosamente.");
            // Avisa al mediador; el mediador decidirá qué servicios deben reaccionar
            mediador.notificar(this, TipoEvento.PAGO_EXITOSO, pedido);
        } else {
            System.out.println("❌ [Pagos] Error: Transacción denegada / fondos insuficientes.");
            // Avisa al mediador del fallo; el mediador se encargará del rollback
            mediador.notificar(this, TipoEvento.PAGO_FALLIDO, pedido);
        }
    }
}

