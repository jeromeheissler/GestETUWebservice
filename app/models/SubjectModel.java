package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

import leodagdag.play2morphia.Model;

public class SubjectModel extends Model {

	@Id
	private ObjectId id;
	private String name;
	
	public static Finder<ObjectId, SubjectModel> finder = new Finder<ObjectId, SubjectModel>(ObjectId.class, SubjectModel.class);

	public ObjectId id()	{
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
