<%--
  Created by IntelliJ IDEA.
  User: kosar
  Date: 2/22/18
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.* , domain.*"%>

<html>
<head>
    <title>moreInfo</title>
</head>
<body>

    <% Individual individual= (Individual)request.getAttribute("user"); %>
    <% House h = (House)request.getAttribute("house");%>

    <% if (request.getAttribute("ERROR") != null) {%>
    <h1><%= request.getAttribute("ERROR") %></h1><% }%>

    <% if (request.getAttribute("HACK") != null) {%>
    <h1><%= request.getAttribute("HACK") %></h1><% }
    else {%>
    <table style="width:100%" border="2">
        <tr>
            <th>اعتبار شما : <%= Tools.getPersian(Integer.toString(individual.getCredit())) %> تومان</th>
            <th>نام کاربری :  <%= individual.getName()%></th>
        </tr>

        <tr>
            <td>
                <p>نوع ساختمان :<%=h.getBuildType()%></p>
                <%if(h.getDeal() == 0){%>
                <p>قیمت:<%=h.getSellP()%><br />
                    نوع:فروشی
                </p>
                    <%}
                    else{
                    %>
                <p>قیمت پایه:<%=h.getBaseP()%><br />
                    قیمت اجاره:<%=h.getRentP()%><br />
                    نوع :‌رهن و اجاره
                </p>
                    <%}%>
                <p>متراژ:<%=h.getArea()%>

                </p>
                <p>آدرس: <%=h.getAddr()%></p>
                <p>توضیحات: <%=h.getDesc()%></p>
                <a href="<%=h.getImageURL()%>" target="_blank">لینک عکس</a><br />

                <%if(individual.searchHouse(h.getId())){%>
                <p> تلفن مالک / مشاور :‌<%= h.getOwnerPhone()%></p>
                <%} else { %>
                <a href="cpay?house_id=<%=h.getId()%>&user=<%=individual.getUsername()%>">دریافت شماره مالک /مشاور </a>
                <% } %>
            </td>
        </tr>
    </table>
<%}%>
</body>
</html>
