package antes;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado del stock de productos en el almacén.
 */
public class ServicioInventario {

    private Map<String, Integer> stock = new HashMap<>();

    public ServicioInventario() {
        stock.put("Laptop Pro", 5);
        stock.put("Mouse Inalámbrico", 10);
        stock.put("Teclado Mecánico", 0); // Agotado para pruebas
    }

    public boolean reservarStock(String producto, int cantidad) {
        int disponible = stock.getOrDefault(producto, 0);
        if (disponible >= cantidad) {
            stock.put(producto, disponible - cantidad);
            System.out.println("📦 [Inventario] Reservadas " + cantidad + " unidades de '" + producto + 
                               "'. Stock restante: " + stock.get(producto));
            return true;
        } else {
            System.out.println("❌ [Inventario] Stock insuficiente para '" + producto + 
                               "'. Solicitado: " + cantidad + " | Disponible: " + disponible);
            return false;
        }
    }

    public void liberarStock(String producto, int cantidad) {
        int actual = stock.getOrDefault(producto, 0);
        stock.put(producto, actual + cantidad);
        System.out.println("🔄 [Inventario] Rollback: Se liberaron " + cantidad + " unidades de '" + producto + 
                           "'. Stock restaurado: " + stock.get(producto));
    }
}

