<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
</head>
<body>
    <a href="/index.html">메인</a>
    <a href="/posts/new">글쓰기</a>
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
                    <td><a href="/posts/${item.id}">${item.title}</a></td>
                    <td><a href="/posts/${item.id}">${item.content}</a></td>
                    <td>
                        <form action="/posts/${item.id}/edit" method="get">
                            <button type="submit">수정</button>
                        </form>
                    </td>
                    <td>
                        <form action="/posts/${item.id}/delete" method="post">
                          <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>