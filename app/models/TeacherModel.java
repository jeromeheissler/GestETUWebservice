package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.query.Query;
import org.codehaus.jackson.annotate.JsonProperty;

import leodagdag.play2morphia.Model;

public class TeacherModel extends Model {

	@Id
	private ObjectId id;
	
	private String mail;
	private String pass;
	private String firstname;
	private String lastname;
	
	public static Finder<ObjectId, TeacherModel> finder = new Finder<ObjectId, TeacherModel>(ObjectId.class, TeacherModel.class);

	public static TeacherModel findByLoginHashedPass(String login, String pass)	{
		Query<TeacherModel> query = finder.getDatastore().createQuery(TeacherModel.class);
		return query.field("mail").equal(login).field("pass").equal(pass).get();
	}
	
	public TeacherModel()	{
		
	}
	
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
