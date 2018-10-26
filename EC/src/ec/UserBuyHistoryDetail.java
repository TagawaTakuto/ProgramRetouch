package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.ItemDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();


		int BuyId = Integer.parseInt(request.getParameter("buy_id"));

		ItemDAO itemdao = new ItemDAO();
		ArrayList<ItemDataBeans> itemData = new ArrayList<ItemDataBeans>();

		BuyDAO buydao = new BuyDAO();
		BuyDataBeans buyhis;
		try {
			buyhis = buydao.getBuyData();
			request.setAttribute("BuyData", buyhis);

			itemData = itemdao.getItemData(BuyId);
			request.setAttribute("itemData", itemData);
			request.setAttribute("c", BuyId);
			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

		} catch (SQLException e) {
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
			e.printStackTrace();
		}
	}
}