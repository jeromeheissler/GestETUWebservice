package controllers;

import java.io.IOException;

import models.PromotionModel;
import models.StudentModel;
import models.TestModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
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

	public static Result addStudent() throws JsonParseException, JsonMappingException, IOException {
		StudentModel student = mapper.readValue(request().body().asJson(), StudentModel.class);
		student.insert();
		ObjectNode node = new ObjectNode(factory);
		node.put("status", 1);
		return ok(node);
	}

	public static Result delStudent(String id) {
		ObjectNode node = new ObjectNode(factory);
		
		StudentModel student = StudentModel.finder.byId(new ObjectId(id));
		if(student != null)	{
			student.delete();
			node.put("status", 1);
		} else {
			node.put("status", 2);
			node.put("msg", "student not found");
		}

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
			String json = mapper.writeValueAsString(student);
			return ok(json);
		}
	}

	public static Result editStudent(String id) {
		ObjectNode node = new ObjectNode(factory);
		node.put("status", 1);
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

	public static Result addMark() throws JsonParseException, JsonMappingException, IOException {
		TestModel newObject = mapper.readValue(request().body().asJson(), TestModel.class);
		if(newObject.getSubject().id() == null)	{
			newObject.getSubject().insert();
		}
		newObject.insert();
		ObjectNode node = new ObjectNode(factory);
		node.put("status", 1);
		return ok(node);
	}

	public static Result delMark(String id) {
		ObjectNode node = new ObjectNode(factory);
		
		TestModel test = TestModel.finder.byId(new ObjectId(id));
		if(test != null)	{
			test.delete();
			node.put("status", 1);
		} else {
			node.put("status", 404);
		}
		
		return ok(node);
	}

	public static Result getMark(String id) throws JsonGenerationException, JsonMappingException, IOException {
		TestModel test = TestModel.finder.byId(new ObjectId(id));
		String json = mapper.writeValueAsString(test);
		return ok(json);

	}

	public static Result editMark(String id) throws JsonParseException, JsonMappingException, IOException {
		TestModel test = TestModel.finder.byId(new ObjectId(id));
		TestModel newObject = mapper.readValue(request().body().asJson(), TestModel.class);
		test.setNote(newObject.getNote());
		if(newObject.getSubject().id() == null)
			newObject.getSubject().insert();
		test.setSubject(newObject.getSubject());
		test.update();
		
		ObjectNode node = new ObjectNode(factory);
		node.put("ok", 1);
		return ok(node);
	}

}
