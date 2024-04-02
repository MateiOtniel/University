using System.Collections.Generic;
using model;

namespace persistance.Interfaces{
    public interface IRepository<ID, T> where T : Entity<ID>{
        
        void Add(T entity);

        void Remove(T entity);

        void RemoveById(int id);

        List<T> GetAll();

        T GetById(ID id);
    }
}