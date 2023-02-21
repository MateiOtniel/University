class Carti(object):
    def __init__(self, id, nume, autor, an):
        self.__id = id
        self.__nume = nume
        self.__autor = autor
        self.__an = an

    def get_id(self):
        return int(self.__id)

    def get_nume(self):
        return self.__nume

    def get_autor(self):
        return self.__autor

    def get_an(self):
        return int(self.__an)

    def __str__(self):
        return f"{self.__id} {self.__nume} {self.__autor} {self.__an}"

class Imprumut(object):
    def __init__(self, id, id_carte, data, durata):
        self.__id = id
        self.__id_carte = id_carte
        self.__data = data
        self.__durata = durata

    def get_id(self):
        return int(self.__id)

    def get_id_carte(self):
        return int(self.__id_carte)

    def get_data(self):
        return self.__data

    def get_durata(self):
        return int(self.__durata)

    def __str__(self):
        return f"{self.__id} {self.__id_carte} {self.__data} {self.__durata}"