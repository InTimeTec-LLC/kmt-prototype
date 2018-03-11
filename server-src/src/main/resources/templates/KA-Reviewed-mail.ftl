<!-- When the article is reviewed, an email is sent to the creator.-->
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>Hi ${firstName},</p>
<p>Your <a href="#localhost:8080/index.html+/#/+articlePage">article name</a> has been reviewed <#if comments??> with <status>Review Comments.

<p style="text-decoration: underline;"><b>Comments:</b></p>
 ${comments}
</#if>

<p>Regards,<br>
ADPQ-KMT</p>
</body>
</html>
