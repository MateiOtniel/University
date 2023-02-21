using NBA.entities;
using NBA.repository.abstracts;

namespace NBA.repository.filerepository {
    internal class FileJucatorActivRepository : AbstractGenericRepository<JucatorActiv>{
        private string filepath;

        public FileJucatorActivRepository(string filepath) {
            this.filepath = filepath;
            Read();
        }

        public void Insert(JucatorActiv obj) {
            base.Insert(obj);
            Save();
        }

        public void Delete(JucatorActiv jucator) {
            base.Delete(jucator);
            Save();
        }

        public override JucatorActiv GetById(int id)
            => list.FirstOrDefault(predicate: x => x.getIdJucator().Equals(id));

        public override void Save() {
            using(StreamWriter writer = new StreamWriter(filepath)) {
                foreach(JucatorActiv jucator in list) {
                    writer.WriteLine(jucator.ToString());
                }
            }
        }

        private void Read() {
            string[] lines = File.ReadAllLines(filepath);
            foreach(string line in lines) {
                string[] vs = line.Split(new char[] { ' ' },
                    StringSplitOptions.RemoveEmptyEntries);
                if(vs.Length > 0)
                    list.AddLast(new JucatorActiv(Int32.Parse(vs[0]), Int32.Parse(vs[1]),
                        Int32.Parse(vs[2]), vs[3]));
            }
        }

    }
}
