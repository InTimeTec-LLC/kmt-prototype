#Creates a database, user and userRoles

mongo kmt_db --eval 'db.sample.insert({"name":"test"})'

mongo kmt_db --eval 'db.role.insert([
    {
        _id: 1,
        role: "user"
    },
    {
        _id: 2,
        role: "manager"
    },
    {
        _id: 3,
        role: "admin"
    }]
)'

mongo kmt_db --eval 'db.createUser(
  {
    user: "itt",
    pwd: "itt@123",
    roles: [ { role: "userAdmin", db: "kmt_db" } ]
  }
)'


