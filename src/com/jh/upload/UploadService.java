package com.jh.upload;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jh.action.Action;
import com.jh.action.actionForward;
import com.jh.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UploadService implements Action {
	private UploadDAO uploadDAO;
	public UploadService() {
		uploadDAO = new UploadDAO();
	}

	@Override
	public actionForward list(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public actionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public actionForward insert(HttpServletRequest request, HttpServletResponse response) {
		// 스마트에디터 파일 업로드
		actionForward actionforward = new actionForward();
		int maxPostSize = 50*1024*1024;
		String saveDirectory = request.getServletContext().getRealPath("upload");
		System.out.println(saveDirectory);
		
		File file = new File(saveDirectory);
		if(!file.exists()) {
			file.mkdirs();
		}
		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
			String callback = multipartRequest.getParameter("callback");
			String callback_func = multipartRequest.getParameter("callback_func");
			String fileName = multipartRequest.getFilesystemName("Filedata");
			
			//1. 절대 경로
			String path = request.getContextPath();
			//2. 최종 결과물
			String result = "&bNewLine=true&sFileURL="+path+"/upload/"+fileName;
			//3.
			result =callback+"?callback_func="+callback_func+result;
			
			request.setAttribute("result", result);
			actionforward.setPath(result);
			actionforward.setCheck(false);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return actionforward;
	}

	@Override
	public actionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public actionForward delete(HttpServletRequest request, HttpServletResponse response) {
		actionForward actionforward = new actionForward();
		actionforward.setCheck(true);
		Connection con = null;
		int result = 0;
		try {
			con = DBConnector.getConnect();
			int pnum = Integer.parseInt(request.getParameter("pnum"));
			result = uploadDAO.delete(pnum, con);
			
			
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
