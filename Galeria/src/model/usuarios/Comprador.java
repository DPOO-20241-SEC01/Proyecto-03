package model.usuarios;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.inventario.Pieza;
//import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;
import view.ViewComprador;

public class Comprador extends Usuario {

	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Arreglo con las piezas que actualmente son propiedad del usuario.
	 */
	private ArrayList<Pieza> piezasActuales = new ArrayList<Pieza>();
	
	
	/**
	 * Arreglo con todas las piezas que han sido propiedad del usuario.
	 */
	private ArrayList<Pieza> piezasPasadas = new ArrayList<Pieza>();
	
//	private HashMap<String, String> facturas = new HashMap<String, String>();
	
	/**
	 * Valor máximo de compras establecido para el usuario.
	 */
	private int valorMaximoCompras;
	
	
	/**
	 * Valor acumulado de las compras que ha realizado el usuario.
	 */
	private int totalComprasRealizadas;
	
	
	/**
	 * Valor del saldo disponible que el usuario tiene para realizar compras u ofertas
	 * dentro del sistema
	 */
	private int saldoDisponible;
	
	
	/**
	 * Representación de una subasta que se realiza en ese instante
	 * y de la que el usuario hace parte.
	 */
	private Subasta subastaEnCurso;
	
	
	/**
	 * La pieza de la subasta en curso que se está ofreciendo en
	 * ese instante.
	 */
	private Pieza piezaSubastaEnCurso;
	
//	private ArrayList<Consignacion> consignaciones = new  ArrayList<Consignacion>();
	
	/**
	 * Arreglo con las ofertas que el usuario a realizado pero que aún 
	 * no se han procesado como una compra. Puede que esas ofertas sean rechazadas. 
	 */
	private ArrayList<Oferta> ofertasPendientes = new  ArrayList<Oferta>();
	
	private String tarjetaCredito;
	private String cvcTarjetaCredito;
	private int saldoTarjetaCredito;
	
	/**
	 * El view del usuario de tipo "Comprador"
	 */
	private transient ViewComprador viewComprador;
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param apellido
	 * @param cedula
	 * @param login
	 * @param password
	 * @param tipoUsuario
	 */
	public Comprador(String nombre, String apellido,  String cedula ,String login, String password, String tipoUsuario, String tarjetaCredito, String cvcTarjetaCredito
			, int saldoTarjetaCredito) {
		super(nombre, apellido, cedula, login, password, tipoUsuario);
		this.valorMaximoCompras = 200000;
		setSaldoDisponible();
	}

	
	public ArrayList<Pieza> getPiezasActuales() {
		return piezasActuales;
	}
	
	
	public ArrayList<Pieza> getPiezasPasadas() {
		return piezasPasadas;
	}
	
	
//	public HashMap<String, String> getFacturas() {
//		return facturas;
//	}
	
	public int getValorMaximoCompras() {
		return valorMaximoCompras;
	}

	
	public void setValorMaximoCompras(int valorMaximoCompras) {
		this.valorMaximoCompras = valorMaximoCompras;
		setSaldoDisponible();
	}
	
	public int getTotalComprasRealizadas() {
		return totalComprasRealizadas;
	}
	
	
	public void setgetTotalComprasRealizadas(int valorCompra) {
		this.totalComprasRealizadas += valorCompra;
	}
	
	
	public int getSaldoDisponible() {
		return saldoDisponible;
	}
	
	public void setSaldoDisponible() {
		int valorMaximoCompras = getValorMaximoCompras();
		int totalComprasRealizadas = getTotalComprasRealizadas();
		this.saldoDisponible = valorMaximoCompras - totalComprasRealizadas;
	}
	
	public void setPiezaSubastaEnCurso(Pieza pieza) {
		this.piezaSubastaEnCurso = pieza;
	}
	
	
	public Pieza getPiezaSubastaEnCurso() {
		return piezaSubastaEnCurso;
	}
	
	
	
	
	public Subasta getSubastaEnCurso() {
		return subastaEnCurso;
	}

	public void setSubastaEnCurso(Subasta subastaEnCurso) {
		this.subastaEnCurso = subastaEnCurso;
	}
	
	

	public ViewComprador getViewComprador() {
		return viewComprador;
	}

	public void setViewComprador(ViewComprador viewComprador) {
		this.viewComprador = viewComprador;
	}
	
	
	public ArrayList<Oferta> getOfertasPendientes() {
		return ofertasPendientes;
	}

	/*
	 * Métodos
	 */

	public void hacerOfertaSubasta(int valorOferta, String metodoPago) {
		Pieza pieza = getPiezaSubastaEnCurso();
		String peticion = null;
		Oferta oferta = new Oferta(pieza, this, valorOferta, peticion, metodoPago);
		getSubastaEnCurso().addOferta(oferta);
		ofertasPendientes.add(oferta);
	}
	
	public void comprarPieza(String tipoPieza, String idPieza, int valorOferta, String peticion, String metodoPago) {
		Pieza pieza = galeria.getPiezaPorID(tipoPieza, idPieza);
		Oferta oferta = new Oferta(pieza, this, valorOferta, peticion, metodoPago);
		
		if (peticion != null) {
			galeria.getAdminstrador().getOfertasARevisar().put(oferta.getIdOferta(), oferta);
			oferta.getPieza().setPropietario(this);
		} else {
			galeria.getCajero().getOfertasAceptadas().add(oferta);
			oferta.getPieza().setPropietario(this);
		
		
		if (metodoPago == "Tarjeta Credito") {
			JFrame ventanaPago = new JFrame();
			ventanaPago.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventanaPago.setSize(400, 300);
			ventanaPago.setLocationRelativeTo(null);
			JPanel panelTarjeta = new JPanel();
			JLabel etiquetaNumeroTarje = new JLabel("Ingrese su numero de tarjeta: ");
			JLabel  etiquetaCVC = new JLabel("Ingrese el cvc de la tarjeta (Numeros de atras)");
			JTextField numeroTarjeta = new JTextField();
			JTextField numeroCVC = new JTextField();
			GridLayout layoutTop = new GridLayout(0,4);
			JButton botonComprar = new JButton("Comprar");
			panelTarjeta.setLayout(layoutTop);
			panelTarjeta.add(etiquetaNumeroTarje);
			panelTarjeta.add(numeroTarjeta);
			panelTarjeta.add(etiquetaCVC);
			panelTarjeta.add(numeroCVC);
			ventanaPago.add(panelTarjeta);
			ventanaPago.add(botonComprar);
			
			botonComprar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String numeros =  numeroTarjeta.getText();
					String numerosCVC = numeroCVC.getText();
					if (numeros== tarjetaCredito) {
						if (numerosCVC == cvcTarjetaCredito) {
							if (valorOferta >=saldoTarjetaCredito ) {
                            saldoTarjetaCredito -= valorOferta;
	                        JOptionPane.showMessageDialog(ventanaPago, "Su transacción fue aprobada por: " + valorOferta );
	                        galeria.getCajero().getOfertasAceptadas().add(oferta);
                            ventanaPago.dispose(); 
                            String[] options = {"JSON", "TXT", "XML"};
                            String formato = (String) JOptionPane.showInputDialog(ventanaPago, "Seleccione el formato del archivo:","Formato de Archivo", JOptionPane.PLAIN_MESSAGE, null,options,options[0]);

                            if (formato != null) {
                                switch (formato) {
                                    case "TXT":
                                        generarTXT(oferta);
                                        break;
                                    case "XML":
                                        generarXMLe(oferta);
                                        break;
                                }
                            }

                        } else {
                            JOptionPane.showMessageDialog(ventanaPago, "Su transacción no fue aprobada por falta de cupo en la tarjeta");
                        }
                    } else {
                        JOptionPane.showMessageDialog(ventanaPago, "Su transacción no fue aprobada por error en el CVC de la tarjeta");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventanaPago, "Su transacción no fue aprobada por errores al ingreso de los datos");
                }
            }
        });
    }}
}

private void generarTXT(Oferta oferta) {
    String invoice = "Cliente: " + oferta.getComprador().getNombre() + "\n" +
                     "Producto: " + oferta.getPieza().getTituloPieza() + "\n" +
                     "Cantidad: 1\n" +
                     "Precio: " + oferta.getValorOferta();

    try (FileWriter file = new FileWriter("invoice.txt")) {
        file.write(invoice);
        file.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void generarXMLe(Oferta oferta) {
    String invoice = "<invoice>\n" +
                     "    <cliente>" + oferta.getComprador().getNombre() + "</cliente>\n" +
                     "    <producto>" + oferta.getPieza().getTituloPieza() + "</producto>\n" +
                     "    <cantidad>1</cantidad>\n" +
                     "    <precio>" + oferta.getValorOferta() + "</precio>\n" +
                     "</invoice>";

    try (FileWriter file = new FileWriter("invoice.xml")) {
        file.write(invoice);
        file.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}