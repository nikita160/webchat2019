<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Contact list</title>
</head>
<body>

<ul>
<#list users as user>

   <li>
        <form action="/chat" method="get">
            <input type="hidden" name="to" value=${user}>
            <span class="username">${user}</span><button type="submit">Написать</button>
        </form>
    </li>

</#list>
</ul>

</body>
</html>