class Validation(object):

    def verifica_scop(self, scop):
        """
        Functia verifica daca scopul este valid.
        :param scop:
        :return:
        """
        if scop != "militar" and scop != "medical" and scop != "agricultura" and scop != "vacanta" and scop != "livrare":
            raise ValueError("Scop invalid!")
        return 1