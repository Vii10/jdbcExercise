package application;

import java.sql.Connection;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		//Teste de conexão
		Connection conn = DB.getConnection();
		DB.closeConnection();
	}

}
