<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<c:url value= 'login.do'/>" method="POST" name="loginform" >
 <Table  width='500px' style="border-width:2; background:#E0E0E0; 
                        border-style:inset; border-color:#EF02A4;">
                        <TR>
             <TD width="180" align="right">帳號：　</TD>
             <TD width="180" colspan='2' align="LEFT">
             <input  type="text" name="userId" size="10" 
             value="${sessionScope.user}">
             
             &nbsp;<small><Font color='red' size="-3">${ErrorMsgKey.AccountEmptyError}
             </Font></small></TD>
         </TR>
         <TR>
             <TD width="180" align="right">密碼：　</TD>
             <TD width="180" colspan='2' align="LEFT" >
             <input  type="password" name="pswd"  size="10" 
             value="${sessionScope.password}">
              
             &nbsp;<small><Font color='red'  size="-3">${ErrorMsgKey.PasswordEmptyError}
             </Font></small></TD>
             
         </TR>  
         <tr>
         <TD width="180" align="right" >
             <input type="checkbox" name="rememberMe" 
               <c:if test='${sessionScope.rememberMe==true}'>
                  checked='checked'
               </c:if> 
             value="true">
         </TD>
         <TD width="180"  colspan='2' align="left"><small>記住密碼</small></TD>
         </tr>
         <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;<Font color='red' size="-1">
             ${ErrorMsgKey.LoginError}&nbsp;</Font></TD>
         </TR>
        <TR>
            <TD colspan="2" align="center"><input type="submit" value="提交"> </TD>
         </TR>
</Table>
</form>
</body>
</html>