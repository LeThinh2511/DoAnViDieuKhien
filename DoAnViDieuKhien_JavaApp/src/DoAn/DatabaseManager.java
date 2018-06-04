package DoAn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatabaseManager 
{
	Connection connection;
	Boolean isConnected = false;
	
	public DatabaseManager()
	{
		Connection conn = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String dbName = "DoAnViDieuKhien";
	    String driver = "com.mysql.jdbc.Driver";
	    String userName = "root";
	    String password = "123456";

	    try
	    {
	        Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url+dbName, userName, password);
	        System.out.println("Connected to the database");
	        this.connection = conn;
	        isConnected = true;
	    }
	    catch (Exception e)
	    {
	        System.out.println("NO CONNECTION");
	    }
	}
	
	// Ham lay tat ca du lieu tu database
	public void listAllData()
	{
		String query = "select * from SOLIEU";
		try {
			Statement statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next())
			{
				System.out.println(resultSet.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Ham lay du lieu tu mot ngay cu the
	public Data getDataOfADate(Date date)
	{
		Data data = new Data();
		String dateString = dateToString(date);
		String query = "select * from SOLIEU where date = Date('" + dateString + "')";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				data.setDate(date);
				data.setNumOfProduct1(resultSet.getInt(2));
				data.setNumOfProduct2(resultSet.getInt(3));
			}
			else
			{
				data = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	// Ham lay data cua mot thang tu database
	public ArrayList<Data> getDataOfAMonth(int year, int month)
	{
		ArrayList<Data> dataOfAMonth = new ArrayList<Data>();
		
		String query = "select * from SOLIEU where  year(date) = " + year + " and month(date) = " + month;
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next())
			{
				Data data = new Data();
				data.setDate(resultSet.getDate(1));
				data.setNumOfProduct1(resultSet.getInt(2));
				data.setNumOfProduct2(resultSet.getInt(3));
				dataOfAMonth.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataOfAMonth;
	}
	
	
	// Ham lay du lieu trong mot nam tu database
	public ArrayList<Data> getDataOfAYear(int year)
	{
		ArrayList<Data> dataOfAYear = new ArrayList<Data>();
		
		String query = "select * from SOLIEU where  year(date) = " + year;
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next())
			{
				Data data = new Data();
				data.setDate(resultSet.getDate(1));
				data.setNumOfProduct1(resultSet.getInt(2));
				data.setNumOfProduct2(resultSet.getInt(3));
				dataOfAYear.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataOfAYear;
	}
	
	
	// Ham them du lieu vao database
	public void addNewData(Data data)
	{
		String dateString = dateToString(data.date);
		int numOfProduct1 = data.numOfProduct1;
		int numOfProduct2 = data.numOfProduct2;
		String query = "insert into SOLIEU values (Date('" + dateString + "'), " + numOfProduct1 + ", " + numOfProduct2 + ")";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			int numOfRowEffected = statement.executeUpdate(query);
			System.out.println(numOfRowEffected);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Du lieu ngay " + dateString + " da co!");
		}
	}
	
	
	// Ham update du lieu trong mot ngay trong database
	public void updateData(Data data)
	{
		String dateString = dateToString(data.date);
		int numOfProduct1 = data.numOfProduct1;
		int numOfProduct2 = data.numOfProduct2;
		String query = "update SOLIEU "
				+ "set date = Date('" + dateString + "'), product_1 = " + numOfProduct1 + ", product_2 = " + numOfProduct2 +
				" where date = Date('" + dateString + "')";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			int numOfRowEffected = statement.executeUpdate(query);
			System.out.println(numOfRowEffected);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("loi update");
		}
	}
	
	// ----------------------- Cac ham tien ich---------------------------
	
	// Ham tra ve ngay sau khi tang N ngay tu ngay nhap vao
	private Date increaseNDays(Date previousDate, int numOfDay)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousDate);
		cal.add(cal.DATE, numOfDay);
		
		Date date = new Date();
		date = cal.getTime();
		return date;
	}
	
	
	// ham chuyen tu kieu Date sang String
	private String dateToString(Date date)
	{
		// Chuyen tu date sang chuoi co dang yyyy-mm-dd
		String dateString = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		dateString = year + "-" + month + "-" + day;
		return dateString;
	}
	
	// Ham cho du lieu mau
	public void addSampleData()
	{
		String inputString = "1-6-2017";
		int numOfDays = 29;
		int temp = 0;
		int pro1 = 0;
		int pro2 = 0;
		String dateString = "";
		Random rand = new Random();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = dateFormat.parse(inputString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < 1; i++) //them thi sua lai i < num
		{
			temp = rand.nextInt(7) + 1;
			if (temp == 2 || temp == 3)
			{
				date = increaseNDays(date, 2);
			}
			else
			{
				date = increaseNDays(date, 1);
			}
			dateString = dateToString(date);
			pro1 = rand.nextInt(50) + 80;
			pro2 = rand.nextInt(50) + 80;
			String query = "insert into SOLIEU values (Date('" + dateString + "'), " + pro1 + ", " + pro2 + ")";
			Statement statement;
			try {
				statement = this.connection.createStatement();
				int numOfRowEffected = statement.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Du lieu ngay " + dateString + " da co!");
			}
		}
	}
	
}
