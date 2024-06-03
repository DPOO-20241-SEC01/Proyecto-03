package interfaz;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Galeria;
import model.persistencia.CentralPersistencia;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Empleado;
import model.ventas.Subasta; 

public class ViewEmpleadoGUI extends JFrame {
   
	private Empleado empleado;
	
	private JLabel letreroEmpleado;
	private JLabel mensajeBienvenida;
	private JLabel mensajeparaIngresarPieza;
	private JTextField ponerFecha;
	private JButton iniciarSubasta;
	private JButton cerrarSesion;
	
	
    public ViewEmpleadoGUI(Empleado empleado) {
		this.empleado = empleado;
		InicializarViewEmpleado();
	}
    
    public void InicializarViewEmpleado() {
    	setTitle("Empleado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));
        
        letreroEmpleado=new JLabel("Empleado");
        letreroEmpleado.setFont(new Font("Arial", Font.BOLD, 16));
        add(letreroEmpleado);
        
        mensajeBienvenida=new JLabel("Bienvenido, "+empleado.getNombre() +"! Ingrese la opción que desee:");
        add(mensajeBienvenida);
        
        mensajeparaIngresarPieza= new JLabel("Ingrese una fecha para iniciar una Subasta(con formato (DD-MM-YYYY)):");
        add(mensajeparaIngresarPieza);
        
        ponerFecha=new JTextField();
        add(ponerFecha);
        
        iniciarSubasta= new JButton("Iniciar Subasta");
        iniciarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = ponerFecha.getText();
                if (fecha == null || fecha.isEmpty()) {
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Error");
                    dialog.setSize(300, 100);
                    dialog.setLocationRelativeTo(null);

                    JLabel error = new JLabel("No ha puesto una fecha");
                    error.setHorizontalAlignment(JLabel.CENTER);

                    dialog.add(error);
                    dialog.setVisible(true);
                } else {
                	if(empleado.getSubastaEnCurso() != null) {
                		JDialog dialog = new JDialog();
                        dialog.setTitle("Error");
                        dialog.setSize(300, 100);
                        dialog.setLocationRelativeTo(null);

                        JLabel error = new JLabel("Ya hay una subasta en curso");
                        error.setHorizontalAlignment(JLabel.CENTER);

                        dialog.add(error);
                        dialog.setVisible(true);
                	}else {
                		iniciarSubasta(fecha);
                	}
                }
            }
        });
        
        cerrarSesion= new JButton("Cerrar");
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cerrarSesion();
            }
        });
        
        add(iniciarSubasta);
        add(cerrarSesion);
    	
    	
    }    
    public void iniciarSubasta(String fecha) {
		HashMap<String, Subasta> subastas = empleado.getGaleria().getSubastas();
		if (subastas.containsKey(fecha)) {
			Subasta subasta = subastas.get(fecha);
			if (!(subasta.getCompradoresVerificados().isEmpty()) && !(subasta.getPiezasSubasta().isEmpty())) {
				JOptionPane.showMessageDialog(this,"Se ha iniciado la subasta :).");
				subasta.iniciarSubasta();
			} else {
				JOptionPane.showMessageDialog(this, "\nNo es posible iniciar la subasta porque no hay suficientes piezas o compradores verificados.");
			}
		} else {
			JOptionPane.showMessageDialog(this,"No se encontró una subasta para la fecha.");
		}
	}
    
    private void cerrarSesion() {
        empleado.getGaleria().getCentralPersistencia().guardar(empleado.getGaleria());
        dispose();
    }
    
    public static void main(String[] args) {
        CentralPersistencia centralPersistencia = new CentralPersistencia();
        Galeria galeria;

        Object data = centralPersistencia.cargar(); 
        if (data instanceof Galeria) {
           
                    galeria = (Galeria) data;
                    galeria.setCentralPersistencia(centralPersistencia);
                    System.out.println("Galería cargada con éxito");
                    Administrador admin = new Administrador("Admin", "Admin", "1234", "admin", "password", "Administrador");
                    galeria.setAdministrador(admin);
                    Empleado empleado= new Empleado("empleado", "empleado", "12356", "Empl", "contrasenia3", "Empleado");
                    galeria.addEmpleado(empleado);
                    
                    //Comprador comprador=new Comprador("Comp", "COMP", "123", "comprador", "contrasenia1", "Comprador");
                    //Pintura pieza=new Pintura("idPieza","ubicacion","titulo","1009","lugar","nombreArtista",10, "Pintura", 39, 20);
                    //Oferta oferta=new Oferta(pieza, comprador, 10, "null", "efectivo");
                    //ArrayList<Oferta> ofertasaceptadas= cajero.getOfertasAceptadas();
                    
                    //admin.aceptarOferta(oferta);
                    //ofertasaceptadas.add(oferta);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new ViewEmpleadoGUI(empleado).setVisible(true);
                        }
                    });
                } else {
                    galeria = new Galeria();
                    centralPersistencia.guardar(galeria);
                    Administrador admin = new Administrador("Admin", "Admin", "1234", "admin", "password", "Administrador");
                    galeria.setAdministrador(admin);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new ViewAdministradorGUI(admin).setVisible(true);
                        }
                    });
                }
            }
}