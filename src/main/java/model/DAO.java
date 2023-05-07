package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {

	/* Módulo de conexão */

	// parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/agenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password;

	// método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// teste de conexão
	public void testeConexão() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// persistir os objetos no banco de dados
	public void inserirContato(JavaBeans contato) {
		String query = "INSERT INTO contatos (nome, fone, email) VALUES (?, ?, ?)";
		try {
			PreparedStatement pstm = conectar().prepareStatement(query);
			pstm.setString(1, contato.getNome());
			pstm.setString(2, contato.getFone());
			pstm.setString(3, contato.getEmail());
			// executa a query:
			pstm.executeUpdate();
			//encerra a conexão:
			conectar().close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
}
