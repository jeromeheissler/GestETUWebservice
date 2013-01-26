package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

import leodagdag.play2morphia.Model;

public class TeacherModel extends Model {

	@Id
	private ObjectId id;
	
	private String mail;
	private String pass;
	
	public static Finder<ObjectId, TeacherModel> finder = new Finder<ObjectId, TeacherModel>(ObjectId.class, TeacherModel.class);

	public ObjectId id()	{
		return id;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
