package controllers;

import models.PromotionModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

public class Promotion extends Controller {

	public static Result promotion()	{
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
	}
	
	public static Result addPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputName = signupForm.field("name").value();
		String inputAnnee = signupForm.field("annee").value();
		PromotionModel promo = new PromotionModel();
		promo.setAnnee(Integer.parseInt(inputAnnee));
		promo.setLabel(inputName);
		promo.insert();
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		node.put("state", "success");
		node.put("id", promo.id().toString());
		return ok(node);
	}
	
	public static Result delPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(promo != null)	{
			promo.delete();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result editPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		String inputname = signupForm.field("name").value();
		String inputannee = signupForm.field("annee").value();
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(promo != null)	{
			promo.setAnnee(Integer.parseInt(inputannee));
			promo.setLabel(inputname);
			promo.update();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
}
