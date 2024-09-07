package com.example.service;

import java.util.List;
import com.example.model.Client;
import org.springframework.data.domain.Page;
public interface ClientService {
    List<Client> getAllClients();
    void saveClient(Client employee);
    Client getClientById(long id);
    void deleteClientById(long id);
    Page<Client> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Client> findPaginatedWithKeyword(int pageNo, int pageSize, String sortField, String sortDir, String keyword);
}
