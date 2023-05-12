package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

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
			// encerra a conexão:
			conectar().close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// listar todos os contatos:
	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> listaContatos = new ArrayList<>();
		String query = "select * from contatos order by nome";
		try {
			PreparedStatement pstm = conectar().prepareStatement(query);
			ResultSet st = pstm.executeQuery();
			// iterar sobre as linhas da tabela até chegar na última linha da query:
			while (st.next()) {
				JavaBeans contato = new JavaBeans();
				contato.setIdcon(st.getString(1));
				contato.setNome(st.getString(2));
				contato.setFone(st.getString(3));
				contato.setEmail(st.getString(4));
				listaContatos.add(contato);
			}
			// encerra a conexão:
			conectar().close();
			return listaContatos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	// 
	public void selecionarContato(JavaBeans contato) {
		String query = "select * from contatos where idcon=?";
		try {
			PreparedStatement pstm = conectar().prepareStatement(query);
			pstm.setString(1, contato.getIdcon());
			ResultSet st = pstm.executeQuery();
			while (st.next()) {				
				contato.setIdcon(st.getString(1));
				contato.setNome(st.getString(2));
				contato.setFone(st.getString(3));
				contato.setEmail(st.getString(4));
			}
			st.close();
			conectar().close();
		} catch (SQLException e) {
			e.printStackTrace();			
		}
	}
	
	public void alterarContato(JavaBeans contato) {
		String query = "UPDATE contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			PreparedStatement pstm = conectar().prepareStatement(query);			
			pstm.setString(1, contato.getNome());
			pstm.setString(2, contato.getFone());
			pstm.setString(3, contato.getEmail());
			pstm.setString(4, contato.getIdcon());			
			pstm.executeUpdate();
			conectar().close();
		} catch (SQLException e) {
			e.printStackTrace();			
		}
	}
	
	public void deletarContato(JavaBeans contato) {
		String query = "delete from contatos where idcon=?";
		try {
			PreparedStatement pstm = conectar().prepareStatement(query);			
			pstm.setString(1, contato.getIdcon());
			pstm.executeUpdate();
			conectar().close();
		} catch (SQLException e) {
			e.printStackTrace();			
		}
	}
	
}
