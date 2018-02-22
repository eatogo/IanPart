<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<c:url value= 'verifymail.do'/>" method="POST" name="verifymail" >
信箱驗證碼<input style="background:white;margin-left:1px;" type="text" name=verifynumber value="${param.verifynumber}">
							<font color="red" size="-1">${MsgMap.verifynumberEmptyMsg}</font>
      						<br/>
      						<button type="submit" name="submit" value="Sumbit">驗證信箱
							</button>	
      						</form>
</body>
</html>