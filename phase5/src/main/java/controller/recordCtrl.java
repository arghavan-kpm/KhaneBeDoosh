package controller;

import domain.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class recordCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json ; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String result = "";
        boolean flag = false;

        try {

            JSONObject json = new JSONObject((String)request.getAttribute("body"));
            String dealType = json.getString("dealType");
            String buildingType = json.getString("buildingType");
            String area = json.getString("area");
            String phone = json.getString("phone");
            String basePrice = json.getString("basePrice");
            String rentPrice = json.getString("rentPrice");
            String sellPrice = json.getString("sellPrice");
            String address = json.getString("address");
            String description = json.getString("description");
            String user = (String)request.getAttribute("user");

            if(user == null)
                user = "";

            if(dealType.contains("<") || buildingType.contains("<") || area.contains("<") || phone.contains("<")
            || basePrice.contains("<") || rentPrice.contains("<") || sellPrice.contains("<") || address.contains("<")
            || description.contains("<")){
                dealType = "";
                flag = true;
                System.out.println("*** XSS ATTEMPT CATCHED ***");
            }

            if (buildingType.equals("") ||area.equals("") ||
                    dealType.equals("") || address.equals("") || phone.equals("") ||
                    description.equals("") ){


                result = "empty params";
                response.setStatus(400);
                if( flag )
                    result = "xss attempt";

                //request.setAttribute("msg", "فرم ناقص است . لطفا آن را تکمیل بفرمایید");
            }
            else if(user.equals("")){
                result = "No User";
                response.setStatus(401);
            }

            else {

                if(dealType.equals("اجاره")){
                    if(rentPrice.equals("") || basePrice.equals("")) {
                        result = "empty params";
                        response.setStatus(400);
                    }
                }

                else if(dealType.equals("خرید")){
                    if(sellPrice.equals("")) {
                        result = "empty params";
                        response.setStatus(400);
                    }
                }
                else{
                    result = "invalid dealType";
                    response.setStatus(400);
                }
                if (result.equals("") && InfoBank.getUserByUsername(user) != null
                    /*Tools.IsItNum(request.getParameter("area")) && Tools.IsItNum(request.getParameter("price"))*/) {

                    InfoBank.estate.addHouse(buildingType, Tools.decode(area),
                            dealType, Tools.decode(sellPrice),Tools.decode(rentPrice),Tools.decode(basePrice),
                            address,phone, description, InfoBank.getUserByUsername(user));


                    result = "OK";
                    response.setStatus(200);

                    // request.setAttribute("msg", "خانه ی شما با موفقیت ثبت شد");
                } /*else if (!Tools.IsItNum(request.getParameter("area")) && Tools.IsItNum(request.getParameter("price")))
                    request.setAttribute("msg", "متراژ مورد نظر قابل ثبت نیست");

                else if (!Tools.IsItNum(request.getParameter("price")) && Tools.IsItNum(request.getParameter("area")))
                    request.setAttribute("msg", "قیمت مورد نظر قابل ثبت نیست");

                */ else {
                    response.setStatus(400);
                    result = "invalid user";
                    //request.setAttribute("msg", "مقادیر متراژ و قیمت مورد نظر قابل ثبت نیستند");
                }
            }


        }catch(Exception e){
            result = e.getClass().getName() + " : " + e.getMessage();

        }
        out.println("{\"result\" : \"" + result + "\"}");
        //request.setAttribute("user", InfoBank.getUserByUsername(request.getParameter("user")));
        // request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}