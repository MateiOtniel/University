using NBA.constants;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.entities {
    internal class JucatorActiv {
        
        private int idJucator;

        private int idMeci;

        private int nrPuncteInscrise;

        private Tip tip;

        public JucatorActiv(int idJucator, int idMeci, int nrPuncteInscrise, String tip) {
            this.idJucator = idJucator;
            this.idMeci = idMeci;
            this.nrPuncteInscrise = nrPuncteInscrise;
            if(tip.Equals("Rezerva"))
                this.tip = Tip.Rezerva;
            else if(tip.Equals("Participant"))
                this.tip = Tip.Participant;
        }

        public int getIdJucator() {
            return idJucator;
        }

        public int getIdMeci() {
            return idMeci;
        }

        public int NrPuncteInscrise() {
            return nrPuncteInscrise;
        }

        public Tip GetTip() {
            return tip;
        }

        public override string ToString() {
            return idJucator + " " + idMeci + " " + nrPuncteInscrise + " " + tip;
        }
    }
}
