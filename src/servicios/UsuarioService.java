package servicios;

import config.Listas;
import entidades.Usuario;
import enumeraciones.Rol;
import excepciones.MailDuplicadoException;
import java.util.List;

public class UsuarioService {

    public static void crear(String nombre, String apellido, String mail, String celular, String password, Rol rol) throws MailDuplicadoException {
        if (existeMail(mail)) {
            throw new MailDuplicadoException("El mail '" + mail + "' ya está registrado en el sistema.");
        }
        Usuario nuevo = new Usuario(0L, nombre, apellido, mail, celular, password, rol);
        if (!Listas.agregarUsuario(nuevo)) {
            throw new MailDuplicadoException("El mail '" + mail + "' ya está registrado en el sistema.");
        }
    }

    public static boolean existeMail(String mail) {
        for (Usuario u : Listas.getUsuariosActivos()) {
            if (u.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }
        return false;
    }

    public static List<Usuario> obtenerActivos() {
        return Listas.getUsuariosActivos();
    }

    public static Usuario buscarPorId(Long id) {
        return Listas.buscarUsuarioPorId(id);
    }

    public static String editar(Long id, String nombre, String apellido, String mail, String celular) throws MailDuplicadoException {
        Usuario u = Listas.buscarUsuarioPorId(id);
        if (u == null) return "Usuario no encontrado.";

        if (!nombre.isEmpty()) u.setNombre(nombre);
        if (!apellido.isEmpty()) u.setApellido(apellido);
        if (!celular.isEmpty()) u.setCelular(celular);

        if (!mail.isEmpty() && !mail.equalsIgnoreCase(u.getMail())) {
            if (existeMail(mail)) {
                throw new MailDuplicadoException("El mail '" + mail + "' ya está en uso por otro usuario.");
            }
            u.setMail(mail);
        }
        return "Usuario actualizado correctamente.";
    }

    public static boolean eliminar(Long id) {
        Usuario u = Listas.buscarUsuarioPorId(id);
        if (u != null) {
            u.setEliminado(true);
            return true;
        }
        return false;
    }
}
