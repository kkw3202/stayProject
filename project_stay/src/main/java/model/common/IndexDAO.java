package model.common;

import java.sql.*;
import java.util.ArrayList;

import DBPKG.Util;

public class IndexDAO {
	public ArrayList<IndexDTO> getIndex_section(){
		ArrayList<IndexDTO> result = new ArrayList<IndexDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();

			String sql = "select avg(r.rev_rating) as avg_rev_rating, count(r.rev_rating), a.acc_name, a.acc_img, r.acc_code "
					+ "from accommodation a "
					+ "inner join reviews r on r.acc_code = a.acc_code "
					+ "group by a.acc_code "
					+ "order by avg_rev_rating desc limit 4";
			pstmt = con.prepareStatement(sql);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				IndexDTO idto = new IndexDTO();
				idto.setAvg_rev_rating(rs.getDouble(1));
				idto.setCount_rev_rating(rs.getInt(2));
				idto.setAcc_name(rs.getString(3));
				idto.setAcc_img(rs.getString(4));
				result.add(idto);
			}
			
			for(int i=0;i<4;i++) {
				sql = "select rev_content from reviews where acc_code=? order by rev_code desc limit 1";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, result.get(i).getAcc_code());
				rs = pstmt.executeQuery();
				if(rs.next()) result.get(i).setRev_content(rs.getString(1));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
