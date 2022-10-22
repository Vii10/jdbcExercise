package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		//Precisaremos criar um SDF para atribuir uma data de nascimento
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DB.getConnection();
			
			//Não há necessidade de adicionar o id na string, pois ele é auto-incrementável pelo SQL
			ps = conn.prepareStatement(
				"INSERT INTO seller" //Adiciona na tabela seller (Escrito em linguagem SQL)
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)" //Seleciona os campos que serão inseridos os dados
				+ "VALUES " //Valores que serão inseridos
				+ "(?, ?, ?, ?, ?)", //A interrogação é um placeHolder que "Segura" os campos para serem alterados depois
				Statement.RETURN_GENERATED_KEYS); //Realizando sobrecarga para retornar as chaves criadas através de um enumerated
			
			//Adicionando valores
			ps.setString(1, "Carlito Franguito"); //Acessa o primeiro "?" e atribui à ele um valor. Usa-se cada set de acordo com o tipo de dado
			ps.setString(2, "carlito@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("31/02/1996").getTime()));
			/* O setDate precisará importar o sql.Date ao invés do util.Date, pois estamos trabalhando com SQL - JDBC e não um programa 100% JAVA
			 * A função .getTime pega a data que escrevemos e a converte para um valor "entendível" para o sql.Date
			 * A função .parse realiza a conversão da String da data em uma data verdadeira*/
			ps.setDouble(4, 3000.);
			ps.setInt(5, 3);
			
			/*
			//Testando inserção de departamentos novos
			ps = conn.prepareStatement(
					"insert into department (Names) ('D1'), ('D2')"
					, Statement.RETURN_GENERATED_KEYS);
			*/
			
			//Para realizar a alteração dos dados
			int rowsAffected = ps.executeUpdate(); //Essa função retorna um número inteiro que exibe o valor das linhas alteradas
			
			//Para fins de Debug e estudo, realiza-se o print da varíavel anterior
			System.out.println("DONE! Rows affecteds: " + rowsAffected);
			
			//Tratamento especial para retornar as chaves
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys(); //A função .getGeneratedKeys retorna um objeto do tipo ResultSet, por isso a criação dele
				
				//Percorrendo o ResultSet
				while(rs.next()) { //A função .next foi explicada no commit anterior
					int id = rs.getInt(1); //Pega a primeira coluna da tabela inserida no objeto ResultSet
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No rows affected!");
			}
		}
		catch (SQLException e) {
			e.printStackTrace(); //A função .printStackTrace exibe uma lista de passos que acarretaram o erro
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			//Fechamento manual dos recursos abertos
			DB.closeStatement(ps);
			DB.closeConnection();
			//Por questões lógicas, sempre fecharemos a conexão por último
		}
	}

}
