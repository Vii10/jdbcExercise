package db;

public class DbException extends RuntimeException {
	
	//Exceptions do tipo Runtime não precisam ser tratadas o tempo todo com try-catch
	
	private static final long serialVersionUID = 1L;

	public DbException(String msg) {
		super(msg);
	}
}
