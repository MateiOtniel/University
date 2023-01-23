namespace NBA.repository.abstracts {
    internal abstract class AbstractGenericRepository<T> : IRepository<T> where T : class {
        protected LinkedList<T> list;

        public AbstractGenericRepository() {
            list = new LinkedList<T>();
        }

        public IEnumerable<T> GetAll() {
            return list;
        }

        public abstract T GetById(int id);

        public void Insert(T obj) {
            foreach (T value in list) {
                if(value.Equals(obj))
                    throw new Exception("Already exists!");
            }
            list.AddLast(obj);
        }

        public void Delete(T obj) {
            if(list.Remove(obj) == false)
                throw new Exception("Doesn't exist!");
        }

        public abstract void Save();

    }
}
