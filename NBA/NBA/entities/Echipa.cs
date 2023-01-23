using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.entities {
    internal class Echipa {
        private int id;
        private string nume;

        public Echipa(int id, string nume) {
            this.id = id;
            this.nume = nume;
        }
        public int getId() {
            return id;
        }
        public string getNume() {
            return nume;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setNume(string nume) {
            this.nume = nume;
        }

        public override string ToString() {
            return this.id + " " + this.nume;
        }
    }
}
