package com.inzaana.pos.utils;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.managers.CategoryManager;
import com.inzaana.pos.managers.ProductManager;
import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.Product;
import com.inzaana.pos.models.User;

public class Main
{

	public static void main(String[] args)
	{
		System.out.println("Start Testing");
		
		//	testProduct();
		// testDBAccess();
		// testUser();
		//testCategory();
		//testProduct();
		
		String userNameAndPassword = " Jisan: s";
		String[] credentials = userNameAndPassword.split(":");

		System.out.println(Integer.toString(credentials.length));
		if (credentials.length != 2)
		{
			System.out.println("Error");
			return;
		}
		else if (credentials[0].trim().isEmpty() || credentials[1].trim().isEmpty())
		{
			System.out.println("Index Error");
			return;
		}
		
		System.out.println(credentials[0]);
		System.out.println(credentials[1]);
		
	}
	
	public static void testUser(){
		DBManager dbManager = new DBManager();
		System.out.println(dbManager.getUserPassword("Jisan"));
		System.out.println(dbManager.getUserRole("Jisan"));
	}

	public static void testProduct()
	{
		Product product = new Product("id_1", "Updated_reference", "code_1", "codetype_1", "name_1", 100.12, 200.12, "category_1", "taxcat",
				"attributeset_id", 100.12, 100.12, "image", true, true, true, true, true, true, "attributes",
				"display", 100, 100, "textTip", 100, 100.12);
		
//		product.insertRecordIntoDB("Jisan");
		product.updateRecordInDB("Jisan");
		
//		ProductManager manager = new ProductManager();
//		List<Product> productList = manager.getProducts("");
//		
//		for (Product productItem : productList)
//		{
//			System.out.println(productItem.getUserId());
//			System.out.println(productItem.getName());
//			System.out.println("-------------------------");
//		}
		
		//manager.deleteProduct("Jisan", "name");
	}

	public static void testDBAccess()
	{
		User user = new User("Jisan", "1234", UserRole.ADMIN.ToString());
		// user.insertRecordIntoDB();

		UserManager manager = new UserManager();
		manager.deleteUser("Jisansss");

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
		Category category = new Category("id", "name_4", "parentId", "image", "textTip", 200);
		
//		category.insertRecordIntoDB("Jisan");
		category.updateRecordInDB("Jisan");
	}
	
	public static void testJaxB()
	{
		Category category = new Category("id", "name_4", "parentId", "image", "textTip", 100);
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
