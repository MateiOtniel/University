using NBA.repository.abstracts;
using NBA.entities;
using NBA.repository.filerepository;
using System.Linq;
using NBA.constants;
using NBA.constants.exceptions;

namespace NBA.service {
    internal class Service {
        private FileJucatorRepository fileJucatorRepository;
        
        private FileMeciRepository fileMeciRepository;

        private FileJucatorActivRepository fileJucatorActivRepository;

        public Service() {
            fileJucatorRepository = new FileJucatorRepository(@"C:\Users\OTI\OneDrive\Desktop\Facultate\Materii\MAP\NBA\NBA\data\jucatori.txt");
            fileMeciRepository = new FileMeciRepository(@"C:\Users\OTI\OneDrive\Desktop\Facultate\Materii\MAP\NBA\NBA\data\meciuri.txt");
            fileJucatorActivRepository = new FileJucatorActivRepository(@"C:\Users\OTI\OneDrive\Desktop\Facultate\Materii\MAP\NBA\NBA\data\jucatoriactivi.txt");
        }

        internal IEnumerable<JucatorActiv> getAllJucatoriActivi() {
            return fileJucatorActivRepository.GetAll();
        }

        internal IEnumerable<Meci> getAllMeciuri() {
            return fileMeciRepository.GetAll();
        }

        internal IEnumerable<Jucator> getAllJucatori() {
            return fileJucatorRepository.GetAll();
        }

        public void Cerinta1(int idEchipa) {
            IEnumerable<Jucator> jucatori = fileJucatorRepository.GetAll()
                 .Where(x => x.GetIdEchipa() == idEchipa);
            foreach(Jucator jucator in jucatori)
                Console.WriteLine(jucator.ToString());
        }

        public void Cerinta2(int idEchipa, int idMeci) {
            Meci meci = fileMeciRepository.GetById(idMeci);
            if(meci == null)
                throw new NullException("Nu exista meci cu acest id!");
            JucatoriActiviDinMeci(idEchipa, idMeci, meci);
        }

        private void JucatoriActiviDinMeci(int idEchipa, int idMeci, Meci meci) {
            Echipa echipa;
            if(meci.GetEchipa1().getId() == idEchipa)
                echipa = meci.GetEchipa1();
            else if(meci.GetEchipa2().getId() == idEchipa)
                echipa = meci.GetEchipa2();
            IEnumerable<JucatorActiv> jucatoriActivi = fileJucatorActivRepository.GetAll()
                .Where(x => x.getIdMeci() == idMeci
                 && fileJucatorRepository.GetById(x.getIdJucator()).GetIdEchipa() == idEchipa
                 && x.GetTip() == Tip.Participant);
            List<Jucator> jucatori = new List<Jucator>();
            foreach(JucatorActiv jucatorActiv in jucatoriActivi)
                jucatori.Add(fileJucatorRepository.GetById(jucatorActiv.getIdJucator()));
            foreach(Jucator jucator in jucatori)
                Console.WriteLine(jucator.ToString());
        }

        public void Cerinta3(string data1, string data2) {
            IEnumerable<Meci> meciuri = fileMeciRepository.GetAll();
            foreach(Meci meci in meciuri)
                if(IsBetweenDates(meci, data1, data2))
                    Console.WriteLine(meci.ToString());
        }

        private bool IsBetweenDates(Meci meci, string data1, string data2){
            string[] ldata1 = data1.Split("/");
            string[] ldata2 = data2.Split("/");
            DateTime date1 = new DateTime(int.Parse(ldata1[2]), int.Parse(ldata1[1]), int.Parse(ldata1[0]), 0, 0, 0);
            DateTime date2 = new DateTime(int.Parse(ldata2[2]), int.Parse(ldata2[1]), int.Parse(ldata2[0]), 12, 0, 0);
            if(date1 <= meci.GetDate() && meci.GetDate() <= date2)
                return true;
            return false;
        }

        public void Cerinta4(int idMeci) {
            Meci meci = fileMeciRepository.GetById(idMeci);
            if(meci == null)
                throw new NullException("Nu exista meci cu acest id!");
            else {
                Echipa echipa1 = meci.GetEchipa1();
                Echipa echipa2 = meci.GetEchipa2();
                List<Jucator> jucatoriEchipa1 = fileJucatorRepository.GetAll()
                    .Where(x => x.GetIdEchipa() == echipa1.getId())
                    .ToList();
                List<Jucator> jucatoriEchipa2 = fileJucatorRepository.GetAll()
                   .Where(x => x.GetIdEchipa() == echipa2.getId())
                   .ToList();
                FindScore(jucatoriEchipa1, jucatoriEchipa2, idMeci);
                
            }
        }

        private void FindScore(List<Jucator> jucatoriEchipa1, List<Jucator> jucatoriEchipa2, int idMeci) {
            int scor1 = 0, scor2 = 0;
            foreach(Jucator jucator in jucatoriEchipa1) {
                JucatorActiv jucatorActiv = fileJucatorActivRepository.GetById(jucator.getId());
                if(jucatorActiv != null && jucatorActiv.getIdMeci() == idMeci) {
                    scor1 += jucatorActiv.NrPuncteInscrise();
                }
             }
            foreach(Jucator jucator in jucatoriEchipa2) {
                JucatorActiv jucatorActiv = fileJucatorActivRepository.GetById(jucator.getId());
                if(jucatorActiv != null && jucatorActiv.getIdMeci() == idMeci) {
                    scor2 += jucatorActiv.NrPuncteInscrise();
                }
            }
            Console.WriteLine(scor1 + "-" + scor2);
        }
    }
}
