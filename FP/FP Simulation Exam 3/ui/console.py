class Console:
    def __init__(self, valid, service):
        self.__valid = valid
        self.__service = service

    def run(self):
        print("Alege una dintre cerinte:")
        print("1 - afisarea pe ecran a concurentilor nascuti dupa un an dat de la tastatura.")
        print("2 - afisarea clasamentului pe tari, cu punctajul aferent fiecare tari.")
        print("exit - iesire program")
        while True:
            print(">>>", end="")
            cmd = input()
            if cmd == "1":
                an = input("Dati anul: ")
                try:
                    self.__valid.valideaza_an(an)
                    print(self.__service.cerinta_1(int(an)))
                except ValueError:
                    print("An invalid!")
            elif cmd == "2":
                lista_rez = self.__service.cerinta_2()
                for rasp in lista_rez:
                    print(f"{rasp[0]}: {rasp[1]}")
            elif cmd == "exit":
                print("Ati iesit din program.")
                return
            else:
                print("Comanda invalida!")