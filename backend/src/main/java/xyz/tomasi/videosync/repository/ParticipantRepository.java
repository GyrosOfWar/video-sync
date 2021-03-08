package xyz.tomasi.videosync.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import xyz.tomasi.videosync.entity.Participant;

public interface ParticipantRepository
  extends ReactiveCrudRepository<Participant, Long> {}
