using NBA.entities;
using NBA.repository.abstracts;

namespace NBA.repository.filerepository {
    internal class FileMeciRepository : AbstractGenericRepository<Meci> {

        private string filepath;

        public FileMeciRepository(string filepath) {
            this.filepath = filepath;
            Read();
        }

        public void Insert(Meci meci) {
            base.Insert(meci);
            Save();
        }

        public void Delete(Meci meci) {
            base.Delete(meci);
            Save();
        }

        public override Meci GetById(int id)
            => list.FirstOrDefault(predicate: x => x.getId().Equals(id));

        public override void Save() {
            using(StreamWriter writer = new StreamWriter(filepath)) {
                foreach(Meci meci in list) {
                    writer.WriteLine(meci.ToString());
                }
            }
        }

        private void Read() {
            string[] lines = File.ReadAllLines(filepath);
            foreach(string line in lines) {
                string[] vs = line.Split(new char[] { ' ' },
                    StringSplitOptions.RemoveEmptyEntries);
                if(vs.Length > 0)
                    list.AddLast(new Meci(Int32.Parse(vs[0]), new Echipa(Int32.Parse(vs[1]), vs[2])
                        , new Echipa(Int32.Parse(vs[3]), vs[4]),
                        DateTime.Parse(vs[5] + " " + vs[6] + " " + vs[7])));
            }
        }
    }
}
