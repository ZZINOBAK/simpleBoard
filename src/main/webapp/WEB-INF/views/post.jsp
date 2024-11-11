<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 보기</title>
</head>
<body>
    <p>Title: ${post.title}</p>
    <p>Content: ${post.content}</p>

    <a href="/index.html">메인</a>
    <a href="/post/list">글목록보기</a>
</body>
</html>