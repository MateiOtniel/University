from simulare_test.entities.entities import Helicopter
class Console(object):

    def __init__(self, validator, services):
        self.__validator = validator
        self.__services = services


    def run(self):
        """
        Se citesc datele introduse de la utilizator si se rezolva corespunzator cerintei.
        :return:
        """
        print("Helicopter, Helicopter")
        print("Alegeti una dintre cerintele de mai jos:")
        print("1 - Afisarea tuturor elicopterelor care contin un anumit scop citit de la tastatura, ordonate descrescator dupa nume.")
        print("2 - Gruparea elicopterelor dupa scopuri: se afiseaza scopul si anul fabricatiei.")
        print("exit - iesire program")
        while True:
            print(">>>", end="")
            cmd = input()
            if cmd == "1":
                print("Dati scopul:", end=" ")
                scop = input()
                try:
                    self.__validator.verifica_scop(scop)
                    lista_rez = self.__services.cerinta_1(scop)
                    self.__afisare_cerinta_1(lista_rez)
                except ValueError as ve:
                    print(str(ve))
            elif cmd == "2":
                print(self.__services.cerinta_2())
            elif cmd == "exit":
                print("Ati iesit din program!")
                return
            else:
                print("Comanda invalida!")

    def __afisare_cerinta_1(self, lista_rez):
        if not lista_rez:
            print("Nu exista elicoptere cu scopul dat.")
        for helicopter in lista_rez:
            lista = helicopter.get_scopuri()
            print(f"Id: {helicopter.get_id()}; Nume: {helicopter.get_nume()}; Scopuri: {' '.join(lista)}; an: {helicopter.get_an()}")