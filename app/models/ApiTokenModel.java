package models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;

import scala.Array;

import com.google.code.morphia.annotations.Id;

import leodagdag.play2morphia.Model;

/**
 * Singleton of all api token
 * @author jeromeheissler
 *
 */
public class ApiTokenModel extends Model {

	private static Random random = new Random();
	
	@Id
	private ObjectId id;
	private List<String> tokensList;
	
	/**
	 * private constructor to unable instantiation of any object
	 */
	private ApiTokenModel()	{
		tokensList = new ArrayList<String>();
	}
	
	private static String generator()	{
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	private static Finder<ObjectId, ApiTokenModel> finder = new Finder<ObjectId, ApiTokenModel>(ObjectId.class, ApiTokenModel.class);
	
	private static ApiTokenModel tokenObject = getTokens();
	
	private static ApiTokenModel getTokens()	{
		List<ApiTokenModel> all = finder.all();
		if(all.size() == 0)	{
			ApiTokenModel tok = new ApiTokenModel();
			tok.insert();
			all.add(tok);
		}
		return all.get(0);
	}
	
	public static boolean asToken(String token)	{
		return tokenObject.tokensList.contains(token);
	}
	
	public static String generateNewToken()	{
		String token = generator();
		while(tokenObject.tokensList.contains(token))
			token = generator();
		tokenObject.tokensList.add(token);
		tokenObject.update();
		return token;
	}
	
	public static void deleteToken(String token)	{
		tokenObject.tokensList.remove(token);
		tokenObject.update();
	}
}
