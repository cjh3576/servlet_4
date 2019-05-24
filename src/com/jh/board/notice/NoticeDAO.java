package com.jh.board.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jh.board.BoardDAO;
import com.jh.board.BoardDTO;
import com.jh.page.SearchRow;

public class NoticeDAO implements BoardDAO{

	@Override
	public int getNum() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCount(SearchRow searchRow, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardDTO> selectList(SearchRow searchRow, Connection con) throws Exception {
		ArrayList<BoardDTO> ar = new ArrayList<BoardDTO>();
		
		String sql = "select * from " + 
				"(select rownum R, p.* from " + 
				"(select * from notice where "+searchRow.getSearch().getKind()+" like ? order by num desc) p) " + 
				"where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			NoticeDTO dto = new NoticeDTO();
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
			dto.setContents(rs.getString("contents"));
			dto.setWriter(rs.getString("writer"));
			dto.setReg_date(rs.getString("reg_date"));
			dto.setHit(rs.getInt("hit"));
			ar.add(dto);
		}
		rs.close();
		st.close();
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(BoardDTO boardDTO, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BoardDTO boardDTO, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int num, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
