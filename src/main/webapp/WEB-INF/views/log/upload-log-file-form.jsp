<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그 파일 업로드</title>
</head>
<body>
     <a href="/index.html">메인</a>
     <h2> 로그 파일 업로드 </h2>
     <form action="/uploadLogFile" method="post" enctype="multipart/form-data">
         <label for="file">Choose a file:</label>
         <input type="file" id="file" name="file" accept=".txt" required>
         <button type="submit">Upload</button>
     </form>
</body>
</html>