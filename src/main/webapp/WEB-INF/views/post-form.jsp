<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="UTF-8">
    <title>글쓰기</title>
</head>
<body>
    <form action="/post/save" method="post">
        <label for="title">title:</label>
        <input type="text" name="title" id="title" style="display: block; margin-bottom: 10px;" />

        <label for="content">content:</label>
        <textarea name="content" id="content" rows="5" cols="40" style="display: block; margin-bottom: 10px;"></textarea>

        <button type="submit">업로드</button>
    </form>
</body>
</html>