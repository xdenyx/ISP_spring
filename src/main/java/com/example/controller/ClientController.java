package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Client;
import com.example.service.ClientService;

@Controller
public class ClientController {
    @Autowired
    private ClientService clientService;

    // Display list of clients
    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(defaultValue = "") String keyword) {
        return findPaginated(1, "firstName", "asc", model, keyword);
    }

    @GetMapping("/showNewClientForm")
    public String showNewEmployeeForm(Model model) {
        // Create model attribute to bind form data
        Client client = new Client();
        model.addAttribute("client", client);
        return "new_client";
    }

    @PostMapping("/saveClient")
    public String saveClient(@ModelAttribute("client") Client client) {
        // Save client to database
        clientService.saveClient(client);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // Get client from the service
        Client client = clientService.getClientById(id);

        // Set client as a model attribute to pre-populate the form
        model.addAttribute("client", client);
        return "update_client";
    }

    @GetMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable(value = "id") long id) {
        // Call delete client method
        this.clientService.deleteClientById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model,
                                @RequestParam(defaultValue = "") String keyword) {
        int pageSize = 5;

        Page<Client> page;
        if (keyword == null || keyword.isEmpty()) {
            page = clientService.findPaginated(pageNo, pageSize, sortField, sortDir);
        } else {
            page = clientService.findPaginatedWithKeyword(pageNo, pageSize, sortField, sortDir, keyword);
        }

        List<Client> listClients = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listClients", listClients);
        model.addAttribute("keyword", keyword);

        return "index";
    }
}
