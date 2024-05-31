package view;

import model.Galeria;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.usuarios.Usuario;
import interfaz.ViewAdministradorGUI;

public class ViewLogin extends View {

    private Galeria galeria;

    public ViewLogin(Galeria galeria) {
        this.galeria = galeria;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===========================================");
        System.out.println("Bienvenido al sistema de la Galería");
        System.out.println("===========================================\n");
        if (galeria != null) {
            System.out.println("Iniciar sesión ");
            String login = getInput("\nUsuario: ");
            if (galeria.getUsuarios().containsKey(login)) {
                validarPassword(login);
                Usuario usuario = galeria.getUsuarios().get(login);
                System.out.println("\nInicio de sesión exitoso.");
                iniciarSesion(usuario);
            } else {
                usuarioNoRegistrado();
            }
        }
    }

    public void usuarioNoRegistrado() {
        boolean opcion = getInputY_N("\nEl usuario no se encuentra registrado. ¿Deseas registrarte?");
        if (opcion) {
            ViewRegistro viewRegistro = new ViewRegistro(galeria);
            viewRegistro.mostrarMenuUsuario("Comprador");
        } else {
            mostrarMenu();
        }
    }

    private void validarPassword(String login) {
        while (true) {
            String password = getInput("\nContraseña: ").trim();
            Usuario usuario = galeria.getUsuarios().get(login);
            try {
                if (password.equals(usuario.getPassword())) {
                    break;
                } else {
                    throw new IllegalArgumentException("La contraseña no es correcta");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                boolean opcion = getInputY_N("\n¿Volver?");
                if (opcion) {
                    mostrarMenu();
                    break;
                }
            }
        }
    }

    public String validarLogin() {
        while (true) {
            String login = getInput("\nNombre de usuario: ");
            try {
                if (login.equals("")) {
                    throw new IllegalArgumentException("No has ingresado información.\n");
                }
                return login;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
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
            ViewEmpleado viewEmpleado = new ViewEmpleado(empleado);
            viewEmpleado.mostrarMenu();
            break;
        case "Cajero":
            Cajero cajero = (Cajero) usuario;
            ViewCajero viewCajero = new ViewCajero(cajero);
            viewCajero.mostrarMenu();
            break;
        case "Comprador":
            Comprador comprador = (Comprador) usuario;
            ViewComprador viewComprador = new ViewComprador(comprador);
            viewComprador.mostrarMenu();
            break;
        }
    }
}
