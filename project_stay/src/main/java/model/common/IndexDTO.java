package model.common;
// 
public class IndexDTO {
	private int acc_code;
	private String acc_type;
	private String acc_name;
	private String acc_img;
	
	private String city_code;
	private String city_name;
	private String city_img;
	
	private double avg_rev_rating; // 평균 구한값으로 가져오기!
	private String rev_content; 
	private int count_rev_rating; // count 구한값으로 가져오기!
	
	
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
	
	
	
}
