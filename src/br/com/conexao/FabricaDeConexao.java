package br.com.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexao {
    public static Connection getConnectionFactory() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/projetoEscola","root","root");
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex.getMessage());
        }

    }
}

