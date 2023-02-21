from basket_coach.console.ui import Console
from basket_coach.validation.validator import Validator
from basket_coach.repository.repository import Repository
from basket_coach.domain.service import Service
from basket_coach.test.test import Tests
if __name__ == '__main__':

    validator = Validator()
    repository = Repository("datas/players")

    service = Service(repository)
    test = Tests()
    test.run_all_tests()

    console = Console(service, validator)
    console.run_all()