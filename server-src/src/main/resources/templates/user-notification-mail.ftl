<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>Hi ${firstName},</p>
<#if user??>
<p>The reviewer ${reviewerName} of your article ${articleTitle} has been deactivated by the Admin.
<p>Click <a href="${articleLink}">here</a> to change the reviewer.</p>
<#else>
<p>The user <user name> has been deactivated by the Admin. You need not approve and publish the ${articleTitle}.
</#if>
<p>Regards,<br>
ADPQ-KMT</p>
</body>
</html>
