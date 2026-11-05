package despues.componentes;

import despues.mediador.MediadorCompra;

/**
 * Clase base para todos los colegas (servicios) del sistema.
 * 
 * BENEFICIO CLAVE:
 * Cada componente solo conoce al Mediador (1 sola referencia).
 * Ya no conoce a ningún otro servicio del sistema.
 */
public abstract class ComponenteServicio {
    protected MediadorCompra mediador;

    public void setMediador(MediadorCompra mediador) {
        this.mediador = mediador;
    }
}

