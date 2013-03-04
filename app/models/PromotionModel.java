package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

import leodagdag.play2morphia.Model;

public class PromotionModel extends Model {

	public static Finder<ObjectId, PromotionModel> finder = new Finder<ObjectId, PromotionModel>(ObjectId.class, PromotionModel.class);
	
	@Id
	private ObjectId id;
	
	private int annee;
	private String label;
	
	public ObjectId id()	{
		return id;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
