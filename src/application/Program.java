package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

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
					"DELETE FROM department " //Deletando dados da tabela de departamentos
					+"WHERE "	//Condicional de função = Só deleta se:
					+"Id = ?"); //Valor que torna a condição acima verdadeira
			
			pt.setInt(1, 5); //Atribuindo valores
			
			//Executar atualização e atribuir a uma varíavel
			int rows = pt.executeUpdate();
			
			//Mostrar linhas afetadas
			System.out.println("Done! Rows affected: " + rows);
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage()); //Lançando exception personalizada
		}
		finally {
			DB.closeStatement(pt);
			DB.closeConnection();
		}
	}
}
