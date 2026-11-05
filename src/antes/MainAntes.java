package antes;

/**
 * Demostración del sistema SIN el Patrón Mediador.
 * 
 * PROBLEMAS EVIDENTES:
 * 1. Dependencias circulares o en red (Spaghetti Code):
 *    - ServicioPedidos conoce a ServicioInventario, ServicioPagos y ServicioNotificaciones.
 *    - ServicioPagos conoce a ServicioInventario, ServicioEnvios y ServicioNotificaciones.
 *    - ServicioEnvios conoce a ServicioNotificaciones.
 * 2. Si queremos agregar un nuevo servicio (ej. ServicioPuntosFidelidad),
 *    debemos modificar ServicioPagos y ServicioPedidos directamente.
 * 3. Difícil de testear: Para hacer test unitario de Pagos, hay que crear y configurar
 *    instancias de Inventario, Envíos y Notificaciones.
 */
public class MainAntes {
    public static void main(String[] args) {
        System.out.println("######################################################################");
        System.out.println("           SISTEMA DE E-COMMERCE (SIN PATRÓN MEDIADOR)               ");
        System.out.println("   Todos los servicios se conocen entre sí (Acoplamiento Fuerte)     ");
        System.out.println("######################################################################\n");

        // Configuración manual e interconectada de dependencias
        ServicioNotificaciones notificaciones = new ServicioNotificaciones();
        ServicioEnvios envios = new ServicioEnvios(notificaciones);
        ServicioInventario inventario = new ServicioInventario();
        ServicioPagos pagos = new ServicioPagos();
        
        // Inyección forzada de dependencias cruzadas en ServicioPagos
        pagos.setDependencias(inventario, envios, notificaciones);

        ServicioPedidos pedidos = new ServicioPedidos(inventario, pagos, notificaciones);

        // ESCENARIO 1: Flujo exitoso completo
        System.out.println("\n>>> ESCENARIO 1: Compra con stock y pago exitoso");
        Pedido pedido1 = new Pedido("ORD-101", "Carlos Gómez", "Laptop Pro", 1, 1200.0);
        pedidos.realizarPedido(pedido1, true);

        // ESCENARIO 2: Pago rechazado (Pagos debe revertir el stock llamando a Inventario)
        System.out.println("\n>>> ESCENARIO 2: Compra con stock pero pago RECHAZADO");
        Pedido pedido2 = new Pedido("ORD-102", "Ana Martínez", "Mouse Inalámbrico", 2, 50.0);
        pedidos.realizarPedido(pedido2, false);

        // ESCENARIO 3: Sin stock disponible
        System.out.println("\n>>> ESCENARIO 3: Compra de producto agotado");
        Pedido pedido3 = new Pedido("ORD-103", "Luis Pérez", "Teclado Mecánico", 1, 80.0);
        pedidos.realizarPedido(pedido3, true);

        System.out.println("\n⚠️ Observación: Nota cómo ServicioPagos tuvo que encargarse de revertir");
        System.out.println("el inventario y coordinar envíos, asumiendo tareas de orquestación que");
        System.out.println("no le pertenecen a su responsabilidad única.\n");
    }
}

