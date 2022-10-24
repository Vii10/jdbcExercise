package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		//Var's de conexão
		Connection con = null;
		PreparedStatement pt = null;
		
		try {
			//Conectando
			con = DB.getConnection();
			
			//Instrução de atualização dos dados
			pt = con.prepareStatement(
					"UPDATE seller " //Acessando a tabela para ser atualizada
					+ "SET BaseSalary = BaseSalary + ? " //Atualizar salário com base no que já tinha + um valor digitado
					+ "WHERE " //Restrição para definir somente quando a condição da linha a baixo for verdadeira
					+ "(DepartmentId = ?)"); //Condição para a alteração do salário
			
			//Atribuindo valores
			pt.setDouble(1, 200.00);
			pt.setInt(2, 2);
			
			//Executar atualização e atribuir a uma varíavel
			int rows = pt.executeUpdate();
			
			//Mostrar linhas afetadas
			System.out.println("Done! Rows affected: " + rows);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(pt);
			DB.closeConnection();
		}
	}
}
