package cuentasCorrientes;

public class Persona {
    // atributos
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String DNI;
    private String telefono;

    // getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String dniUsr) {
        if ( dniUsr.length () > 10 || dniUsr.isEmpty () ) {
            System.err.println ( "EL DNI ingresado no es válido." );
        } else {
            DNI = dniUsr;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Constructores
    public Persona () {
    }
    public Persona( String nombre, String apellido1, String apellido2, String DNI, String telefono) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.DNI = DNI;
        this.telefono = telefono;
    }
    public Persona(String dniUser){
        this.DNI = dniUser;
    }

}// fin-class Persona
