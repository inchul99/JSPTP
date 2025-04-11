<!-- resetPwd.jsp -->

<%@page import="DTO.UserDTO"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="userDao" class="DAO.UserDAO"/>
<%
		String id = request.getParameter("id");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기 | everyWEAR</title>
  <link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/resetPwd.css">
  <link rel="icon" type="image/png" href="images/logo-white.png">
</head>
<body>

<%@ include file="includes/loginHeader.jsp" %>

<div class="login-container">
  <h2 class="form-title">비밀번호 변경</h2>
  <form action="resetPwd2" method="post" id="resetPwd">
	<label for="username">아이디</label>
	<input type="text" id="userId" name="userId" value="<%=id%>" class="readonly-input" readonly>

    <label for="newPassword">비밀번호</label>
    <input type="password" id="newPassword" name="newPassword" placeholder="비밀번호를 입력하세요." required>
    <div id="pwCheck"></div>

    <label for="confirmPassword">비밀번호 확인</label>
    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호를 입력하세요." required>
    <div id="pwCheckMsg"></div>

    <button type="button" class="login-btn" onclick="pwdCheck()">로그인</button>
  </form>
  </div>

<footer>2025©everyWEAR</footer>

<script>
	  const passwordInput = document.getElementById("newPassword");
	  const confirmPasswordInput = document.getElementById("confirmPassword");
	  const pwCheckMsg = document.getElementById("pwCheckMsg");
	  const pwCheck = document.getElementById("pwCheck");
	  let isPwdChecked = false;

	  function validatePasswords() {
	    const pw = passwordInput.value;
	    const confirmPw = confirmPasswordInput.value;
	    const regex = /^(?=.*[!@#$%^&*(),.?":{}|<>]).{4,16}$/;

	    if (pw === "") {
	        pwCheck.textContent = "";        // ← 이 부분 추가!
	        pwCheck.className = "";
	      pwCheckMsg.textContent = "";
	      pwCheckMsg.className = "";
	      isPwdChecked = false;
	      return;
	    }

	    if (!regex.test(pw)) {
	    	pwCheck.textContent = "4자 이상 16자 이하이며, 특수문자 1개 이상을 포함";
	    	pwCheck.className = "not-match";
	      isPwdChecked = false;
	      return;
	    } else{
	    	pwCheck.textContent = "조건에 맞는 비밀번호입니다!";
	    	pwCheck.className = "match";
	    	 isPwdChecked = false;
	    }
	    

	    if (pw !== confirmPw) {
	    	if(confirmPw === ""){
		  	      pwCheckMsg.textContent = "";
			      pwCheckMsg.className = "";
			      isPwdChecked = false;
			      return;
	    	}
	      pwCheckMsg.textContent = "비밀번호가 일치하지 않습니다.";
	      pwCheckMsg.className = "not-match";
	      isPwdChecked = false;
	      return;
	    } else {
	      pwCheckMsg.textContent = "비밀번호가 일치합니다.";
	      pwCheckMsg.className = "match";
	      isPwdChecked = true;
	      return;
	    }
	    
	  }

	  passwordInput.addEventListener("input", validatePasswords);
	  confirmPasswordInput.addEventListener("input", validatePasswords);

	  
function pwdCheck() {
  if (isPwdChecked) {
    document.getElementById("resetPwd").submit();
  } else {
    alert("비밀번호를 다시 확인해 주세요.");
  }
}
</script>

</body>
</html>
