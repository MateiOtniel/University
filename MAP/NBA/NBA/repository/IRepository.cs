using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.repository {
    internal interface IRepository<T> where T : class {
        IEnumerable<T> GetAll();
        void Insert(T obj);
        void Delete(T obj);
        void Save();
        T GetById(int id);
    }
}
