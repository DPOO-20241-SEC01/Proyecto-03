package model;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import model.ventas.VentasAnuales;
import model.inventario.Artista;
import model.inventario.Pieza;
import model.persistencia.CentralPersistencia;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Empleado;
import model.usuarios.Usuario;
import model.ventas.Consignacion;
import interfaz.VentasObserver;
import model.ventas.Subasta;
import model.ventas.Venta;
import view.ViewEmpleado;
import view.ViewRegistro;
import interfaz.*;

public class Galeria implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<VentasObserver> ventasObservers;
    private HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
    private Administrador administrador;
    private Cajero cajero;
    private HashMap<String, Empleado> empleados = new HashMap<String, Empleado>();
    private ArrayList<Empleado> empleadosDisponibles = new ArrayList<Empleado>();

    private HashMap<String, HashMap<String, Pieza>> piezasInventario = new HashMap<>();
    private ArrayList<Pieza> piezasExhibidas = new ArrayList<Pieza>();
    private ArrayList<Pieza> piezasBodega = new ArrayList<Pieza>();
    private ArrayList<Pieza> piezasPasadas = new ArrayList<Pieza>();
    private ArrayList<Pieza> piezasDisponibles = new ArrayList<Pieza>();
    private HashMap<String, Artista> artistas = new HashMap<String, Artista>();

    private HashMap<String, Venta> ventas = new HashMap<String, Venta>();
    private HashMap<String, Subasta> subastas = new HashMap<String, Subasta>();
    private ArrayList<Consignacion> consignaciones = new ArrayList<Consignacion>();

    private transient CentralPersistencia centralPersistencia = new CentralPersistencia();

    private transient ViewRegistro viewRegistro;

    public Galeria() {
        piezasInventario.put("Escultura", new HashMap<String, Pieza>());
        piezasInventario.put("Pintura", new HashMap<String, Pieza>());
        piezasInventario.put("Impresión", new HashMap<String, Pieza>());
        piezasInventario.put("Fotografía", new HashMap<String, Pieza>());
        piezasInventario.put("Vídeos", new HashMap<String, Pieza>());
        this.ventasObservers = new ArrayList<>();
        viewRegistro = new ViewRegistro(this);
        viewRegistro.mostrarMenuUsuario("Administrador");
    }

    // Método readObject para inicializar ventasObservers durante la deserialización
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (this.ventasObservers == null) {
            this.ventasObservers = new ArrayList<>();
        }
        if (this.centralPersistencia == null) {
            this.centralPersistencia = new CentralPersistencia();
        }
    }

    // Otros métodos...

    public void addVenta(Venta venta) {
        ventas.put(venta.getIdVenta(), venta);
        notifyVentasObservers();
    }

    public void addVentasObserver(VentasObserver observer) {
        ventasObservers.add(observer);
    }

    private void notifyVentasObservers() {
        for (VentasObserver observer : ventasObservers) {
            observer.onVentaRegistrada();
        }
    }

    // Otros getters y setters...
    // Administrador
    public Administrador getAdminstrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        administrador.setGaleria(this);
        addUsuario(administrador);
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }
    public void addUsuario(Usuario usuario) {
	    if (usuario == null) {
	        throw new IllegalArgumentException("Usuario cannot be null.");
	    }
	    if (usuario.getLogin() == null) {
	        throw new IllegalArgumentException("Login cannot be null.");
	    }
	    this.usuarios.put(usuario.getLogin(), usuario);

	    if (this.centralPersistencia == null) {
	        this.centralPersistencia = new CentralPersistencia();
	    }

	    this.centralPersistencia.guardar(this);
	}
    public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}
    public void addEmpleado(Empleado empleado) {
        ViewEmpleado viewEmpleado = new ViewEmpleado(empleado);
        empleado.setViewEmpleado(viewEmpleado);
        empleado.setGaleria(this);
        this.empleados.put(empleado.getCedula(), empleado);
        empleadosDisponibles.add(empleado);
        addUsuario(empleado);
        centralPersistencia.guardar(this);
    }

    public Empleado getEmpleado(String numeroCedula) {
        return empleados.get(numeroCedula);
    }

    public ArrayList<Empleado> getEmpleadosDisponibles() {
        return empleadosDisponibles;
    }

    // Piezas inventario
    public Pieza getPiezaPorID(String tipoPieza, String idPieza) {
        if (piezasInventario.containsKey(tipoPieza)) {
            return piezasInventario.get(tipoPieza).get(idPieza);
        }
        return null;
    }

    public HashMap<String, HashMap<String, Pieza>> getPiezasInventario() {
        return piezasInventario;
    }

    public void setPiezasInventario(HashMap<String, HashMap<String, Pieza>> piezasInventario) {
        this.piezasInventario = piezasInventario;
    }

    public ArrayList<Pieza> getPiezasExhibidas() {
        return piezasExhibidas;
    }

    public void setPiezasExhibidas(ArrayList<Pieza> piezasExhibidas) {
        this.piezasExhibidas = piezasExhibidas;
    }

    public ArrayList<Pieza> getPiezasBodega() {
        return piezasBodega;
    }

    public void setPiezasBodega(ArrayList<Pieza> piezasBodega) {
        this.piezasBodega = piezasBodega;
    }

    public ArrayList<Pieza> getPiezasPasadas() {
        return piezasPasadas;
    }

    public void setPiezasPasadas(ArrayList<Pieza> piezasPasadas) {
        this.piezasPasadas = piezasPasadas;
    }

    public ArrayList<Pieza> getPiezasDisponibles() {
        return piezasDisponibles;
    }

    public void addPiezasDisponibles(Pieza pieza) {
        this.piezasDisponibles.add(pieza);
        centralPersistencia.guardar(this);
    }

    public HashMap<String, Artista> getArtistas() {
        return artistas;
    }

    public void addArtista(Artista artista) {
        this.artistas.put(artista.getNombre(), artista);
        centralPersistencia.guardar(this);
    }

    public Artista getArtista(String nombreArtista) {
        return artistas.get(nombreArtista);
    }

    public HashMap<String, Subasta> getSubastas() {
        return subastas;
    }

    public HashMap<String, Venta> getVentas() {
        return ventas;
    }

    public void setVentas(HashMap<String, Venta> ventas) {
        this.ventas = ventas;
    }

    public ArrayList<Consignacion> getConsignaciones() {
        return consignaciones;
    }

    public void setConsignaciones(ArrayList<Consignacion> consignaciones) {
        this.consignaciones = consignaciones;
    }

    public CentralPersistencia getCentralPersistencia() {
        return centralPersistencia;
    }

    public void setCentralPersistencia(CentralPersistencia centralPersistencia) {
        this.centralPersistencia = centralPersistencia;
    }

    public ViewRegistro getViewRegistro() {
        return viewRegistro;
    }

    public void setViewRegistro(ViewRegistro viewRegistro) {
        this.viewRegistro = viewRegistro;
    }

    public boolean crearSubasta(String fecha, Empleado empleado) {
        if (piezasDisponibles.size() >= 5) {
            Collections.shuffle(piezasDisponibles);
            Subasta subasta = new Subasta(fecha, empleado);

            for (int i = 0; i < 5; i++) {
                subasta.addPiezasSubasta(piezasDisponibles.get(i));
            }

            subastas.put(fecha, subasta);
            centralPersistencia.guardar(this);
            return true;
        } else {
            return false;
        }
    }

    public void guardarVenta(String idVenta, Venta venta) {
        ventas.put(idVenta, venta);
        centralPersistencia.guardar(this);
    }

    public String generarLogin(String nombre, String apellido) {
        String login = nombre.substring(0, 1).toLowerCase() + "." + apellido.toLowerCase();
        int i = 1;
        while (usuarios.containsKey(login)) {
            login = login + i;
            i++;
        }
        return login;
    }

    public String generarPassword() {
        Random random = new Random();
        String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minisculas = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String simbolos = "!@#$%^&*";

        char first = mayusculas.charAt(random.nextInt(mayusculas.length()));
        char second = minisculas.charAt(random.nextInt(minisculas.length()));
        char third = minisculas.charAt(random.nextInt(minisculas.length()));
        char fourth = numeros.charAt(random.nextInt(numeros.length()));
        char fifth = simbolos.charAt(random.nextInt(simbolos.length()));

        return "" + first + second + third + fourth + fifth;
    }

    public void removeEmpleado(Empleado empleado) {
        empleadosDisponibles.remove(empleado);
        centralPersistencia.guardar(this);
    }
}
