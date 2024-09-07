package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Client;
import com.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Override
    public void saveClient(Client client) {
        this.clientRepository.save(client);
    }
    @Override
    public Client getClientById(long id) {
        Optional<Client> optional = clientRepository.findById(id);
        Client client = null;
        if (optional.isPresent()) {
            client = optional.get();
        } else {
            throw new RuntimeException(" Клієнт за таким id не знайдено :: " + id);
        }
        return client;
    }
    @Override
    public void deleteClientById(long id) {
        this.clientRepository.deleteById(id);
    }
    @Override
    public Page<Client> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.clientRepository.findAll(pageable);
    }
    @Override
    public Page<Client> findPaginatedWithKeyword(int pageNo, int pageSize, String sortField, String sortDir, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());

        return clientRepository.search(keyword, pageable);
    }
}
