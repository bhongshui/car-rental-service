To keep things simple there is:

* h2 db
* lombok and mapstruct
* no timezone consideration
* only few tests (one for controller, one for mapper, one for service, one dao)
* error handling, configs and converters copy-pasted from another personal project
* no security, no documentation, no metrics, no logging etc.

## Packages

Project structure is based on classical controller-service-repository structure:

* `api` package is for controller layer classes (subpackages by type e.g. controller, mapper)
* `domain` is for service layer classes (possible subpackages by domain e.g. vehicle, vehicleType)
* `infrastructure` is for "repository layer"; it handles repository/database operations (`db` package)
  but also external clients (`client` package); classes involved in sending events could also be added there

In `infrastructure` there are two types present in each package (`db`/ `client`):

* interfaces (extending spring repositories / annotated with `@Exchange`)
* service classes using those interfaces (Dao / ClientService)

This allows for layer specific logic to stay within the layer, making domain services simpler