package model.ventas;

import java.util.ArrayList;
import java.util.HashMap;

public class VentasAnuales {
    private HashMap<String, Venta> ventas;

    public VentasAnuales() {
        this.ventas = new HashMap<>();
    }

    public void registrarVenta(Venta venta) {
        ventas.put(venta.getIdVenta(), venta);
    }

    public ArrayList<Venta> getVentas() {
        return new ArrayList<>(ventas.values());
    }

    public double calcularTotalVentas() {
        double total = 0;
        for (Venta venta : ventas.values()) {
            total += venta.getPieza().getCostoFijo();
        }
        return total;
    }

    public int contarVentas() {
        return ventas.size();
    }
}
