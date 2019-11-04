package controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 仮
        Task task = new Task();

        String content = "eラーニング第一章の課題提出";
        task.setContent(content);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        task.setCreated_at(currentTime);
        task.setUpdated_at(currentTime);

        // データベースに保存
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();

        // データベースの自動番号取得(確認用)
        response.getWriter().append((Integer.valueOf(task.getId())).toString());

        em.close();

    }

}
