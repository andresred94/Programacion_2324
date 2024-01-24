package com.ecodeup.jdbc;

import java.util.Scanner;

public class CuentaCorriente {
    static ConexionABdD conex1 = new ConexionABdD ();
    private static final Scanner lector = new Scanner ( System.in );

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
            int op = menuBienvenida ();
            // Creación de objetos de la clase
            CuentaCorriente cuenta1 = new CuentaCorriente ();
            CuentaCorriente cuenta2 = new CuentaCorriente ();
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
                    // establecemos el DNI a la cuenta1
                    cuenta1.setDNI ( dni1 );
                    System.out.print ( "Ingresa tu nombre = " );
                    String nom1 = lector.nextLine ();
                    // establecemos el nombre de la cuenta1
                    cuenta1.setTitular ( nom1 );
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
                    // establecemos el DNI a la cuenta1
                    cuenta1.setDNI ( dni2 );
                    // comprobar que se encuentra el DNI introducido en la BdD
                    String sentenciaConsulta2 = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    String col2 = "saldo";
                    conex1.setSentencia ( sentenciaConsulta2 );
                    String resConsulta2 = conex1.almacenarResultadoConsulta ( sentenciaConsulta2 , dni2 , col2 );
                    while ( resConsulta2 == null ){
                        System.err.println ("El DNI ingresado no existe...");
                        System.out.print ( "Vuelve a ingresar tu DNI con la letra incluida = " );
                        dni2 = lector.nextLine ();
                        dni2 = dni2.toUpperCase ();
                        resConsulta2 = conex1.almacenarResultadoConsulta ( sentenciaConsulta2 , dni2 , col2 );
                    }

                    System.out.printf ( "Tienes %s € en la cuenta.%n" , resConsulta2 );

                    System.out.print ( "¿ Cuanto vas a ingresar ? = " );
                    String cantIngresa = lector.nextLine ();
                    System.out.print ( "Presiona la tecla [ENTER] para continuar..." );
                    lector.nextLine ();

                    double canDoub = Double.parseDouble ( cantIngresa );
                    double saldoNow = Double.parseDouble ( resConsulta2 );

                    saldoNow = saldoNow + canDoub;
                    String saldoNowSt = Double.toString ( saldoNow );
                    // establecemos el saldo resultante despues de sumar la cantidad ingresada
                    cuenta1.setSaldo ( Double.parseDouble ( saldoNowSt ) );

                    // Modificar una fila de una tabla
                    double saldoTotal2 = cuenta1.getSaldo ();// obtener el saldo actual de la cuenta1
                    String saldoSt2 = Double.toString ( saldoTotal2 );
                    String updateDatos2 = "UPDATE cuentas SET saldo = ? WHERE DNI = ?";
                    conex1.setSentencia ( updateDatos2 );
                    String[][] datos2 = {
                            {"1" , saldoSt2} ,
                            {"2" , dni2}

                    };
                    conex1.modificarFila ( datos2 );
                    break;
                case 3:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI con la letra incluida = " );
                    String dni3 = lector.nextLine ();
                    dni3 = dni3.toUpperCase ();
                    cuenta1.setDNI ( dni3 );
                    // comprobar que se encuentra el DNI introducido
                    // realizar una consulta
                    //String sentenciaConsulta = "SELECT DNI,titular,saldo FROM cuentas WHERE DNI = '52053342H';\n";
                    String sentenciaConsulta = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    String col3 = "saldo";
                    conex1.setSentencia ( sentenciaConsulta );
                    String resConsulta = conex1.almacenarResultadoConsulta ( sentenciaConsulta , dni3 , col3 );

                    System.out.printf ( "Tienes %s€ en la cuenta.%n" , resConsulta );

                    System.out.print ( "¿ Cuanto vas a retirar ? = " );
                    String cantRetirar = lector.nextLine ();

                    double canDoub3 = Double.parseDouble ( cantRetirar );
                    double saldoNow3 = Double.parseDouble ( resConsulta );
                    if ( canDoub3 < saldoNow3 ) {
                        canDoub3 = saldoNow3 - canDoub3;
                        cuenta1.setSaldo ( canDoub3 );
                    } else {
                        System.out.print ( "No tienes suficiente saldo." );
                    }
                    // Modificar una fila de una tabla
                    double saldoTotal = cuenta1.getSaldo ();
                    String saldoSt = Double.toString ( saldoTotal );
                    String updateDatos3 = "UPDATE cuentas SET saldo = ? WHERE DNI = ?";
                    conex1.setSentencia ( updateDatos3 );
                    String[][] datos3 = {
                            {"1" , saldoSt} ,
                            {"2" , dni3}

                    };
                    conex1.modificarFila ( datos3 );
                    break;
                case 4:
                    lector.nextLine ();
                    System.out.print ( "Ingresa el DNI del titular de la cuenta de origen = " );
                    String dniTo = lector.nextLine ();
                    dniTo = dniTo.toUpperCase ();

                    // comprobar que el DNI de origen existe
                    String sentenciaConsultaOrigen = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    String colOri = "DNI";
                    conex1.setSentencia ( sentenciaConsultaOrigen );
                    String resConsultaOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , colOri );
                    while ( resConsultaOrigen == null ) {
                        System.out.print ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de origen = " );
                        dniTo = lector.nextLine ();
                        dniTo = dniTo.toUpperCase ();
                        resConsultaOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , colOri );
                    }

                    System.out.print ( "Ingresa el DNI del titular de la cuenta de destino = " );
                    String dniTd = lector.nextLine ();
                    dniTd = dniTd.toUpperCase ();

                    // comprobar que el DNI de destino existe
                    String sentenciaConsultaDestino = "SELECT DNI FROM cuentas WHERE DNI = ?\n";
                    String colDes = "DNI";
                    conex1.setSentencia ( sentenciaConsultaDestino );
                    String resultadoConsultaDestino = conex1.almacenarResultadoConsulta ( sentenciaConsultaDestino , dniTd , colDes );
                    while ( resultadoConsultaDestino == null ) {
                        System.out.printf ( "No se encuentra el DNI ingresado en la Base de datos...%n" );
                        System.out.print ( "Vuelve a ingresar otro DNI de destino = " );
                        dniTd = lector.nextLine ();
                        dniTd = dniTd.toUpperCase ();
                        resultadoConsultaDestino = conex1.almacenarResultadoConsulta ( resultadoConsultaDestino , dniTd , colDes );
                    }

                    // obtener saldo cuenta origen
                    String sentenciaConsultaSaldoOrigen = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    String colConDes = "saldo";
                    conex1.setSentencia ( sentenciaConsultaSaldoOrigen );
                    String resultadoConsultaSaldoOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaSaldoOrigen , dniTo , colConDes );
                    System.out.println ( resultadoConsultaSaldoOrigen );

                    // obtener saldo cuenta destino
                    String sentenciaConsultaSaldoDestino = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    String colConOri = "saldo";
                    conex1.setSentencia ( sentenciaConsultaSaldoDestino );
                    String resultadoConsultaSaldoDestino = conex1.almacenarResultadoConsulta ( sentenciaConsultaSaldoDestino , dniTd , colConOri );
                    System.out.println ( resultadoConsultaSaldoDestino );

                    double saldoOr = Double.parseDouble ( resultadoConsultaSaldoOrigen );
                    double saldoDe = Double.parseDouble ( resultadoConsultaSaldoDestino );

                    while ( saldoOr <= 0 ) {
                        System.out.printf ( "La cuenta de origen no tiene suficiente saldo.%n" );
                        System.out.print ( "Ingresa el DNI de otro titular que tenga saldo = " );
                        dniTo = lector.nextLine ();
                        resultadoConsultaSaldoOrigen = conex1.almacenarResultadoConsulta ( sentenciaConsultaOrigen , dniTo , colOri );
                        saldoOr = Double.parseDouble ( resultadoConsultaSaldoOrigen );
                    }

                    // pedir la cantidad de dinero a ingresar en la otra cuenta
                    System.out.print ( "¿Qué cantidad de dinero quieres ingresar en la otra cuenta? = " );
                    double saldoAIngresar = lector.nextDouble ();

                    // Actualizar el saldo de la cuenta de origen
                    saldoOr = saldoOr - saldoAIngresar;
                    String saldoStOr = Double.toString ( saldoOr );
                    String actualizarSaldoOr = "UPDATE cuentas SET saldo = ? WHERE DNI = ?";
                    conex1.setSentencia ( actualizarSaldoOr );
                    String[][] datosOrigen = {
                            {"1" , saldoStOr} ,
                            {"2" , dniTo}

                    };
                    conex1.modificarFila ( datosOrigen );

                    // Actualizar el saldo de la cuenta de destino
                    saldoDe = saldoDe + saldoAIngresar;
                    String saldoStDe = Double.toString ( saldoDe );
                    String actualizarSaldoDe = "UPDATE cuentas SET saldo = ? WHERE DNI = ?";
                    conex1.setSentencia ( actualizarSaldoDe );
                    String[][] datosDestino = {
                            {"1" , saldoStDe} ,
                            {"2" , dniTd}

                    };
                    conex1.modificarFila ( datosDestino );
                    System.out.printf ( "Transferencia realizada con éxito...%n" );
                    lector.nextLine ();

                case 5:
                    lector.nextLine ();
                    System.out.print ( "Ingresa tu DNI para mostrar tu saldo = " );
                    String dniUsuario = lector.nextLine ();
                    dniUsuario = dniUsuario.toUpperCase ();

                    // obtener el nombre y saldo del DNI ingresado
                    String sentenciaMostrarNombre = "SELECT titular FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarNombre );
                    String nombreUsuario = conex1.almacenarResultadoConsulta ( sentenciaMostrarNombre , dniUsuario , "titular" );

                    String sentenciaMostrarSaldo = "SELECT saldo FROM cuentas WHERE DNI = ?\n";
                    conex1.setSentencia ( sentenciaMostrarSaldo );
                    String saldoUsuario = conex1.almacenarResultadoConsulta ( sentenciaMostrarSaldo , dniUsuario , "saldo" );
                    System.out.printf ( "Tu saldo %s es de %s€.%n" , nombreUsuario , saldoUsuario );
                    System.out.print ( "Presiona la tecla [ENTER] para continuar..." );
                    lector.nextLine ();

            }// fin switch

            System.out.print ( "¿Deseas continuar? (S/N) = " );
            opEscogida = lector.nextLine ().charAt ( 0 );
            opEscogida = Character.toLowerCase ( opEscogida );

        } while ( opEscogida == 's' );

    }// fin main

    // atributos
    private static String Titular = "";
    private static String DNI = "";
    private static double saldo = 0;

    // constructores
    public CuentaCorriente () {
    }

    public CuentaCorriente ( String dni , String titular ) {
        this.DNI = dni;
        this.Titular = titular;
    }


    // getter y setters
    public void setTitular ( String titular ) {
        Titular = titular;
        // Insertamos los datos que queremos almacenar
        //conex1.insertarDatos (url,username,password,sentencia,insertarDatos);
    }

    public String getDNI () {
        return DNI;
    }

    public void setDNI ( String dni ) {
        if ( dni.length () > 10 || dni.isEmpty () ) {
            System.err.println ( "EL DNI ingresado no es válido." );
        } else {
            DNI = dni;
        }
    }

    public double getSaldo () {
        return saldo;
    }

    public void setSaldo ( double saldoIngresado ) {
        saldo = saldoIngresado;
    }

    public void retirar ( double cantidad ) {
        if ( cantidad > saldo ) {
            System.err.println ( "No se puede retirar el saldo" );
        } else {
            saldo = saldo - cantidad;
        }
    }

    public void ingresar ( double cantidad ) {
        saldo = saldo + cantidad;
    }

    // Mensajes para mostrar en pantalla.
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

}// fin-class CuentaCorriente
