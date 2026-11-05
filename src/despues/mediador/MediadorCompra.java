package despues.mediador;

import despues.componentes.ComponenteServicio;
import despues.modelo.TipoEvento;

/**
 * Interfaz del Mediador (Colleague-Mediator contract).
 * Define el canal único a través del cual los componentes notifican
 * qué ha ocurrido en su dominio, sin saber quién lo consumirá.
 */
public interface MediadorCompra {
    void notificar(ComponenteServicio remitente, TipoEvento evento, Object datos);
}

