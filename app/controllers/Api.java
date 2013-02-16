package controllers;

import models.PromotionModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.*;
import play.mvc.*;

import views.html.*;

public class Api extends Controller {

	public static Result login() {
		return ok();
	}

	public static Result addStudent() {
		return ok();
	}

	public static Result delStudent(String id) {
		return ok();
	}

	public static Result getStudent(String id) {
		return ok();
	}

	public static Result editStudent(String id) {
		return ok();
	}

	public static Result addPromotion() {
		return ok();
	}

	public static Result delPromotion(String id) {
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(id));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(promo != null)	{
			promo.delete();
			node.put("status", "success");
		}else	{
			node.put("state", "failure");
			node.put("msg", "Promotion does not exist");
		}
		return ok(node);
	}

	public static Result getPromotion(String id) {
		if(id.compareTo("") == 0)	{
			PromotionModel[] all = PromotionModel.finder.all().toArray(new PromotionModel[0]);
			JsonNodeFactory factory = JsonNodeFactory.instance;
			ArrayNode ret = new ArrayNode(factory);
			for(PromotionModel promo : all)	{
				ObjectNode node = new ObjectNode(factory);
				node.put("id", promo.id().toString());
				node.put("annee", promo.getAnnee());
				node.put("name", promo.getLabel());
				ret.add(node);
			}
			return ok(ret);
		}else	{
			PromotionModel promo = PromotionModel.finder.byId(new ObjectId(id));
			JsonNodeFactory factory = JsonNodeFactory.instance;
	
			ObjectNode node = new ObjectNode(factory);
			node.put("id", promo.id().toString());
			node.put("annee", promo.getAnnee());
			node.put("name", promo.getLabel());
			return ok(node);
		}	
	}

	public static Result editPromotion(String id) {
		return ok();
	}

	public static Result addMark() {
		return ok();
	}

	public static Result delMark(String id) {
		return ok();
	}

	public static Result getMark(String id) {
		return ok();
	}

	public static Result editMark(String id) {
		return ok();
	}

}
