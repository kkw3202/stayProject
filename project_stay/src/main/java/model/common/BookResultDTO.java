package model.common;

public class BookResultDTO {
	private String book_number;
	private String book_name;
	private String book_checkin_date;
	private String book_checkout_date;
	private String prt_book_checkin_date;
	private String prt_book_checkout_date;;
	
	private String acc_name;
	private String room_name;
	
	private int room_price;
	private String prt_room_price;
	private int date_of_stay;
	private int total_price;
	private String prt_total_price;
	
	


	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getBook_checkin_date() {
		return book_checkin_date;
	}

	public void setBook_checkin_date(String book_checkin_date) {
		this.book_checkin_date = book_checkin_date;
	}

	public String getBook_checkout_date() {
		return book_checkout_date;
	}

	public void setBook_checkout_date(String book_checkout_date) {
		this.book_checkout_date = book_checkout_date;
	}

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public int getRoom_price() {
		return room_price;
	}

	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}

	public String getBook_number() {
		return book_number;
	}

	public void setBook_number(String book_number) {
		this.book_number = book_number;
	}

	public int getDate_of_stay() {
		return date_of_stay;
	}

	public void setDate_of_stay(int date_of_stay) {
		this.date_of_stay = date_of_stay;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	public String getPrt_total_price() {
		return prt_total_price;
	}

	public void setPrt_total_price(String prt_total_price) {
		this.prt_total_price = prt_total_price;
	}

	public String getPrt_room_price() {
		return prt_room_price;
	}

	public void setPrt_room_price(String prt_room_price) {
		this.prt_room_price = prt_room_price;
	}

	public String getPrt_book_checkin_date() {
		return prt_book_checkin_date;
	}

	public void setPrt_book_checkin_date(String prt_book_checkin_date) {
		this.prt_book_checkin_date = prt_book_checkin_date;
	}

	public String getPrt_book_checkout_date() {
		return prt_book_checkout_date;
	}

	public void setPrt_book_checkout_date(String prt_book_checkout_date) {
		this.prt_book_checkout_date = prt_book_checkout_date;
	}
	
	
}
