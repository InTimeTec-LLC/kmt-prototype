#Creates a database and a user

mongo javaTemplate --eval 'db.sample.insert({"name":"test"})'

mongo javaTemplate --eval 'db.createUser(
  {
    user: "javaUser",
    pwd: "password",
    roles: [ { role: "userAdmin", db: "javaTemplate" } ]
  }
)'
