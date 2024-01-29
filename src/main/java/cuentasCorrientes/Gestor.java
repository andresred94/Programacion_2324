package cuentasCorrientes;

public class Gestor {

    // ATRIBUTOS
    private static int id = 0;
    private String cod_Gestor = "";
    private String nombre = ""; // aunque sea private, se puede consultar de forma pública gracias al getter
    private static String telefono = "" ; // aunque sea private, se puede consultar de forma pública gracias al getter
    public static double importeMaxOperacion; // una cosa es que pueda ser consultado (getter) y otra que pueda ser visible o no (public o private)


    // CONSTRUCTORES
    public Gestor(){
        cod_Gestor = "SinGestor";
        importeMaxOperacion = 10000;
    }
    public Gestor( String nomUsr , String tlfUsr){
        cod_Gestor = "GST-";
        nombre = nomUsr;
        telefono = tlfUsr;
        importeMaxOperacion = 1000000;
    }

    // GETTERS Y SETTERS
    public double getImporteMaxOperacion(){
        return importeMaxOperacion;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCod_Gestor(){return cod_Gestor;}
    public String getTelefono(){
        return telefono;
    }
    public void setNombre (String nomGestor){
        nombre = nomGestor;
    }
    public void setTelefono(String telefonoGestor){
        telefono = telefonoGestor;
    }
    public void setId(int id_gestor){
        id = id_gestor;
    }
    public int getId(){
        return id;
    }

    public void setCod_Gestor ( String cod_Gestor ) {
        this.cod_Gestor = cod_Gestor;
    }
}// fin-class Gestor
