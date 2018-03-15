<!-- When a user deletes KA, an email is sent to the reviewer.-->
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>Hi ${firstName},</p>

<p>The <#if articleTitle??>${articleTitle}</#if> submitted for your review has been deleted.</p>

<p>Regards,<br>
Cognizance</p>
</body>
</html>