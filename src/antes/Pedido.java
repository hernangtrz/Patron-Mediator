package antes;

/**
 * Representa la información de una orden de compra.
 */
public class Pedido {
    private String id;
    private String cliente;
    private String producto;
    private int cantidad;
    private double monto;

    public Pedido(String id, String cliente, String producto, int cantidad, double monto) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public String getId() { return id; }
    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getMonto() { return monto; }
}

