/* O aqruivo "db.properties" guarda informações de acessos ao banco de dados do MySQL */
package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	//Metodo de conexão ao banco de dados
	private static Connection conn = null;  // Variável de controle de conexão
	
	public static Connection getConnection() {
		if(conn == null) { //Verificar se a variável de controle é nula
			try {
				Properties props = loadProperties(); //Passando método de leitura para uma váriavel
				String url = props.getProperty("dburl"); //Acessando e atribuindo a url do arquivo do banco de dados à uma variável
				conn = DriverManager.getConnection(url, props); //Estabelecendo conexão através da url e informações de login da "props"
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	//Metodo para fechar a conexao com o banco de dados
	public static void closeConnection() {
		if(conn != null){ //Verifica se a conexao foi estabelecida
			try {
				conn.close(); //Fecha a conexao guardada na variavel "conn"
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//Metodo auxiliar para carregar as informações do arquivo e guardar em um objeto
	private static Properties loadProperties() { //Metodo do tipo "Propriedade" do java
		try(FileInputStream fs = new FileInputStream("db.properties")){ //Leitura direta do arquivo na raiz do projeto, criando uma sequência de dados
			Properties props = new Properties(); //Criando objeto do tipo "Propriedades"
			props.load(fs); //".load" Faz a leitura direta do arquivo e grava ao mesmo tempo *IMPORTANTE*
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage()); //Print da exception personalizada
		}
	}
	
	//Metodo auxiliar para fechar um Statement se ele não for nulo
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//Metodo auxiliar para fechar um ResultSet se ele não for nulo
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
