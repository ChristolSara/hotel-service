package hotelservice.web;

import hotelservice.dto.ClientDTO;
import hotelservice.exceptions.ClientNotFoundException;
import hotelservice.exceptions.EmailAlreadyExistsException;
import hotelservice.service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
@CrossOrigin("*")//to enable CORS on all handler methods of this class
public class ClientController {

    private ClientServiceImpl clientService;


    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO registerClient(@RequestBody ClientDTO clientDTO)throws EmailAlreadyExistsException{
        return clientService.registerClient(clientDTO);
    }

    @PostMapping("authenticate")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO authenticateClient(@RequestParam String email,@RequestParam String password)throws ClientNotFoundException{
        return clientService.authenticateClient(email,password);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOP_ADMIN')")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO){
        return  clientService.updateClient(clientDTO);
    }


    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ClientDTO getClientByEmail(@PathVariable String email)throws  ClientNotFoundException{
        return clientService.getClientByMail(email);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<ClientDTO> getAllClients(){
        return clientService.gettAllClients();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteClientById(@PathVariable Long id)throws ClientNotFoundException{
        clientService.deleteClientById(id);
    }



}
