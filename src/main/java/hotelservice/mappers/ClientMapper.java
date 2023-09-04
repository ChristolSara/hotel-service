package hotelservice.mappers;

import hotelservice.dto.ClientDTO;
import hotelservice.models.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

//utilise method pou copier les proprties de client au clientDTO
    public ClientDTO fromClient(Client client){
         ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        return clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO){
        Client client = new Client();

        BeanUtils.copyProperties(clientDTO,client);
        return client;
    }
}
