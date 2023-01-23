using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.entities {
    internal class Jucator : Elev {

        private Echipa echipa;

        public Jucator(int id, string nume, string scoala, Echipa echipa) : base(id, nume, scoala) {
            this.echipa = echipa;
        }

        public Echipa GetEchipa() {
            return echipa;
        }

        public int GetIdEchipa() {
            return echipa.getId();
        }

        public override string ToString() {
            return base.ToString() + " " + echipa.ToString();
        }
    }
}
