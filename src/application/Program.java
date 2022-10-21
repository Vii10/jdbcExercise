package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		//Conecta ao banco de dados
		Connection conn = null;
		//Realiza uma consulta no banco de dados
		Statement st = null;
		//Guarda o resultado da consulta em uma tabela associada à uma varíavel
		ResultSet rs = null;
		
		//Toddas as operações trabalham com recurso externo, podendo gerar exceptions
		try {
			
			conn = DB.getConnection(); //conectado ao servidor
			st = conn.createStatement(); //criou-se um objeto do tipo Statement
			rs = st.executeQuery("select * from department"); //Lê-se: "Execute uma pesquisa, selecionando todos os dados da tabela "department" do BD do SQL"
			//A atribuição à variável rs faz com que a pesquisa seja transformada em uma tabela de fácil acesso
			
			//Percorrendo os dados guardados em rs
			while(rs.next()) { //.next retorna um false quando chega ao final da lista. Por isso não é necessário usar "!= null"
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
				//rs.getInt acessa o valor inteiro da tabela e o rs.getString acessa o nome que está associado à Id
			}
		}
		catch(SQLException e) {
			e.printStackTrace(); //Vai printar uma lista de erros encontrados
		}
		finally {
			//Como são recursos externos ao JVM, as variáveis precisam ser finalizadas manualmente
			DB.closeResultSet(rs); //Fechamento do ResultSet
			DB.closeStatement(st); //Fechamento do Statement
			DB.closeConnection(); //Encerra a conexão com o servidor
			
		}
	}

}
