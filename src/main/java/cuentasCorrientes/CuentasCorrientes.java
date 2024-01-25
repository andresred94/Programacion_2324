package cuentasCorrientes;

import com.ecodeup.jdbc.ConexionABdD;

import java.util.Scanner;

// ToDo Crear Clase Persona que se relaciona con clase bancaria
public class CuentasCorrientes {
    // atributos
    private static Persona PERSONA;
    private double saldo = 0;
    private String entidadBancaria = "BBVA";

    static ConexionABdD conex1 = new ConexionABdD ();
    private static final Scanner lector = new Scanner ( System.in );

    // constructores
    public CuentasCorrientes(){
        this.saldo = saldo;
        this.entidadBancaria = entidadBancaria;
    }


    // getter y setters
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
                    cuenta1.PERSONA.setDNI ( dni1 );
                    //persona1.setDNI ( dni1 );
                    System.out.print ( "Ingresa tu nombre = " );
                    String nom1 = lector.nextLine ();
                    // establecemos el nombre de la persona1
                    cuenta1.PERSONA.setNombre ( nom1 );
                    //persona1.setNombre ( nom1 );
                    // ingresamos los datos en la base de datos
                    String insertarDatos = "INSERT INTO cuentas (DNI,titular) VALUES (?, ?)";
                    conex1.setSentencia ( insertarDatos );
                    String[][] datos1 = {
                            {"1" , dni1} ,
                            {"2" , nom1}
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
                    String resConsulta2 = conex1.almacenarResultadoConsulta ( sentenciaConsulta2 , dni2 , "saldo" );
                    while ( resConsulta2 == null ){
                        System.err.println ("El DNI ingresado no existe...");
                        System.out.print ( "Vuelve a ingresar tu DNI con la letra incluida = " );
                        dni2 = lector.nextLine ();
                        dni2 = dni2.toUpperCase ();
                        resConsulta2 = conex1.almacenarResultadoConsulta ( sentenciaConsulta2 , dni2 , "saldo" );
                    }
                    // Este el saldo de la cuenta origen
                    double saldoNow = Double.parseDouble ( resConsulta2 );
                    // establecemos el DNI a la cuenta1
                    cuenta1.PERSONA.setDNI ( dni2 );
                    //persona1.setDNI ( dni2 );
                    System.out.printf ( "Tienes %.2f € en la cuenta.%n" , saldoNow );
                    // Establecemos el saldo del DNI ingreasado
                    cuenta1.setSaldo ( saldoNow );
                    System.out.print ( "¿ Cuanto vas a ingresar ? = " );
                    double canDoub = lector.nextDouble ();
                    // Actualizamos el saldo sumando con la cantidad ingresada
                    cuenta1.ingresar ( canDoub );
                    // Modificar una fila de una tabla
                    String updateDatos2 = "UPDATE cuentas SET saldo = ? WHERE DNI = "+"'"+ cuenta1.PERSONA.getDNI ()+"'";
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
                    cuenta1.PERSONA.setDNI ( dni3 );
                    //persona1.setDNI ( dni3 );
                    // comprobar que se encuentra el DNI introducido y obtener el saldo corresopndiente
                    String sentenciaConsulta = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsulta );
                    String resConsulta = conex1.almacenarResultadoConsulta ( sentenciaConsulta , dni3 , "saldo" );
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
                    // comprobar que el DNI de origen existe
                    String sentenciaConsultaOrigen = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaOrigen );
                    String resConsultaOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , "DNI" );
                    while ( resConsultaOrigen == null ) {
                        System.out.print ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de origen = " );
                        dniTo = lector.nextLine ();
                        dniTo = dniTo.toUpperCase ();
                        resConsultaOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , "DNI" );
                    }
                    // Establecemos el DNI de origen si existe dentro de la BdD
                    PERSONA.setDNI ( dniTo );
                    System.out.print ( "Ingresa el DNI del titular de la cuenta de destino = " );
                    String dniTd = lector.nextLine ();
                    dniTd = dniTd.toUpperCase ();
                    // comprobar que el DNI de destino existe
                    String sentenciaConsultaDestino = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaDestino );
                    String resultadoConsultaDestino = conex1.almacenarResultadoConsulta ( sentenciaConsultaDestino , dniTd , "DNI" );
                    while ( resultadoConsultaDestino == null ) {
                        System.out.printf ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de destino = " );
                        dniTd = lector.nextLine ();
                        dniTd = dniTd.toUpperCase ();
                        resultadoConsultaDestino = conex1.almacenarResultadoConsulta ( resultadoConsultaDestino , dniTd , "DNI" );
                    }
                    // establecemos el DNI de destino si existe dentro de BdD
                    PERSONA.setDNI ( dniTd );// ToDo Es necesario crear dos personas para simular el de origen y entrada?

                    // obtener saldo cuenta origen
                    String sentenciaConsultaSaldoOrigen = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaSaldoOrigen );
                    String resultadoConsultaSaldoOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaSaldoOrigen , dniTo , "saldo" );// ToDo obtener el DNI correcto con el getter
                    // obtener saldo cuenta destino
                    String sentenciaConsultaSaldoDestino = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaConsultaSaldoDestino );
                    String resultadoConsultaSaldoDestino = conex1.almacenarResultadoConsulta ( sentenciaConsultaSaldoDestino , dniTd , "saldo" );

                    double saldoOr = Double.parseDouble ( resultadoConsultaSaldoOrigen );
                    double saldoDe = Double.parseDouble ( resultadoConsultaSaldoDestino );

                    while ( saldoOr <= 0 ) {
                        System.out.printf ( "La cuenta de origen no tiene suficiente saldo.%n" );
                        System.out.print ( "Ingresa el DNI de otro titular que tenga saldo = " );
                        dniTo = lector.nextLine ();
                        resultadoConsultaSaldoOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , "DNI" );
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
                    persona1.setDNI ( dniUsuario );

                    // obtener el nombre y saldo del DNI ingresado
                    String sentenciaMostrarNombre = "SELECT titular FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarNombre );
                    String nombreUsuario = conex1.almacenarResultadoConsulta ( sentenciaMostrarNombre , dniUsuario , "titular" );
                    // establecemos el Nombre
                    persona1.setNombre ( nombreUsuario );
                    String sentenciaMostrarSaldo = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarSaldo );
                    String saldoUsuario = conex1.almacenarResultadoConsulta ( sentenciaMostrarSaldo , dniUsuario , "saldo" );
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
    }// fin main
}// fin-class CuentaCorriente
