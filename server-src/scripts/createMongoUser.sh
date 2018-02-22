#Creates a database and a user

mongo kmt_db --eval 'db.sample.insert({"name":"test"})'

mongo kmt_db --eval 'db.createUser(
  {
    user: "itt",
    pwd: "itt@123456",
    roles: [ { role: "userAdmin", db: "kmt_db" } ]
  }
)'
