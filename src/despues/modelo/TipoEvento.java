package despues.modelo;

/**
 * Eventos del ciclo de vida de una compra que los componentes notifican al Mediador.
 */
public enum TipoEvento {
    PEDIDO_INICIADO,
    STOCK_DISPONIBLE,
    STOCK_INSUFICIENTE,
    PAGO_EXITOSO,
    PAGO_FALLIDO,
    ENVIO_PROGRAMADO
}

