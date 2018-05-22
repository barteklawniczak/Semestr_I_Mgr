package com.blawniczak.controller;

import com.blawniczak.domain.EditUser;
import com.blawniczak.domain.User;
import com.blawniczak.service.UserService;
import com.blawniczak.validator.UserFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserFormValidator userFormValidator;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model, String error, String success) {
        if (error != null)
            model.addAttribute("message", "User with requested id does not exist!");
        if (success != null)
            model.addAttribute("message", "You have successfully removed user from system.");
        model.addAttribute("users", userService.findAllUsers("ROLE_USER"));
        return "users";
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.GET)
    public String editUserGET(Model model, @PathParam("id") Long id, String success) {
        User user = userService.findById(id).orElse(null);
        if(user==null) {
            return "redirect:/admin/users?error";
        } else {
            if (success != null)
                model.addAttribute("message", "You have successfully updated user!");
            EditUser editUser = new EditUser();
            editUser.updateFields(user);
            model.addAttribute("editUserForm", editUser);
            return "edit";
        }
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String editUserPOST(@ModelAttribute("editUserForm") EditUser editUser, BindingResult bindingResult, Model model) {
        userFormValidator.validateUpdate(editUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        User user = userService.findById(editUser.getId()).orElse(null);
        if(user==null) {
            return "redirect:/admin/users?error";
        } else {
            user.updateFields(editUser);
            userService.update(user);
            return "redirect:/admin/users/edit?id=" + user.getId() + "&success";
        }
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.GET)
    public String deleteUserGET(Model model, @RequestParam("id") Long id) {
        User user = userService.findById(id).orElse(null);
        if(user==null) {
            return "redirect:/admin/users?error";
        } else {
            userService.delete(user);
            return "redirect:/admin/users?success";
        }
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String employeesGET(Model model, String error, String success, String registration) {
        if (error != null)
            model.addAttribute("message", "User with requested id does not exist!");
        if (success != null)
            model.addAttribute("message", "You have successfully removed user from system.");
        if (registration != null)
            model.addAttribute("message", "You have successfully registered new employee.");
        model.addAttribute("users", userService.findAllUsers("ROLE_EMPLOYEE"));
        model.addAttribute("employee", "employee");
        return "users";
    }


    @RequestMapping(value = "/employees/edit", method = RequestMethod.GET)
    public String editEmployeeGET(Model model, @PathParam("id") Long id, String success) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/admin/employees?error";
        } else {
            if (success != null)
                model.addAttribute("message", "You have successfully updated employee!");
            EditUser editUser = new EditUser();
            editUser.updateFields(user);
            model.addAttribute("editUserForm", editUser);
            return "edit";
        }
    }

    @RequestMapping(value = "/employees/edit", method = RequestMethod.POST)
    public String editEmployeePOST(@ModelAttribute("editUserForm") EditUser editUser, BindingResult bindingResult, Model model) {
        userFormValidator.validateUpdate(editUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        User user = userService.findById(editUser.getId()).orElse(null);
        if(user==null) {
            return "redirect:/admin/employees?error";
        } else {
            user.updateFields(editUser);
            userService.update(user);
            return "redirect:/admin/employees/edit?id=" + user.getId() + "&success";
        }
    }

    @RequestMapping(value = "/employees/delete", method = RequestMethod.GET)
    public String deleteEmployeeGET(Model model, @RequestParam("id") Long id) {
        User user = userService.findById(id).orElse(null);
        if(user==null) {
            return "redirect:/admin/employees?error";
        } else {
            userService.delete(user);
            return "redirect:/admin/employees?success";
        }
    }

    @RequestMapping(value ="/employees/add", method = RequestMethod.GET)
    public String addEmployeeGET(Model model) {
        model.addAttribute("userForm", new User());
        return "registerEmployee";
    }

    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
    public String addEmployeePOST(@ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {

        userFormValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registerEmployee";
        }
        user.setEnabled(true);
        userService.saveEmployee(user);
        return "redirect:/admin/employees?registration";
    }
}
