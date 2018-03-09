<!-- When the article is reviewed, an email is sent to the creator.-->
<p>Sub: Article Reviewed</p>
<p>Hi ${firstName},</p>
<p>Your article has been reviewed.
<#if comments??>
   <p>With these comments</p>
   ${comments}
</#if>
<p>Regards,</p>
<p>ADPQ-KMT</p>
