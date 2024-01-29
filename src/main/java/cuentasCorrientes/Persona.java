package cuentasCorrientes;

public class Persona {
    // atributos
    private int id_persona = 0;
    private int id_cuenta = 0;
    private String cod_gestor = "";
    private final String nombre;
    private final String apellido1;
    private final String apellido2;
    private final String DNI;
    private final String telefono;

    // getters y setters
    public String getNombre() {
        return nombre;
    }
    /*public void setNombre(String nombre) {
        this.nombre = nombre;
    }*/
    public String getApellido1() {
        return apellido1;
    }
    /*public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }*/
    public String getApellido2() {
        return apellido2;
    }
    /*public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }*/
    public String getDNI() {
        return DNI;
    }
    /*public void setDNI(String dniUsr) {
        if ( dniUsr.length () > 10 || dniUsr.isEmpty () ) {
            System.err.println ( "EL DNI ingresado no es válido." );
        } else {
            DNI = dniUsr;
        }
    }*/
    public String getTelefono() {
        return telefono;
    }
    /*public void setTelefono(String telefono) {
        this.telefono = telefono;
    }*/
    public int getId_persona (){return id_persona;}
    public void setId_persona(int id_titular){id_persona = id_titular;}
    public int getId_cuenta () {
        return id_cuenta;
    }
    public void setId_cuenta ( int id_cuenta ) {
        this.id_cuenta = id_cuenta;
    }
    public String getCod_gestor (){return cod_gestor;}
    public void setCod_gestor ( String idGestor){
        cod_gestor = idGestor;
    }


    // Constructores
    public Persona( String nombre, String apellido1, String apellido2, String DNI, String telefono) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.DNI = DNI;
        this.telefono = telefono;
    }

}// fin-class Persona
