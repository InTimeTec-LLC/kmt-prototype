<!-- When the article is reviewed and approved, an email is sent to the creator.-->
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<p>Hi ${firstName},</p>

<p>Your article has been approved and published. Click <a href="${articleLink}">here</a> to view the article.</p>
<#if comments??>
<p style="text-decoration: underline;"><b>Comments:</b></p>
 ${comments}
</#if>
<p>Regards,<br>
ADPQ-KMT</p>
</body>
</html>