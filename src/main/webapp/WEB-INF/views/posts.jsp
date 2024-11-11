<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
</head>
<body>
    <a href="/index.html">메인</a>
    <table>
        <thead>
            <th>연번</th>
            <th>제목</th>
            <th>내용</th>
            <th></th>
        </thead>
        <tbody>
            <c:forEach var="item" items="${posts}">
                <tr>
                    <td>${item.id}</td>
                    <td><a href="/post/${item.id}">${item.title}</a></td>
                    <td><a href="/post/${item.id}">${item.content}</a></td>
                    <td><a href="/post/edit/${item.id}"> 수정 </a></td>
                    <td><a href="/post/delete/${item.id}"> 삭제 </a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>