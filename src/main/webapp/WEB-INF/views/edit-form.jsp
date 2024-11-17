<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
    <title>글수정하기</title>
</head>
<body>
    <a href="/index.html">메인</a>
    <a href="/posts">글목록보기</a>
     <form action="/posts/${post.id}" method="post">
         <!-- id 숨기기 -->
         <input type="hidden" name="id" value="${post.id}" />

         <!-- title 입력 -->
         <label for="title">Title:</label>
         <input type="text" name="title" id="title" value="${post.title}" style="display: block; margin-bottom: 10px;" />

         <!-- content 입력 -->
         <label for="content">Content:</label>
        <textarea name="content" id="content" rows="5" cols="40" style="display: block; margin-bottom: 10px;">${post.content}</textarea>
         <button type="submit">수정하기</button>
     </form>
     <form action="/posts/${post.id}/delete" method="post">
          <button type="submit">삭제</button>
     </form>
    </body>
</html>