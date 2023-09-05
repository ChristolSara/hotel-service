package hotelservice.service;

import hotelservice.dto.ClientDTO;

import java.util.List;

public interface IClientService {


    ClientDTO registerClient(ClientDTO clientDTO);
    ClientDTO authenticateClient(String email,String password);
    ClientDTO updateClient(ClientDTO client);
    List<ClientDTO> gettAllClients();
    void deleteClientById(Long id);
}
