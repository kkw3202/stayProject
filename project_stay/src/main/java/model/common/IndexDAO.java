package model.common;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import DBPKG.Util;

public class IndexDAO {
	public ArrayList<IndexDTO> getIndex_section(){
		ArrayList<IndexDTO> result = new ArrayList<IndexDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();

			String sql = "select avg(r.rev_rating) as avg_rev_rating, count(r.rev_rating), a.acc_name, a.acc_img, r.acc_code, c.city_name, a.acc_code  "
					+ "from accommodation a "
					+ "inner join reviews r on r.acc_code = a.acc_code "
					+ "inner join city c on c.city_code = a.city_code "
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
				idto.setAcc_code(rs.getInt(5));
				idto.setCity_name(rs.getString(6));
				idto.setLinked_acc_code(rs.getString(7));
				idto.setAcc_code(rs.getInt(7));
				result.add(idto);
			}
			
			for(int i=0;i<4;i++) {
				
				sql = "select rev_content, u.user_name, r.rev_created_datetime "
						+ "from reviews r "
						+ "inner join user u on u.user_code = r.user_code "
						+ "where acc_code=? order by rev_code desc limit 1";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, result.get(i).getAcc_code());

				
				
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					result.get(i).setRev_content(rs.getString(1));
					
					String name = rs.getString(2);
					//String prt_name = name.substring(1);
					String star ="**";
					// for(int j =0;j<prt_name.length();j++) star += "*";
					
					result.get(i).setUser_name(name.substring(0,1) + star);
					result.get(i).setRev_created_datetime(rs.getString(3));
				}
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
	
	// 추천숙소 8개 가져오는 메소드
	public ArrayList<IndexDTO> getIndexRecommend(String recommend_codes){
		ArrayList<IndexDTO> result = new ArrayList<IndexDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DecimalFormat de = new DecimalFormat("#.0");
		
		try {
			con = Util.getConnection();
			
			StringTokenizer st = new StringTokenizer(recommend_codes, ",");
			
			String sql = "select a.acc_code, a.acc_name, a.acc_type, a.acc_img, c.city_name, avg(r.rev_rating) "
					+ "from accommodation a "
					+ "inner join city c on c.city_code = a.city_code "
					+ "inner join reviews r on r.acc_code = a.acc_code where ";
					for (int i=0; i<7; i++) sql += "a.acc_code = ? or ";
					sql += "a.acc_code = ? group by a.acc_code";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(st.nextToken()));
			pstmt.setInt(2, Integer.parseInt(st.nextToken()));
			pstmt.setInt(3, Integer.parseInt(st.nextToken()));
			pstmt.setInt(4, Integer.parseInt(st.nextToken()));
			pstmt.setInt(5, Integer.parseInt(st.nextToken()));
			pstmt.setInt(6, Integer.parseInt(st.nextToken()));
			pstmt.setInt(7, Integer.parseInt(st.nextToken()));
			pstmt.setInt(8, Integer.parseInt(st.nextToken()));
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				IndexDTO rec_idto = new IndexDTO();
				rec_idto.setAcc_code(rs.getInt(1));
				rec_idto.setAcc_name(rs.getString(2));
				rec_idto.setAcc_type(rs.getString(3));
				rec_idto.setAcc_img(rs.getString(4));
				rec_idto.setCity_name(rs.getString(5));
				String prt_avg_rev_rating = de.format(rs.getDouble(6));
				rec_idto.setPrt_avg_rev_rating(prt_avg_rev_rating);
				
				result.add(rec_idto);
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
