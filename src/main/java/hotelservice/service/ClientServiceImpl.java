package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.exceptions.ClientNotFoundException;
import hotelservice.exceptions.EmailAlreadyExistsException;
import hotelservice.mappers.ClientMapper;
import hotelservice.models.Client;
import hotelservice.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional//toute les methodes sont transactionnelles
@AllArgsConstructor
@Slf4j//generer les logs : remplace Logger=...
public class ClientServiceImpl implements IClientService{

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    @Override
    public ClientDTO registerClient(ClientDTO clientDTO) throws EmailAlreadyExistsException {
        log.info("save new client");

        Client client=clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO authenticateClient(String email, String password) throws ClientNotFoundException {
        Client client=clientRepository.findByEmail(email);

        if(client != null && client.getPassword() != null && client.getPassword().equals(password)){
            return clientMapper.fromClient(client);
        }else{
            throw  new ClientNotFoundException("Client with email "+email+ " not found or invalid credentials. ");
        }
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        log.info("update client");
        Client client= clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public List<ClientDTO> gettAllClients() {
        List<Client> clients = clientRepository.findAll();

        //transforme tout la list en clientDTO
        List<ClientDTO> clientDTOS = clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public void deleteClientById(Long id) throws ClientNotFoundException{
        //si le client n'existe pas
        if(!clientRepository.existsById(id)){
            throw new ClientNotFoundException("client with id :" + id+" not found");
        }

        Optional<Client> OptionalClient = clientRepository.findById(id);
        if(OptionalClient.isEmpty()){
            throw new ClientNotFoundException("Room with id : "+id+" not found !");
        }
        //tranforme en entity
        Client client=OptionalClient.get();

        ClientDTO clientDTO= clientMapper.fromClient(client);
        clientRepository.delete(client);
    }
}
