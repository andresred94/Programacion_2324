package com.ecodeup.jdbc;

import java.sql.*;

public class ConexionABdD {
    // atributos
    private String URL = "jdbc:mysql://192.168.56.101:3306/nomBdD?serverTimezone=UTC";
    private String usr = "";
    private String pwd = "";
    private String sentencia;
    private String [] campos;


    // getters y setters
    public void setURL ( String urlModificada ) {
        URL = urlModificada;
    }

    public void setUsr ( String usuario ) {
        usr = usuario;
    }

    public void setPwd ( String password ) {
        pwd = password;
    }

    public void setSentencia ( String sentencia ) {
        this.sentencia = sentencia;
    }

    public void setCampos ( String[] campos ) {
        this.campos = campos;
    }

    public String getURL () {
        return URL;
    }

    // contructores
    public ConexionABdD(){
    }// ConexionABdD

    public ConexionABdD( String sentencia , String [] campos){
        this.sentencia = sentencia;
        this.campos = campos;
    }// ConexionABdD

    // métodos de la clase
    public void crearBdD ( ) {
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);
            Statement statement = connection.createStatement();

            // Ejecutar la sentencia para crear la base de datos
            int resultado = statement.executeUpdate(sentencia);

            if (resultado >= 0) {
                System.out.println("Base de datos creada exitosamente");
            } else {
                System.out.println("No se pudo crear la base de datos");
            }

            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin crearBdD

    public String almacenarResultadoConsulta ( String sentencia , String valAbuscar , String columna){
        String resultado = null;

        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);


            // Crear una sentencia preparada con la sentencia SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Establecer el valor del parámetro
            preparedStatement.setString(1, valAbuscar);

            // Ejecutar la consulta y obtener el resultado
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener el valor de la columna deseada (cambia "columna_deseada" al nombre real)
                resultado = resultSet.getString(columna);
            }

            // Cerrar la conexión, la sentencia preparada y el resultado
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    public void realizarConsulta( String col [] ){
        try {
            Connection connection = DriverManager.getConnection ( URL, usr , pwd );
            Statement statement = connection.createStatement ();

            ResultSet resultSet = statement.executeQuery ( sentencia );

            for ( int i = 0 ; i < col.length ; i++ ) {
                System.out.printf ("%s | " , col[i]);
            }
            System.out.println ();

            while ( resultSet.next () ){

                for ( int i = 0 ; i < col.length ; i++ ) {
                    System.out.print ((resultSet.getString ( col[i]  ) + " | "));

                }
                System.out.println ();
            }

            connection.close ();
            statement.close ();
            resultSet.close ();

        } catch ( SQLException e ){
            e.printStackTrace ();
        }
    }// fin realizarConsulta

    public void crearTabla(){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);
            Statement statement = connection.createStatement();

            // Crear la tabla dentro de la base de datos
            String createTableSQL = sentencia;
            statement.executeUpdate(createTableSQL);

            System.out.println("Tabla creada exitosamente");

            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin crearTabla

    public void insertarEntero (int numCol , int numEntero){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);

            // Crear una sentencia preparada para la inserción
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Ingresa los valores que son Decimales
            preparedStatement.setInt(numCol, numEntero);

            // Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se pudo insertar el registro.");
            }

            // Cerrar la conexión y la sentencia preparada
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }// fin insertarEntero

    public void insertarDecimal (int numCol , double numDec){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);

            // Crear una sentencia preparada para la inserción
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Ingresa los valores que son Decimales
            preparedStatement.setDouble(numCol, numDec); // saldo

            // Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se pudo insertar el registro.");
            }

            // Cerrar la conexión y la sentencia preparada
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin insertarDecimal

    public void insertarVarchar ( int numCol , String varchar ){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);

            // Crear una sentencia preparada para la inserción
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Ingresa los valores que son Varchar
            preparedStatement.setString(numCol, varchar); // DNI VARCHAR 9

            // Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se pudo insertar el registro.");
            }

            // Cerrar la conexión y la sentencia preparada
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin insertarDatos

    public void insertarDatos (  String [][] datos ){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);

            // Crear una sentencia preparada para la inserción
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Ingresa los valores que son Varchar
            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < datos[i].length; j++) {
                    int parametro = Integer.parseInt ( datos[i][0] );
                    preparedStatement.setString(parametro, datos[i][j]); // Ajuste de posición del parámetro
                }
            }
            // Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se pudo insertar el registro.");
            }

            // Cerrar la conexión y la sentencia preparada
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin insertarDatos


    public void modificarFila( String [][] datos ){
        try {
            Connection connection = DriverManager.getConnection(URL, usr, pwd);

            // Sentencia SQL para actualizar una fila
            // Crear una sentencia preparada para la inserción
            PreparedStatement preparedStatement = connection.prepareStatement(sentencia);

            // Ingresa los valores que son Varchar
            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < datos[i].length; j++) {
                    int parametro = Integer.parseInt ( datos[i][0] );
                    preparedStatement.setString(parametro, datos[i][j]); // Ajuste de posición del parámetro
                }
            }


            // Ejecutar la actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Fila modificada exitosamente. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se pudo modificar la fila. Puede que el ID no exista.");
            }

            // Cerrar la conexión y la sentencia preparada
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }// fin modificarFila


    public String establecerNombreBdD(String nombreBdD){
        String urlFormateada  = URL.replaceAll("nomBdD", nombreBdD);
        return urlFormateada;
    }// fin establecerNombreBdD


}// fin-class ConexionABdD
