package com.inzaana.pos.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.managers.CategoryManager;
import com.inzaana.pos.managers.ProductManager;
import com.inzaana.pos.managers.StockDiaryManager;
import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.Product;
import com.inzaana.pos.models.StockDiary;
import com.inzaana.pos.models.User;

public class Main
{

	public static void main(String[] args)
	{
		System.out.println("Start Testing");
		
		
		
//		UserManager userManager = new UserManager();
//		UserManager.USER_ID = "super_admin";
//		userManager.verifyPassword("Iut2008");
		
		//	testProduct();
		// testDBAccess();
//		 testUser();
//		testCategory();
//		testProduct();

//		testStock();
		
		//testProgressbar();
		
		
		//clearInzaanaData();
		
		testUserResource();
		
	}
	
	
	public static void testUserResource()
	{
		System.out.println("testUserResource");
		
		UserManager userManager = new UserManager();
		UserManager.USER_ID = "super_admin";
		userManager.verifyPassword("Iut2008");
		
		User newUser = new User("TestUser", "user_2", "pass", "POS");
		
		if (userManager.deleteUser("TestUser"))
			System.out.println("true");
		else
			System.out.println("false");
	}
	
	
	public static void testProgressbar()
	{
		JFrame frame = new JFrame();
		final InzaanaProgressBar progressBar = new InzaanaProgressBar(frame);
		progressBar.setModal(true);
		progressBar.setLocationRelativeTo(null);
		
		
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				progressBar.setVisible(true);	
				
			}
		};
		
		
		for (int i = 0; i < 100; i++)
		{
			progressBar.setProgress(i);
			progressBar.setStatusMessage("Updating ...");
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Thread threadT = new Thread(runnable);
		   threadT.start();
		   
	}
	
	public static void clearInzaanaData(){
		Preferences p = Preferences.userRoot();
		System.out.println("Key: " + p.get("INZAANA_SECURITY_KEY", "NOT FOUND"));
		System.out.println("ID: " + p.get("INZAANA_USER_ID", "NOT FOUND"));
		p.remove("INZAANA_SECURITY_KEY");
		p.remove("INZAANA_USER_ID");
		p.remove("INZAANA_USER_NAME");
		p.remove("INZAANA_URL");
	}
	
	public static void testStock(){
		StockDiary stock = new StockDiary("id", "dateNewNew", 1, "location", "product", "attributeSetInstance_id", 100.00, 100.00, "appUser");
		
		StockDiaryManager manager = new StockDiaryManager();
		
		System.out.println(manager.deleteStockDiaryItem("Jisan", "id"));
	}
	
	public static void testUser(){
//		DBManager dbManager = new DBManager();
//		System.out.println("Password: " + dbManager.getUserPassword("user_id_1"));
//		System.out.println("Role: " + dbManager.getUserRole("user_id_1"));
//		System.out.println("Name: " + dbManager.getUserName("user_id_3"));
//		System.out.println("Name: " + dbManager.getUserNameFromNameId(2));
//		System.out.println("NameId: " + dbManager.getUserNameId("user_id_4"));
//		System.out.println("NameId: " + dbManager.getUserNameIdFromName("Mahmud"));
		
		User user = new User("John", "John_user_id_2", "***@@@***NEW_USER_PASSWORD***@@@***", "POS");
		
		UserManager userManager = new UserManager();
		userManager.addNewUser(user);
	}

	public static void testProduct()
	{
		Product product = new Product("id_2", "new_reference", "code_2", "codetype_2", "name_Updated", 100.12, 200.12, "category_1", "taxcat",
				"attributeset_id", 100.12, 100.12, "image", true, true, true, true, true, true, "attributes",
				"display", true, true, "textTip", true, 100.12);
//		
//		product.insertRecordIntoDB("Jisan");
//		product.updateRecordInDB("Jisan");
		
		ProductManager manager = new ProductManager();
//		List<Product> productList = manager.getProducts("");
//		
//		for (Product productItem : productList)
//		{
//			System.out.println(productItem.getUserId());
//			System.out.println(productItem.getName());
//			System.out.println("-------------------------");
//		}
		
//		System.out.println(manager.updateProduct("Jisan", product));
		
		manager.deleteProduct("Jisan", "id_2");
	}

	public static void testDBAccess()
	{
		User user = new User("Jisan", "user_id_1", "1234", UserRole.ADMIN.ToString());
		// user.insertRecordIntoDB();

		UserManager manager = new UserManager();
		manager.deleteUserId("Jisansss");

		// CategoryManager manager = new CategoryManager();
		// manager.deleteItemFromCategory("Bahnii", "name_2");

		// List<Category> list = manager.getCategoryItems("bahnii");
		//
		// for (Category cat : list)
		// {
		// System.out.println(cat.getUserId());
		// System.out.println(cat.getName());
		// System.out.println("--------------------");
		// }
	}

	public static void testCategory()
	{
		Category category = new Category("id_12", "Update Cat", "parentId", "image", "textTip", true);
		
//		System.out.println(category.insertRecordIntoDB("Jisan"));
//		category.updateRecordInDB("Jisan");
		CategoryManager categoryManager = new CategoryManager();
		System.out.println(categoryManager.deleteCategory("Jisan", "id_12"));
		
//		System.out.println(categoryManager.getAllCategoryItems().get(0).getName());
		//categoryManager.deleteCategory("Mahmud", "id_1");
	}
	
	public static void testJaxB()
	{
		Category category = new Category("id", "name_5", "parentId", "image", "textTip", true);
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(Category.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(category, System.out);
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
