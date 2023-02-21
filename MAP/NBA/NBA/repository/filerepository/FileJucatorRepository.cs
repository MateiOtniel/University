using NBA.entities;

namespace NBA.repository.abstracts {
    internal class FileJucatorRepository : AbstractGenericRepository<Jucator>{

        private string filepath;

        public FileJucatorRepository(string filepath) {
            this.filepath = filepath;
            Read();
        }

        public void Insert(Jucator obj) {
            base.Insert(obj);
            Save();
        }

        public void Delete(Jucator jucator) {
            base.Delete(jucator);
            Save();
        }

        public override Jucator GetById(int id)
            => list.FirstOrDefault(predicate: x => x.getId().Equals(id));

        public override void Save() {
            using(StreamWriter writer = new StreamWriter(filepath)) {
                foreach(Jucator jucator in list) {
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
                    list.AddLast(new Jucator(Int32.Parse(vs[0]), vs[1], vs[2],
                        new Echipa(Int32.Parse(vs[3]), vs[4])));
            }
        }

    }
}

