using NBA.constants;
using NBA.constants.exceptions;
using NBA.entities;
using NBA.service;
using NBA.validators;

namespace NBA.ui {
    internal class UI {
        Service service;

        public UI(Service service) {
            this.service = service;
        }

        public void Run() {
            Info();
            while(true) {
                Console.Write(">>> ");
                string cmd = Console.ReadLine();
                if(cmd.Equals("1"))
                    Cerinta1();
                else if(cmd.Equals("2"))
                    Cerinta2();
                else if(cmd.Equals("3"))
                    Cerinta3();
                else if(cmd.Equals("4"))
                    Cerinta4();
                else if(cmd.Equals("0"))
                    return;
                else
                    Console.WriteLine("Comanda invalida...");
            }
            
        }

        private void Cerinta1() {
            Console.Write("Dati Id-ul echipei: ");
            string cmd = Console.ReadLine();
            try {
                InputValidator inputValidator = new InputValidator();
                inputValidator.validate(cmd);
                IEnumerable<Jucator> jucatori = new LinkedList<Jucator>();
                service.Cerinta1(int.Parse(cmd));
            } catch (InvalidInputException e) {
                Console.WriteLine(e.Message);
            }
           }

        private void Cerinta2() {
            try {
                InputValidator inputValidator = new InputValidator();
                Console.Write("Dati id-ul echipei: ");
                string idEchipa = Console.ReadLine();
                Console.Write("Dati id-ul meciului: ");
                string idMeci = Console.ReadLine();
                inputValidator.validate(idMeci);
                inputValidator.validate(idEchipa);
                service.Cerinta2(int.Parse(idEchipa), int.Parse(idMeci));
            } catch(InvalidInputException e) {
                Console.WriteLine(e.Message);
            } catch(NullException e) {
                Console.WriteLine(e.Message);
            }
        }

        private void Cerinta3() {
            try {
                Console.Write("Dati prima data calendaristica(dd/mm/yyyy): ");
                string data1 = Console.ReadLine();
                Console.Write("Dati a doua data calendaristica(dd/mm/yyyy): ");
                string data2 = Console.ReadLine();
                DateValidator dateValidator = new DateValidator();
                dateValidator.validate(data1);
                dateValidator.validate(data2);
                service.Cerinta3(data1, data2);
            } catch(InvalidInputException e) {
                Console.WriteLine(e.Message);
            }
        }

        private void Cerinta4() {
            try {
                Console.Write("Dati id-ul meciului: ");
                string idMeci = Console.ReadLine();
                InputValidator inputValidator = new InputValidator();
                inputValidator.validate(idMeci);
                service.Cerinta4(int.Parse(idMeci));
            } catch(InvalidInputException e) {
                Console.WriteLine(e.Message);
            } catch(NullException e) {
                Console.WriteLine(e.Message);
            }
        }

        private void Info() {
            Console.WriteLine("1 - Sa se afiseze toti jucatorii unei echipe date");
            Console.WriteLine("2 - Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci");
            Console.WriteLine("3 - Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica");
            Console.WriteLine("4 - Sa se determine si sa se afiseze scorul de la un anumit meci");
            Console.WriteLine("0 - iesire");
        }
    }
}
