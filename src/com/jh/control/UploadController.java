package com.jh.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jh.action.actionForward;
import com.jh.upload.UploadService;

/**
 * Servlet implementation class FIleController
 */
@WebServlet("/FIleController")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UploadService uploadService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
        super();
        uploadService = new UploadService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		actionForward actionforward = null;
		if(command.equals("/fileDelete")) {
			actionforward = uploadService.delete(request, response);
		}else if(command.equals("/fileUpload")) {
			actionforward = uploadService.insert(request, response);
		}
		
		if(actionforward.isCheck()) {
			RequestDispatcher view = request.getRequestDispatcher(actionforward.getPath());
			view.forward(request, response);
		}else {
			response.sendRedirect(actionforward.getPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
