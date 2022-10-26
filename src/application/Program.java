package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		//Var's de conexão
		Connection con = null;
		Statement st = null;
		
		try {
			//Conectando
			con = DB.getConnection();
			st = con.createStatement(); //Criando Statement
			
			//Criando lógica de transação usando a função de autocommit()
			con.setAutoCommit(false);
			
			//Variáveis de colunas atribuidas à execução da atualização da var st
			int rows = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*//Teste de erro fake durante a atualização das informações
			int x = 1;
			if(x < 2) {
				throw new SQLException("Fake error");
			}
			*/
			
			int rowsTwo = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			//Lê-se: "Atualizar o vendedor; Mude o valor base para tanto quando o id do departamento for tal"
			
			//A função .commit realiza a confirmação do fim da transação. Assim os dados só são passados após a excecução dela
			con.commit();
			
			//Auto-explicativo
			System.out.println("Rows 1: " + rows);
			System.out.println("Rows 2: " + rowsTwo);
		}
		
			
		catch(SQLException e) {
			//A função .rollback retorna o estado original dos dados antes das alterações. Porém, ela dá uma SQLException
			try { //Try de tratamento da incapacidade de realizar a transação
				con.rollback();
				throw new DbException("Transaction failed! Caused by " + e.getMessage());
			} catch (SQLException e1) { //Catch de tratamento no rollback! Acontece quando se torna impossível voltar
				throw new DbException("Rollback error " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
