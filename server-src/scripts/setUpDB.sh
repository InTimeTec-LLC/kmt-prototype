#Setup db for kmt-prototype

#Clear user details from db
mongo kmt_db --eval 'db.user.remove({ })'

#Clear role details from db
mongo kmt_db --eval 'db.role.remove({ })'

#Clear article type details from db
mongo kmt_db --eval 'db.articleType.remove({ })'


#Create admin account
#Table = user
mongo kmt_db --eval 'db.user.insert(
     {
         "email": "kmtadmin@mailinator.com",
         "userRole": "admin",
         "firstName": "kmt",
         "lastName": "admin",
         "password": "$2a$12$A/DcfQo0jprUH9reawlm9.svCzK9RrRjUeVoeHJ5vHzOTtRob1VHm",
         "active": true,
         "dateJoined": new Date(),
		 "session": false
     }
)'


#Create user roles
#Table = role
mongo kmt_db --eval 'db.role.insert([
     {
         role : "User"
     },
     {
         role: "Manager"
     },
     {
         role: "Admin"
     }]
)'

#Create article content type
#Table = articleType
mongo kmt_db --eval 'db.articleType.insert([
       {
           type : "How To"
       },
       {
           type: "General"
       },
       {
           type: "Troubleshooting"
       },
       {
           type: "Documents"
       },
       {
           type: "Report"
       },
       {
           type: "Public"
       }]
)'