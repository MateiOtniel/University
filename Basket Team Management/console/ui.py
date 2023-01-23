class Console(object):

    def __init__(self, service, validator):
        self._service = service
        self._validator = validator

    def run_all(self):
        print("Alege cerinta:")
        print("1 - adauga jucator")
        print("2 - modifica inaltime jucator")
        print("3 - filtrare echipa cu inaltimea cea mai mare")
        print("4 - importare jucator din fisier")
        print("exit - iesire program")
        while True:
            cmd = input(">>> ")
            if cmd == "1":
                self.__citeste_player()
            elif cmd == "2":
                self.__citeste_modificari()
            elif cmd == "3":
                print(self._service.filtrare())
            elif cmd == "4":
                self.__citeste_fisier()
            elif cmd == "exit":
                print("Ati iesit din program!")
                return
            else:
                print("Comanda invalida!")

    def __citeste_player(self):
        """
        Functia citeste datele playerului si le trimite spre validare pt. a putea fi creat.
        :return:
        """
        try:
            prenume = input("Dati prenume: ")
            nume = input("Dati nume: ")
            post = input("Dati post: ")
            inaltime = input("Dati inaltime: ")
            self._validator.valideaza_player(prenume, nume, post, inaltime)
            self._service.creaza_player(prenume, nume, post, inaltime)
        except AttributeError as ae:
            print(str(ae))

    def __citeste_modificari(self):
        """
        Functia citeste toate datele de modificare a inaltimii playerului.
        :return:
        """
        try:
            prenume = input("Dati prenume: ")
            nume = input("Dati nume: ")
            inaltime = input("Dati noua inaltime: ")
            self._validator.valideaza_date(prenume, nume, inaltime)
            self._service.modifica_player(prenume, nume, inaltime)
        except AttributeError as ae:
            print(str(ae))

    def __citeste_fisier(self):
        """
        Functia citeste fisierul de la tastatura.
        :return:
        """
        fisier = input("Dati numele fisierului: ")
        fisier.strip()
        fisier = f"C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\basket_coach\\datas\\{fisier}"
        self._service.adauga_playeri_fisier(fisier)
