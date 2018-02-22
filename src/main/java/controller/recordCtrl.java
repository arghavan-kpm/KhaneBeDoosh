package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class recordCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("buildingType").equals("") || request.getParameter("area").equals("") ||
                request.getParameter("dealType").equals("") || request.getParameter("price").equals("") ||
                request.getParameter("address").equals("") || request.getParameter("phone").equals("") ||
                request.getParameter("description").equals("") || request.getParameter("user").equals(""))

            request.setAttribute("msg", "فرم ناقص است . لطفا آن را تکمیل بفرمایید");



        else {
            InfoBank.estate.addHouse(request.getParameter("buildingType"), Tools.decode(request.getParameter("area")),
                    request.getParameter("dealType"), Tools.decode(request.getParameter("price")),
                    request.getParameter("address"), request.getParameter("phone"), request.getParameter("description"),
                    InfoBank.getUserByUsername(request.getParameter("user")));

            request.setAttribute("msg", "خانه ی شما با موفقیت ثبت شد");
        }

        request.setAttribute("user", InfoBank.getUserByUsername(request.getParameter("user")));
        request.getRequestDispatcher("form.jsp").forward(request, response);
    }

}