package controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        String id = request.getParameter("id");
        Task task = em.find(Task.class, Integer.parseInt(id));
        em.close();

        request.setAttribute("task", task);
        request.setAttribute("_token", request.getSession().getId());

        // Update, DeleteServletで使うためにidをセッションスコープに保存
        request.getSession().setAttribute("task_id", task.getId());

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);
    }

}
