package interfaz;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;

import model.inventario.Escultura;
import model.inventario.Fotografia;
import model.inventario.Impresion;
import model.inventario.Pieza;
import model.inventario.Pintura;
import model.inventario.Video;
import model.usuarios.Administrador;
import model.usuarios.Comprador;
import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;
import view.ViewComprador;
import interfaz.ViewAdministradorGUI;

public class ViewCompradorGUI extends JFrame {

    private Comprador comprador;

    private Administrador administrador = ViewAdministradorGUI.administrador;
    
    public ViewCompradorGUI(Comprador comprador) {
        this.comprador = comprador;
        initialize();
    }

    private void initialize() {
        setTitle("Comprador - Galería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        JLabel welcomeLabel = new JLabel("Bienvenido " + comprador.getNombre() + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel);

        JButton btnComprarPieza = new JButton("Comprar pieza");
        JButton btnConsignarPieza = new JButton("Consignar pieza");
        JButton btnOfertarPieza = new JButton("Ofertar por pieza en subasta");
        JButton btnPiezasConsignadas = new JButton("Ver piezas en consignación");
        JButton btnPiezasEstado = new JButton("Estado de consignaciones pendientes");
        JButton btnConsignacionesActual = new JButton("Revisar consignaciones actuales (propias)");
        JButton btnRevisarOfertasPendientes = new JButton("Ofertas pendientes por aceptar");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        btnComprarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazCompraPieza();
            }
        });
        
        

        btnConsignarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazConsignarPieza();
            }
        });

        btnOfertarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazOfertarPieza();
            }
        });
        btnPiezasConsignadas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				interfazPiezasConsignadas();
			}
        	
        });
        btnPiezasEstado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				interfazPiezasEstado();
			}
        	
        });
        btnConsignacionesActual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				interfazPiezasConsignadas();
			}
			});
        btnRevisarOfertasPendientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				interfazOfertasPendientes();
			}
        });
        
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        
        
        panel.add(btnComprarPieza);
        panel.add(btnConsignarPieza);
        panel.add(btnOfertarPieza);
        panel.add(btnPiezasConsignadas);
        panel.add(btnPiezasEstado);
        panel.add(btnConsignacionesActual);
        panel.add(btnRevisarOfertasPendientes);
        panel.add(btnCerrarSesion);

        add(panel);
    }

    private void interfazCompraPieza() {
        JFrame frame = new JFrame("ComprarPieza");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField fieldTipoPieza = new JTextField();
        JTextField fieldIdPieza = new JTextField();
        JTextField fieldValorOferta = new JTextField();
        JTextField fieldPeticion = new JTextField();
        String[] metodosPAGO = {"Tarjeta", "Efectivo", "Tranferencia"};
        JComboBox<String> comboPago = new JComboBox<>(metodosPAGO);
        panel.add(new JLabel("Tipo de Pieza:"));
        panel.add(fieldTipoPieza);
        panel.add(new JLabel("ID de la Pieza a comprar:"));
        panel.add(fieldIdPieza);
        panel.add(new JLabel("Valor que desea pagar por la pieza:"));
        panel.add(fieldValorOferta);
        panel.add(new JLabel("Digite su petición"));
        panel.add(fieldPeticion);
        panel.add(new JLabel("Método de pago:"));
        panel.add(comboPago);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoPieza = fieldTipoPieza.getText();
                String idPieza = fieldIdPieza.getText();
                int valorOferta = Integer.parseInt(fieldValorOferta.getText());
                String peticion = fieldPeticion.getText();
                String metodoPago = (String) comboPago.getSelectedItem();
                comprador.comprarPieza(tipoPieza, idPieza, valorOferta, peticion, metodoPago);
            }
        });

        panel.add(btnGuardar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void interfazConsignarPieza() {
        JFrame frame = new JFrame("Consignar una pieza");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField fieldTitulo = new JTextField();
        JTextField fieldAnio = new JTextField();
        JTextField fieldLugar = new JTextField();
        JTextField fieldArtista = new JTextField();
        JTextField fieldColectivo = new JTextField();
        JTextField fieldCosto = new JTextField();
        JTextField fieldIdPieza = new JTextField();
        JTextField fieldfechaMax = new JTextField();
        JTextField fieldModoConsignacion = new JTextField();
        String[] ubicaciones = {"Exhibición", "Bodega"};
        JComboBox<String> comboUbicacion = new JComboBox<>(ubicaciones);

        String[] tipos = {"Escultura", "Pintura", "Impresión", "Fotografía", "Vídeos"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        panel.add(new JLabel("ID de la pieza:"));
        panel.add(fieldIdPieza);
        panel.add(new JLabel("Título de la pieza:"));
        panel.add(fieldTitulo);
        panel.add(new JLabel("Año de creación:"));
        panel.add(fieldAnio);
        panel.add(new JLabel("Lugar de creación:"));
        panel.add(fieldLugar);
        panel.add(new JLabel("Nombre del artista:"));
        panel.add(fieldArtista);
        panel.add(new JLabel("Nombre del colectivo (si aplica):"));
        panel.add(fieldColectivo);
        panel.add(new JLabel("Ubicación:"));
        panel.add(comboUbicacion);
        panel.add(new JLabel("Tipo de pieza:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Costo de la pieza:"));
        panel.add(fieldCosto);
        panel.add(new JLabel("Fecha Maxima de consigna:"));
        panel.add(fieldfechaMax);
        panel.add(new JLabel("Modo de consignación:"));
        panel.add(fieldModoConsignacion);

        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = fieldTitulo.getText();
                String idPieza = fieldIdPieza.getText();
                String anio = fieldAnio.getText();
                String lugar = fieldLugar.getText();
                String artista = fieldArtista.getText();
                String colectivo = fieldColectivo.getText();
                String ubicacion = (String) comboUbicacion.getSelectedItem();
                String tipo = (String) comboTipo.getSelectedItem();
                String fechaMax = fieldfechaMax.getText();
                String modoConsignacion  = fieldModoConsignacion.getText();
                
                int costo = Integer.parseInt(fieldCosto.getText());
                ArrayList consignacionRev = administrador.getConsignacionesARevisar();

                switch (tipo) {
                    case "Escultura":
                        String dimensiones = JOptionPane.showInputDialog("Dimensiones (alto-largo-ancho en cm):");
                        String peso = JOptionPane.showInputDialog("Peso (kg):");
                        String materiales = JOptionPane.showInputDialog("Materiales de construcción:");
                        boolean requiereElectricidad = JOptionPane.showConfirmDialog(frame, "¿Requiere electricidad?") == JOptionPane.YES_OPTION;
                        Escultura escultura = new Escultura(idPieza, ubicacion, titulo, anio, lugar, artista, costo, tipo, dimensiones, peso, materiales, requiereElectricidad);
                        Consignacion consignaEscultura = new Consignacion(escultura, fechaMax, modoConsignacion);
                        consignacionRev.add(consignaEscultura);
                        break;
                    case "Pintura":
                        int largoP = Integer.parseInt(JOptionPane.showInputDialog("Largo (cm):"));
                        int anchoP = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        Pintura pintura= new Pintura(idPieza, ubicacion, titulo, anio, lugar, artista, costo, tipo, largoP, anchoP);
                        Consignacion consignaPintura = new Consignacion(pintura, fechaMax, modoConsignacion);
                        consignacionRev.add(consignaPintura);
                        break;
                    case "Impresión":
                        String tipoHoja = JOptionPane.showInputDialog("Tipo de hoja:");
                        int largoI = Integer.parseInt(JOptionPane.showInputDialog("Largo (cm):"));
                        int anchoI = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        Impresion impresion = new Impresion(idPieza, ubicacion, titulo, anio, lugar, artista, costo, tipo, tipoHoja, largoI, anchoI);
                        Consignacion consignaImpresion = new Consignacion(impresion, fechaMax, modoConsignacion);
                        consignacionRev.add(consignaImpresion);
                        break;
                    case "Fotografía":
                        String tipoFoto = JOptionPane.showInputDialog("Tipo de fotografía:");
                        int altoF = Integer.parseInt(JOptionPane.showInputDialog("Alto (cm):"));
                        int anchoF = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        String resolucion = JOptionPane.showInputDialog("Resolución (PPI):");
                        Fotografia foto = new Fotografia(idPieza, ubicacion, titulo, anio, lugar, artista, costo, tipo, altoF, anchoF, tipoFoto, resolucion);
                        Consignacion consignaFoto = new Consignacion(foto, fechaMax, modoConsignacion);
                        consignacionRev.add(consignaFoto);
                        break;
                    case "Vídeos":
                        int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración (minutos):"));
                        int pesoV = Integer.parseInt(JOptionPane.showInputDialog("Peso (MB):"));
                        boolean mayoresDeEdad = JOptionPane.showConfirmDialog(frame, "¿Para mayores de 18?") == JOptionPane.YES_OPTION;
                        String resolucionVideo = JOptionPane.showInputDialog("Resolución (PPI):");
                        Video video = new Video(idPieza, ubicacion, titulo, anio, lugar, artista, costo, tipo, duracion, mayoresDeEdad, resolucionVideo, pesoV);
                        Consignacion consignaVideo = new Consignacion(video, fechaMax, modoConsignacion);
                        consignacionRev.add(consignaVideo);
                        break;
                }

                JOptionPane.showMessageDialog(frame, "A espera del administrador para aceptar la consignación.");
                frame.dispose();
            }
        });

        panel.add(btnGuardar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void interfazOfertarPieza() {
        JFrame frame = new JFrame("Ofertar por pieza en subasta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField fieldOferta = new JTextField();
        panel.add(new JLabel("Ingrese la cantidad a ofertar por la pieza: "));
        panel.add(fieldOferta);

        String[] metodoPago = {"Tarjeta", "Efectivo", "Transferencia"};
        JComboBox<String> comboMetodoPago = new JComboBox<>(metodoPago);
        panel.add(comboMetodoPago);

        JButton btnEnviarOferta = new JButton("Enviar Oferta");
        btnEnviarOferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metodoPago = (String) comboMetodoPago.getSelectedItem();
                int oferta = Integer.parseInt(fieldOferta.getText());

                if (comprador.getPiezaSubastaEnCurso().getValorActualSubasta() > comprador.getSaldoDisponible()) {
                    JOptionPane.showMessageDialog(frame, "No tienes suficiente saldo para ofertar por esta pieza.");
                } else if (oferta >= comprador.getPiezaSubastaEnCurso().getValorActualSubasta()) {
                    JOptionPane.showMessageDialog(frame,"El valor de tu oferta debe ser mayor al de la oferta actual.");
                } else {
                    comprador.hacerOfertaSubasta(oferta, metodoPago);
                }
                frame.dispose();
            }
        });

        panel.add(btnEnviarOferta);
        frame.add(panel);
        frame.setVisible(true);
    }
    private void interfazPiezasConsignadas() {
    	JFrame frame = new JFrame("Piezas Consignadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        ArrayList<Pieza> arregloRecorrible = comprador.getPiezasActuales();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (Pieza pieza : arregloRecorrible) {
        	String titulo = pieza.getTituloPieza();
        	String idPieza = pieza.getIdPieza();
        	String ubicacion = pieza.getUbicacion();
        	String anioCreacion = pieza.getAnioCreacion();
        	String nombreArtista = pieza.getNombreArtista();
        	int costoFijo = pieza.getCostoFijo();
        	String costoFijoString = String.valueOf(costoFijo);
        	String formato = ("titulo: "+titulo+", "  +" IdPieza;" + idPieza + ","+ " Ubicación:" + ubicacion+ "," +" Año creación: "+ anioCreacion + "," +" Nombre Autor:" + nombreArtista+ ","+" Costo fijo es de:"+ costoFijoString+ "\n");
            JLabel etiqueta = new JLabel(formato);
            panel.add(etiqueta);

        }            
       
        frame.add(panel);
        frame.setVisible(true);
    }
    public void interfazPiezasEstado() {
    	JFrame frame = new JFrame("Estado de piezas a consignar");
    	JPanel panel = new JPanel();
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);   
        JLabel etiquea1 = new JLabel("Estado piezas:");
    	panel.add(etiquea1);

    	if (comprador.getOfertasPendientes().isEmpty()) {
    	    	JOptionPane.showMessageDialog(frame, "Ahora mismo no tienes ofertas pendientes por ser aceptadas.");}
    	else {
    	    	for (Oferta o : comprador.getOfertasPendientes()) {
    	    		String formato = ("ID" +o.getIdOferta() +  ", " + o.getFecha()+ ". status: PENDIENTE");
    	    		JLabel etiquetaEspecifica = new JLabel(formato);
    	    		panel.add(etiquetaEspecifica);
    	    	}
    	    	}
    	 frame.add(panel);
         frame.setVisible(true);
     }

    private void interfazOfertasPendientes() {
    	JFrame frame = new JFrame("Ofertas pendientes");
    	JPanel panel = new JPanel();
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);  
        HashMap<String, Oferta> hashOfertas = administrador.getOfertasARevisar();
        Set<String> arreglo = hashOfertas.keySet();
        for (String llave : arreglo) {
        	Oferta oferta = hashOfertas.get(llave);
        	Comprador compradorOferta = oferta.getComprador();
        	String nombreComprador = compradorOferta.getNombre();
        	String fecha = oferta.getFecha();
        	String metodoPago = oferta.getMetodoPago();
        	String peticion =oferta.getPeticion();
        	String idOferta = oferta.getIdOferta();
        	Pieza pieza = oferta.getPieza();
        	if (nombreComprador == comprador.getNombre()) {
            	JLabel etiquetaOfertas = new JLabel("El id de la oferta es"+ idOferta+ ", La fecha de la oferta es: "+ fecha+", Su metodo de pago es"
        	+metodoPago+", la Pieza en cuestion es: "+ pieza.getTituloPieza() + ", Su petición es: "+ peticion+" La oferta viene de: "+ nombreComprador + "\n");
            	panel.add(etiquetaOfertas);
        	}
        }
        frame.add(panel);
        frame.setVisible(true);
    }
    private void cerrarSesion() {
        // Confirm dialog for closing session
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
    }}
    
    public static void main (String [] args) {
    
		Comprador comprador = new Comprador("Comprador1", "k", "1031", "comprador", "Cc!1", "comprador", "1312", "123", 1234);

        new ViewCompradorGUI(comprador);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewCompradorGUI(comprador).setVisible(true);
            }});
    	
    }
    
    
    
    
    
    
}