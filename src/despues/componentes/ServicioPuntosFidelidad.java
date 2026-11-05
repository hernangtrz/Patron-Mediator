package despues.componentes;

import despues.modelo.Pedido;

/**
 * Servicio de Fidelización (Puntos y Recompensas).
 * 
 * VENTAJA DIDÁCTICA:
 * Muestra el Principio Abierto/Cerrado (OCP).
 * Se puede agregar este nuevo servicio al flujo de compras SIN tocar ni una
 * sola línea de ServicioPagos, ServicioInventario, ServicioPedidos ni ServicioEnvios.
 */
public class ServicioPuntosFidelidad extends ComponenteServicio {

    public void acumularPuntos(Pedido pedido) {
        int puntos = (int) (pedido.getMonto() / 10);
        System.out.println("⭐ [Fidelización] ¡Genial! Se acumularon " + puntos + 
                           " puntos para el cliente " + pedido.getCliente() + ".");
    }
}

