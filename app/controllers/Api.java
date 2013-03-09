package controllers;

import java.io.IOException;

import models.PromotionModel;
import models.StudentModel;
import models.TestModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.mvc.*;

public class Api extends Controller {

	private static ObjectMapper mapper = new ObjectMapper();
	private static JsonNodeFactory factory = JsonNodeFactory.instance;
	
	public static Result login() {
		return ok();
	}

	public static Result addStudent() {
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}

	public static Result delStudent(String id) {
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}

	public static Result getStudent(String id) throws JsonGenerationException, JsonMappingException, IOException {
		if(id.compareTo("") == 0)	{
			StudentModel[] all = StudentModel.finder.all().toArray(new StudentModel[0]);
			ArrayNode ret = new ArrayNode(factory);
			for(StudentModel student : all)	{
				String json = mapper.writeValueAsString(student);
				ret.add(json);
			}
			return ok(ret);
		}else	{
			StudentModel student = StudentModel.finder.byId(new ObjectId(id));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(student);
			return ok(json);
		}
	}

	public static Result editStudent(String id) {
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}


	public static Result getPromotion(String id) throws JsonGenerationException, JsonMappingException, IOException {
		if(id.compareTo("") == 0)	{
			PromotionModel[] all = PromotionModel.finder.all().toArray(new PromotionModel[0]);
			ArrayNode ret = new ArrayNode(factory);
			for(PromotionModel promo : all)	{
				String json = mapper.writeValueAsString(promo);
				ret.add(json);
			}
			return ok(ret);
		}else	{
			PromotionModel promo = PromotionModel.finder.byId(new ObjectId(id));
			String json = mapper.writeValueAsString(promo);;
			return ok(json);
		}	
	}

	public static Result addMark() {
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}

	public static Result delMark(String id) {
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}

	public static Result getMark(String id) throws JsonGenerationException, JsonMappingException, IOException {
		TestModel test = TestModel.finder.byId(new ObjectId(id));
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(test);
		return ok(json);

	}

	public static Result editMark(String id) {
		return ok();
	}

}
