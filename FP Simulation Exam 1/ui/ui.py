class Console(object):

    def __init__(self, service):
        self.__service = service

    def run(self):
        print("Alege una din cerinte")
        print("1 - Afișarea tuturor cartilor care se termina intr-un anumit șir de caractere citit de la tastatură, ordonate crescator după anul publicarii.")
        print("2 - Un elev poate realiza un imprumut. Sa se afiseze toate imprumuturile care au durata imprumutului un numar dat de la tastatura.")
        print("exit - iesire program")
        while True:
            cmd = input(">>> ")
            if cmd == "1":
                sir = input("Dati sirul: ")
                print(self.__service.cerinta_1(sir))
            elif cmd == "2":
                try:
                    imprumut = input("Alegeti nr. de zile pt. imprumut: ")
                    imprumut = int(imprumut)
                    if imprumut < 0:
                        raise ValueError
                    print(self.__service.cerinta_2(imprumut))
                except ValueError:
                    print("Imprumut invalid!")
            elif cmd == "exit":
                print("Ati iesit din program!")
                break
            else:
                print("Comanda invalida!")