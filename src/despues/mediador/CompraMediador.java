package despues.mediador;

import despues.componentes.*;
import despues.modelo.Pedido;
import despues.modelo.TipoEvento;

/**
 * Mediador Concreto: Encapsula y centraliza toda la interacción y reglas
 * de coordinación entre los servicios del sistema de compras.
 */
public class CompraMediador implements MediadorCompra {

    private ServicioPedidos pedidos;
    private ServicioInventario inventario;
    private ServicioPagos pagos;
    private ServicioEnvios envios;
    private ServicioNotificaciones notificaciones;
    private ServicioPuntosFidelidad puntosFidelidad; // Componente opcional/extensible

    public CompraMediador(ServicioPedidos pedidos, 
                          ServicioInventario inventario, 
                          ServicioPagos pagos, 
                          ServicioEnvios envios, 
                          ServicioNotificaciones notificaciones) {
        this.pedidos = pedidos;
        this.inventario = inventario;
        this.pagos = pagos;
        this.envios = envios;
        this.notificaciones = notificaciones;

        // Registrar este mediador en cada uno de los componentes
        this.pedidos.setMediador(this);
        this.inventario.setMediador(this);
        this.pagos.setMediador(this);
        this.envios.setMediador(this);
        this.notificaciones.setMediador(this);
    }

    public void setServicioPuntos(ServicioPuntosFidelidad puntos) {
        this.puntosFidelidad = puntos;
        this.puntosFidelidad.setMediador(this);
    }

    @Override
    public void notificar(ComponenteServicio remitente, TipoEvento evento, Object datos) {
        switch (evento) {
            case PEDIDO_INICIADO:
                Pedido nuevoPedido = (Pedido) datos;
                System.out.println("🎛️ [Mediador] Evento: PEDIDO_INICIADO. Solicitando validación a Inventario...");
                inventario.verificarYReservar(nuevoPedido);
                break;

            case STOCK_DISPONIBLE:
                Pedido pedidoConStock = (Pedido) datos;
                System.out.println("🎛️ [Mediador] Evento: STOCK_DISPONIBLE. Solicitando procesamiento de cobro a Pagos...");
                pagos.procesarPago(pedidoConStock);
                break;

            case STOCK_INSUFICIENTE:
                Pedido pedidoSinStock = (Pedido) datos;
                System.out.println("🎛️ [Mediador] Evento: STOCK_INSUFICIENTE. Deteniendo flujo e informando al cliente...");
                notificaciones.enviarErrorStock(pedidoSinStock);
                break;

            case PAGO_EXITOSO:
                Pedido pedidoPagado = (Pedido) datos;
                System.out.println("🎛️ [Mediador] Evento: PAGO_EXITOSO. Coordinando envío, facturación y puntos...");
                notificaciones.enviarConfirmacionPago(pedidoPagado);
                envios.programarEnvio(pedidoPagado);
                if (puntosFidelidad != null) {
                    puntosFidelidad.acumularPuntos(pedidoPagado);
                }
                System.out.println("✅ [Mediador] Flujo de compra completado con éxito para #" + pedidoPagado.getId());
                break;

            case PAGO_FALLIDO:
                Pedido pedidoRechazado = (Pedido) datos;
                System.out.println("🎛️ [Mediador] Evento: PAGO_FALLIDO. Coordinando compensación (rollback de stock y alerta)...");
                inventario.liberarStock(pedidoRechazado.getProducto(), pedidoRechazado.getCantidad());
                notificaciones.enviarAlertaPagoFallido(pedidoRechazado);
                System.out.println("⚠️ [Mediador] Flujo cancelado de forma segura para #" + pedidoRechazado.getId());
                break;

            case ENVIO_PROGRAMADO:
                ServicioEnvios.EnvioInfo infoEnvio = (ServicioEnvios.EnvioInfo) datos;
                System.out.println("🎛️ [Mediador] Evento: ENVIO_PROGRAMADO. Notificando guía al cliente...");
                notificaciones.enviarGuiaSeguimiento(infoEnvio.getPedido(), infoEnvio.getGuia());
                break;
        }
    }
}

