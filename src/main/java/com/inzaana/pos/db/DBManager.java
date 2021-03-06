package com.inzaana.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import com.inzaana.pos.filters.AuthenticationRequestFilter;
import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.Payment;
import com.inzaana.pos.models.Product;
import com.inzaana.pos.models.StockDiary;
import com.inzaana.pos.models.User;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBResponsePerser;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.POSConfig;
import com.inzaana.pos.utils.ResponseMessage;
import com.inzaana.pos.utils.UserRole;

public class DBManager
{

	// JDBC driver name and database URL
	private final String	JDBC_DRIVER			= POSConfig.JDBC_DRIVER;
	private final String	DB_URL				= POSConfig.DB_URL;
	private final String	DB_USER_NAME		= POSConfig.DB_USER_NAME;
	private final String	DB_PASS				= POSConfig.DB_PASS;

	public DBManager()
	{
	}

	public boolean executeUpdate(String sqlQuery, ArrayList<Object> paramList, ResponseMessage response)
	{
		boolean success = true;
		response.clear();

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("[TEST]Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);

			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			for (int i = 0; i < paramList.size(); i++)
			{
				preparedStatement.setObject(i + 1, paramList.get(i));
			}

			System.out.println(sqlQuery + " \n" + paramList.toString());
			preparedStatement.executeUpdate();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
			DBResponsePerser responsePerser = new DBResponsePerser(se.getMessage());
			response.setMessage(responsePerser.getResponse().toString());
			success = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DBResponsePerser responsePerser = new DBResponsePerser(e.getMessage());
			response.setMessage(responsePerser.getResponse().toString());
			success = false;
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		if (success)
		{
			response.setMessage(DBResponse.SUCCESS.toString());
		}

		return success;
	}

	public List<Category> getCategoryItems(String userName)
	{
		List<Category> categoryList = new ArrayList<Category>();
		int userNameId = -5555;

		if (!userName.isEmpty())
		{
			if (!validateUserName(userName))
			{
				return categoryList;
			}

			userNameId = getUserNameIdFromName(userName);
			if (userNameId < 0)
			{
				return categoryList;
			}
		}

		String sqlQuery = "SELECT * FROM " + DBTables.CATEGORIES.toString();

		if (!userName.isEmpty())
		{
			sqlQuery += " WHERE USER_ID = ?";
		}
		
		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;

		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);

			if (!userName.isEmpty())
			{
				preparedStatement.setInt(1, userNameId);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				Category category = new Category();
				category.setId(resultSet.getString(Category.ID));
				category.setUserId(resultSet.getInt(Category.USER_ID));
				category.setName(resultSet.getString(Category.NAME));
				category.setParentId(resultSet.getString(Category.PARENTID));
				category.setImage(resultSet.getString(Category.IMAGE));
				category.setTextTip(resultSet.getString(Category.TEXTTIP));
				category.setCatShowName(resultSet.getBoolean(Category.CATSHOWNAME));
				categoryList.add(category);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return categoryList;
	}

	public List<Product> getProducts(String userName)
	{
		List<Product> productList = new ArrayList<Product>();
		int userNameId = -5555;
		String sqlQuery = "SELECT * FROM " + DBTables.PRODUCTS.toString();

		if (!userName.isEmpty())
		{
			if (!validateUserName(userName))
			{
				return productList;
			}

			userNameId = getUserNameIdFromName(userName);
			if (userNameId < 0)
			{
				return productList;
			}

			sqlQuery += " WHERE USER_ID = ?";
		}

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);

			if (!userName.isEmpty())
			{
				preparedStatement.setInt(1, userNameId);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				Product product = new Product();
				product.setId(resultSet.getString(Product.ID));
				product.setUserId(resultSet.getInt(Product.USER_ID));
				product.setReference(resultSet.getString(Product.REFERENCE));
				product.setCode(resultSet.getString(Product.CODE));
				product.setCodetype(resultSet.getString(Product.CODETYPE));
				product.setName(resultSet.getString(Product.NAME));
				product.setPriceBuy(resultSet.getDouble(Product.PRICEBUY));
				product.setPriceSell(resultSet.getDouble(Product.PRICESELL));
				product.setCategory(resultSet.getString(Product.CATEGORY));
				product.setTaxcat(resultSet.getString(Product.TAXCAT));
				product.setAttributeset_id(resultSet.getString(Product.ATTRIBUTESET_ID));
				product.setStockCost(resultSet.getDouble(Product.STOCKCOST));
				product.setStockVolume(resultSet.getDouble(Product.STOCKVOLUME));
				product.setImage(resultSet.getString(Product.IMAGE));
				product.setIscom(resultSet.getBoolean(Product.ISCOM));
				product.setScale(resultSet.getBoolean(Product.ISSCALE));
				product.setKitchen(resultSet.getBoolean(Product.ISKITCHEN));
				product.setPrintkb(resultSet.getBoolean(Product.PRINTKB));
				product.setSendStatus(resultSet.getBoolean(Product.SENDSTATUS));
				product.setService(resultSet.getBoolean(Product.ISSERVICE));
				product.setAttributes(resultSet.getString(Product.ATTRIBUTES));
				product.setDisplay(resultSet.getString(Product.DISPLAY));
				product.setIsVPrice(resultSet.getBoolean(Product.ISVPRICE));
				product.setIsVerpatrib(resultSet.getBoolean(Product.ISVERPATRIB));
				product.setTextTip(resultSet.getString(Product.TEXTTIP));
				product.setWarranty(resultSet.getBoolean(Product.WARRANTY));
				product.setStockunits(resultSet.getDouble(Product.STOCKUNITS));
				productList.add(product);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return productList;
	}

	public List<Payment> getPaymentItems(String userName)
	{
		List<Payment> paymentList = new ArrayList<Payment>();

		if (!canUserDoDBTransaction(userName))
		{
			return paymentList;
		}

		String sqlQuery = "SELECT * FROM " + DBTables.PAYMENTS.toString();

		if (!userName.isEmpty())
		{
			sqlQuery += " WHERE USER_ID = ?";
		}
		
		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;

		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);

			if (!userName.isEmpty())
			{
				preparedStatement.setString(1, userName);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				Payment payment = new Payment();
				paymentList.add(payment);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return paymentList;
	}

	public List<StockDiary> getStockDiaryItems(String userName)
	{

		List<StockDiary> stockList = new ArrayList<StockDiary>();
		int userNameId = -5555;

		String sqlQuery = "SELECT * FROM " + DBTables.STOCKDIARY.toString();

		if (!userName.isEmpty())
		{
			if (!validateUserName(userName))
			{
				return stockList;
			}

			userNameId = getUserNameIdFromName(userName);
			if (userNameId < 0)
			{
				return stockList;
			}

			sqlQuery += " WHERE USER_ID = ?";
		}

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);

			if (!userName.isEmpty())
			{
				preparedStatement.setInt(1, userNameId);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				StockDiary stock = new StockDiary();
				stock.setId(resultSet.getString(StockDiary.ID));
				stock.setUserId(resultSet.getInt(StockDiary.USER_ID));
				stock.setDateNew(resultSet.getString(StockDiary.DATENEW));
				stock.setReason(resultSet.getInt(StockDiary.REASON));
				stock.setLocation(resultSet.getString(StockDiary.LOCATION));
				stock.setProduct(resultSet.getString(StockDiary.PRODUCT));
				stock.setAttributeSetInstance_id(resultSet.getString(StockDiary.ATTRIBUTESETINSTANCE_ID));
				stock.setUnits(resultSet.getDouble(StockDiary.UNITS));
				stock.setPrice(resultSet.getDouble(StockDiary.PRICE));
				stock.setAppUser(resultSet.getString(StockDiary.APPUSER));

				stockList.add(stock);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return stockList;
	}

	public int getUserNameIdFromName(String userName)
	{
		int userNameId = -5555;

		if (!validateUserName(userName))
		{
			return userNameId;
		}

		String sqlQuery = "SELECT * FROM " + DBTables.USERNAME.toString();
		sqlQuery += " WHERE NAME = ?";

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, userName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				userNameId = resultSet.getInt("ID");
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userNameId;
	}

	public String getUserNameFromNameId(int userNameId)
	{
		String userName = UserManager.USER_NAME;

		String sqlQuery = "SELECT * FROM " + DBTables.USERNAME.toString() + " WHERE ID = ?";

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userNameId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				userName = resultSet.getString("NAME");
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userName;
	}

	public int getUserNameId(String userId)
	{
		int userNameId = -5555;

		if (!validateUserId(userId))
		{
			return userNameId;
		}

		String sqlQuery = "SELECT * FROM " + DBTables.USERS.toString() + " WHERE USER_ID = ?";

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, userId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				userNameId = resultSet.getInt("NAME");
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userNameId;
	}

	public String getUserName(String userId)
	{
		int userNameId = getUserNameId(userId);
		String userName = UserManager.USER_NAME;

		if (!validateUserId(userId))
		{
			return userName;
		}

		String sqlQuery = "SELECT * FROM " + DBTables.USERNAME.toString() + " WHERE ID = ?";

		if (userNameId < 0)
		{
			return DBResponse.FAILURE.toString();
		}
		
		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;

		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userNameId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				userName = resultSet.getString("NAME");
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userName;
	}

	public List<User> getUsers(String userName)
	{
		List<User> userList = new ArrayList<User>();

		if (!canUserDoDBTransaction(userName))
		{
			return userList;
		}

		int userNameId = -5555;

		String sqlQuery = "SELECT * FROM " + DBTables.USERS.toString();
		
		if (!userName.isEmpty())
		{
			if (!validateUserName(userName))
			{
				return userList;
			}
			
			userNameId = getUserNameIdFromName(userName);
			
			if (userNameId < 0)
			{
				return userList;
			}
			
			sqlQuery += " WHERE NAME = ?";
		}


		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			if (!userName.isEmpty())
			{
				preparedStatement.setInt(1, userNameId);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				User user = new User();
				user.setUserName(getUserNameFromNameId(Integer.valueOf(resultSet.getString(User.NAME))));
				System.out.println("NAME: " + user.getName());
				user.setUserId(resultSet.getString(User.USER_ID));
				System.out.println("ID: " + user.getUserId());
				user.setUserPassword(resultSet.getString(User.PASSWORD));
				System.out.println("PASS: " + user.getUserPassword());
				user.setUserRole(resultSet.getString(User.ROLE));
				System.out.println("ROLE: " + user.getUserRole());
				userList.add(user);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{	
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userList;
	}

	public String getUserPassword(String userId)
	{
		String password = "*@*@*@**USER_PASSWORD_NOT_FOUND**@*@*@*";

		if (!validateUserId(userId))
		{
			return password;
		}

		String sqlQuery = "SELECT password FROM " + DBTables.USERS.toString() + " WHERE user_id = ?";

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, userId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				password = resultSet.getString(User.PASSWORD);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return password;
	}

	public String getUserRole(String userId)
	{
		String userRole = UserRole.GUEST.toString();

		if (!validateUserId(userId))
		{
			return userRole;
		}

		String sqlQuery = "SELECT role FROM " + DBTables.USERS.toString() + " WHERE user_id = ?";

		Connection				dbConnection		= null;
		Statement				dbStatement			= null;
		PreparedStatement		preparedStatement	= null;
		ResultSet				resultSet			= null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, userId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				userRole = resultSet.getString(User.ROLE);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection(resultSet, preparedStatement, dbConnection);
		}

		return userRole;
	}

	public boolean validateUserId(String id)
	{
		// Important for sql injection attack
		return true;
	}

	public boolean validateUserName(String userName)
	{
		// Important for sql injection attack

//		if (UserManager.USER_NAME_ID < 0)
//		{
//			return false;
//		}

		return true;
	}

	public boolean canUserDoDBTransaction(String name)
	{
		// Important

		if (UserManager.USER_ROLE.equals(UserRole.ADMIN.toString()) || name.equalsIgnoreCase(UserManager.USER_NAME))
		{
			return true;
		}

		return false;
	}

	private void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection dbConnection)
	{
		try
		{
			if (resultSet != null)
				resultSet.close();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}

		try
		{
			if (preparedStatement != null)
				preparedStatement.close();
		}
		catch (SQLException se2)
		{
		}
		try
		{
			if (dbConnection != null)
				dbConnection.close();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}
}
