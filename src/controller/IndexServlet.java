package controller;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DBより全データ取得
        EntityManager em = DBUtil.createEntityManager();

        // 開くページ数を取得(=表示のページ数)
        int page = 1;   // デフォルトは1
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){
            // paserIntで数値以外が入る例外がでたらキャッチ
            // そのままデフォルトページを表示するような処理
        }

        // 指定したデータを取得
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                .setFirstResult(( page - 1) * 15)   // データ取得の最初の位置指定
                .setMaxResults(15)                  // 最大取得件数を指定
                .getResultList();

        // 全データ数取得
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class).getSingleResult();

        em.close();

        // flushメッセージ取得
        if(request.getSession().getAttribute("flush") != null){
            // リクエストスコープに保存しセッションスコープのメッセージはremove()
            // 1回のみの表示にする為
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        // リクエストスコープに保存
        request.setAttribute("tasks", tasks);
        request.setAttribute("tasks_count", tasks_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);


    }

}
