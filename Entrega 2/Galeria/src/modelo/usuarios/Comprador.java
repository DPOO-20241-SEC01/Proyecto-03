package modelo.usuarios;

import java.util.ArrayList;
import java.util.HashMap;

import modelo.inventario.Pieza;
import modelo.subastas.Oferta;
import modelo.subastas.Subasta;
import modelo.ventas.MetodoPago;
import modelo.ventas.Venta;
import modelo.Galeria;
import java.util.Date;

public class Comprador extends Usuario {

	/*
	 * Atributos
	 */
	
	private ArrayList<Pieza> piezasActuales = new ArrayList<Pieza>();
	
	private ArrayList<Pieza> piezasPasadas = new ArrayList<Pieza>();
	
	private HashMap<String, String> facturas = new HashMap<String, String>();
	
	private ArrayList<MetodoPago> metodosPago;
	
	private int valorMaximoCompras;
	
	private int valorTotalCompras;
	
	private int saldoDisponible;
	
	private Pieza piezaSubasta;
	
	private Subasta subasta;
	
	/*
	 * Constructor
	 */
	
	public Comprador(String nombre, String apellido, String login, String password, String cedula, String tipoUsuario) {
		super(nombre, apellido, cedula, login, password, tipoUsuario);
	}

	/*
	 * Getters
	 */

	public ArrayList<Pieza> getPiezasActuales() {
		return piezasActuales;
	}
	
	public ArrayList<Pieza> getPiezasPasadas() {
		return piezasPasadas;
	}
	
	public HashMap<String, String> getFacturas() {
		return facturas;
	}
	
	public ArrayList<MetodoPago> getMetodosPago() {
		return metodosPago;
	}

	public int getValorMaximoCompras() {
		return valorMaximoCompras;
	}

	public void setValorMaximoCompras(int valorMaximoCompras) {
		this.valorMaximoCompras = valorMaximoCompras;
	}
	
	public int getValorTotalCompras() {
		return valorTotalCompras;
	}
	
	public void setValorTotalCompras(int valorCompra) {
		this.valorTotalCompras += valorCompra;
	}
	
	public void verPieza(Pieza pieza) {
		this.piezaSubasta = pieza;
	}
	
	public int getSaldoDisponible() {
		return saldoDisponible;
	}
	
	public void setSaldoDisponible() {
		int valorMaximoCompras = getValorMaximoCompras();
		int valorTotalCompras = getValorTotalCompras();
		this.saldoDisponible = valorMaximoCompras - valorTotalCompras;
	}
	/*
	 * Métodos
	 */

	public void hacerOferta(int valorOferta, int idPieza) {
		
			new Oferta(subasta, piezaSubasta, this, valorOferta);
			int pieza = Pieza.getIdPieza();
			
		}
	
	public void comprarPieza(Pieza pieza, Date fecha) {
		
		Venta.realizarVenta( pieza, this, fecha , saldoDisponible);
	}
	
	
	
}

