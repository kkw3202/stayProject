package model.Review;

public class ReviewDTO {
	private int rev_code;  // 리뷰코드
	private int acc_code;  // 숙소코드
	private int user_code;  // 회원코드
	private int rev_rating;  // 평점
	private String rev_content;  // 리뷰내용
	private String rev_created_datetime;  // 작성일자/시간
	private int rev_chk_del;  // 리뷰체크키
	private int rev_like;  // 좋아요 (해당 리뷰글을 보고, 다른사람들이 추천을 누르면 증가)
	
	private String user_name;  // 회원명
	private String acc_name;  // 숙소명
	private String user_email;  // 회원 이메일
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public int getRev_code() {
		return rev_code;
	}
	public void setRev_code(int rev_code) {
		this.rev_code = rev_code;
	}
	public int getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(int acc_code) {
		this.acc_code = acc_code;
	}
	public int getUser_code() {
		return user_code;
	}
	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}
	public int getRev_rating() {
		return rev_rating;
	}
	public void setRev_rating(int rev_rating) {
		this.rev_rating = rev_rating;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public String getRev_created_datetime() {
		return rev_created_datetime;
	}
	public void setRev_created_datetime(String rev_created_datetime) {
		this.rev_created_datetime = rev_created_datetime;
	}
	public int getRev_chk_del() {
		return rev_chk_del;
	}
	public void setRev_chk_del(int rev_chk_del) {
		this.rev_chk_del = rev_chk_del;
	}
	public int getRev_like() {
		return rev_like;
	}
	public void setRev_like(int rev_like) {
		this.rev_like = rev_like;
	}
	
	
}
