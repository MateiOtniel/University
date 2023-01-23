#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>
#include "ui.h"
#include "test.h"
#include "service.h"
#include "enitities.h"

int main(){
    v = createDynamicVector();
    generate_expenses();
    test_all();
    console();
    destruct();
    _CrtDumpMemoryLeaks();
}
