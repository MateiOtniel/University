#include "test.h"
#include "ui.h"
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
int main(){
	{
		test_all();
		RepoMedicamente repo;
		Service srv{repo};
		Validator validator;
		Console c{srv, validator};
		c.consola();
	}
	_CrtDumpMemoryLeaks();
}