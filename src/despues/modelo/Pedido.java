package despues.modelo;

/**
 * Representa la información de una orden de compra en el sistema.
 */
public class Pedido {
    private String id;
    private String cliente;
    private String producto;
    private int cantidad;
    private double monto;
    private boolean simularPagoExitoso; // Flag de prueba para la simulación

    public Pedido(String id, String cliente, String producto, int cantidad, double monto, boolean simularPagoExitoso) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.monto = monto;
        this.simularPagoExitoso = simularPagoExitoso;
    }

    public String getId() { return id; }
    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getMonto() { return monto; }
    public boolean debeSimularPagoExitoso() { return simularPagoExitoso; }
}

