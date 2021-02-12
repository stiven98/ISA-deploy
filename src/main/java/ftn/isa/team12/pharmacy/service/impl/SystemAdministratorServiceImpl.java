package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.users.SystemAdministrator;
import ftn.isa.team12.pharmacy.repository.SystemAdministratorRepository;
import ftn.isa.team12.pharmacy.service.SystemAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class SystemAdministratorServiceImpl implements SystemAdministratorService {

    @Autowired
    private SystemAdministratorRepository systemAdministratorRepository;

    @Override
    public SystemAdministrator saveAndFlush(SystemAdministrator systemAdministrator) {
        return this.systemAdministratorRepository.saveAndFlush(systemAdministrator);
    }
}
