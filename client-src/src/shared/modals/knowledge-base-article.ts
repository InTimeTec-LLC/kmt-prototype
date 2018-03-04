export class KnowledgeBaseArticle {
  title: string;
  description: string;
  createdBy: string;
  approver: string;
  articleType: string;
  restricted: boolean;
}


export class UpdateKnowledgeBaseArticle {
  title: string;
  description: string;
  modifiedBy: string;
  approver: string;
  articleType: string;
  restricted: boolean;
  needsApproval: boolean;
  approved: boolean;
}
