class Helicopter(object):
    def __init__(self, id, nume, scopuri, an):
        self.__id = id
        self.__nume = nume
        self.__scopuri = scopuri
        self.__an = an

    def get_id(self):
        return int(self.__id)

    def get_nume(self):
        return self.__nume

    def get_scopuri(self):
        return self.__scopuri

    def get_an(self):
        return int(self.__an)

    def __str__(self):
        lista = self.get_scopuri()
        return f"Id: {self.get_id()} Nume: {self.get_nume()} Scopuri: {' '.join(lista)} an: {self.get_an()}"