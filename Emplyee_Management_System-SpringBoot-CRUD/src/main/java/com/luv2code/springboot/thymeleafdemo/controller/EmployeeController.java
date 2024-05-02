package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired // As I'm doing only one injection, Autowired is optional
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;

    }
    @GetMapping("/list")
    public String listEmployees(Model theModel){

        //get employees from db
        List<Employee> theEmployees = employeeService.findAll();

        //add the employee to the model
        theModel.addAttribute("employees",theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "/employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){

        // add new employee to databases
        employeeService.save(theEmployee);

        return "redirect:/employees/list"; //it redirects to /employees/list url
    }


    // updating employee data
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel ){

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set the employee in the model to pre-populate the form
        theModel.addAttribute("employee",theEmployee); //It means I've found the employee and I'm sending it over to the form


        // send over to our form. The form will show up prepopulated with employee data.

        return "employees/employee-form";
    }

    // deleting employee data
    @GetMapping("/delete")
    public String showFormForDelete(@RequestParam("employeeId") int theId){

        employeeService.deleteById(theId);

        return "redirect:/employees/list";
    }


}

