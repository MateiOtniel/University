using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.entities {
    internal class Meci {

        private int id;

        private Echipa echipa1;

        private Echipa echipa2;

        private DateTime date;

        public Meci(int id, Echipa echipa1, Echipa echipa2, DateTime date) {
            this.id = id;
            this.echipa1 = echipa1;
            this.echipa2 = echipa2;
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public Echipa GetEchipa1() {
            return echipa1;
        }

        public Echipa GetEchipa2() {
            return echipa2;
        }

        public DateTime GetDate() {
            return date;
        }

        public string ToString() {
            return id + " " + echipa1.ToString() + " " + echipa2.ToString() + " " + date.ToString();
        }
    }
}
