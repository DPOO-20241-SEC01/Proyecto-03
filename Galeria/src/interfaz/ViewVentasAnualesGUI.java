package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import model.Galeria;
import model.ventas.Venta;

public class ViewVentasAnualesGUI extends JFrame implements VentasObserver {
    private Galeria galeria;
    private VentasPanel panel;

    public ViewVentasAnualesGUI(Galeria galeria) {
        this.galeria = galeria;
        galeria.addVentasObserver(this);
        initialize();
    }

    private void initialize() {
        setTitle("Ventas Anuales - Galería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);

        panel = new VentasPanel(galeria);
        add(panel);
    }

    @Override
    public void onVentaRegistrada() {
        panel.recalcularVentas();
        panel.repaint();
    }

    private static class VentasPanel extends JPanel {
        private Galeria galeria;
        private Map<String, Integer> ventasPorDia;

        public VentasPanel(Galeria galeria) {
            this.galeria = galeria;
            this.ventasPorDia = calcularVentasPorDia();
        }

        private Map<String, Integer> calcularVentasPorDia() {
            Map<String, Integer> ventasPorDia = new HashMap<>();
            for (Venta venta : galeria.getVentas().values()) {
                String fecha = venta.getFecha().split(" ")[0];
                ventasPorDia.put(fecha, ventasPorDia.getOrDefault(fecha, 0) + 1);
            }
            return ventasPorDia;
        }

        public void recalcularVentas() {
            this.ventasPorDia = calcularVentasPorDia();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cellSize = 20;
            int startX = 60;
            int startY = 40;

            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

            for (int i = 0; i < months.length; i++) {
                g.drawString(months[i], startX + (i * cellSize * 4), startY - 10);
            }

            for (int i = 0; i < days.length; i++) {
                g.drawString(days[i], startX - 30, startY + (i * cellSize) + 10);
            }

            for (int week = 0; week < 52; week++) {
                for (int day = 0; day < 7; day++) {
                    int x = startX + (week * cellSize);
                    int y = startY + (day * cellSize);

                    String fecha = obtenerFecha(week, day);
                    int ventas = ventasPorDia.getOrDefault(fecha, 0);
                    g.setColor(getColorForSales(ventas));
                    g.fillRect(x, y, cellSize - 1, cellSize - 1);
                }
            }

            int legendX = startX;
            int legendY = startY + (7 * cellSize) + 30;
            g.setColor(Color.WHITE);
            g.fillRect(legendX, legendY, cellSize, cellSize);
            g.setColor(new Color(198, 239, 206));
            g.fillRect(legendX + cellSize + 5, legendY, cellSize, cellSize);
            g.setColor(new Color(123, 201, 111));
            g.fillRect(legendX + (2 * cellSize) + 10, legendY, cellSize, cellSize);
            g.setColor(new Color(35, 154, 59));
            g.fillRect(legendX + (3 * cellSize) + 15, legendY, cellSize, cellSize);

            g.setColor(Color.BLACK);
            g.drawString("Less", legendX, legendY + cellSize + 15);
            g.drawString("More", legendX + (3 * cellSize) + 15, legendY + cellSize + 15);
        }

        private String obtenerFecha(int week, int day) {
            int totalDays = (week * 7) + day;
            int month = 1;
            int dayOfMonth = totalDays + 1;
            int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            for (int i = 0; i < daysInMonth.length; i++) {
                if (dayOfMonth <= daysInMonth[i]) {
                    month = i + 1;
                    break;
                } else {
                    dayOfMonth -= daysInMonth[i];
                }
            }

            return String.format("2023-%02d-%02d", month, dayOfMonth);
        }

        private Color getColorForSales(int sales) {
            if (sales == 0) return Color.WHITE;
            if (sales == 1) return new Color(198, 239, 206);
            if (sales == 2) return new Color(123, 201, 111);
            return new Color(35, 154, 59);
        }
    }
}
