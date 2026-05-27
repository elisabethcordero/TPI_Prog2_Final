
package entidades;

import enumeraciones.Rol;

/**
 * Clase que representa a los usuarios del sistema.
 */
public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String password;
    private Rol rol;

    public Usuario() {
        super();
        this.nombre = "Sin nombre";
        this.apellido = "Sin apellido";
        this.mail = "Sin mail";
        this.celular = "Sin celular";
        this.password = "";
        this.rol = Rol.USUARIO;
    }

    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String password, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - " + nombre + " " + apellido + " (" + mail + ") - Rol: " + rol;
    }
}
