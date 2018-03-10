<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">
   <link rel="stylesheet" href="WOW-master/css/libs/animate.css">
<title>Insert title here</title>
<style>

/* WebKit browsers */
input:focus::-webkit-input-placeholder {
color: transparent;
/* transparent是全透明黑色(black)的速记法，即一个类似rgba(0,0,0,0)这样的值 */
}
/* Mozilla Firefox 4 to 18 */
input:focus:-moz-placeholder {
color: transparent;
}
/* Mozilla Firefox 19+ */
input:focus::-moz-placeholder {
color: transparent;
}
/* Internet Explorer 10+ */
input:focus:-ms-input-placeholder {
color: transparent;
}
/* 点击搜索框获取焦点 placeholder消失-结束 */

.mainInfo{  
    color: white;
    margin: 0 auto;
    padding-top: 15%;
    width: 700px;
    max-width: 80%;
}
.body{
   font-family: 'Baloo Tammudu', cursive; 
}
.cover{
    background: url('images/morgan-sessions-6255-unsplash.jpg');
    height: 700px;
 
    background-size: cover;
    background-position: center;
   
}
.coverH1{
text-align:center;
    font-size: 75px;
    height: 100px;
    margin: 0 auto;
}

.shadow{
    text-shadow: 
        0px 2px 1px rgba(22,22,22,0.8) ,
        0px 1px 4px rgba(22,22,22,0.3) , 
        0px 4px 10px rgba(55,55,55,0.1);
}
.coverH2{
 font-size: 25px;
    width: 60%;
     height: 80px;
    margin: 0 auto;
}
.coverForm{
 font-size: 15px;
    margin: 0 auto;
    width: 350px;
}
.form-control{
 font-size: 20px;
  width: 250px;
   height: 25px;
  text-align: center;
}
input.placeholder {
    text-align: center;
}
</style>
</head>


<body>
<div class="cover shadow">
<div class="mainInfo wow fadeInDown">
<h1 class="coverH1 ">Please Login</h1>
  <h2 class="coverH2 ">It can be easier than you think if you break it down in small moves forward.</h2>
	<form class="coverForm">
		<div>
			<label for="exampleInputEmail1">Cell Phone Number</label> 
			<input type="text" class="form-control" id="exampleInputEmail1" name="userCellphone" 
			placeholder="0 9 * * * * * * * *" value="${sessionScope.usercellphone}">
			 &nbsp;<small><Font color='red' size="-3">${ErrorMsg.CellphoneEmptyMsg}
             </Font></small>
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Password</label> <input
				type="password" class="form-control" id="exampleInputPassword1"  name="pswd"
				placeholder="P a s s w o r d"  value="${sessionScope.password}">
				 &nbsp;<small><Font color='red'  size="-3">${ErrorMsg.PasswordEmptyMsg}
		</div>

		<button type="submit"  align="center" class="btn btn-default" style="font-weight:bold;"><Font size="3" >Submit</Font></button>
	 <tr>
         <TD width="180" align="right" >
             <input type="checkbox" name="rememberMe" 
               <c:if test='${sessionScope.rememberMe==true}'>
                  checked='checked'
               </c:if> 
             value="true">
         </TD>
         <TD width="180"  colspan='2' align="right"><small><Font color='white'  size="3">RememberMe</Font></small></TD>
         </tr>
          <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;<Font color='red' size="-1">
             ${ErrorMsgKey.LoginError}&nbsp;</Font></TD>
         </TR>
	</form>
	</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
</body>
    <script src="WOW-master/dist/wow.min.js"></script>
    <script>
        wow = new WOW(
                  {
                  boxClass:     'wow',      // default
                  animateClass: 'animated', // default
                  offset:       0,          // default
                  mobile:       true,       // default
                  live:         true        // default
                }
                )
                wow.init();
    </script>
</html>