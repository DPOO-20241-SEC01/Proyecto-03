package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import model.Galeria;
import model.inventario.Pieza;
import model.persistencia.CentralPersistencia;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Comprador;
import model.ventas.Oferta;

public class ViewCajeroGUI extends JFrame {
    private Cajero cajero;
    private JLabel TituloCajero;
    private JLabel mensaje;
    private JButton verOfertas;
    private JButton registrarVentas;
    private JButton cerrarSesion;

    public ViewCajeroGUI(Cajero cajero) {
        cajero.setViewCajeroGUI(this);
        this.cajero = cajero;
        initialize();
    }

    public void initialize() {
        setTitle("Cajero - Galería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        TituloCajero = new JLabel("Cajero");
        TituloCajero.setFont(new Font("Arial", Font.BOLD, 16));

        mensaje = new JLabel("Bienvenido, Cajero! Ingrese la opción que desee:");
        verOfertas = new JButton("Ver Ofertas Aceptadas");
        registrarVentas = new JButton("Registrar Ventas");
        cerrarSesion = new JButton("Cerrar Sesión");

        verOfertas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verOfertasAceptadas();
            }
        });

        registrarVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cajero.getOfertasAceptadas() == null) {
                    JOptionPane.showMessageDialog(ViewCajeroGUI.this, "No hay Ofertas aceptadas.");
                } else {
                    cajero.registrarVentas();
                    cajero.getGaleria().getCentralPersistencia().guardar(cajero.getGaleria());
                    JOptionPane.showMessageDialog(ViewCajeroGUI.this, "Oferta aceptada correctamente.");
                }
            }
        });

        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(TituloCajero);
        panel.add(mensaje);
        panel.add(verOfertas);
        panel.add(registrarVentas);
        panel.add(cerrarSesion);

        add(panel);
    }

    public void verOfertasAceptadas() {
        JFrame frame = new JFrame("Revisar Ofertas Aceptadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        ArrayList<Oferta> arregloRecorrible = cajero.getOfertasAceptadas();
        if (arregloRecorrible == null) {
            arregloRecorrible = new ArrayList<>();
        }
        Comprador comprador = new Comprador("Comp", "COMP", "123", "comprador", "contrasenia1", "Comprador", "1312", "123", 1234);
        String idPieza = cajero.getGaleria().getAdminstrador().ingresarPintura("ubicacion", "titulo", "1009", "lugar", "nombreArtista", 10, "Pintura", 39, 20);
        Pieza piezaPrueba = cajero.getGaleria().getPiezaPorID("Pintura", idPieza);
        Oferta oferta = new Oferta(piezaPrueba, comprador, 10, "null", "efectivo");
        arregloRecorrible.add(oferta);
        cajero.setVentas(arregloRecorrible);

        System.out.println("Ofertas Aceptadas:");
        if (cajero.getOfertasAceptadas() == null) {
            JOptionPane.showMessageDialog(frame, "No hay Ofertas aceptadas.");
        } else {
            for (Oferta oferta2 : arregloRecorrible) {
                JLabel ofertasAceptadas = new JLabel("ID: " + oferta.getIdOferta() + ". Monto: " + oferta.getValorOferta() + ". Fecha: " + oferta.getFecha());
                panel.add(ofertasAceptadas);
            }
            frame.add(panel);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        CentralPersistencia centralPersistencia = new CentralPersistencia();
        Galeria galeria;
        Administrador admin = new Administrador("Admin", "Admin", "1234", "admin", "password", "Administrador");

        Cajero cajero = new Cajero("Caja", "Caje", "1235", "cajero", "contrasenia", "Cajero");

        Object data = centralPersistencia.cargar();
        if (data instanceof Galeria) {
            galeria = (Galeria) data;
            galeria.setCentralPersistencia(centralPersistencia);
            System.out.println("Galería cargada con éxito");
            galeria.setAdministrador(admin);
            cajero.setGaleria(galeria);
            galeria.setCajero(cajero);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new ViewCajeroGUI(cajero).setVisible(true);
                }
            });
        } else {
            galeria = new Galeria();
            galeria.setAdministrador(admin);
            cajero.setGaleria(galeria);
            galeria.setCajero(cajero);
            centralPersistencia.guardar(galeria);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new ViewCajeroGUI(cajero).setVisible(true);
                }
            });
        }
    }
}
