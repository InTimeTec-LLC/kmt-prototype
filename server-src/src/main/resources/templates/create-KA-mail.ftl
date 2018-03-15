<!-- When a user creates a new article, an email is sent to the reviewer.-->
<p>Hi ${firstName},</p>
<#if articleLink??><p>An article <a href="${articleLink}"><#if articleTitle??>${articleTitle}</#if></a></#if> has been submitted for review. Please review.</p>
<p>Regards,<br>
Cognizance</p>
