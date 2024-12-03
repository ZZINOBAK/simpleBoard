<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그 테스트</title>
</head>
<body>
    <a href="/index.html">메인</a>
    <a href="/admin">관리자 페이지</a>

<h2> 로그 분석 완료</h2>

 <!-- 파일 이름을 표시 -->
    <p><strong>분석된 파일:</strong> ${fileName}</p>

    <!-- 로그 분석 결과를 표시 -->
    <div>
        <h2>분석 결과:</h2>
        <pre>${analysisResult}</pre>
    </div>

    <br>

</body>
</html>