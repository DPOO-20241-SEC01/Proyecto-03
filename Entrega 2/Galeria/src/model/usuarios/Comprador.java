package model.usuarios;

import java.util.ArrayList;
import java.util.HashMap;

import model.inventario.Pieza;
import modelo.ventas.MetodoPago;
import model.ventas.Oferta;
import model.ventas.Subasta;

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
	
	public void hacerOfectar(int valorOferta) {
		
//			new Oferta(subasta, piezaSubasta, this, valorOferta);
		}
	
	public void comprarPieza(Pieza pieza) {
		//TODO
	}
	
	
	
}

