package despues;

import despues.componentes.*;
import despues.mediador.CompraMediador;
import despues.modelo.Pedido;

/**
 * Demostración del sistema CON el Patrón Mediador.
 * 
 * BENEFICIOS OBTENIDOS:
 * 1. Desacoplamiento total: Ningún servicio conoce a los demás; solo conocen a la interfaz MediadorCompra.
 * 2. Responsabilidad Única: Cada servicio hace solo su trabajo (Pagos solo cobra, Envíos solo despacha).
 * 3. Centralización de reglas: Las políticas de compensación y flujo residen en un solo lugar (CompraMediador).
 * 4. Extensibilidad (OCP): Añadimos el nuevo servicio 'ServicioPuntosFidelidad' sin modificar ninguna de las
 *    clases de servicios existentes.
 */
public class MainDespues {
    public static void main(String[] args) {
        System.out.println("######################################################################");
        System.out.println("            SISTEMA DE E-COMMERCE (CON PATRÓN MEDIADOR)              ");
        System.out.println("    Los servicios se comunican a través del Mediador Central         ");
        System.out.println("######################################################################\n");

        // 1. Instanciamos los servicios independientes (no se conocen entre ellos)
        ServicioPedidos servicioPedidos = new ServicioPedidos();
        ServicioInventario servicioInventario = new ServicioInventario();
        ServicioPagos servicioPagos = new ServicioPagos();
        ServicioEnvios servicioEnvios = new ServicioEnvios();
        ServicioNotificaciones servicioNotificaciones = new ServicioNotificaciones();

        // 2. Instanciamos el Mediador conectando a todos los colegas
        CompraMediador mediador = new CompraMediador(
            servicioPedidos,
            servicioInventario,
            servicioPagos,
            servicioEnvios,
            servicioNotificaciones
        );

        // ESCENARIO 1: Flujo exitoso
        System.out.println("\n>>> ESCENARIO 1: Compra con stock y pago exitoso");
        Pedido pedido1 = new Pedido("ORD-201", "Carlos Gómez", "Laptop Pro", 1, 1200.0, true);
        servicioPedidos.registrarPedido(pedido1);

        // ESCENARIO 2: Pago rechazado
        System.out.println("\n>>> ESCENARIO 2: Compra con stock pero pago RECHAZADO (Manejo de Rollback)");
        Pedido pedido2 = new Pedido("ORD-202", "Ana Martínez", "Mouse Inalámbrico", 2, 50.0, false);
        servicioPedidos.registrarPedido(pedido2);

        // ESCENARIO 3: Sin stock disponible
        System.out.println("\n>>> ESCENARIO 3: Compra de producto agotado");
        Pedido pedido3 = new Pedido("ORD-203", "Luis Pérez", "Teclado Mecánico", 1, 80.0, true);
        servicioPedidos.registrarPedido(pedido3);

        // ESCENARIO 4: Demostración de extensibilidad (Principio Abierto / Cerrado)
        System.out.println("\n>>> ESCENARIO 4: Incorporando un nuevo servicio (Puntos de Fidelización)");
        System.out.println("    ¡Sin modificar ninguna clase de servicio existente!\n");
        ServicioPuntosFidelidad servicioPuntos = new ServicioPuntosFidelidad();
        mediador.setServicioPuntos(servicioPuntos);

        Pedido pedido4 = new Pedido("ORD-204", "Sofía Herrera", "Mouse Inalámbrico", 1, 25.0, true);
        servicioPedidos.registrarPedido(pedido4);

        System.out.println("\n🎯 Conclusión pedagógica:");
        System.out.println("El Mediador convirtió una red caótica N-a-N en una estrella limpia 1-a-N.");
    }
}

