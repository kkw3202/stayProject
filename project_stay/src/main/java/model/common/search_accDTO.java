package model.common;

public class search_accDTO {
	private String acc_img;
	private String acc_type;
	private String acc_name;
	private String city_name;
	private double avg_rating;
	private int acc_code;
	private int room_price;
	private int cnt_reviews;
	// 추후 필터링 항목도 추가로 담아야함! => 첫 검색에서 가져올 건 다 가져와서 기존 검색 건드릴 필요 없음
	public String getAcc_img() {
		return acc_img;
	}
	public void setAcc_img(String acc_img) {
		this.acc_img = acc_img;
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
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public double getAvg_rating() {
		return avg_rating;
	}
	public void setAvg_rating(double avg_rating) {
		this.avg_rating = avg_rating;
	}
	public int getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(int acc_code) {
		this.acc_code = acc_code;
	}
	public int getRoom_price() {
		return room_price;
	}
	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}
	public int getCnt_reviews() {
		return cnt_reviews;
	}
	public void setCnt_reviews(int cnt_reviews) {
		this.cnt_reviews = cnt_reviews;
	}
	

	

}
