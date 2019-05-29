package com.jh.board.qna;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jh.action.Action;
import com.jh.action.actionForward;
import com.jh.board.BoardDTO;
import com.jh.page.SearchMakePage;
import com.jh.page.SearchPager;
import com.jh.page.SearchRow;
import com.jh.upload.UploadDAO;
import com.jh.upload.UploadDTO;
import com.jh.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaService implements Action {
	private QnaDAO qnaDAO;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		qnaDAO = new QnaDAO();
		uploadDAO = new UploadDAO();
	}
	
	public actionForward reply(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public actionForward list(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		int curPage = 1;
		
		try {
		curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1. row
		SearchRow searchRow = s.makeRow();
		
		int totalCount = 0;
		Connection con = null;
		try {
			con = DBConnector.getConnect();
			List<BoardDTO> ar = qnaDAO.selectList(searchRow, con);
			//2. page
			totalCount = qnaDAO.getTotalCount(searchRow, con);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			request.setAttribute("board", "qna");
			actionforward.setCheck(true);
			actionforward.setPath("../WEB-INF/views/board/boardList.jsp");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionforward.setCheck(true);
			actionforward.setPath("../WEB-INF/views/common/result.jsp");
		
		}
		
		return actionforward;
	}

	@Override
	public actionForward select(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		
		BoardDTO boardDTO = null;
		List<UploadDTO> ar = null;
		Connection con = null;
		try {
			con = DBConnector.getConnect();
			int num = Integer.parseInt(request.getParameter("num"));
			boardDTO = qnaDAO.selectOne(num, con);
			ar = uploadDAO.selectList(num,con);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		String path = "";
		if(boardDTO != null) {
			request.setAttribute("dto", boardDTO);
			request.setAttribute("upload", ar);
			path = "../WEB-INF/views/board/boardSelect.jsp";
		} else {
			request.setAttribute("message", "No Data");
			request.setAttribute("path", "../board/boardList");
			path="../WEB-INF/views/common/result.jsp";
		}
		actionforward.setCheck(true);
		actionforward.setPath(path);
		return actionforward;
	}

	@Override
	public actionForward insert(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		actionforward.setCheck(true);
		actionforward.setPath("../WEB-INF/views/board/boardWrite.jsp");
		
		String method = request.getMethod();
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			
			File file = new File(saveDirectory);
			if(!file.exists()) {
				file.mkdirs();
			} //파일인식 못할때 자동생성
			
			
			int maxPostSize = 1024*1024*100;
			Connection con = null;
			try {
				MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration<String> e = multipartRequest.getFileNames();
				ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
				while(e.hasMoreElements()) {
					String s =e.nextElement();
					String fname = multipartRequest.getFilesystemName(s);
					String oname = multipartRequest.getOriginalFileName(s);
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setFname(fname);
					uploadDTO.setOname(oname);
					ar.add(uploadDTO);
				} //파일 name속성이 불분명하기 때문에 모두 받아와서 세팅
				QnaDTO qnaDTO = new QnaDTO();
				qnaDTO.setTitle(multipartRequest.getParameter("title"));
				qnaDTO.setWriter(multipartRequest.getParameter("writer"));
				qnaDTO.setContents(multipartRequest.getParameter("contents"));
				
				con = DBConnector.getConnect();
				//1. 시퀀스 번호
				int num = qnaDAO.getNum();
				qnaDTO.setNum(num);
				con.setAutoCommit(false);
				//2. qna insert
				num = qnaDAO.insert(qnaDTO, con);
				
				//3. upload insert
				for(UploadDTO uploadDTO : ar) {
					uploadDTO.setNum(qnaDTO.getNum());
					num = uploadDAO.insert(uploadDTO, con);
					if(num<1) {
						throw new Exception();
					}
				}
				
				con.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actionforward.setCheck(false);
			actionforward.setPath("./qnaList");
			
		}//post끝
		return actionforward;
	}

	@Override
	public actionForward update(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionfoward = new actionForward();
		actionfoward.setCheck(true);
		actionfoward.setPath("../WEB-INF/views/board/boardUpdate.jsp");
		String method = request.getMethod();
		
		if(method.equals("POST")) {
			QnaDTO qnaDTO = new QnaDTO();
			
		}else {
			int num = Integer.parseInt(request.getParameter("num"));
			Connection con = null;;
			BoardDTO boardDTO = null;
			List<UploadDTO> ar = null;
			try {
				con = DBConnector.getConnect();
				boardDTO = qnaDAO.selectOne(num, con);
				ar = uploadDAO.selectList(num, con);
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
			} //finally
			request.setAttribute("dto", boardDTO);
			request.setAttribute("upload", ar);
			
		} //GET방식
		
		
		return actionfoward;
	}

	@Override
	public actionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
