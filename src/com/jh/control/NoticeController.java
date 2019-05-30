package com.jh.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jh.action.actionForward;
import com.jh.board.notice.NoticeService;

/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/NoticeController")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService noticeservice;
    private String board;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeController() {
        super();
        noticeservice = new NoticeService();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
    	board = config.getInitParameter("board");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String v = request.getServletContext().getInitParameter("view");
		System.out.println(v);
		String command = request.getPathInfo();
		actionForward actionforward = null;
		if(command.equals("/noticeList")){
			actionforward= noticeservice.list(request, response);
		}else if(command.equals("/noticeSelect")) {
			actionforward= noticeservice.select(request, response);
		}else if(command.equals("/noticeUpdate")) {
			actionforward= noticeservice.update(request, response);
		}else if(command.equals("/noticeWrite")) {
			actionforward= noticeservice.insert(request, response);
		}else if(command.equals("/noticeDelete")) {
			actionforward = noticeservice.delete(request, response);
		}
		
		request.setAttribute("board", board);
		
		
		if(actionforward.isCheck()) {
			RequestDispatcher view = request.getRequestDispatcher(actionforward.getPath());
			view.forward(request, response);
		}else {
			response.sendRedirect(actionforward.getPath());
		}
		System.out.println("Notice !!!!!!!!!!!!!!!!!!!!!!!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
