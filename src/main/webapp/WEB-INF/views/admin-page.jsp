<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인</title>
</head>
<body>
    <a href="/index.html">메인</a>

    <li><a href="/uploadLogFileForm">로그 파일 업로드</a></li>

<h2>로그 파일 목록</h2>
<br>분석할 파일 클릭 ㄱㄱ<br>
<c:if test="${not empty fileNames}">
    <ul>
        <c:forEach var="fileName" items="${fileNames}">
            <!-- 파일 이름을 URL 파라미터로 전달 -->
            <li><a href="/analyzeLog?fileName=${fileName}">${fileName}</a></li>
        </c:forEach>
    </ul>
</c:if>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>
</body>
</html>