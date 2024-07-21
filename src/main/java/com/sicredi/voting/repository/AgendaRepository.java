package com.sicredi.voting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sicredi.voting.document.Agenda;
import com.sicredi.voting.document.Vote;
import com.sicredi.voting.document.VoteSession;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String> {
	
	@Query("{ 'id': ?0 }, { voteSession: 1, _id: 0}")
	Optional<VoteSession> findVoteSessionByAgendaId(String id);

	@Query("{'id': ?0} , {}")
	void updateVotes(List<Vote> votes);

}
