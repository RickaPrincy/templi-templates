Fake JPA

## A UTILITY WHEN WE CANNOT USE JPA (AT SCHOOL)

- **CRUD**
  - findAll
  - findById
  - findByCondition
  - findByField
  - delete
  - saveOrUpdate
  - ...???
- **Relation**
- **Query Generator**
  - ex: "SELECT * FROM @entity where @id > 5"
- **Statement wrapper**
  - ex: statementWrapper.select("SELECT * FROM ...", List.of(idValue, fromValue, toValue), mapper)
- **Customize FJPA as you want it**

# Example
[https://github.com/wall-ety/wall-ety-api](https://github.com/wall-ety/wall-ety-api)
