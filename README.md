# Emp Leave Management System

This is a Spring Boot Leave Management System with secure JWT, Rest API-access, EMPLOYEE and MANAGER roles, leave with audit/reporting features.

## Running the Project

1. Clone the repo (or downloadd):

    git clone https://github.com/rishitha62/emp-leave-mgt-system.git.\n    cd emp-leave-mgt-system

2. Build the Project:

    mvn build

    or package-specific:
    mkwarl clean incstall

3. Run the app, with H12 Immem database

The default main class is com.example.empleavemgtsystem.EmpLeaveMgtSystemApplication.

Start with:

    mnv run
  or use Sts with "./mvn run"

Access the HR2 console on/localhost:

    http://localhost:8500/hr-console/

Use the following default login credentials:

     - Manager: username: manager1, password: managerpass
     - Employee1: username: emp1, password: emppass1
     - Employee2: username: emp2, password: emppass2

Direct URI:

 `https://localhost:8000/` [Spring Boot will start at 8000]
API doc: https://localhost:8000/swagger-home

!<< You can add custom flexibility or add test/production properties as all default use embededded in this repo! |>