package model.common;
// 
public class IndexDTO {
	private int acc_code;
	private String linked_acc_code;
	private String acc_type;
	private String acc_name;
	private String acc_img;
	
	private String city_code;
	private String city_name;
	private String city_img;
	
	private double avg_rev_rating;
	private String prt_avg_rev_rating;
	private String rev_content; 
	private int count_rev_rating;
	
	private String user_name;
	private String rev_created_datetime;
	
	
	public String getLinked_acc_code() {
		return linked_acc_code;
	}
	public void setLinked_acc_code(String linked_acc_code) {
		this.linked_acc_code = linked_acc_code;
	}
	public int getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(int acc_code) {
		this.acc_code = acc_code;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public String getAcc_img() {
		return acc_img;
	}
	public void setAcc_img(String acc_img) {
		this.acc_img = acc_img;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCity_img() {
		return city_img;
	}
	public void setCity_img(String city_img) {
		this.city_img = city_img;
	}
	public double getAvg_rev_rating() {
		return avg_rev_rating;
	}
	public void setAvg_rev_rating(double avg_rev_rating) {
		this.avg_rev_rating = avg_rev_rating;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public int getCount_rev_rating() {
		return count_rev_rating;
	}
	public void setCount_rev_rating(int count_rev_rating) {
		this.count_rev_rating = count_rev_rating;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getRev_created_datetime() {
		return rev_created_datetime;
	}
	public void setRev_created_datetime(String rev_created_datetime) {
		this.rev_created_datetime = rev_created_datetime;
	}
	public String getPrt_avg_rev_rating() {
		return prt_avg_rev_rating;
	}
	public void setPrt_avg_rev_rating(String prt_avg_rev_rating) {
		this.prt_avg_rev_rating = prt_avg_rev_rating;
	}
	
	
	
}
