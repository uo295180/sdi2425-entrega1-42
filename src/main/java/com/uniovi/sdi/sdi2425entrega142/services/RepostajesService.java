package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Repostaje;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.RepostajesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepostajesService {

    private final RepostajesRepository repostajesRepository;

    public RepostajesService(RepostajesRepository repostajesRepository) {
        this.repostajesRepository = repostajesRepository;
    }

    public void addRepostaje(Repostaje repostaje) {
        repostajesRepository.save(repostaje);
    }

    public Page<Repostaje> getRepostajesByVehiculo(Pageable pageable, Vehiculo vehiculo) {
        return repostajesRepository.findByVehiculo(pageable, vehiculo.getMatricula());
    }

    public Optional<Repostaje> getLastRepostaje(String matricula) {
        return repostajesRepository.findLastRepostajeByVehiculo(matricula);
    }
}
