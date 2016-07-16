package SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewData {
	
	private static Connection conn = null;
	private static Scanner scan = new Scanner(System.in);
	
	static ArrayList<String> tables;
	static ArrayList<String> columns;

	public static void main(String[] args) {
		try {
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/mitch_db?useSSL=false";
			conn = DriverManager.getConnection(url,userName,password);
			System.out.println("Which table do you want to view?");
			tables = new ArrayList<String>();
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			int i = 0;
			while(rs.next()){
				System.out.println(i +") " + rs.getString(3));
				i++;
				tables.add(rs.getString(3));
			}
			Scanner scan = new Scanner(System.in);
			int input = scan.nextInt();
			printData(tables.get(input));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printData(String table) throws SQLException{
		//Retrieve the columns in the table
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getColumns(null, null, table, "%");
		int i = 0;
		columns = new ArrayList<String>();
		while(rs.next()){
			columns.add(rs.getString(4));
			i++;
		}
		
		//Now print the data
		ResultSet resultSet = null;
		Statement stmt;
		stmt = conn.createStatement();
		resultSet = stmt.executeQuery("SELECT * FROM " + table);
		i = 0;
		while(resultSet.next()){
			System.out.println((i+1) + " ............");
			for(int j = 0; j<columns.size(); j++){
				String colName = columns.get(j);
				Object colVar = resultSet.getObject(columns.get(j));
				System.out.println(colName + ": " + colVar);
			}
			i++;
			System.out.println();
		}
	}

}
