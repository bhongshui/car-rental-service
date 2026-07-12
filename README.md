# Car Rental Service (CRS)

Project structure is based on controller-service-repository structure:

* `api` package is for controller layer classes (subpackages by type e.g. controller, mapper)
* `domain` is for service layer classes (possible subpackages by domain e.g. vehicle, vehicleType)
* `infrastructure` is for "repository layer"; it handles repository/database operations (`db` package)
  but also external clients (`client` package); classes involved in sending events could be added there as well