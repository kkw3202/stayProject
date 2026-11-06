package model.Handler.dateHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateHandlerDAO {
	// 입실일 - 퇴실일 사이 날짜 담은 리스트 만드는 매소드
	public ArrayList<String> getAllDay(String book_checkin_date, String book_checkout_date) throws ParseException {
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		
		Date checkin = sd.parse(book_checkin_date);
		Date checkout = sd.parse(book_checkout_date);
		boolean run = true;
		
		ArrayList<String> dates = new ArrayList<String>();
		
		Date current_date = checkin;
		
		while(run) {
			dates.add(sd.format(current_date));
			Calendar c = Calendar.getInstance();
			c.setTime(current_date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			current_date = c.getTime();
			if(current_date.equals(checkout)) run = false;
		}
		
		return dates;
	}
}
