using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.entities {
    internal class Elev {
        protected int id;
        protected string nume;
        protected string scoala;

        public Elev(int id, string nume, string scoala) {
            this.id = id;
            this.nume = nume;
            this.scoala = scoala;
        }

        public int getId() {
            return id;
        }

        public string getNume() {
            return nume;
        }

        public string getScoala() {
            return scoala;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNume(string nume) {
            this.nume = nume;
        }

        public void setScoala(string scoala) {
            this.scoala = scoala;
        }

        public override string ToString() {
            return this.id + " " + this.nume + " " + this.scoala;
        }
    }
}
