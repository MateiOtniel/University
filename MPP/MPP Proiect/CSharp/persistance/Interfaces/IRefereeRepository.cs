using model;

namespace persistance.Interfaces{
    public interface IRefereeRepository : IRepository<int, Referee>{
        Referee GetRefereeByEmailAndPassword(string email, string password);
    }
}