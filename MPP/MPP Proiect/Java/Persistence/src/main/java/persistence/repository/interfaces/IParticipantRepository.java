package persistence.repository.interfaces;


import model.Participant;

public interface IParticipantRepository extends IRepository<Integer, Participant> {
    void update(Participant entity);
}
