<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그 파일 업로드</title>
</head>
<body>
     <a href="/index.html">메인</a>
     <li><a href="/admin">관리자 페이지</a></li>

     <h2> 로그 파일 업로드 결과</h2>

    <div id="resultMessage">
        <p>${message}</p>  <!-- ${message}로 모델에서 전달된 값을 출력 -->
    </div>
</body>
</html>