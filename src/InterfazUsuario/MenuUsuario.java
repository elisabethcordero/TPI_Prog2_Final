package interfazUsuario;

import servicios.UsuarioService;
import entidades.Usuario;
import enumeraciones.Rol;
import excepciones.MailDuplicadoException;
import static interfazUsuario.MenuBase.leer;

public class MenuUsuario extends MenuBase {
    private static final String REGEX_SOLO_TEXTO = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$";
    private static final String REGEX_SOLO_NUMEROS = "[0-9]+";
    
    @Override
    protected String getTitulo() {
        return "GESTIÓN DE USUARIOS";
    }

    @Override
    protected void mostrarOpciones() {
        System.out.println("1. Crear Usuario");
        System.out.println("2. Listar Usuarios");
        System.out.println("3. Editar Usuario");
        System.out.println("4. Eliminar Usuario");
    }

    @Override
    protected void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1: crear(); break;
            case 2: listar(); break;
            case 3: editar(); break;
            case 4: eliminar(); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private void crear() {
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = leer.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("Error: El nombre es obligatorio.");
            } else if (!nombre.matches(REGEX_SOLO_TEXTO)) {
                System.out.println("Error: El nombre solo puede contener letras. Por favor, reintente.");
                nombre = "";
            }
        } while (nombre.isEmpty());

        String apellido;
        do {
            System.out.print("Apellido: ");
            apellido = leer.nextLine().trim();
            if (apellido.isEmpty()) {
                System.out.println("Error: El apellido es obligatorio.");
            } else if (!apellido.matches(REGEX_SOLO_TEXTO)) {
                System.out.println("Error: El apellido solo puede contener letras. Por favor, reintente.");
                apellido = "";
            }
        } while (apellido.isEmpty());

        String mail;
        do {
            System.out.print("Mail: ");
            mail = leer.nextLine().trim();
            if (mail.isEmpty()) {
                System.out.println("Error: El mail es obligatorio.");
            } else if (mail.contains(" ")) {
                System.out.println("Error: El mail no puede contener espacios.");
                mail = ""; 
            } else if (!mail.contains("@")) {
                System.out.println("Error: El mail debe contener el símbolo '@'.");
                mail = ""; 
            } else if (UsuarioService.existeMail(mail)) {
                System.out.println("Error: El mail ya existe en el sistema. Por favor, ingrese uno diferente.");
                mail = ""; // Vaciamos para que el bucle vuelva a pedirlo
            }
        } while (mail.isEmpty());

        String celular;
        do {
            System.out.print("Celular (solo números): ");
            celular = leer.nextLine().trim();
            if (celular.isEmpty()) {
                System.out.println("Error: El celular es obligatorio.");
            } else if (!celular.matches(REGEX_SOLO_NUMEROS)) {
                System.out.println("Error: El celular solo debe contener números. Por favor, reintente.");
                celular = ""; 
            }
        } while (celular.isEmpty());

        String pass;
        do {
            System.out.print("Password (debe contener al menos 4 caracteres): ");
            pass = leer.nextLine().trim();
            if (pass.length() < 4) {
                System.out.println("Error: La contraseña debe tener al menos 4 caracteres.");
                pass = ""; 
            }
        } while (pass.isEmpty());
        
        System.out.println("Elija el Rol: 1. ADMIN, 2. USUARIO");
        int opRol = Integer.parseInt(leer.nextLine());
        Rol rol = (opRol == 1) ? Rol.ADMIN : Rol.USUARIO;
        
        try {
            UsuarioService.crear(nombre, apellido, mail, celular, pass, rol);
            System.out.println("¡Usuario creado!");
        } catch (MailDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE USUARIOS ACTIVOS ---");
        var activos = UsuarioService.obtenerActivos();
        if (activos.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario u : activos) System.out.println(u);
        }
    }

    private void editar() {
        listar();
        if (UsuarioService.obtenerActivos().isEmpty()) return;

        System.out.print("ID del usuario a editar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            
            System.out.print("Nuevo nombre: "); 
            String nom = leer.nextLine().trim();
            if (!nom.isEmpty() && !nom.matches(REGEX_SOLO_TEXTO)) {
                System.out.println("Error: El nombre solo puede contener letras. No se modificará.");
                nom = "";
            }

            System.out.print("Nuevo apellido: "); 
            String ape = leer.nextLine().trim();
            if (!ape.isEmpty() && !ape.matches(REGEX_SOLO_TEXTO)) {
                System.out.println("Error: El apellido solo puede contener letras. No se modificará.");
                ape = "";
            }

            System.out.print("Nuevo mail: "); String mail = leer.nextLine().trim();
            System.out.print("Nuevo celular: "); String cel = leer.nextLine().trim();

            System.out.println(UsuarioService.editar(id, nom, ape, mail, cel));
        } catch (MailDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al editar.");
        }
    }

    private void eliminar() {
        listar();
        if (UsuarioService.obtenerActivos().isEmpty()) return;
        
        System.out.print("ID del usuario a eliminar: ");
        try {
            Long id = Long.parseLong(leer.nextLine());
            Usuario u = UsuarioService.buscarPorId(id);
            if (u != null) {
                System.out.print("¿Desea eliminar a '" + u.getNombre() + "'? (S/N): ");
                if (leer.nextLine().equalsIgnoreCase("S")) {
                    UsuarioService.eliminar(id);
                    System.out.println("Usuario eliminado.");
                }
            } else {
                System.out.println("Error: ID inexistente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID ingresado no es válido (debe ser un número).");
        } catch (Exception e) {
            System.out.println("Error al eliminar.");
        }
    }
}
