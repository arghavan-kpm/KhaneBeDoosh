package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;
import java.io.IOException;

public class chargeCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            if(Tools.IsItNum(request.getParameter("credit")))

                if(Tools.POSTreq(InfoBank.apiKey,"http://acm.ut.ac.ir/ieBank/pay", request.getParameter("user"), Tools.decode(request.getParameter("credit")) ))
                    InfoBank.getUserByUsername(request.getParameter("user")).charge(
                                            Tools.decode(request.getParameter("credit")));
                else
                    request.setAttribute("msg", "ارتباط با سرور بانک امکان پذیر نمی باشد .");
            else
                throw new IllegalArgumentException("");

        }catch(IllegalArgumentException ie){
            request.setAttribute("msg", "اعتبار وارد شده صحیح نمی باشد .");
        }
        catch(NullPointerException ne){
            request.setAttribute("msg", "کاربر نامعتبر است .");
        }
        /// BANK
        request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
        request.getRequestDispatcher("form.jsp").forward(request, response);

    }
}
