package com.jh.board.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jh.board.BoardDAO;
import com.jh.board.BoardDTO;
import com.jh.page.SearchRow;
import com.jh.util.DBConnector;

public class QnaDAO implements BoardDAO {

	@Override
	public int getNum() throws Exception {
		int result = 0;
	      Connection con = DBConnector.getConnect();
	      String sql = "select qna_seq.nextval from dual";
	      PreparedStatement st = con.prepareStatement(sql);
	      ResultSet rs = st.executeQuery();
	      rs.next();
	      result = rs.getInt(1);
	      
	      rs.close();
	      st.close();
	      return result;
	}

	@Override
	public int getTotalCount(SearchRow searchRow, Connection con) throws Exception {
		int result;
		String sql = "select count(num) from qna where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		
		rs.close();
		st.close();
		return result;
	}

	@Override
	public List<BoardDTO> selectList(SearchRow searchRow, Connection con) throws Exception {
		ArrayList<BoardDTO> ar = new ArrayList<BoardDTO>();
		
		String sql = "select * from " + 
				"(select rownum R, p.* from " + 
				"(select * from qna where "+searchRow.getSearch().getKind()+" like ? order by ref desc,step asc) p) " + 
				"where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getString("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			ar.add(qnaDTO);
		}
		rs.close();
		st.close();
		
		
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num, Connection con) throws Exception {
		QnaDTO qnadto = new QnaDTO();
		
		String sql = "select * from qna where num=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
			qnadto.setNum(rs.getInt("num"));
			qnadto.setTitle(rs.getString("title"));
			qnadto.setContents(rs.getString("contents"));
			qnadto.setWriter(rs.getString("writer"));
			qnadto.setReg_date(rs.getString("reg_date"));
			qnadto.setHit(rs.getInt("hit"));
		}
		rs.close();
		st.close();
		return qnadto;
	}
	

	@Override
	public int insert(BoardDTO boardDTO, Connection con) throws Exception {
		int result = 0;
		String sql = "insert into qna values(?,?,?,?,sysdate,0,?,0,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		st.setString(4, boardDTO.getWriter());
		st.setInt(5, boardDTO.getNum());
		result = st.executeUpdate();
		
		st.close();
		return result;
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
	
	//답글
	public int reply(QnaDTO qnaDTO) throws Exception{
		
		
		return 0;
	}

}
