package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.exceptions.ClientNotFoundException;
import hotelservice.exceptions.EmailAlreadyExistsException;

import java.util.List;

public interface IClientService {


    ClientDTO registerClient(ClientDTO clientDTO) throws EmailAlreadyExistsException;
    ClientDTO authenticateClient(String email,String password) throws ClientNotFoundException;
    ClientDTO updateClient(ClientDTO client);
    List<ClientDTO> gettAllClients();
    void deleteClientById(Long id) throws ClientNotFoundException;
}
