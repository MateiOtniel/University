using model;

namespace persistance.Interfaces{
    public interface IParticipantRepository : IRepository<int, Participant>{
        void Update(Participant participant);
    }
}