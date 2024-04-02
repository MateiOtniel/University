using System.Collections.Generic;
using model;

namespace services{
    public interface IServices{
        Referee Login(Referee referee, IObserver client);

        IEnumerable<Participant> GetAllParticipantsSortedByName();

        void AddTest(Test test);

        IEnumerable<Participant> GetAllActiveParticipants(int refereeId);
        
        void Logout(Referee referee);
    }
}