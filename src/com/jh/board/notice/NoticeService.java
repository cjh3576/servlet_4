package com.jh.board.notice;

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

public class NoticeService implements Action {
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
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
		//c,w,t
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1. row
		SearchRow searchRow = s.makeRow();
		List<BoardDTO> ar = null;
		Connection con = null;
		try {
			con = DBConnector.getConnect();
			ar = noticeDAO.selectList(searchRow, con);
			//2. page
			int totalCount = noticeDAO.getTotalCount(searchRow,con);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			actionforward.setCheck(true);
			actionforward.setPath("../WEB-INF/views/board/boardList.jsp");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionforward.setCheck(true);
			actionforward.setPath("../WEB-INF/views/common/result.jsp");
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
				NoticeDTO noticeDTO = new NoticeDTO();
				noticeDTO.setTitle(multipartRequest.getParameter("title"));
				noticeDTO.setWriter(multipartRequest.getParameter("writer"));
				noticeDTO.setContents(multipartRequest.getParameter("contents"));
				
				//1. 시퀀스 번호
				int num = noticeDAO.getNum();
				noticeDTO.setNum(num);
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				//2. notice insert
				num = noticeDAO.insert(noticeDTO, con);
				
				//3. upload insert
				for(UploadDTO uploadDTO : ar) {
					uploadDTO.setNum(noticeDTO.getNum());
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
			actionforward.setPath("./boardList");
			
		}//post끝
		return actionforward;
	}

	@Override
	public actionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public actionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
