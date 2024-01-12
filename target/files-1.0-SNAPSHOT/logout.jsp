<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
  session = request.getSession();
  session.invalidate();

  response.sendRedirect("login.jsp");
%>
