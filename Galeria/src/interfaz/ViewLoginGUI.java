package interfaz;

import model.Galeria;
import model.persistencia.CentralPersistencia;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.usuarios.Usuario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import interfaz.ViewAdministradorGUI;

public class ViewLoginGUI extends JFrame {

    private Galeria galeria;
    private JFrame REGISTRO;
    
    private JLabel Letrero;
    private JLabel Bienvenido;
    private JLabel usuario;
    private JTextField txtusuario;
    private JLabel contrasena;
    private JTextField txtcontrasenia;
    private JButton inscribirse;
    private JButton ingresar;
    
    private JTextField txtnombre;
    private JTextField txtapellido;
    private JTextField txtcedula;
    

    public ViewLoginGUI(Galeria galeria) {
        this.galeria = galeria;
        initialize();
    }
 
    public JFrame getREGISTRO() {
		return REGISTRO;
	}

	public void initialize() {
    	setTitle("Galería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel arriba= new JPanel();
        arriba.setLayout(new GridLayout(2, 1));
        Letrero=new JLabel("GALERÍA");
        Letrero.setFont(new Font("Arial", Font.BOLD, 16));
        Bienvenido=new JLabel("¡Bienvenido!");
        arriba.add(Letrero);
        arriba.add(Bienvenido);
        
        add(arriba,BorderLayout.NORTH);
        
        JPanel medio= new JPanel();
        medio.setLayout(new GridLayout(2, 2));
        usuario= new JLabel("Usuario: ");
        txtusuario= new JTextField();
        contrasena= new JLabel("Contraseña: ");
        txtcontrasenia= new JTextField();
        medio.add(usuario);
        medio.add(txtusuario);
        medio.add(contrasena);
        medio.add(txtcontrasenia);
        
        add(medio,BorderLayout.CENTER);
        
        JPanel abajo= new JPanel();
        abajo.setLayout(new FlowLayout());
        inscribirse= new JButton("Registrarse");
        inscribirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	registrarUsuario();
            }
        });
        
        
        ingresar= new JButton("Ingresar");
        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (galeria != null) {
            		String usuarioo= txtusuario.getText();
            		if (galeria.getUsuarios().containsKey(usuarioo)) {
            			String contrasenausuario=txtcontrasenia.getText();
            			validarPassword(usuarioo,contrasenausuario);
                        Usuario usuario = galeria.getUsuarios().get(usuarioo);
                        iniciarSesion(usuario);
            		}else {
            			JDialog dialog = new JDialog();
                        dialog.setTitle("Error");
                        dialog.setSize(300, 100);
                        dialog.setLocationRelativeTo(null);

                        JLabel error = new JLabel("No se encontró el usuario, si no tienes un usuario deberías registrarte ");
                        error.setHorizontalAlignment(JLabel.CENTER);

                        dialog.add(error);
                        dialog.setVisible(true);
            		}
            	}
            }
        });
       abajo.add(inscribirse);
       abajo.add(ingresar);
       add(abajo,BorderLayout.SOUTH);
       
    }
	
	private void registrarUsuario() {
		JFrame frame = new JFrame("Registrar Empleado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldNombre = new JTextField();
        JTextField fieldApellido = new JTextField();
        JTextField fieldCedula = new JTextField();
        JTextField fieldLogin = new JTextField();
        JTextField fieldPassword = new JTextField();
        JTextField TipoUsuario = new JTextField();
        
        panel.add(new JLabel("Nombre:"));
        panel.add(fieldNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(fieldApellido);
        panel.add(new JLabel("Cédula:"));
        panel.add(fieldCedula);
        panel.add(new JLabel("Login:"));
        panel.add(fieldLogin);
        panel.add(new JLabel("Contraseña:"));
        panel.add(fieldPassword);
        panel.add(new JLabel("Tipo de usuario(Administrador, Empleado o Comprador)"));
        panel.add(TipoUsuario);
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = fieldNombre.getText().trim();
                String apellido = fieldApellido.getText().trim();
                String cedula = fieldCedula.getText().trim();
                String login = fieldLogin.getText().trim();
                String password = fieldPassword.getText().trim();
                String tipoUsuario=TipoUsuario.getText().trim();
                if(nombre!=null && apellido!=null && cedula!=null && login!=null && password!=null && tipoUsuario!=null) {
                	if (tipoUsuario.equals("Administrador")) {
                    	Administrador admin=new Administrador(nombre,apellido,cedula,login,password,tipoUsuario);
                    	galeria.setAdministrador(admin);
                    	JOptionPane.showMessageDialog(frame, "Administrador registrado correctamente.");
                    	galeria.getCentralPersistencia().guardar(galeria);
                        frame.dispose();
                    }else if((tipoUsuario.equals("Empleado"))) {
                    	Empleado empleado= new Empleado(nombre,apellido,cedula,login,password,tipoUsuario);
                    	galeria.addEmpleado(empleado);
                    	JOptionPane.showMessageDialog(frame, "Empleado registrado correctamente.");
                    	galeria.getCentralPersistencia().guardar(galeria);
                        frame.dispose();
                    }else if((tipoUsuario.equals("Comprador"))) {
                    	Comprador comprador= new Comprador(nombre,apellido,cedula,login,password,tipoUsuario);
                    	galeria.addUsuario(comprador);
                    	JOptionPane.showMessageDialog(frame, "Comprador registrado correctamente.");
                    	galeria.getCentralPersistencia().guardar(galeria);
                        frame.dispose();
                    	
                    }else {
                    	JOptionPane.showMessageDialog(frame, "No escribio correctamente el tipo de usuario");
                    	galeria.getCentralPersistencia().guardar(galeria);
                        frame.dispose();
                    }
                }else {
                	JOptionPane.showMessageDialog(frame, "Llene todos los cuadros");
                	frame.dispose();
                }
                
            }
        });
        panel.add(btnRegistrar);
        frame.add(panel);
        frame.setVisible(true);
        
        
	}


    private void validarPassword(String login, String password1) {
        while (true) {
            String password = password1.trim();
            Usuario usuario = galeria.getUsuarios().get(login);
            try {
                if (password.equals(usuario.getPassword())) {
                    break;
                } else {
                    throw new IllegalArgumentException("La contraseña no es correcta");
                }
            } catch (IllegalArgumentException a) {
            	JDialog dialog = new JDialog();
                dialog.setTitle("Error");
                dialog.setSize(300, 100);
                dialog.setLocationRelativeTo(null);
                setLayout(new BorderLayout());

                JLabel error = new JLabel(a.getMessage()+" Hubo problemas cierra esta ventana para intentarlo de nuevo");
                error.setHorizontalAlignment(JLabel.CENTER);
                
                JButton closeButton = new JButton("Cerrar");
                closeButton.addActionListener(e -> dispose());
                JPanel buttonPanelabajo = new JPanel();
                buttonPanelabajo.add(closeButton);
                
                dialog.add(error, BorderLayout.NORTH);
                add(buttonPanelabajo, BorderLayout.SOUTH);

                dialog.setVisible(true);
            }
        }
    }

    public void iniciarSesion(Usuario usuario) {
        String tipoUsuario = usuario.getTipoUsuario();
        switch (tipoUsuario) {
        case "Administrador":
            Administrador administrador = (Administrador) usuario;
            // Crear y mostrar la GUI para el administrador
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ViewAdministradorGUI(administrador).setVisible(true);
                }
            });
            break;
        case "Empleado":
            Empleado empleado = (Empleado) usuario;
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ViewEmpleadoGUI(empleado).setVisible(true);
                }
            });
            break;
        case "Cajero":
            Cajero cajero = (Cajero) usuario;
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ViewCajeroGUI(cajero).setVisible(true);
                }
            });
            break;
        case "Comprador":
            Comprador comprador = (Comprador) usuario;
            //ViewCompradorGUI viewComprador = new ViewCompradorGUI(comprador);
            //viewComprador.mostrarMenu();
            break;
        }
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
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new ViewLoginGUI(galeria).setVisible(true);
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