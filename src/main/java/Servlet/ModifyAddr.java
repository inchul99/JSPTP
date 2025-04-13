package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import DTO.UserAddrDTO;


@WebServlet("/modifyAddr")
public class ModifyAddr extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("id");
		String type = (String)session.getAttribute("userType");
		
		int addrId = Integer.parseInt(request.getParameter("addrId"));
		
		String label = "";
		if(request.getParameter("addrLabel") != null)
			label = request.getParameter("addrLabel");
		
		String zipcode = request.getParameter("zipcode");
		String address1 = request.getParameter("address1");
		String address2 = "";
		if(request.getParameter("address1") != null)
			address2 = request.getParameter("address2");
		
		String isDefault = "N";
		if(request.getParameter("isDefault") != null)
			isDefault = "Y";
		
		UserAddrDTO userAddrDto = new UserAddrDTO();
		userAddrDto.setAddr_label(label);
		userAddrDto.setAddr_zipcode(zipcode);
		userAddrDto.setAddr_road(address1);
		userAddrDto.setAddr_detail(address2);
		userAddrDto.setAddr_isDefault(isDefault);
		
		UserDAO userDao = new UserDAO();
		userDao.updateAddr(id, type, addrId, userAddrDto);
		
		response.sendRedirect("deliveryMn.jsp");
	}

}
