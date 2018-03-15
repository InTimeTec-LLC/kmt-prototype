<!-- When an Admin deletes a user’s KA, an email is sent to the user and reviewer.-->
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>Hi ${firstName},</p>

<p>The <#if articleTitle??>${articleTitle}</#if> has been deleted by the Admin.Contact the Admin for further clarifications.</p>
<p>Regards,<br>
Cognizance</p>
</body>
</html>