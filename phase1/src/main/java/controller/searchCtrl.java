package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class searchCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


            if( ( Tools.IsItNum(request.getParameter("minArea")) || request.getParameter("minArea").equals("")) &&
                    ( Tools.IsItNum(request.getParameter("maxPrice")) || request.getParameter("maxPrice").equals("")) ) {

                if(request.getParameter("dealType").equals("") || request.getParameter("dealType").equals("خرید") || request.getParameter("dealType").equals("اجاره")) {

                    request.setAttribute("houses", InfoBank.estate.search(
                            new SearchForm(request.getParameter("minArea"), request.getParameter("buildingType"), request.getParameter("dealType"),
                                    request.getParameter("maxPrice"))));

                    request.setAttribute("user", InfoBank.getUserByUsername(request.getParameter("user")));
                    request.getRequestDispatcher("search.jsp").forward(request, response);
                }

                else{
                    request.setAttribute("msg", "برای نوع قرار داد فقط از واژه های خرید یا اجاره استفاده کنید");
                    request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
                    request.getRequestDispatcher("form.jsp").forward(request, response);
                }
            }

            else{
                if(!Tools.IsItNum(request.getParameter("minArea")) && !request.getParameter("minArea").equals("") &&
                        ( Tools.IsItNum(request.getParameter("maxPrice")) || request.getParameter("maxPrice").equals("")))

                    request.setAttribute("msg", "حداقل متراژ وارد شده نامعتبر است");

                if( (Tools.IsItNum(request.getParameter("minArea")) || request.getParameter("minArea").equals("")) &&
                        !Tools.IsItNum(request.getParameter("maxPrice")) && !request.getParameter("maxPrice").equals(""))

                    request.setAttribute("msg", "حداکثر قیمت وارد شده نامعتبر است");

                if(!Tools.IsItNum(request.getParameter("minArea")) && !request.getParameter("minArea").equals("") &&
                        !Tools.IsItNum(request.getParameter("maxPrice")) && !request.getParameter("maxPrice").equals(""))

                    request.setAttribute("msg", "حداقل متراژ و حداکثر قیمت وارد شده نا معتبر هستند ");

                request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
                request.getRequestDispatcher("form.jsp").forward(request, response);
            }


        }catch (Exception e){
            request.setAttribute("mesg",e.getLocalizedMessage());
        }




    }
}
