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
	<div id="wrapper">

				

				<!-- Banner -->
				<!-- Note: The "styleN" class below should match that of the header element. -->
					<section id="banner" >
						<div >
							<!-- <span class="image">
								<img src="images/pic07.jpg" alt="" />
							</span> -->
							<header >
								<h1>註冊</h1>
							</header>
							<form ENCTYPE="multipart/form-data" action="<c:url value='register.do' />"  id="register.do" method="POST"> 
							<div id="aaa">
							行動電話<input style="background:white;margin-left:1px;" type="text" name=usercellphone value="${param.usercellphone}">
							<font color="red" size="-1">${MsgMap.errorphone}${MsgMap.errorIDDup}</font>
      						<br/>
							
							
							用戶密碼<input  style="background:white;margin-left:1px;" type="password" name=userpassword value="${param.userpassword}">
							<font color="red" size="-1">${MsgMap.errorPasswordEmpty}</font>      
     						<br/>
							
							用戶暱稱<input  style="background:white;margin-left:1px;" type="password" name=username value="${param.username}">
							<font color="red" size="-1">${MsgMap.errorName}</font>      
     						<br/>
						
							
							電子信箱<input style="background:white;margin-left:1px;"  type="email" name=useremail1 value="${param.useremail1}">
							<font color="red" size="-1">${MsgMap.errorEmail1}</font>
      						<br/>
							
							信箱驗證碼<input style="background:white;margin-left:1px;" type="text" name=useremail2 value="${param.useremail2}">
							<font color="red" size="-1">${MsgMap.errorEmail2}</font>
      						<br/>
							
							<button>寄出驗證信</button><BR>
							
							
							上傳圖片<input style="background:white;margin-left:1px;"  type="file" name=photo ><br>
							
							
							<!-- 用戶暱稱<input style="background:white;margin-left:1px;"  type="text"><br>  地圖引出-->
				
							<button type="submit" name="submit" value="Sumbit">下一頁
							</button>							
							<button type="reset" name="reset" value="reset">重填						
							</button>
								
							</div>
							</form>
						</div>
					</section>

				
			</div>
</body>
</html>