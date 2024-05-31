package model.usuarios;

import java.util.ArrayList;
import java.util.HashMap;

import model.inventario.Artista;
import model.inventario.Escultura;
import model.inventario.Fotografia;
import model.inventario.Impresion;
import model.inventario.Pieza;
import model.inventario.Pintura;
import model.inventario.Video;
import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;
import interfaz.ViewAdministradorGUI;
import view.ViewRegistro;

public class Administrador extends Empleado {

	/*
	 * Atributos
	 */
	
	private static final long serialVersionUID = 1L;

	private HashMap<String, Oferta> ofertasARevisar = new HashMap<String, Oferta>();
	
	private ArrayList<Consignacion> consignacionesARevisar = new ArrayList<Consignacion>();
	
	private transient ViewAdministradorGUI viewAdministrador;
	
	/*
	 * Constructor 
	 */

	public Administrador(String nombre, String apellido,  String cedula, String login, String password, String tipoUsuario) {
		super(nombre, apellido, cedula, login, password, tipoUsuario);
	}

	/*
	 * Gettters
	 */
	
	
	public HashMap<String, Oferta> getOfertasARevisar() {
		return ofertasARevisar;
	}


	public void setOfertasARevisar(HashMap<String, Oferta> ofertasARevisar) {
		this.ofertasARevisar = ofertasARevisar;
	}


	public ArrayList<Consignacion> getConsignacionesARevisar() {
		return consignacionesARevisar;
	}


	public void setConsignacionesARevisar(ArrayList<Consignacion> consignacionesARevisar) {
		this.consignacionesARevisar = consignacionesARevisar;
	}


	public ViewAdministradorGUI getViewAdministradorGUI() {
		return viewAdministrador;
	}


	public void setViewAdministrador(ViewAdministradorGUI viewAdministrador) {
		this.viewAdministrador = viewAdministrador;
	}
	
	/*
	 * Métodos
	 */
	
	public void registrarEmpleado() {
		 ViewRegistro viewRegistro = new ViewRegistro(galeria);
		 viewRegistro.mostrarMenuUsuario("Empleado");
	}
	
	public void ingresarPieza(Pieza pieza) {
		String tipoPieza = pieza.getTipoPieza();
		String idPieza = pieza.getIdPieza();
		HashMap<String, Pieza> piezas = galeria.getPiezasInventario().get(tipoPieza);
		piezas.put(idPieza, pieza);
		galeria.getPiezasPasadas().add(pieza);
		if (pieza.getCostoFijo() > 0) {
			ArrayList<Pieza> piezasDisponibles = galeria.getPiezasDisponibles();
			piezasDisponibles.add(pieza);
		}
		if(pieza.getUbicacion().equals("Bodega")) {
			galeria.getPiezasBodega().add(pieza);
		} else {
			galeria.getPiezasExhibidas().add(pieza);
		}
		galeria.getCentralPersistencia().guardar(galeria);
	}
	
	public void agregarPiezaAArtista(String idPieza, String tipoPieza, String nombreArtista, String nombreColectivo, boolean perteneceAColectivo) {
		Pieza pieza = galeria.getPiezaPorID(tipoPieza, idPieza);
		if (nombreArtista.equals(nombreColectivo)) {
			Artista colectivo = new Artista(nombreColectivo);
			colectivo.setColectivo(true);
			galeria.addArtista(colectivo);
			pieza.addArtista(colectivo);
			
		} else {
			Artista artista = new Artista(nombreArtista);
			galeria.addArtista(artista);
			pieza.addArtista(artista);
			if (perteneceAColectivo) {
				if(galeria.getArtistas().containsKey(nombreColectivo)) {
					Artista colectivo = galeria.getArtista(nombreArtista);
					colectivo.addArtista(artista);
					pieza.addArtista(colectivo);
				} else {
					Artista colectivo = new Artista(nombreColectivo);
					colectivo.addArtista(artista);
					galeria.addArtista(colectivo);
					pieza.addArtista(colectivo);
				}
			}
		}
		galeria.getCentralPersistencia().guardar(galeria);
	}

	public String ingresarEscultura(String ubicacion, String tituloPieza, String anioCreacion,String lugarCreacion, String nombreArtista, int costoFijo, String tipoPieza, String dimensiones, String peso, String materialesConstruccion, boolean requiereElectricidad) {
		String idPieza = String.valueOf(galeria.getPiezasPasadas().size() + 1);
		Escultura escultura = new Escultura(idPieza, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, dimensiones, peso, materialesConstruccion, requiereElectricidad);
		ingresarPieza(escultura);
		return idPieza;
	}

	public void aceptarOferta(Oferta oferta) {
		getGaleria().getCajero().getOfertasAceptadas().add(oferta);
	}
	

	public void VerificarComprador(Subasta subasta, Comprador comprador) {
		subasta.addCompradorVerificado(comprador);
	}

	public String ingresarVideo(String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion, String nombreArtista, int costoFijo, String tipoPieza, int duracion, boolean paraMayoresDe18,String resolucion, int espacioEnMemoria) {
		String idVideo= String.valueOf(galeria.getPiezasPasadas().size() + 1);
		Video video= new Video(idVideo, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, duracion, paraMayoresDe18, resolucion, espacioEnMemoria);
		ingresarPieza(video);
		return idVideo;
	}
	
	public String ingresarFotografia(String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion,String nombreArtista, int costoFijo, String tipoPieza, int alto, int ancho, String tipoFotografia,String resolucionImagen){
		String idFotografia = String.valueOf(galeria.getPiezasPasadas().size() + 1);
		Fotografia fotografia= new Fotografia(idFotografia, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, alto, ancho, tipoFotografia, resolucionImagen);
		ingresarPieza(fotografia);
		return idFotografia;
	}
	
	public String ingresarImpresion(String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion,String nombreArtista, int costoFijo, String tipoPieza, String tipoHoja, int largo, int ancho){
		String idImpresion = String.valueOf(galeria.getPiezasPasadas().size() + 1);
		Impresion Impresion= new Impresion(idImpresion, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, tipoHoja, largo, ancho);
		ingresarPieza(Impresion);
		return idImpresion;
	}
	
	public String ingresarPintura(String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion, String nombreArtista, int costoFijo, String tipoPieza, int largo, int ancho) {
		String idPintura= String.valueOf(galeria.getPiezasPasadas().size() + 1);
		Pintura pintura= new Pintura(idPintura, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, largo, ancho);
		ingresarPieza(pintura);
		return idPintura;
	}
	public void devolverPieza(Pieza pieza) {
		galeria.getPiezasDisponibles().remove(pieza);
		pieza.getPropietario().getPiezasActuales().add(pieza);
	}
	
}
	

	
	
	
	
	
	

