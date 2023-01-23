from tests.tests import Tests
from validation.validation import Validation
from repository.repository import Repository
from infrasctructure.services import Services
from ui.ui import Console
if __name__ == '__main__':

    validator = Validation()
    repo = Repository("datas/datas.txt")
    services = Services(repo)

    test = Tests()
    test.run_tests()

    ui = Console(validator, services)
    ui.run()