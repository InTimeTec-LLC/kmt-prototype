#Setup db for kmt-prototype

#Clear user details from db
mongo kmt_db --eval 'db.user.remove({ })'

#Clear role details from db
mongo kmt_db --eval 'db.role.remove({ })'


#Create admin account
#Table = user
mongo kmt_db --eval 'db.user.insert(
     {
         "email": "kmtAdmin@mailinator.com",
         "userRole": "admin",
         "firstName": "kmt",
         "lastName": "admin",
         "password": "Admin123",
         "status": "A"
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