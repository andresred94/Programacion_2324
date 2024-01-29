package cuentasCorrientes;

import com.ecodeup.jdbc.ConexionABdD;

import java.util.Scanner;

// ToDo Crear Clase Persona que se relaciona con clase bancaria
public class CuentasCorrientes {
    // atributos
    private static int numCuenta = 1;
    private static String cod_gestor = "";
    private static int id_titular = 1;
    private static Gestor GESTOR;
    private static Persona PERSONA;
    private double saldo;
    private String entidadBancaria;

    static ConexionABdD conex1 = new ConexionABdD ();
    private static final Scanner lector = new Scanner ( System.in );

    // constructores
    public CuentasCorrientes(String nomb ,String apLL1, String apLL2 , String DNI , String telf){
        saldo = 0;
        entidadBancaria = "Banco Sin-Fondos";
        PERSONA = new Persona (nomb,apLL1,apLL2,DNI,telf);
    }

    // getter y setters
    public String getCod_gestor (){return cod_gestor;}
    public void setCod_gestor ( String id_gestor){
        cod_gestor = id_gestor;}
    public int getNumTitular(){
        return id_titular;
    }
    public void setId_titular(int id_persona){
        id_titular = id_persona;
    }
    public int getId_cuenta(){
        return numCuenta;
    }
    public void setId_cuenta(int id_cuenta){numCuenta = id_cuenta;}
    public String getEntidadBancaria () {
        return entidadBancaria;
    }
    public void setEntidadBancaria ( String entidadBancariaUser ) {
        this.entidadBancaria = entidadBancariaUser;
    }
    public double getSaldo () {
        return saldo;
    }
    public void setSaldo ( double saldoIngresado ) {
        saldo = saldoIngresado;
    }

    // métodos de la clase Externos
    public void retirar ( double cantidad ) {
        saldo = saldo - cantidad;
    }
    public void ingresar ( double cantidad ) {
        saldo = saldo + cantidad;
    }

    // métodos de la clase internos
    public static int menuBienvenida () {
        System.out.printf ( "Bienvenido a la app del banco Sin Fondos.%n" );
        System.out.printf ( "1.- Darte de alta en el banco.%n" );
        System.out.printf ( "2.- Ingresar dinero a mi cuenta.%n" );
        System.out.printf ( "3.- Retirar dinero de mi cuenta.%n" );
        System.out.printf ( "4.- Ingresar dinero a otra cuenta.%n" );
        System.out.printf ( "5.- Mostrar mi saldo.%n" );
        System.out.print ( "Escoge una opción = " );
        return lector.nextInt ();
    }// fin menuBienvenida

    public static void main ( String[] args ) {
        // Establecer conexion con la BdD
        String nombreBdD = "CuentasBancarias";
        String username = "admin00";
        String password = "alumno";
        String urlmodificada = conex1.establecerNombreBdD ( nombreBdD );
        conex1.setURL ( urlmodificada );
        conex1.setUsr ( username );
        conex1.setPwd ( password );
        // creacion de variables y objetos
        char continua;
        CuentasCorrientes cuenta1;
        // Inicio del programa
        do {
            int idGestor = 0;
            int op = menuBienvenida ();
            // control de errores
            while ( op < 1 || op > 5 ){
                System.out.print ("No has ingresado una opción válida...%n");
                System.out.print ("Vuelve a ingresar otra opción = ");
                op = lector.nextInt ();
            }
            switch ( op ){
                case 1:
                    lector.nextLine ();
                    GESTOR = new Gestor ();// Si no tiene gestor el id_gestor es 0
                    System.out.print ( "Introduce tu nombre = " );
                    String nomUsr = lector.nextLine ();
                    System.out.print ( "Introduce tu primer apellido = " );
                    String apeUsr1 = lector.nextLine ();
                    System.out.print ( "Introduce tu segundo apellido = " );
                    String apeUsr2 = lector.nextLine ();
                    System.out.print ( "Introduce tu DNI con la letra incluida = " );
                    String dniUsr = lector.nextLine ();
                    System.out.print ( "Introduce un teléfono de contacto = " );
                    String tlfUsr = lector.nextLine ();
                    cuenta1 = new CuentasCorrientes ( nomUsr,apeUsr1,apeUsr2,dniUsr,tlfUsr );

                    // <-- CREACIÓN DE LA PERSONA -->
                    String altaCuentaNueva = "INSERT INTO personas(id_persona,DNI,id_cuenta,id_gestor,nombre,ape1,ape2,telefono) VALUES (NULL, ?, NULL, NULL, ?, ?, ?, ?)";
                    conex1.setSentencia ( altaCuentaNueva );
                    String [][] datosCuenta = {
                            {"1", PERSONA.getDNI ()},
                            {"2", PERSONA.getNombre ()},
                            {"3", PERSONA.getApellido1 ()},
                            {"4", PERSONA.getApellido2 ()},
                            {"5", PERSONA.getTelefono ()}
                    };
                    conex1.insertarDatos ( datosCuenta );

                    // <-- CREACION DE LA CUENTA --->
                    String buscarID_PERSONA = "SELECT id_persona FROM personas WHERE DNI = ?\n";
                    conex1.setSentencia ( buscarID_PERSONA );
                    String id_Per = conex1.obtenerUnVarchar ( buscarID_PERSONA , PERSONA.getDNI (), "id_persona" );
                    id_titular = Integer.parseInt ( id_Per );
                    PERSONA.setId_persona ( id_titular );// Se relaciona cuenta con persona
                    // <-- Añadimos el cod_Gestor y la entidad en la tabla cuentas-->
                    String insertarId_persona = "INSERT INTO cuentas(numCuenta,id_persona,cod_gestor,saldo,entidadBancaria,id_gestor) VALUES (NULL, ?,"+"'"+PERSONA.getCod_gestor ()+"'"+", 0,"+"'"+cuenta1.getEntidadBancaria ()+"'"+", NULL)";
                    conex1.setSentencia ( insertarId_persona );
                    conex1.insertarEntero ( 1 , id_titular);

                    // <-- ESTABLECER EL id_cuenta A PERSONA --> //
                    String buscarNUM_CUENTA = "SELECT numCuenta FROM cuentas WHERE id_persona= ?";
                    conex1.setSentencia ( buscarNUM_CUENTA );
                    String id_cuenta = conex1.obtenerUnVarchar ( buscarNUM_CUENTA, Integer.toString ( id_titular ), "numCuenta");
                    int num = Integer.parseInt ( id_cuenta );
                    cuenta1.setId_cuenta ( num );
                    PERSONA.setId_cuenta ( num );


                    System.out.print ( "¿Quieres ingresar dinero en la cuenta? (S/N) = " );
                    char opSaldo = lector.nextLine ().charAt ( 0 );
                    opSaldo = Character.toLowerCase ( opSaldo );
                    System.out.print ( "Presiona la tecla [ENTER] para continuar..." );
                    lector.nextLine ();

                    if ( opSaldo == 's' ){
                        System.out.print ( "Introduce cantidad deseas ingresar = " );
                        double cant = lector.nextDouble ();
                        // control de errores
                        if ( cant > 0 && cant < 10000 ){
                            cuenta1.setCod_gestor ( GESTOR.getCod_Gestor () );
                            PERSONA.setCod_gestor ( GESTOR.getCod_Gestor () );
                            cuenta1.setSaldo ( cant );
                            String actuCuentaCod_gestor = "UPDATE cuentas SET cod_Gestor = ? WHERE id_persona = "+id_titular;
                            conex1.setSentencia ( actuCuentaCod_gestor );
                            conex1.insertarVarchar ( 1,GESTOR.getCod_Gestor () );

                        }else {

                            cuenta1.setSaldo ( cant );
                            GESTOR = new Gestor ("nombre_del_gestor","telf_gestor");
                            cuenta1.setCod_gestor ( GESTOR.getCod_Gestor () );
                            PERSONA.setCod_gestor ( GESTOR.getCod_Gestor () );
                            System.out.printf ( "Necesitarás un gestor para realizar esta tranferencia...%n" );
                            System.out.printf ( "En las proximas horas se te se asignará un Gestor.%n" );
                            System.out.printf ( "Gracias por asociarte al banco Sin Fondos.%n" );
                            // <-- Acutalizamos el cod_Gestor en la tabla cuentas
                            String actuCuentaCod_gestor = "UPDATE cuentas SET cod_Gestor = ? WHERE id_persona = "+id_titular;
                            conex1.setSentencia ( actuCuentaCod_gestor );
                            conex1.insertarVarchar ( 1,GESTOR.getCod_Gestor () );
                            // <-- CREAMOS EL GESTOR -->
                            String crearGestor = "INSERT INTO gestores(id,cod_Gestor,id_cuenta,nombre,telefono) VALUES (NULL, ?,NULL,NULL,NULL)";
                            conex1.setSentencia ( crearGestor );
                            conex1.insertarVarchar ( 1 , GESTOR.getCod_Gestor ());
                            // seleccionamos el ID mas alto para añadirlo al cod_gestor
                            String buscarId = "SELECT MAX(id) FROM gestores";
                            conex1.setSentencia ( buscarId );
                            idGestor = conex1.obtenerUnEntero ( buscarId );
                            GESTOR.setId ( idGestor );
                            String cod_Gestor = GESTOR.getCod_Gestor ()+GESTOR.getId ();// GEST-n
                            cuenta1.setCod_gestor ( cod_Gestor );
                            PERSONA.setCod_gestor ( cod_Gestor );
                            GESTOR.setCod_Gestor ( cod_Gestor );
                            // actualizamos la tabla gestores
                            String buscarId_cuenta = "SELECT MAX(id) FROM gestores";
                            conex1.setSentencia ( buscarId_cuenta );
                            GESTOR.setId ( conex1.obtenerUnEntero ( buscarId_cuenta ) );
                            String actuIdGestores = "UPDATE gestores SET id_cuenta = ? WHERE id = "+GESTOR.getId ();
                            conex1.setSentencia ( actuIdGestores );
                            conex1.insertarEntero ( 1,cuenta1.getId_cuenta () );
                            String actuCod_Gestor = "UPDATE gestores SET cod_Gestor = ? WHERE id = "+GESTOR.getId ();
                            conex1.setSentencia ( actuCod_Gestor );
                            conex1.insertarVarchar ( 1,GESTOR.getCod_Gestor () );
                        }

                    } else{
                        String actuIdGestores = "UPDATE gestores SET id_cuenta = ? WHERE id = "+GESTOR.getId ();
                        conex1.setSentencia ( actuIdGestores );
                        conex1.insertarEntero ( 1,cuenta1.getId_cuenta () );
                        cuenta1.setCod_gestor ( GESTOR.getCod_Gestor () );
                        PERSONA.setCod_gestor ( GESTOR.getCod_Gestor () );
                        cuenta1.setSaldo ( 0 );
                    }

                    // actualizamos el saldo en la tabla cuentas
                    String actuSaldoCuenta = "UPDATE cuentas SET saldo = ?, cod_Gestor = "+"'"+cuenta1.getCod_gestor ()+"'"+" WHERE id_persona = "+PERSONA.getId_persona ();
                    conex1.setSentencia ( actuSaldoCuenta );
                    conex1.insertarDecimal ( 1, cuenta1.getSaldo () );

                    // actualizamos el id_cuenta de personas
                    String actuCuentaPersona = "UPDATE personas SET id_cuenta = ? WHERE DNI = "+"'"+PERSONA.getDNI ()+"'";
                    conex1.setSentencia ( actuCuentaPersona );
                    conex1.insertarDecimal ( 1, PERSONA.getId_cuenta () );
                    // actualizamos el id_gestor de personas
                    String actuId_Gestor = "UPDATE personas SET id_gestor = ? WHERE id_persona = "+"'"+PERSONA.getId_persona ()+"'";
                    conex1.setSentencia ( actuId_Gestor );
                    conex1.insertarVarchar ( 1, PERSONA.getCod_gestor () );

                    break;
                case 2:


            }

            System.out.print ( "Transacción terminada, presiona [ENTER] para continuar..." );
            lector.nextLine ();
            System.out.printf ( "%n¿Deseas salir? (S/N) = " );
            continua = lector.nextLine ().charAt ( 0 );
            continua = Character.toLowerCase ( continua );

        } while ( continua == 'n' );
    }// fin main

    /*public static void main ( String[] args ) {
        // Establecer nombre BdD
        String nombreBdD = "CuentasBancarias";
        String username = "admin00";
        String password = "alumno";
        String urlmodificada = conex1.establecerNombreBdD ( nombreBdD );
        conex1.setURL ( urlmodificada );
        conex1.setUsr ( username );
        conex1.setPwd ( password );

        char opEscogida;
        // Inicio del programa
        do {
            // Creación de objetos de la clase
            CuentasCorrientes cuenta1 = new CuentasCorrientes();
            CuentasCorrientes cuenta2 = new CuentasCorrientes();
            PERSONA = new Persona ();
            Persona persona1 = new Persona ();
            Persona persona2 = new Persona ();
            int op = menuBienvenida ();
            while ( op < 1 || op > 5 ){
                System.out.print ("No has ingresado una opción válida...%n");
                System.out.print ("Vuelve a ingresar otra opción = ");
                op = lector.nextInt ();
            }
            switch ( op ) {
                case 1:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI con la letra incluida = " );
                    String dni1 = lector.nextLine ();
                    dni1 = dni1.toUpperCase ();
                    // establecemos el DNI a la persona1
                    PERSONA.setDNI ( dni1 );
                    System.out.print ( "Ingresa tu nombre = " );
                    String nom1 = lector.nextLine ();
                    // establecemos el nombre de la persona1
                    PERSONA.setNombre ( nom1 );
                    // ingresamos los datos en la base de datos
                    String insertarDatos = "INSERT INTO cuentas (DNI,titular) VALUES (?, ?)";
                    conex1.setSentencia ( insertarDatos );
                    String[][] datos1 = {
                            {"1" , PERSONA.getDNI ()} ,
                            {"2" , PERSONA.getNombre ()}
                    };
                    conex1.insertarDatos ( datos1 );
                    break;
                case 2:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI con la letra incluida = " );
                    String dni2 = lector.nextLine ();
                    dni2 = dni2.toUpperCase ();
                    // comprobar que se encuentra el DNI introducido en la BdD
                    String sentenciaConsulta2 = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsulta2 );
                    String resConsulta2 = conex1.obtenerUnVarchar ( sentenciaConsulta2 , dni2, "saldo" );
                    while ( resConsulta2 == null ){
                        System.err.println ("El DNI ingresado no existe...");
                        System.out.print ( "Vuelve a ingresar tu DNI con la letra incluida = " );
                        dni2 = lector.nextLine ();
                        dni2 = dni2.toUpperCase ();
                        resConsulta2 = conex1.obtenerUnVarchar ( sentenciaConsulta2 , dni2, "saldo" );
                    }
                    // establecemos el DNI a la cuenta1
                    PERSONA.setDNI ( dni2 );
                    double saldoNow = Double.parseDouble ( resConsulta2 );
                    // Si existe el DNI lo almacenamos
                    System.out.printf ( "Tienes %.2f € en la cuenta.%n" , saldoNow );
                    // Establecemos el saldo del DNI ingreasado
                    cuenta1.setSaldo ( saldoNow );
                    System.out.print ( "¿ Cuanto vas a ingresar ? = " );
                    double canDoub = lector.nextDouble ();
                    // Actualizamos el saldo sumando con la cantidad ingresada
                    cuenta1.ingresar ( canDoub );
                    // Modificar una fila de una tabla
                    String updateDatos2 = "UPDATE cuentas SET saldo = ? WHERE DNI = "+"'"+ PERSONA.getDNI ()+"'";
                    conex1.setSentencia ( updateDatos2 );
                    conex1.insertarDecimal ( 1, cuenta1.getSaldo () );
                    System.out.printf ( "Presiona una tecla para continuar...%n" );
                    lector.nextLine ();
                    break;
                case 3:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI con la letra incluida = " );
                    String dni3 = lector.nextLine ();
                    dni3 = dni3.toUpperCase ();
                    PERSONA.setDNI ( dni3 );
                    // comprobar que se encuentra el DNI introducido y obtener el saldo corresopndiente
                    String sentenciaConsulta = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsulta );
                    String resConsulta = conex1.obtenerUnVarchar ( sentenciaConsulta , PERSONA.getDNI(), "saldo" );
                    System.out.printf ( "Tienes %s€ en la cuenta.%n" , resConsulta );
                    System.out.print ( "¿ Cuanto vas a retirar ? = " );
                    double cantRetirar = lector.nextDouble ();
                    double saldoAhora = Double.parseDouble ( resConsulta );
                    cuenta1.setSaldo ( saldoAhora );
                    if ( cantRetirar < saldoAhora ) {
                        cuenta1.retirar (cantRetirar);
                        // Modificar una fila de una tabla
                        String updateDatos3 = "UPDATE cuentas SET saldo = ? WHERE DNI = "+"'"+cuenta1.PERSONA.getDNI ()+"'";
                        conex1.setSentencia ( updateDatos3 );
                        conex1.insertarDecimal ( 1, cuenta1.getSaldo () );

                    } else {// Si la cantidad a retirar es mayor que la que tiene de saldo la person1
                        System.out.printf ( "No puedes retirar esa cantidad...%n" );
                    }
                    System.out.printf ( "Presiona una tecla para continuar...%n" );
                    lector.nextLine ();
                    break;
                case 4:
                    lector.nextLine ();
                    System.out.print ( "Ingresa el DNI del titular de la cuenta de origen = " );
                    String dniTo = lector.nextLine ();
                    dniTo = dniTo.toUpperCase ();
                    // Establecemos el DNI de origen si existe dentro de la BdD
                    PERSONA.setDNI ( dniTo );
                    // comprobar que el DNI de origen existe
                    String sentenciaConsultaOrigen = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaOrigen );
                    String resConsultaOrigen = conex1.obtenerUnVarchar ( sentenciaConsultaOrigen , PERSONA.getDNI(), "DNI" );
                    while ( resConsultaOrigen == null ) {
                        System.out.print ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de origen = " );
                        dniTo = lector.nextLine ();
                        dniTo = dniTo.toUpperCase ();
                        resConsultaOrigen = conex1.obtenerUnVarchar ( sentenciaConsultaOrigen , PERSONA.getDNI(), "DNI" );
                    }
                    PERSONA.setDNI ( resConsultaOrigen );


                    System.out.print ( "Ingresa el DNI del titular de la cuenta de destino = " );
                    String dniTd = lector.nextLine ();
                    dniTd = dniTd.toUpperCase ();
                    // comprobar que el DNI de destino existe
                    String sentenciaConsultaDestino = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaDestino );
                    String resultadoConsultaDestino = conex1.obtenerUnVarchar ( sentenciaConsultaDestino , dniTd , "DNI" );
                    while ( resultadoConsultaDestino == null ) {
                        System.out.printf ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de destino = " );
                        dniTd = lector.nextLine ();
                        dniTd = dniTd.toUpperCase ();
                        resultadoConsultaDestino = conex1.obtenerUnVarchar ( resultadoConsultaDestino , dniTd , "DNI" );
                    }
                    // establecemos el DNI de destino si existe dentro de BdD
                    PERSONA.setDNI ( dniTd );// ToDo Es necesario crear dos personas para simular el de origen y entrada?

                    // obtener saldo cuenta origen
                    String sentenciaConsultaSaldoOrigen = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaSaldoOrigen );
                    String resultadoConsultaSaldoOrigen = conex1.obtenerUnVarchar ( sentenciaConsultaSaldoOrigen , dniTo , "saldo" );// ToDo obtener el DNI correcto con el getter
                    // obtener saldo cuenta destino
                    String sentenciaConsultaSaldoDestino = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaSaldoDestino );
                    String resultadoConsultaSaldoDestino = conex1.obtenerUnVarchar ( sentenciaConsultaSaldoDestino , dniTd , "saldo" );

                    double saldoOr = Double.parseDouble ( resultadoConsultaSaldoOrigen );
                    double saldoDe = Double.parseDouble ( resultadoConsultaSaldoDestino );

                    while ( saldoOr <= 0 ) {
                        System.out.printf ( "La cuenta de origen no tiene suficiente saldo.%n" );
                        System.out.print ( "Ingresa el DNI de otro titular que tenga saldo = " );
                        dniTo = lector.nextLine ();
                        resultadoConsultaSaldoOrigen = conex1.obtenerUnVarchar ( sentenciaConsultaOrigen , dniTo , "DNI" );
                        saldoOr = Double.parseDouble ( resultadoConsultaSaldoOrigen );
                    }
                    // Establecemos los saldos a las dos cuentas
                    cuenta1.setSaldo ( saldoOr );
                    cuenta2.setSaldo ( saldoDe );

                    // pedir la cantidad de dinero a ingresar en la otra cuenta
                    System.out.print ( "¿Qué cantidad de dinero quieres ingresar en la otra cuenta? = " );
                    double saldoAIngresar = lector.nextDouble ();
                    // Actualizar el saldo de la cuenta de origen
                    saldoOr = saldoOr - saldoAIngresar;
                    // a la cuenta1 le asginamos el saldo resultante de la resta
                    cuenta1.setSaldo ( saldoOr );
                    // Actualizamos el saldo de la cuenta de origen
                    String actualizarSaldoOr = "UPDATE cuentas SET saldo = ? WHERE DNI = "+"'"+dniTo+"'";
                    conex1.setSentencia ( actualizarSaldoOr );
                    conex1.insertarDecimal ( 1,cuenta1.getSaldo () );

                    // la cuenta2 se le añade la cantidad a ingresar
                    saldoDe = saldoDe + saldoAIngresar;
                    cuenta2.setSaldo ( saldoDe );
                    String actualizarSaldoDe = "UPDATE cuentas SET saldo = ? WHERE DNI = "+"'"+dniTd+"'";
                    conex1.setSentencia ( actualizarSaldoDe );
                    conex1.insertarDecimal ( 1,cuenta2.getSaldo () );
                    System.out.printf ( "Transferencia realizada con éxito. Presiona la tecla [ENTER] para continuar...%n" );
                    lector.nextLine ();
                    break;
                case 5:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI para mostrar tu saldo = " );
                    String dniUsuario = lector.nextLine ();
                    dniUsuario = dniUsuario.toUpperCase ();
                    // establecemos el DNI a la persona1
                    PERSONA.setDNI ( dniUsuario );
                    // obtener el nombre y saldo del DNI ingresado
                    String sentenciaMostrarNombre = "SELECT titular FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarNombre );
                    String nombreUsuario = conex1.obtenerUnVarchar ( sentenciaMostrarNombre , PERSONA.getDNI() , "titular" );
                    // establecemos el Nombre
                    PERSONA.setNombre ( nombreUsuario );
                    // obtenemos el saldo del DNI ingresado
                    String sentenciaMostrarSaldo = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarSaldo );
                    String saldoUsuario = conex1.obtenerUnVarchar ( sentenciaMostrarSaldo , PERSONA.getDNI() , "saldo" );
                    double saldoUsr = Double.parseDouble ( saldoUsuario );
                    // establecemos el saldo
                    cuenta1.setSaldo (saldoUsr);
                    System.out.printf ( "Tu saldo %s es de %.2f€.%n" , nombreUsuario , saldoUsr );
                    System.out.print ( "Presiona la tecla [ENTER] para continuar..." );
                    lector.nextLine ();
            }// fin switch

            System.out.print ( "¿Deseas continuar? (S/N) = " );
            opEscogida = lector.nextLine ().charAt ( 0 );
            opEscogida = Character.toLowerCase ( opEscogida );

        } while ( opEscogida == 's' );
    }// fin main*/

}// fin-class CuentaCorriente
