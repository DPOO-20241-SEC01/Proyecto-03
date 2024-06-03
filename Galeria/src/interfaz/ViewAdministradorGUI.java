package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Galeria;
import model.usuarios.Administrador;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.usuarios.Cajero;
import model.ventas.Oferta;
import model.ventas.Subasta;
import java.util.*;
import model.persistencia.CentralPersistencia;

public class ViewAdministradorGUI extends JFrame {

    static Administrador administrador;

    public ViewAdministradorGUI(Administrador administrador) {
        this.administrador = administrador;
        initialize();
    }

    private void initialize() {
        setTitle("Administrador - Galería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Bienvenido, Administrador!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel);

        JButton btnIngresarPieza = new JButton("Ingresar pieza a inventario");
        JButton btnConfigurarSubasta = new JButton("Configurar subasta");
        JButton btnIniciarSubasta = new JButton("Iniciar subasta");
        JButton btnVerificarComprador = new JButton("Verificar comprador");
        JButton btnRevisarOfertas = new JButton("Revisar ofertas pendientes");
        JButton btnRegistrarEmpleado = new JButton("Registrar empleado");
        JButton btnConfigurarCajero = new JButton("Configurar cajero");
        JButton btnVerVentasAnuales = new JButton("Ver ventas anuales");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        btnIngresarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarPiezaAInventario();
            }
        });

        btnConfigurarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarSubasta();
            }
        });

        btnIniciarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSubasta();
            }
        });

        btnVerificarComprador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarComprador();
            }
        });

        btnRevisarOfertas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revisarOfertasPendientes();
            }
        });

        btnRegistrarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEmpleado();
            }
        });

        btnConfigurarCajero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarCajero();
            }
        });

        btnVerVentasAnuales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verVentasAnuales();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        panel.add(btnIngresarPieza);
        panel.add(btnConfigurarSubasta);
        panel.add(btnIniciarSubasta);
        panel.add(btnVerificarComprador);
        panel.add(btnRevisarOfertas);
        panel.add(btnRegistrarEmpleado);
        panel.add(btnConfigurarCajero);
        panel.add(btnVerVentasAnuales);
        panel.add(btnCerrarSesion);

        add(panel);
    }

    private void verVentasAnuales() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewVentasAnualesGUI(administrador.getGaleria()).setVisible(true);
            }
        });
    }

    private void ingresarPiezaAInventario() {
        JFrame frame = new JFrame("Ingresar pieza a inventario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldTitulo = new JTextField();
        JTextField fieldAnio = new JTextField();
        JTextField fieldLugar = new JTextField();
        JTextField fieldArtista = new JTextField();
        JTextField fieldColectivo = new JTextField();
        JTextField fieldCosto = new JTextField();
        
        String[] ubicaciones = {"Exhibición", "Bodega"};
        JComboBox<String> comboUbicacion = new JComboBox<>(ubicaciones);
        
        String[] tipos = {"Escultura", "Pintura", "Impresión", "Fotografía", "Vídeos"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        
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
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener datos de los campos de texto
                String titulo = fieldTitulo.getText();
                String anio = fieldAnio.getText();
                String lugar = fieldLugar.getText();
                String artista = fieldArtista.getText();
                String colectivo = fieldColectivo.getText();
                String ubicacion = (String) comboUbicacion.getSelectedItem();
                String tipo = (String) comboTipo.getSelectedItem();
                int costo = Integer.parseInt(fieldCosto.getText());

                // Llamar al método correspondiente del administrador
                switch(tipo) {
                    case "Escultura":
                        String dimensiones = JOptionPane.showInputDialog("Dimensiones (alto-largo-ancho en cm):");
                        String peso = JOptionPane.showInputDialog("Peso (kg):");
                        String materiales = JOptionPane.showInputDialog("Materiales de construcción:");
                        boolean requiereElectricidad = JOptionPane.showConfirmDialog(frame, "¿Requiere electricidad?") == JOptionPane.YES_OPTION;
                        administrador.ingresarEscultura(ubicacion, titulo, anio, lugar, artista, costo, tipo, dimensiones, peso, materiales, requiereElectricidad);
                        break;
                    case "Pintura":
                        int largoP = Integer.parseInt(JOptionPane.showInputDialog("Largo (cm):"));
                        int anchoP = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        administrador.ingresarPintura(ubicacion, titulo, anio, lugar, artista, costo, tipo, largoP, anchoP);
                        break;
                    case "Impresión":
                        String tipoHoja = JOptionPane.showInputDialog("Tipo de hoja:");
                        int largoI = Integer.parseInt(JOptionPane.showInputDialog("Largo (cm):"));
                        int anchoI = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        administrador.ingresarImpresion(ubicacion, titulo, anio, lugar, artista, costo, tipo, tipoHoja, largoI, anchoI);
                        break;
                    case "Fotografía":
                        String tipoFoto = JOptionPane.showInputDialog("Tipo de fotografía:");
                        int altoF = Integer.parseInt(JOptionPane.showInputDialog("Alto (cm):"));
                        int anchoF = Integer.parseInt(JOptionPane.showInputDialog("Ancho (cm):"));
                        String resolucion = JOptionPane.showInputDialog("Resolución (PPI):");
                        administrador.ingresarFotografia(ubicacion, titulo, anio, lugar, artista, costo, tipo, altoF, anchoF, tipoFoto, resolucion);
                        break;
                    case "Vídeos":
                        int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración (minutos):"));
                        int pesoV = Integer.parseInt(JOptionPane.showInputDialog("Peso (MB):"));
                        boolean mayoresDeEdad = JOptionPane.showConfirmDialog(frame, "¿Para mayores de 18?") == JOptionPane.YES_OPTION;
                        String resolucionVideo = JOptionPane.showInputDialog("Resolución (PPI):");
                        administrador.ingresarVideo(ubicacion, titulo, anio, lugar, artista, costo, tipo, duracion, mayoresDeEdad, resolucionVideo, pesoV);
                        break;
                }

                JOptionPane.showMessageDialog(frame, "Pieza ingresada con éxito.");
                frame.dispose();
            }
        });
        
        panel.add(btnGuardar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void configurarSubasta() {
        JFrame frame = new JFrame("Configurar Subasta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldFecha = new JTextField();
        JComboBox<Empleado> comboOperador = new JComboBox<>(new DefaultComboBoxModel<>(administrador.getGaleria().getEmpleadosDisponibles().toArray(new Empleado[0])));
        
        panel.add(new JLabel("Fecha (DD-MM-YYYY):"));
        panel.add(fieldFecha);
        panel.add(new JLabel("Operador disponible:"));
        panel.add(comboOperador);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = fieldFecha.getText();
                Empleado operador = (Empleado) comboOperador.getSelectedItem();
                
                if (operador != null) {
                    boolean subastaCreada = administrador.getGaleria().crearSubasta(fecha, operador);
                    if (subastaCreada) {
                        JOptionPane.showMessageDialog(frame, "Subasta creada para " + fecha);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No hay suficientes piezas disponibles para crear una subasta con 5 piezas.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo asignar un operador para la subasta.");
                }
                frame.dispose();
            }
        });
        
        panel.add(btnGuardar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void iniciarSubasta() {
        JFrame frame = new JFrame("Iniciar Subasta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldFecha = new JTextField();
        
        panel.add(new JLabel("Fecha (DD-MM-YYYY):"));
        panel.add(fieldFecha);
        
        JButton btnIniciar = new JButton("Iniciar Subasta");
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = fieldFecha.getText();
                HashMap<String, Subasta> subastas = administrador.getGaleria().getSubastas();
                
                if (subastas.containsKey(fecha)) {
                    Subasta subasta = subastas.get(fecha);
                    if (!(subasta.getCompradoresVerificados().isEmpty()) && !(subasta.getPiezasSubasta().isEmpty())) {
                        subasta.iniciarSubasta();
                        JOptionPane.showMessageDialog(frame, "Se ha iniciado la subasta.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No es posible iniciar la subasta porque no hay suficientes piezas o compradores verificados.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No se encontró una subasta para la fecha.");
                }
                frame.dispose();
            }
        });
        
        panel.add(btnIniciar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void verificarComprador() {
        JFrame frame = new JFrame("Verificar Comprador");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldFecha = new JTextField();
        JComboBox<Comprador> comboComprador = new JComboBox<>(new DefaultComboBoxModel<>(administrador.getGaleria().getUsuarios().values().stream()
                .filter(usuario -> usuario instanceof Comprador)
                .toArray(Comprador[]::new)));
        
        panel.add(new JLabel("Fecha (DD-MM-YYYY):"));
        panel.add(fieldFecha);
        panel.add(new JLabel("Compradores disponibles:"));
        panel.add(comboComprador);
        
        JButton btnVerificar = new JButton("Verificar Comprador");
        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = fieldFecha.getText();
                Comprador comprador = (Comprador) comboComprador.getSelectedItem();
                HashMap<String, Subasta> subastas = administrador.getGaleria().getSubastas();
                
                if (subastas.containsKey(fecha)) {
                    Subasta subasta = subastas.get(fecha);
                    if (subasta.addCompradorVerificado(comprador)) {
                        administrador.getGaleria().getCentralPersistencia().guardar(administrador.getGaleria());
                        JOptionPane.showMessageDialog(frame, comprador.getNombre() + " " + comprador.getApellido() +
                                " fue agregado exitosamente a la subasta del " + fecha + ".");
                    } else {
                        JOptionPane.showMessageDialog(frame, "El comprador ya está verificado en la subasta del " + fecha + ".");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No se encontró una subasta para la fecha indicada.");
                }
                frame.dispose();
            }
        });
        
        panel.add(btnVerificar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void revisarOfertasPendientes() {
        JFrame frame = new JFrame("Revisar Ofertas Pendientes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel panel = new JPanel(new BorderLayout());

        JLabel OfertasLabel = new JLabel("Las ofertas disponibles son:", JLabel.CENTER);
        OfertasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(OfertasLabel, BorderLayout.NORTH);

        JPanel ofertasPanel = new JPanel(new GridLayout(0, 1));
        HashMap<String, Oferta> ofertas = administrador.getOfertasARevisar();
        
        for (Oferta oferta : ofertas.values()) {
            JPanel ofertaPanel = new JPanel(new BorderLayout());
            ofertaPanel.add(new JLabel("ID: " + oferta.getIdOferta() + ". Monto: " + oferta.getValorOferta()), BorderLayout.CENTER);
            JButton btnAceptar = new JButton("Aceptar");
            JButton btnRechazar = new JButton("Rechazar");
            
            btnAceptar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    administrador.aceptarOferta(oferta);
                    JOptionPane.showMessageDialog(frame, "Oferta aceptada correctamente.");
                }
            });
            
            btnRechazar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Oferta rechazada.");
                }
            });

            JPanel botonesPanel = new JPanel();
            botonesPanel.add(btnAceptar);
            botonesPanel.add(btnRechazar);
            
            ofertaPanel.add(botonesPanel, BorderLayout.SOUTH);
            ofertasPanel.add(ofertaPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(ofertasPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        frame.add(panel);
        frame.setVisible(true);
    }


    private void registrarEmpleado() {
        JFrame frame = new JFrame("Registrar Empleado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JTextField fieldNombre = new JTextField();
        JTextField fieldApellido = new JTextField();
        JTextField fieldCedula = new JTextField();
        JTextField fieldLogin = new JTextField();
        JTextField fieldPassword = new JTextField();
        
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
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = fieldNombre.getText();
                String apellido = fieldApellido.getText();
                String cedula = fieldCedula.getText();
                String login = fieldLogin.getText();
                String password = fieldPassword.getText();
                
                Empleado nuevoEmpleado = new Empleado(nombre, apellido, cedula, login, password, "Empleado");
                administrador.getGaleria().addEmpleado(nuevoEmpleado);
                JOptionPane.showMessageDialog(frame, "Empleado registrado correctamente.");
                frame.dispose();
            }
        });
        
        panel.add(btnRegistrar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void configurarCajero() {
        JFrame frame = new JFrame("Configurar Cajero");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JComboBox<Empleado> comboEmpleado = new JComboBox<>(new DefaultComboBoxModel<>(administrador.getGaleria().getEmpleadosDisponibles().toArray(new Empleado[0])));
        
        panel.add(new JLabel("Empleados disponibles:"));
        panel.add(comboEmpleado);
        
        JButton btnConfigurar = new JButton("Configurar Cajero");
        btnConfigurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = (Empleado) comboEmpleado.getSelectedItem();
                
                if (empleado != null) {
                    Cajero cajero = new Cajero(empleado.getNombre(), empleado.getApellido(), empleado.getLogin(), empleado.getPassword(), empleado.getCedula(), "Cajero");
                    administrador.getGaleria().setCajero(cajero);
                    administrador.getGaleria().removeEmpleado(empleado);
                    administrador.getGaleria().getCentralPersistencia().guardar(administrador.getGaleria());
                    JOptionPane.showMessageDialog(frame, "Empleado configurado como Cajero exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo configurar el empleado como Cajero.");
                }
                frame.dispose();
            }
        });
        
        panel.add(btnConfigurar);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void cerrarSesion() {
        administrador.getGaleria().getCentralPersistencia().guardar(administrador.getGaleria());
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
            SwingUtilities.invokeLater(() -> new ViewAdministradorGUI(admin).setVisible(true));
        } else {
            galeria = new Galeria();
            centralPersistencia.guardar(galeria);
            Administrador admin = new Administrador("Admin", "Admin", "1234", "admin", "password", "Administrador");
            galeria.setAdministrador(admin);
            SwingUtilities.invokeLater(() -> new ViewAdministradorGUI(admin).setVisible(true));
        }
    }
}
