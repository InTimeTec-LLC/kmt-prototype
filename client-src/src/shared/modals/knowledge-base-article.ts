export class Attachement {
  id: string;
}

export class KnowledgeBaseArticle {
  title: string;
  description: string;
  createdBy: string;
  approver: string;
  articleType: string;
  restricted: boolean;
  needsApproval: boolean;
  attachments: Attachement[];
}


export class UpdateKnowledgeBaseArticle {
  title: string;
  description: string;
  lastModifiedBy: string;
  approver: string;
  articleType: string;
  restricted: boolean;
  needsApproval: boolean;
  attachments: Attachement[];
}

export class Aritcles {
  id: string;
  type: string;
  title: string;
  content: string;
  status: Boolean;
  size: number;
  totalPages: number;
  totalElements: number;
  numberOfElements: number;
}

export class ApprovedKnowledgeBaseArticle {
  comment: string;
  approved: boolean;
}
