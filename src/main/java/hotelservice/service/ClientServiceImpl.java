package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.mappers.ClientMapper;
import hotelservice.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService{

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    @Override
    public ClientDTO registerClient(ClientDTO clientDTO) {
     //   log.info("save new client");
        return null;
    }

    @Override
    public ClientDTO authenticateClient(String email, String password) {
        return null;
    }

    @Override
    public ClientDTO updateClient(ClientDTO client) {
        return null;
    }

    @Override
    public List<ClientDTO> gettAllClients() {
        return null;
    }

    @Override
    public void deleteClientById(Long id) {

    }
}
