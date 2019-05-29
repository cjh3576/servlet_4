package com.jh.board.comments;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jh.action.Action;
import com.jh.action.actionForward;
import com.jh.page.SearchMakePage;
import com.jh.page.SearchRow;
import com.jh.util.DBConnector;

public class CommentsService implements Action {
	private CommentsDAO commentsDAO;
	
	public CommentsService() {
		commentsDAO = new CommentsDAO();
	}

	@Override
	public actionForward list(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		int curPage = 1;
		int num = 0;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		num = Integer.parseInt(request.getParameter("num"));
	
		SearchMakePage searchMakePage = new SearchMakePage(curPage, "", "");
		SearchRow searchRow = searchMakePage.makeRow();
		Connection con = null;
		List<CommentsDTO> ar = null;
		try {
			con = DBConnector.getConnect();
			ar = commentsDAO.selectList(searchRow,num, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("commentsList", ar);
		actionforward.setCheck(true);
		actionforward.setPath("../WEB-INF/views/common/list.jsp");
		
		return actionforward;
	}

	@Override
	public actionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public actionForward insert(HttpServletRequest request, HttpServletResponse response) {
	actionForward actionforward = new actionForward();
	CommentsDTO commentsDTO = new CommentsDTO();
	commentsDTO.setNum(Integer.parseInt(request.getParameter("num")));
	commentsDTO.setWriter(request.getParameter("writer"));
	commentsDTO.setContents(request.getParameter("contents"));
	int result = 0;
	Connection con = null;
	try {
		con = DBConnector.getConnect();
		result = commentsDAO.insert(commentsDTO, con);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	request.setAttribute("result", result);
	actionforward.setCheck(true);
	actionforward.setPath("../WEB-INF/views/common/result2.jsp");
	
	return actionforward;
	}

	@Override
	public actionForward update(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionForward = new actionForward();
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setCnum(Integer.parseInt(request.getParameter("cnum")));
		commentsDTO.setContents(request.getParameter("contents"));
		int result=0;
		Connection con=null;
		
		try {
			con = DBConnector.getConnect();
			result = commentsDAO.Update(commentsDTO, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}

	@Override
	public actionForward delete(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		int result = 0;
		Connection con = null;
		
		try {
			int cnum = Integer.parseInt(request.getParameter("cnum"));
			con = DBConnector.getConnect();
			result = commentsDAO.delete(cnum, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("result", result);
		actionforward.setCheck(true);
		actionforward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionforward;
	}

}
