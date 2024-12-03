<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 보기</title>
</head>
<body>
    <a href="/index.html">메인</a>
    <a href="/posts">글목록보기</a>
    <a href="/posts/new">글쓰기</a>

    <p>Title: ${post.title}</p>
    <p>Content: ${post.content}</p>

    <form action="/posts/${post.id}/edit" method="get">
        <button type="submit">수정</button>
    </form>
    <form action="/posts/${post.id}/delete" method="post">
        <button type="submit">삭제</button>
    </form>
</body>
</html>