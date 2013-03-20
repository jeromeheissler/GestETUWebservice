package models;


import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.query.Query;

import leodagdag.play2morphia.Model;

public class StudentModel extends Model {

	@Id
	private ObjectId id;
	
	//prenom
	private String firstname;
	//nom
	private String lastname;
	private String numStu;
	
	private String email;
	@JsonIgnore
	private String password;
	
	@Reference(lazy=true)
	private PromotionModel promotion;
	@Reference(lazy=true)
	private List<TestModel> lstTest;
	
	public static Finder<ObjectId, StudentModel> finder = new Finder<ObjectId, StudentModel>(ObjectId.class, StudentModel.class);
	
	public static StudentModel findOwner(TestModel test)	{
		Query<StudentModel> query = finder.getDatastore().createQuery(StudentModel.class);
		return query.field("lstTest").hasThisElement(test).get();
	}
	
	public static List<StudentModel> findWithPromotion(PromotionModel promo)	{
		Query<StudentModel> query = finder.getDatastore().createQuery(StudentModel.class);
		return query.field("promotion").equal(promo).asList();
	}
	
	public static StudentModel findByStudentNumber(String number)	{
		Query<StudentModel> query = finder.getDatastore().createQuery(StudentModel.class);
		return query.field("numStu").equal(number).get();
	}
	
	public static StudentModel findByLoginHashedPass(String login, String pass)	{
		Query<StudentModel> query = finder.getDatastore().createQuery(StudentModel.class);
		return query.field("email").equal(login).field("password").equal(pass).get();
	}
	
	public StudentModel()	{
		lstTest = new ArrayList<TestModel>();
	}
	
	@JsonProperty
	public String id()	{
		if(id == null)
			return null;
		return id.toString();
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
	public String getNumStu() {
		return numStu;
	}
	public void setNumStu(String numStu) {
		this.numStu = numStu;
	}
	public PromotionModel getPromotion() {
		return promotion;
	}
	public void setPromotion(PromotionModel promotion) {
		this.promotion = promotion;
	}
	public List<TestModel> getLstTest() {
		return lstTest;
	}
	public void addTest(TestModel test) {
		this.lstTest.add(test);
	}
	public void delTest(TestModel test)	{
		int nbBoucle = lstTest.size();
		while(nbBoucle > 0)	{
			if(lstTest.get(nbBoucle-1).id().equals(test.id()))	{
				lstTest.remove(nbBoucle-1);
				break;
			}
			nbBoucle--;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
